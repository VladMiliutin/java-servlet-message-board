<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Message board</title>
</head>
<body>
<h1>Welcome to our platform</h1>
<a href="sign-up.jsp">Sign Up</a>
<a href="users">Sign In</a>
<a href="users">Users</a>
<a href="<%=request.getScheme() + "://log:out@" + request.getHeader("Host") + request.getContextPath()%>">Logout</a>
</body>
</html>
