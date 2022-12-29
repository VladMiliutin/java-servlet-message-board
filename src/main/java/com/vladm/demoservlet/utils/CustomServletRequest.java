package com.vladm.demoservlet.utils;

import com.vladm.demoservlet.model.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.security.Principal;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class CustomServletRequest extends HttpServletRequestWrapper {

    private final HttpServletRequest request;
    private final Map<String, String> customHeaders;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request the {@link HttpServletRequest} to be wrapped.
     * @throws IllegalArgumentException if the request is null
     */
    public CustomServletRequest(HttpServletRequest request) {
        super(request);
        this.request = request;
        this.customHeaders = new HashMap<>();
    }

    public static CustomServletRequest of(HttpServletRequest req) {
        return new CustomServletRequest(req);
    }
    /**
     * Returns relative path from URI
     * http://localhost:8080/demo_servlet_war/some-path -> /some-path
     */
    public String getPath() {
        return request.getRequestURI().substring(request.getContextPath().length());
    }

    public void putHeader(String headerName, String headerValue) {
        customHeaders.put(headerName, headerValue);
    }

    @Override
    public String getHeader(String name) {
        String headerValue = customHeaders.get(name);

        if(headerValue != null) {
            return headerValue;
        }

        return request.getHeader(name);
    }

    @Override
    public Principal getUserPrincipal() {
        String authHeader = getHeader("Authorization");

        String id = getHeader(RequestsConstants.ID);

        Principal principal = null;

        // starts with BASIC
        if(authHeader != null && authHeader.substring(0, 5).equalsIgnoreCase(HttpServletRequest.BASIC_AUTH)){
            // get dXNlcm5hbWU6cGFzc3dvcmQ=
            String userNameAndPassword = new String(Base64.getDecoder().decode(authHeader.substring(6)));
            // username:password
            String username = userNameAndPassword.split(":")[0];
            String password = userNameAndPassword.split(":")[1];

            principal = new UserPrincipal(id, username, password);
        }

        return principal;
    }

    public static String getUserId(HttpServletRequest request) {
        return ((UserPrincipal) (CustomServletRequest.of(request).getUserPrincipal())).getId();
    }
}
