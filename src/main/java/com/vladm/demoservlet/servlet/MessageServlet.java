package com.vladm.demoservlet.servlet;

import com.vladm.demoservlet.model.MessageResponse;
import com.vladm.demoservlet.service.UserMessageService;
import com.vladm.demoservlet.utils.CustomServletRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "messageServlet", value = "/messages/*")
public class MessageServlet extends HttpServlet {

    private final UserMessageService userMessageService = UserMessageService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String messageId = ((CustomServletRequest) req).getPath().substring("/messages/".length());

        MessageResponse messageResponse = userMessageService.findMessageById(messageId);

        req.setAttribute("message", messageResponse);
        req.getRequestDispatcher("/message.jsp").forward(req, resp);
    }
}
