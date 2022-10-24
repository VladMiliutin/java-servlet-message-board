package com.vladm.demoservlet.servlet;

import com.vladm.demoservlet.model.Message;
import com.vladm.demoservlet.model.UserPrincipal;
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

@WebServlet(name = "messageServlet", value = "/message")
public class MessageServlet extends HttpServlet {

    private final MessageService messageService = MessageService.getInstance();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String userId = ((UserPrincipal) new MutableHttpServletRequest(request).getUserPrincipal()).getUserId();
        List<Message> messages = messageService.findAll(userId);
        request.setAttribute("messages", messages);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("messages.jsp");
        requestDispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream();
        String text = IOUtils.toString(inputStream);
        String userId = ((UserPrincipal) new MutableHttpServletRequest(request).getUserPrincipal()).getUserId();

        Message message = messageService.publishMessage(userId, text);
        response.getWriter().println(message);
    }
}
