package com.vladm.demoservlet;

import com.vladm.demoservlet.model.User;
import com.vladm.demoservlet.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

@WebServlet(name = "userServlet", value = "/user")
public class UserServlet extends HttpServlet {

    private UserService userService = new UserService();
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("users", userService.getAllUsers());
        returnJsp(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");

        userService.createUser(name, email);
    }

    private static void returnJsp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("user.jsp");
        requestDispatcher.forward(request, response);
    }

}
