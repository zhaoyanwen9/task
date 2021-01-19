package com.nz.test.config;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.nz.test.model.Permission;
import com.nz.test.model.Role;
import com.nz.test.model.User;
import com.nz.test.service.LoginService;

/**
 * 实现AuthorizingRealm接口用户用户认证
 *
 * @author Louis
 * @date Jun 20, 2019
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private LoginService loginService;

    /**
     * 用户认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        // 加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        System.out.println("########## 用户认证" + authenticationToken.getPrincipal());
        //从token中获取信息,此token只是shiro用于身份验证的,并非前端传过来的token.
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        String username = token.getUsername();

        User user = loginService.findByName(username);

        String password = new String(token.getPassword());
        if (null == password) {
            throw new AuthenticationException("doGetAuthenticationInfo中的用户名不对");
        } else if (!password.equals(new String(token.getPassword()))) {
            throw new AuthenticationException("doGetAuthenticationInfo中的密码不对");
        }
        //组合一个验证信息
        System.out.println("token.getPrincipal()默认返回的username======" + token.getPrincipal());
        System.out.println("getName()" + getName());

        if (authenticationToken.getPrincipal() == null) {
            return null;
        }

        if (user == null) {
            // 这里返回后会报出对应异常
            return null;
        } else {
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(token.getPrincipal(),password,getName());
            // 这里验证authenticationToken和simpleAuthenticationInfo的信息
            // SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, user.getPassword(), getName());
            return info;
        }
    }

    /**
     * 角色权限和对应权限添加
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取登录用户名
        String name = (String) principalCollection.getPrimaryPrincipal();
        // 查询用户名称
        User user = loginService.findByName(name);
        // 添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : user.getRoles()) {
            // 添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            for (Permission permission : role.getPermissions()) {
                // 添加权限
                simpleAuthorizationInfo.addStringPermission(permission.getPermission());
            }
        }
        return simpleAuthorizationInfo;
    }

}