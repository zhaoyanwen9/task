package com.nz.test.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class ShiroConfig {

    // 将自己的验证方式加入容器
    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        return myShiroRealm;
    }

    // 权限管理，配置主要是Realm的管理认证
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }

    // Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //加入自定义的filter
        Map<String, Filter> filterMap = shiroFilterFactoryBean.getFilters();
        filterMap.put("client", new ClientShiroThree());
        shiroFilterFactoryBean.setFilters(filterMap);

        // 登录
        shiroFilterFactoryBean.setLoginUrl("/test/login");
        // 首页
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuthorize");

        //定义拦截路径,记得将静态资源也排除过滤
        /*进行权限的控制,必须使用LinkHashMap,shrio要按照顺序进行设置*/
        Map<String, String> authMap = new LinkedHashMap<>();
        // 登出
        authMap.put("/logout", "logout");
        // swagger
        authMap.put("/swagger**/**", "anon");
        authMap.put("/webjars/**", "anon");
        authMap.put("/v2/**", "anon");
        // 对所有用户认证
        authMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(authMap);
        return shiroFilterFactoryBean;
    }

    // 加入注解的使用，不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}