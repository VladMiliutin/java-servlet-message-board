<%@ page import="com.vladm.demoservlet.utils.RequestsConstants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign Up</title>
</head>
<body>
<form action="users" method="post">
    Username:
    <input name="<%=RequestsConstants.NAME%>">
    Email:
    <input name="<%=RequestsConstants.EMAIL%>">
    Password:
    <input name="<%=RequestsConstants.PASSWORD%>">
    <input type="submit" value="sign up">
</form>
</body>
</html>
