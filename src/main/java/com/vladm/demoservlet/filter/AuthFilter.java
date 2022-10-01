package com.vladm.demoservlet.filter;

import com.vladm.demoservlet.dao.FileStorageUserDao;
import com.vladm.demoservlet.dao.UserDao;
import com.vladm.demoservlet.model.User;
import com.vladm.demoservlet.model.UserPrincipal;
import com.vladm.demoservlet.util.AuthUtils;
import com.vladm.demoservlet.util.MutableHttpServletRequest;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

import static com.vladm.demoservlet.model.RequestParams.ID;

@WebFilter(urlPatterns = "/*")
public class AuthFilter extends HttpFilter {

    private final UserDao userDao = FileStorageUserDao.getInstance();

    // check url list where auth required :)
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        if(req.getRequestURI().contains("/login") || req.getRequestURI().contains("/index")){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        UserPrincipal usr = AuthUtils.getUserInfoFromReq(req);
        if(usr != null) {
            Optional<User> dbUser = userDao.findByName(usr.getName());

            if(dbUser.isPresent() && dbUser.get().getPassword().equals(usr.getPassword())) {
                MutableHttpServletRequest mutatedReq = new MutableHttpServletRequest(req);
                mutatedReq.putHeader(ID, dbUser.get().getId());
                filterChain.doFilter(mutatedReq, servletResponse);
            } else {
                requireLogin((HttpServletResponse) servletResponse);
            }
        } else {
            requireLogin((HttpServletResponse) servletResponse);

        }
    }

    private static void requireLogin(HttpServletResponse resp) {
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        resp.setHeader("WWW-Authenticate", "Basic");
    }

}
