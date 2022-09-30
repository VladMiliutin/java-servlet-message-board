<%@ page import="com.vladm.demoservlet.model.Message" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Info</title>
</head>
<body>
<%
    List<Message> messages = (List<Message>) request.getAttribute("messages");
%>
Your messages:
<% for(int i = 0; i < messages.size(); i+=1) { %>

    <div style="border-style: solid; border-width: 3px">
        <%=messages.get(i).getText()%>
    </div>

<% }
%>
</body>
</html>
