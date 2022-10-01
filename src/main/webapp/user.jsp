<%@ page import="com.vladm.demoservlet.model.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Info</title>
</head>
<body>
<%
    List<User> users = (List<User>) request.getAttribute("users");
%>

<% for (User user : users) { %>

<h1>Email: <%=user.getEmail()%>
</h1>
<h1>Name: <%=user.getName()%>
</h1>

<% }
%>
</body>
</html>
