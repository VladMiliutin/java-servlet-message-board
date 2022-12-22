<%@ page import="com.vladm.demoservlet.model.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
    <style>
        .home {
            display: block;
            text-align: center;
        }
    </style>
</head>
<body>
<%
    List<User> users = (List<User>) request.getAttribute("users");
%>

<table>
    <tr>
        <td>Name:</td>
        <td>Email</td>
        <td>Link</td>
    </tr>
<% for (User user : users) { %>
    <tr>
        <td><%=user.getName()%></td>
        <td><%=user.getEmail()%></td>
        <td><a href="users/<%=user.getId()%>">Profile</a> </td>
    </tr>
<% }
%>

    <a class="home" href="<%=request.getScheme() + "://" + request.getHeader("Host") + request.getContextPath()%>">Home</a>
</table>
</body>
</html>
