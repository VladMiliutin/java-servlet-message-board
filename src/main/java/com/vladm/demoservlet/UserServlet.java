package com.vladm.demoservlet;

import com.vladm.demoservlet.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "userServlet", value = "/user")
public class UserServlet extends HttpServlet {

    public User user = new User("User", "email@test.com");

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("user", user);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("user.jsp");

        requestDispatcher.forward(request, response);
    }
}
