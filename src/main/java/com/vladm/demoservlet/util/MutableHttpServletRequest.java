package com.vladm.demoservlet.util;

import com.vladm.demoservlet.model.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.security.Principal;
import java.util.*;

import static com.vladm.demoservlet.model.RequestParams.ID;

public class MutableHttpServletRequest extends HttpServletRequestWrapper {
    private final Map<String, String> customHeaders;
    private final HttpServletRequest request;

    public MutableHttpServletRequest(HttpServletRequest request){
        super(request);
        this.customHeaders = new HashMap<>();
        this.request = request;
    }

    public void putHeader(String name, String value){
        this.customHeaders.put(name, value);
    }

    public String getHeader(String name) {
        String headerValue = customHeaders.get(name);

        if (headerValue != null){
            return headerValue;
        }
        return ((HttpServletRequest) getRequest()).getHeader(name);
    }

    public Enumeration<String> getHeaderNames() {
        Set<String> set = new HashSet<>(customHeaders.keySet());

        Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
        while (e.hasMoreElements()) {
            String n = e.nextElement();
            set.add(n);
        }

        return Collections.enumeration(set);
    }

    public String getPath() {
        return request.getRequestURI().substring(request.getContextPath().length());
    }

    @Override
    public Principal getUserPrincipal() {
        String authHeader = request.getHeader("Authorization");
        String id = null;
        if(request.getHeader(ID) != null){
            id = request.getHeader(ID);
        }

        Principal user = null;
        if(authHeader != null && authHeader.substring(0, 5).equalsIgnoreCase(HttpServletRequest.BASIC_AUTH)) {
            String usernameAndPassword = new String(Base64.getDecoder().decode(authHeader.substring(6)));
            String username = usernameAndPassword.split(":")[0];
            String password = usernameAndPassword.split(":")[1];

            user = new UserPrincipal(id, username, password);
        }

        return user;
    }

    public static String userId(HttpServletRequest req) {
        return ((UserPrincipal) new MutableHttpServletRequest(req).getUserPrincipal()).getUserId();
    }
}
