<%@ page import="com.vladm.demoservlet.model.MessageResponse" %>
<%@ page import="com.vladm.demoservlet.utils.RequestsConstants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    MessageResponse message = (MessageResponse) request.getAttribute("message");
%>
<head>
    <title><%=message.getText().substring(0, Math.min(message.getText().length(), 10))%></title>
    <style>
        .box {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }
        .tweet {
            border: 1px solid;
            width: 50%;
            max-height: 300px;
            display: flex;
            flex-direction: column;
        }
        .reply {
            border-width: 1px;
            margin-top: 5px;
            max-width: 50px;
        }
        .user-info {
            font-size: 32px;
            font-weight: bold;
        }
        .reply-user-info {
            font-size: 24px;
            font-weight: bold;
        }
        .message {
            justify-content: left;
            font-size: 48px;
        }
        .reply-area {
            display: none;
        }
        .home {
            display: block;
            text-align: center;
        }
        textarea
        {
            border:1px solid #999999;
            width:100%;
            margin:5px 0;
            padding:3px;
        }
    </style>

    <script>
        async function post() {
            let msg = document.getElementById('message').value;
            let href = window.location.href;
            let lastIndex = href.lastIndexOf('/');
            let id = href.substring(lastIndex + 1, href.length);
            let url = href.substring(0, lastIndex) + '?<%=RequestsConstants.REPLY_TO%>=' + id;
            await fetch(url, {method: 'POST', body: msg})
            document.location.reload()
        }
        async function showArea() {
            var x = document.getElementById("reply-area");
            if (x.style.display === "none") {
                x.style.display = "block";
            } else {
                x.style.display = "none";
            }
        }
    </script>
</head>
<body>
<a class="home" href="<%=request.getScheme() + "://" + request.getHeader("Host") + request.getContextPath()%>">Home</a>
<div class="box">
    <div class="tweet">
        <div class="user-info">
            <a href="../users/<%=message.getUserId()%>"> <%=message.getUserName()%></a> says:
        </div>
        <div class="message">
            <%=message.getText()%>
        </div>
        <% if(message.isReply()) { %>
        IN RESPONSE TO:
        <div class="reply-user-info">
            <a href="../users/<%=message.getReplyTo().getUserId()%>"> <%=message.getReplyTo().getUserName()%></a> <a href="<%=message.getReplyTo().getId()%>">says:</a>
        </div>
        <div>
            <%=message.getReplyTo().getText()%>
        </div>
        <% } %>

        <button class="reply" onclick="showArea()">REPLY</button>
        <div class="reply-area" id="reply-area">
            <textarea id="message"></textarea>
            <button onclick="post()">Publish</button>
        </div>
    </div>
    Replies:
    <% for (MessageResponse msg : message.getReplies()) { %>

    <div class="tweet">
        <div class="user-info">
            <a href="../users/<%=msg.getUserId()%>"> <%=msg.getUserName()%></a> <a href="<%=msg.getId()%>">says:</a>
        </div>
        <div class="message">
            <%=msg.getText()%>
        </div>
    </div>

    <% }
    %>

</div>
</body>
</html>
