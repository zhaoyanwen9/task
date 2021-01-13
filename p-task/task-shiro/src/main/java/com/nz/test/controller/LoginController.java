package com.nz.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.nz.test.entity.SysUser;
import com.nz.test.utils.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Shiro是一个强大易用的Java安全框架，提供了认证、授权、加密和会话管理等功能,是目前用得比较多的登陆框架
 */
@Controller
@RequestMapping(value = "/auth")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * 登录 https://blog.csdn.net/myth_g/article/details/82113150
     *
     * @param username
     * @param password
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> submitLogin(@RequestParam(value = "username") String username,
                                           @RequestParam(value = "password") String password,
                                           HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            logger.info("#### 1. 入参 {}, {}, {}", username, password, request.getSession().getId());
            /**
             * Subject:绑定到SecurityManager,与Subject所有交互都会委托给SecurityManager,Subject是个门面，SecurityManager才是实际的执行者。
             * SecurityUtils:一个静态属性,用来存储当前应用中全局唯一的一个SecurityManager;三个静态方法,SecurityManager setter getter、getSubject
             */
            Subject subject = SecurityUtils.getSubject();
            if (!subject.isAuthenticated()) {
                Session session = subject.getSession();
                session.setAttribute("key", "value");
                logger.info("#### 2. Subject(外观模式-Facade) {}, {}", session.getId(), session.getTimeout());
                /**
                 * 1、应用程序构建了一个终端用户认证信息的AuthenticationToken 实例后，调用Subject.login方法。
                 * 2、Sbuject的实例通常是DelegatingSubject类（或子类）的实例对象，在认证开始时，会委托应用程序设置的securityManager实例调用securityManager.login(token)方法。
                 * 3、SecurityManager接受到token(令牌)信息后会委托内置的Authenticator的实例（通常都是ModularRealmAuthenticator类的实例）调用authenticator.authenticate(token). ModularRealmAuthenticator在认证过程中会对设置的一个或多个Realm实例进行适配，它实际上为Shiro提供了一个可拔插的认证机制。
                 * 4、如果在应用程序中配置了多个Realm，ModularRealmAuthenticator会根据配置的AuthenticationStrategy(认证策略)来进行多Realm的认证过程。在Realm被调用后，AuthenticationStrategy将对每一个Realm的结果作出响应。注：如果应用程序中仅配置了一个Realm，Realm将被直接调用而无需再配置认证策略。
                 * 5、判断每一个Realm是否支持提交的token，如果支持，Realm将调用getAuthenticationInfo(token); getAuthenticationInfo 方法就是实际认证处理，我们通过覆盖Realm的doGetAuthenticationInfo方法来编写我们自定义的认证处理
                 */
                UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
                // usernamePasswordToken.setRememberMe(true);
                logger.info("#### 3. UsernamePasswordToken {}, {}, {}", usernamePasswordToken.hashCode(), usernamePasswordToken.getPrincipal(), String.valueOf((char[]) usernamePasswordToken.getCredentials()));
                /**
                 * 执行登录 > doGetAuthenticationInfo
                 */
                subject.login(usernamePasswordToken);
                // 判断用户是否是拥有某种角色
                boolean isRole = subject.hasRole("admin");
                // 是否拥有某种功能
                boolean isPer = subject.isPermitted("xiaoming:run");
                // logger.info("当前用户: {} {} {}", subject.getPrincipals().getPrimaryPrincipal(), isRole, isPer);
                map.put("code", 200);
                map.put("message", "登录成功");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("token", subject.getSession().getId());
                map.put("data", jsonObject);
                try {
                    // 设置session过期时间
                    session.setTimeout(25000);
                    session.setAttribute("currentuser", username);
                } catch (Exception e) {
                    map.put("code", 204);
                    map.put("msg", "登录失败");
                    return map;
                }
            }

            map.put("code", 200);
            map.put("message", "登录成功");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", subject.getSession().getId());
            map.put("data", jsonObject);

            // 实体信息(Principals)
            SysUser user = (SysUser) subject.getPrincipal();
            logger.info("#### 实体: {}", JSONObject.toJSON(user));
            // 凭据信息(Credentials)
        } catch (DisabledAccountException e) {
            logger.info("#### 账户禁用");
            request.setAttribute("msg", "账户已被禁用");
            map.put("code", 205);
            map.put("msg", "账户已被禁用");
            return map;
        } catch (AuthenticationException e) {
            logger.info("#### 账户秘密有误");
            request.setAttribute("msg", "用户名或密码错误");
            request.setAttribute("msg", "账户已被禁用");
            map.put("code", 206);
            map.put("msg", "账户秘密有误");
            return map;
        }
        // 执行到这里说明用户已登录成功
        // logger.info("#### 登陆成功,重定向至:/auth/index");
        // return "redirect:/auth/index";
        return map;
    }

    /**
     * 登录
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        SysUser currentLoginUser = RequestUtils.currentLoginUser();
        logger.info("#### 跳转至登录页");
        return "login";
    }

    /**
     * 欢迎页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String loginSuccessMessage(HttpServletRequest request) {
        logger.info("#### /AUTH/INDEX");
        String username = "未登录";
        SysUser currentLoginUser = RequestUtils.currentLoginUser();
        if (currentLoginUser != null && StringUtils.isNotEmpty(currentLoginUser.getUserName())) {
            username = currentLoginUser.getUserName();
        } else {
            logger.info("#### 未登录 /auth/login");
            return "redirect:/auth/login";
        }
        request.setAttribute("username", username);
        logger.info("#### 登录成功,index页面 {}", JSONObject.toJSON(currentLoginUser));
        return "index";
    }

    /**
     * 被踢出后跳转的页面
     *
     * @return
     */
    @RequestMapping(value = "/kickout", method = RequestMethod.GET)
    public String kickOut() {
        logger.info("#### 跳转至踢出页");
        return "kickout";
    }
}
