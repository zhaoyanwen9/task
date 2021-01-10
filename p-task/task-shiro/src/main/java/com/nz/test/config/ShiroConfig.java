package com.nz.test.config;

import com.nz.test.secutity.KickoutSessionControlFilter;
import com.nz.test.secutity.MyShiroRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Subject: 主体
 * SecurityManager: 安全管理器(相当于 SpringMVC 中的 DispatcherServlet 或者 Struts2 中的 FilterDispatcher),管理着所有 Subject、且负责进行认证和授权、及会话、缓存的管理
 * Authenticator: 认证器
 * Authrizer: 授权器
 * Realm: 安全实体数据源(可以是 JDBC 实现，也可以是 LDAP 实现，或者内存实现)
 * SessionManager: 会话管理器
 * SessionDAO: 控制 session 存储的位置
 * CacheManager: 缓存管理器
 * Cryptography: 密码模块
 */
@Configuration // Shiro的配置类能被启动类加载到
public class ShiroConfig {

    private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    /**
     * 01 为了保证实现了Shiro内部lifecycle函数的bean执行,也是shiro的生命周期
     * LifecycleBeanPostProcessor: Shiro生命周期处理器
     * BeanFactory > AutowireCapableBeanFactory > applyBeanPostProcessorsBeforeInitialization 和 applyBeanPostProcessorsAfterInitialization对应执行的是LifecycleBeanPostProcessor(shiro): postProcessBeforeInitialization(shiro)、postProcessAfterInitialization(shiro)、postProcessBeforeDestruction(shiro) > DestructionAwareBeanPostProcessor(spring) > BeanPostProcessor(spring)
     * LifecycleBeanPostProcessor将Initializable和Destroyable的实现类统一在其内部自动分别调用了Initializable.init()和Destroyable.destroy()方法,从而达到管理shiro bean生命周期的目的
     */
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        logger.info("#### 1. Shiro生命周期处理器: register LifecycleBeanPostProcessor");
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 02. 依赖于01 -> LifecycleBeanPostProcessor 自动创建代理，没有这个鉴权可能会出错
     * DefaultAdvisorAutoProxyCreator 实现了 BeanProcessor（利用反射实例化对象，完成bean 的实例化）接口，ApplicationContext 读取完所有的Bean 配置信息后，这个类扫描上下文寻找所有的 Advisor（一个切入点和一个通知的组成）然后把这些 Advisor 应用到所有符合切入点的 Bean中
     * depend-on 指定了实现哪个 BeanProcessor 接口
     * <p>
     * BeanPostProcessor在 Spring 完成 Bean 的实例化、配置和其他初始化之后，如果想要添加一些自定义的逻辑处理（因此BeanPostProcessor也称为Bean后置处理器） 就需要实现一个或者多个 BeanPostProcessor 接口，注册到Spring容器中。
     * 实现 BeanPostProcessor 主要是两个方法：postProcessBeforeInitialization和postProcessAfterInitialization
     *
     * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        logger.info("#### 2. 扫描上下文(DefaultAdvisorAutoProxyCreator)必须在lifecycleBeanPostProcessor创建之后创建");
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * SecurityManager是Shiro框架的核心,典型的Facade模式(外观（Facade）模式又叫作门面模式)
     * SecurityManager > 登录-login, 注销-logout, 获取Subject对象-createSubject.
     * 继承: 接口Authorizer(认证器), SessionManager(会话管理器), Authenticator(授权器)
     * 实现类:
     * CacheSecurityManager:这个抽象类注入了缓存相关的东西
     * RealmSecurityManager:这个抽象类实现了Realm(数据源)开关的功能
     * AuthenticatingSecurityManager:这个抽象类实现了认证的部分功能，支持使SecurityManager封装一个Authenticator实例，把对验证的操作都委托给该实例
     * AuthorizingSecurityManager:这个抽象类只是单纯的继承,没有实现任何接口，完成授权部分功能。
     * SessionsSecurityManager:这个抽象类注入了会话管理器，只是单纯的继承,没有实现，支持使SecurityManager封装一个sessionManager实例，把对session的操作都委托给该实例。
     * DefaultSecurityManager: 这个抽象类是非web环境的安全管理器，只是单纯的继承,没有实现
     * DefaultWebSecurityManager: 这个抽象类默认的继承web层次的安全管理器，除了继承之外,还实现了一个接口,这个接口的功能就是判断当前是否是web环境
     *
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        logger.info("#### 3. 配置核心安全事务管理器SecurityManager(外观模式)");
        // SecurityManager默认实例的是DefaultSecurityManager
        // DefaultSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        // 管理着所有 Subject、且负责进行认证和授权、及会话、缓存的管理
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // Realm:域(安全数据源)
        securityManager.setRealm(myShiroRealm("3.1"));
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager("3.2"));
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager("3.3"));
        return securityManager;
    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了）
     *
     * @return
     */
    // @Bean
    // public HashedCredentialsMatcher hashedCredentialsMatcher(String step) {
    //     logger.info("{} HashedCredentialsMatcher", step);
    //     HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
    //     //散列算法:这里使用MD5算法;
    //     hashedCredentialsMatcher.setHashAlgorithmName("md5");
    //     //散列的次数，比如散列两次，相当于 md5(md5(""));
    //     hashedCredentialsMatcher.setHashIterations(1024);
    //     return hashedCredentialsMatcher;
    // }

    /**
     * 自定义权限验证对象
     *
     * @return
     */
    @Bean
    public MyShiroRealm myShiroRealm(String step) {
        logger.info("{} 配置自定义的权限登录器", step);
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        // myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher(step));
        // myShiroRealm.setAuthorizationCacheName("");
        return myShiroRealm;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisCacheManager cacheManager(String step) {
        logger.info("{} RedisCacheManager", step);
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager(step));
        // //设置前缀
        redisCacheManager.setKeyPrefix("SPRINGBOOT_CACHE:");
        return redisCacheManager;
    }

    /**
     * SessionManager
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public DefaultWebSessionManager sessionManager(String step) {
        SimpleCookie simpleCookie = new SimpleCookie("Token");
        simpleCookie.setPath("/");
        simpleCookie.setHttpOnly(false);
        logger.info("{} DefaultWebSessionManager", step);
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO(step));
        // shiro 的session默认放在cookie中 禁用
        // sessionManager.setSessionIdCookieEnabled(false);
        // 禁用url 重写 url; shiro请求时默认 jsessionId=id
        // sessionManager.setSessionIdUrlRewritingEnabled(false);
        // sessionManager.setDeleteInvalidSessions(false);
        // sessionManager.setSessionIdCookie(simpleCookie);
        return sessionManager;
    }

    /**
     * 自定义sessionManager
     * @param step
     * @return
     */
    // @Bean
    // public SessionManager sessionManagerSelfDefine(String step) {
    //     logger.info("{} SessionManager", step);
    //     MySessionManager mySessionManager = new MySessionManager();
    //     mySessionManager.setSessionDAO(redisSessionDAO(step));
    //     return mySessionManager;
    // }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO(String step) {
        logger.info("{} RedisSessionDAO", step);
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager(step));
        redisSessionDAO.setKeyPrefix("SPRINGBOOT_SESSION:");
        return redisSessionDAO;
    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisManager redisManager(String step) {
        logger.info("{} RedisManager", step);
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("localhost");
        redisManager.setPort(6379);
        // 配置缓存过期时间
        redisManager.setExpire(60);
        redisManager.setTimeout(0);
        // redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * Shiro过滤器配置
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        logger.info("#### 4. ShiroFilterFactoryBean ");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        /**
         * 身份认证失败,则跳转到登录页面的配置;
         * 没有登录的用户请求需要登录的页面时自动跳转到登录页面;
         * 不是必须的属性,不输入地址的话会自动寻找项目web项目的根目录下的”/login.jsp”页面。
         */
        shiroFilterFactoryBean.setLoginUrl("/auth/login");

        // 登录成功默认跳转页面,不配置则跳转至”/”。如果登陆前点击的一个需要登录的页面，则在登录自动跳转到那个需要登录的页面。不跳转到此。
        shiroFilterFactoryBean.setSuccessUrl("/auth/index");

        // 未授权界面; ----这个配置了没卵用，具体原因想深入了解的可以自行百度
        shiroFilterFactoryBean.setUnauthorizedUrl("/auth/403");

        //限制同一帐号同时在线的个数.
        Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
        filtersMap.put("kickout", kickoutSessionControlFilter("4.1"));
        shiroFilterFactoryBean.setFilters(filtersMap);

        //当运行一个Web应用程序时,Shiro将会创建一些有用的默认Filter实例,并自动地在[main]项中将它们置为可用自动地可用的默认的Filter实例是被DefaultFilter枚举类定义的,
        // 枚举的名称字段就是可供配置的名称

        // Shiro验证URL时,URL匹配成功便不再继续匹配查找(所以要注意配置文件中的URL顺序,尤其在使用通配符时)
        // 配置不会被拦截的链接 顺序判断
        // 权限控制map.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/auth/login", "anon");
        filterChainDefinitionMap.put("/auth/logout", "logout");
        filterChainDefinitionMap.put("/auth/kickout", "anon");
        filterChainDefinitionMap.put("/**", "authc,kickout");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 限制同一账号登录同时登录人数控制
     *
     * @return
     */
    @Bean
    public KickoutSessionControlFilter kickoutSessionControlFilter(String step) {
        logger.info("{} KickoutSessionControlFilter", step);
        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
        kickoutSessionControlFilter.setCacheManager(cacheManager(step));
        kickoutSessionControlFilter.setSessionManager(sessionManager(step));
        kickoutSessionControlFilter.setKickoutAfter(false);
        kickoutSessionControlFilter.setMaxSession(1);
        kickoutSessionControlFilter.setKickoutUrl("/auth/kickout");
        return kickoutSessionControlFilter;
    }

    /***
     * 使授权注解起作用不如不想配置可以在pom文件中加入
     * <dependency>
     *<groupId>org.springframework.boot</groupId>
     *<artifactId>spring-boot-starter-aop</artifactId>
     *</dependency>
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        logger.info("#### 5. 使授权注解起作用");
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
