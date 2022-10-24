package com.vladm.demoservlet.servlet;

import com.vladm.demoservlet.model.User;
import com.vladm.demoservlet.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static com.vladm.demoservlet.model.RequestParams.*;

@WebServlet(name = "usersServlet", value = "/users")
public class UsersServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        final List<User> users;
        Map<String, String[]> parameterMap = request.getParameterMap();
        if(parameterMap.isEmpty()) {
            users = userService.getAllUsers();
        } else {
            String name = request.getParameter(USERNAME);
            String email = request.getParameter(EMAIL);

            Set<User> userSet = new HashSet<>();
            userService.findUserByName(name)
                    .ifPresent(userSet::add);
            userService.findUserByEmail(email)
                    .ifPresent(userSet::add);

            users = new ArrayList<>(userSet);
        }

        request.setAttribute("users", users);
        redirectTo(request, response, "users.jsp");

    }

    private static void redirectTo(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
    }

}
