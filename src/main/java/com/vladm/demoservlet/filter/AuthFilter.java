package com.vladm.demoservlet.filter;

import com.vladm.demoservlet.model.User;
import com.vladm.demoservlet.model.UserPrincipal;
import com.vladm.demoservlet.service.UserService;
import com.vladm.demoservlet.utils.CustomServletRequest;
import com.vladm.demoservlet.utils.RequestsConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// filter all requests
@WebFilter(urlPatterns = "/*")
public class AuthFilter extends HttpFilter {

    private final UserService userService = UserService.getInstance();

    private final static Map<String, List<String>> ALLOW_METHOD_URL_MAP = new HashMap<>();

    static {
        ALLOW_METHOD_URL_MAP.put("GET", List.of("/index", "/", "", "/sign-up.jsp"));
        ALLOW_METHOD_URL_MAP.put("POST", List.of("/users"));
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        CustomServletRequest request = CustomServletRequest.of(req);

        List<String> allowUrls = ALLOW_METHOD_URL_MAP.get(request.getMethod().toUpperCase());

        boolean allow = allowUrls.stream()
                .anyMatch(urlPattern -> request.getPath().equalsIgnoreCase(urlPattern));

        if(allow) {
            chain.doFilter(req, res);
            return;
        }

        var userPrincipal = (UserPrincipal) request.getUserPrincipal();

        if(userPrincipal == null) {
            requireLogin(res);
            return;
        }

        Optional<User> dbUser = userService.findByName(userPrincipal.getName());

        if(dbUser.isPresent() && dbUser.get().getPassword().equals(userPrincipal.getPassword())) {
            request.putHeader(RequestsConstants.ID, dbUser.get().getId());
            chain.doFilter(request, res);
        } else {
            requireLogin(res);
        }
    }

    private static void requireLogin(HttpServletResponse res) {
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.setHeader("WWW-Authenticate", "Basic");
    }
}
