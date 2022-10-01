package com.vladm.demoservlet.servlet;

import com.vladm.demoservlet.util.MutableHttpServletRequest;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Base64;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    private final static Base64.Encoder encoder = Base64.getEncoder();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");

        String header = "Basic " + encoder.encodeToString((name + ":" + password).getBytes());
        resp.setHeader("Authorization", header);
        MutableHttpServletRequest mutableReq = new MutableHttpServletRequest(req);
        mutableReq.putHeader("Authorization", header);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(req, resp);
    }
}
