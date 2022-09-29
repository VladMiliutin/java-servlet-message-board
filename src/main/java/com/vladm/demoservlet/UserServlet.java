package com.vladm.demoservlet;

import com.vladm.demoservlet.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(name = "userServlet", value = "/user")
public class UserServlet extends HttpServlet {

    public Map<String, User> USER_MAP = new HashMap<>();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<User> users = new ArrayList<>(USER_MAP.values());
        request.setAttribute("users", users);
        returnJsp(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");

        User user = createUser(name, email);

        USER_MAP.put(user.id, user);
    }

    private static void returnJsp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("user.jsp");
        requestDispatcher.forward(request, response);
    }

    public User createUser(String name, String email) {
        final String id = UUID.randomUUID().toString();
        return new User(id, name, email);
    }
}
