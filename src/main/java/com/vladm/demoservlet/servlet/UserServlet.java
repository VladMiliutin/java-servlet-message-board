package com.vladm.demoservlet.servlet;

import com.vladm.demoservlet.exception.ClientException;
import com.vladm.demoservlet.model.Message;
import com.vladm.demoservlet.model.MessageResponse;
import com.vladm.demoservlet.model.User;
import com.vladm.demoservlet.model.UserResponse;
import com.vladm.demoservlet.service.MessageService;
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
import java.util.stream.Collectors;

import static com.vladm.demoservlet.model.RequestParams.*;

@WebServlet(name = "userServlet", value = "/users/*")
public class UserServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();
    private final MessageService messageService = MessageService.getInstance();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        final String userId = ((MutableHttpServletRequest) request).getPath().substring("/users/".length());
        if(userId.length() > 0) {
            returnUserInfo(request, response, userService.findOne(userId));
        } else {
            var principal = new MutableHttpServletRequest(request).getUserPrincipal();
            returnUserInfo(request, response, principal.getName());
        }
    }

    private void returnUserInfo(HttpServletRequest request, HttpServletResponse response, String name) throws ServletException, IOException {
        Optional<User> userByName = userService.findUserByName(name);

        if (userByName.isPresent()) {
            returnUserInfo(request, response, userByName.get());
        } else {
            throw new ClientException("Oops, user not found or unauthorized", 404);
        }
    }
    private void returnUserInfo(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
        List<Message> messages = messageService.findAll(user.getId());
        List<MessageResponse> messageResponseList = messages.stream()
                .map(messageService::transformToMessageResponse)
                .collect(Collectors.toList());

        UserResponse userResponse = new UserResponse(user.getId(), user.getName(), user.getEmail(), messageResponseList);
        request.setAttribute("user", userResponse);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/user.jsp");
        requestDispatcher.forward(request, response);
    }


}
