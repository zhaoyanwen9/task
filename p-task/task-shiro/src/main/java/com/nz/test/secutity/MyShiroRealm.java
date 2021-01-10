package com.nz.test.secutity;

import com.alibaba.fastjson.JSONObject;
import com.nz.test.dao.domain.SysUser;
import com.nz.test.service.SysRoleService;
import com.nz.test.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * 缓存功能,认证功能,授权功能
 * <p>
 * 1.首先将 MyShiroRealm 配置为Bean
 * 2.然后将 MyShiroRealm 注入到DefaultWebSecurityManager中
 *
 * @author nz
 */
public class MyShiroRealm extends AuthorizingRealm {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    /**
     * 如果项目中用到了事物，@Autowired注解会使事物失效，可以自己用get方法获取值
     */
    @Autowired
    private SysRoleService roleService;

    @Autowired
    private UserService userService;

    /**
     * 认证: 身份验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        String password = String.valueOf(token.getPassword());
        logger.info("---------- 执行Shiro 凭证认证(AuthenticationToken) {}, {}, {} ----------", authenticationToken.hashCode(), username, password);
        SysUser user = new SysUser();
        user.setUserName(username);
        user.setPassWord(password);
        SysUser userList = userService.getUser(user);
        if (userList != null) {
            if (userList.getUserEnable() != 1) {
                logger.info("#### 用户为禁用状态");
                throw new DisabledAccountException();
            }
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userList, userList.getPassWord(), getName());
            logger.info("---------- Shiro凭证认证成功(SimpleAuthenticationInfo): {} ----------", JSONObject.toJSON(authenticationInfo));
            return authenticationInfo;
        }
        throw new UnknownAccountException();
    }

    /**
     * 授权: 权限获取
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("---------- 执行 Shiro 权限获取(PrincipalCollection) ----------");
        Object principal = principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        if (principal instanceof SysUser) {
            SysUser userLogin = (SysUser) principal;
            Set<String> roles = roleService.findRoleNameByUserId(userLogin.getId());
            authorizationInfo.addRoles(roles);
            Set<String> permissions = userService.findPermissionsByUserId(userLogin.getId());
            authorizationInfo.addStringPermissions(permissions);
        }
        logger.info("#### 权限: {}", authorizationInfo.getStringPermissions().toString());
        logger.info("---------- Shiro 权限获取成功 ----------");
        return authorizationInfo;
    }

}

