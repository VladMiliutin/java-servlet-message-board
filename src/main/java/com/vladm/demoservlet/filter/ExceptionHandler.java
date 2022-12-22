package com.vladm.demoservlet.filter;

import com.vladm.demoservlet.exception.ClientException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(servletNames = { "userServlet", "usersServlet", "messageServlet", "messagesServlet" })
public class ExceptionHandler implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (ClientException e) {
            HttpServletResponse resp = (HttpServletResponse) servletResponse;
            PrintWriter out = resp.getWriter();
            out.println(e.getMessage());
            resp.setStatus(e.statusCode);
        }
    }
}
