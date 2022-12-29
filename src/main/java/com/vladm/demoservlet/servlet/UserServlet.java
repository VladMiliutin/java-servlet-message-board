package com.vladm.demoservlet.servlet;


import com.vladm.demoservlet.model.UserResponse;
import com.vladm.demoservlet.service.UserMessageService;
import com.vladm.demoservlet.utils.CustomServletRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "userServlet", value = "/users/*")
public class UserServlet extends HttpServlet {

    private final UserMessageService userMessageService = UserMessageService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = ((CustomServletRequest) req).getPath().substring("/users/".length());

        if(userId.length() == 0) {
            userId = CustomServletRequest.getUserId(req);
        }

        UserResponse user = userMessageService.findUser(userId);
        req.setAttribute("user", user);
        req.getRequestDispatcher("/user.jsp").forward(req, resp);
    }
}
