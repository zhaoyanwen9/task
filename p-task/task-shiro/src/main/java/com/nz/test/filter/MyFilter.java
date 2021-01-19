package com.nz.test.filter;

import com.nz.test.entity.SysUser;
import com.nz.test.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 验证是否登陆
     */
    public boolean isAuthenticated(String sessionID, HttpServletRequest request, HttpServletResponse response) {
        boolean status = false;

        SessionKey key = new WebSessionKey(sessionID, request, response);
        try {
            Session se = SecurityUtils.getSecurityManager().getSession(key);
            Object obj = se.getAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY);
            if (obj != null) {
                status = (Boolean) obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Session se = null;
            Object obj = null;
        }

        return status;
    }

    /**
     * 获取用户登录信息
     */
    public SysUser getUserInfo(String sessionID, HttpServletRequest request, HttpServletResponse response) {
        boolean status = false;
        SessionKey key = new WebSessionKey(sessionID, request, response);
        try {
            Session se = SecurityUtils.getSecurityManager().getSession(key);
            Object obj = se.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            //org.apache.shiro.subject.SimplePrincipalCollection cannot be cast to com.hncxhd.bywl.entity.manual.UserInfo
            SimplePrincipalCollection coll = (SimplePrincipalCollection) obj;
            return (SysUser) coll.getPrimaryPrincipal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }

    /**
     * https://blog.csdn.net/qq_41018959/article/details/81001184
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String sessionId = request.getSession().getId();
        System.out.println("==============MyFilter," + request.getRequestURI() + ", (" + sessionId + ")");
        //执行
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,Content-Type,Origin,Authorization, X-File-Name,Cookie,Accept,Connection,User-agent");
        // /设置允许携带cookie信息
        response.setHeader("Access-Control-Allow-Credentials", "true");
        System.out.println("*********************************过滤器被使用**************************");
        //这里很重要，options请求，直接返回200，表示成功
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(200);
            return;
        }

        // boolean isLogin = isAuthenticated(sessionId, (HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse);
        // System.out.println("#### " + isLogin);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
