package com.vladm.demoservlet.servlet;

import com.vladm.demoservlet.exception.ClientException;
import com.vladm.demoservlet.model.User;
import com.vladm.demoservlet.service.UserService;
import com.vladm.demoservlet.util.MutableHttpServletRequest;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

import static com.vladm.demoservlet.model.RequestParams.*;

@WebServlet(name = "userServlet", value = "/user")
public class UserServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        var principal = new MutableHttpServletRequest(request).getUserPrincipal();

        returnUserInfo(request, response, principal.getName());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter(USERNAME);
        String email = req.getParameter(EMAIL);
        String password = req.getParameter(PASSWORD);

        User user = userService.createUser(name, email, password);

        returnUserInfo(req, resp, name);
    }

    private void returnUserInfo(HttpServletRequest request, HttpServletResponse response, String name) throws ServletException, IOException {
        Optional<User> userByName = userService.findUserByName(name);

        if (userByName.isPresent()) {
            request.setAttribute("user", userByName.get());

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("user.jsp");
            requestDispatcher.forward(request, response);
        } else {
            throw new ClientException("Oops, user not found or unauthorized", 404);
        }
    }
}
