<%@ page import="com.vladm.demoservlet.model.User" %><%--
  Created by IntelliJ IDEA.
  User: vladm
  Date: 9/28/22
  Time: 7:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Info</title>
</head>
<body>
<%
    User user = (User) request.getAttribute("user");
%>
<h1>Email: <%=user.getEmail()%> </h1>
<h1>Name: <%=user.getName()%> </h1>
</body>
</html>
