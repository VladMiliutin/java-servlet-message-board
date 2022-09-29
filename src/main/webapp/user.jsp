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

<% for(int i = 0; i < users.size(); i+=1) { %>

    <h1>Email: <%=users.get(i).getEmail()%> </h1>
    <h1>Name: <%=users.get(i).getName()%> </h1>

<% }
%>
</body>
</html>
