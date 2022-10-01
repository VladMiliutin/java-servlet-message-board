package com.vladm.demoservlet.filter;

import com.vladm.demoservlet.dao.FileStorageUserDao;
import com.vladm.demoservlet.dao.UserDao;
import com.vladm.demoservlet.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

@WebFilter(urlPatterns = "/*")
public class AuthFilter extends HttpFilter {

    private final UserDao userDao = FileStorageUserDao.getInstance();
    private final static Base64.Decoder DECODER = Base64.getDecoder();

    // check url list where auth required :)
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        String authHeader = req.getHeader("Authorization");

        if(req.getRequestURI().contains("/login") || req.getRequestURI().contains("/index")){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if(authHeader != null && authHeader.substring(0, 5).equalsIgnoreCase(HttpServletRequest.BASIC_AUTH)){
            String usernameAndPassword = new String(DECODER.decode(authHeader.substring(6)));
            String username = usernameAndPassword.split(":")[0];
            String password = usernameAndPassword.split(":")[1];

            Optional<User> dbUser = userDao.findByName(username);

            if(dbUser.isPresent()) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                HttpServletResponse resp = (HttpServletResponse) servletResponse;
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        } else {
            HttpServletResponse resp = (HttpServletResponse) servletResponse;
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setHeader("WWW-Authenticate", "Basic");

        }
    }

}
