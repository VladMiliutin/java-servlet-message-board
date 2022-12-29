package com.vladm.demoservlet.filter;

import com.vladm.demoservlet.exception.ClientException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;


@WebFilter(servletNames = {"userServlet", "usersServlet", "messageServlet", "messagesServlet"})
public class ExceptionHandlerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (ClientException e) {
            HttpServletResponse resp =  (HttpServletResponse) response;
            PrintWriter writer = resp.getWriter();
            writer.println(e.getMessage());
            resp.setStatus(e.statusCode);
        }

    }
}
