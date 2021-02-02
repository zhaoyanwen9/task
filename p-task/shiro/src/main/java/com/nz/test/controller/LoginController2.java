package com.nz.test.controller;

import com.nz.test.config.TokenUtil;
import com.nz.test.model.Role;
import com.nz.test.service.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/test")
public class LoginController2 {
    @Autowired
    private RoleService rolesMapper;

    @RequestMapping("/get")
    @ResponseBody
    public Object get() {
        List<Role> roles = rolesMapper.getAllRoles();
        return roles;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String notLogin() {
        return "login";
    }

    @RequestMapping("/main")
    @ResponseBody
    public String test() {
        return "main";
    }

    @RequestMapping(value = "/noAuthorize")
    @ResponseBody
    public String notRole() {
        return "您没有权限！";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        //注销
        subject.logout();
        return "成功注销！";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(String name, String password, HttpServletRequest request, HttpServletResponse response) {
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(name, password);
            subject.login(usernamePasswordToken);
            Role role = rolesMapper.findById(1L);
            String token = TokenUtil.getToken(role.getRoleName(), role.getId().toString(), request.getRemoteAddr());
            return token;
        } catch (Exception e) {
            return "error";
        }
    }
}
