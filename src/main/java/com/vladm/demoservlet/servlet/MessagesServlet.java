package com.vladm.demoservlet.servlet;

import com.vladm.demoservlet.model.Message;
import com.vladm.demoservlet.model.RequestParams;
import com.vladm.demoservlet.service.MessageService;
import com.vladm.demoservlet.util.MutableHttpServletRequest;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(name = "messagesServlet", value = "/messages")
public class MessagesServlet extends HttpServlet {

    private final MessageService messageService = MessageService.getInstance();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String userId = MutableHttpServletRequest.userId(request);
        final var messages = messageService.findAll(userId).stream()
                    .map(messageService::transformToMessageResponse)
                    .collect(Collectors.toList());

        request.setAttribute("messages", messages);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("messages.jsp");
        requestDispatcher.forward(request, response);
    }

    // Let's keep it as it is
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream();
        String text = IOUtils.toString(inputStream);
        String userId = MutableHttpServletRequest.userId(request);
        Optional<String> replyToId = Optional.ofNullable(request.getParameter(RequestParams.REPLY_TO));

        Optional<Message> message = messageService.publishMessage(userId, text, replyToId);
        response.getWriter().println(message);
    }
}
