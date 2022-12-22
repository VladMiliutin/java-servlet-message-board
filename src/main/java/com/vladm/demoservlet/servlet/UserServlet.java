package com.vladm.demoservlet.servlet;

import com.vladm.demoservlet.model.UserResponse;
import com.vladm.demoservlet.service.UserMessageService;
import com.vladm.demoservlet.util.MutableHttpServletRequest;
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
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        final String userId = ((MutableHttpServletRequest) request).getPath().substring("/users/".length());

        if(userId.length() > 0) {
            returnUserInfo(request, response, userId);
            return;
        }

        returnUserInfo(request, response, MutableHttpServletRequest.userId(request));
    }

    private void returnUserInfo(HttpServletRequest request, HttpServletResponse response, String userId) throws ServletException, IOException {
        UserResponse userResponse = userMessageService.findUser(userId);
        request.setAttribute("user", userResponse);

        request.getRequestDispatcher("/user.jsp").forward(request, response);
    }


}
