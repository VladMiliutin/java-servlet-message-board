package com.vladm.demoservlet.servlet;

import com.vladm.demoservlet.model.MessageResponse;
import com.vladm.demoservlet.service.MessageService;
import com.vladm.demoservlet.util.MutableHttpServletRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "messageServlet", value = "/messages/*")
public class MessageServlet extends HttpServlet {

    private final MessageService messageService = MessageService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String messageId = ((MutableHttpServletRequest) req).getPath().substring("/messages/".length());

        final var message = messageService.findOne(messageId);

        MessageResponse messageResp = messageService.transformToMessageResponse(message);
        req.setAttribute("message", messageResp);
        req.getRequestDispatcher("/message.jsp").forward(req, resp);
    }

}
