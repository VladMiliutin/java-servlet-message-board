<%@ page import="com.vladm.demoservlet.model.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
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
        <td><a href="users?email=<%=user.getEmail()%>">Profile</a> </td>
    </tr>
<% }
%>
</table>
</body>
</html>
