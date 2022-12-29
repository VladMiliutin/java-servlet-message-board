package com.vladm.demoservlet.servlet;

import com.vladm.demoservlet.model.User;
import com.vladm.demoservlet.service.UserService;
import com.vladm.demoservlet.utils.RequestsConstants;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "usersServlet", value = "/users")
public class UsersServlet extends HttpServlet {


    private final UserService userService = UserService.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("users", userService.findAll());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("users.jsp");

        requestDispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter(RequestsConstants.NAME);
        String email = request.getParameter(RequestsConstants.EMAIL);
        String password = request.getParameter(RequestsConstants.PASSWORD);

        User user = userService.createUser(name, email, password);

        response.sendRedirect(request.getContextPath() + "/users/" + user.getId());
    }
}
