<%@ page import="com.vladm.demoservlet.model.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Info</title>
</head>
<body>
<%
    User user = (User) request.getAttribute("user");
%>

<h1>Welcome <%=user.getName()%></h1>

<p>
    You can find your messages here: <a href="message">Link</a>
</p>
<p>
You can search for another users here: <a href="users">Link</a>
</p>
</body>
</html>
