<%@ page import="com.vladm.demoservlet.model.Message" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Info</title>
    <script>
        async function post() {
            let msg = document.getElementById('message').value;

            await fetch("message", {method: 'POST', body: msg})
            document.location.reload()
        }
    </script>
</head>
<body>
<%
    List<Message> messages = (List<Message>) request.getAttribute("messages");
%>
<h1>What's on your mind</h1>
<div>
    <textarea id="message"></textarea>
    <button onclick="post()">Publish</button>
</div>
Your messages:
<% for (Message message : messages) { %>

<div style="border-style: solid; border-width: 3px">
    <%=message.getText()%>
</div>

<% }
%>
</body>
</html>
