package com.vladm.demoservlet.filter;

import com.vladm.demoservlet.dao.FileStorageUserDao;
import com.vladm.demoservlet.dao.UserDao;
import com.vladm.demoservlet.model.User;
import com.vladm.demoservlet.model.UserPrincipal;
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

    private final static Map<String, List<String>> ALLOW_URL_MAP = new HashMap<>();

    static {
        ALLOW_URL_MAP.put("GET", List.of("/index", "/", "/sign-up.jsp"));
        ALLOW_URL_MAP.put("POST", List.of("/user"));
    }

    // check url list where auth required :)
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        MutableHttpServletRequest req = new MutableHttpServletRequest((HttpServletRequest) servletRequest);

        List<String> allowUrls = ALLOW_URL_MAP.get(req.getMethod().toUpperCase());

        boolean allow = allowUrls.stream()
                .anyMatch(urlPatter -> req.getPath().equalsIgnoreCase(urlPatter));

        if(allow){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        var usr = (UserPrincipal) req.getUserPrincipal();

        if(usr != null) {
            Optional<User> dbUser = userDao.findByName(usr.getName());

            if(dbUser.isPresent() && dbUser.get().getPassword().equals(usr.getPassword())) {
                req.putHeader(ID, dbUser.get().getId());
                filterChain.doFilter(req, servletResponse);
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
