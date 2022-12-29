package com.vladm.demoservlet.servlet;

import com.vladm.demoservlet.model.Message;
import com.vladm.demoservlet.service.MessageService;
import com.vladm.demoservlet.utils.CustomServletRequest;
import com.vladm.demoservlet.utils.RequestsConstants;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "messagesServlet", value = "/messages")
public class MessagesServlet extends HttpServlet {

    private final MessageService messageService = MessageService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletInputStream inputStream = req.getInputStream();
        String text = IOUtils.toString(inputStream);
        Optional<String> replyToId = Optional.ofNullable(req.getParameter(RequestsConstants.REPLY_TO));

        String userId = CustomServletRequest.getUserId(req);

        Message msg = messageService.save(userId, text, replyToId);
        resp.getWriter().println(msg);
    }

}
