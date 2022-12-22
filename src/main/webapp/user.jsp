<%@ page import="com.vladm.demoservlet.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.vladm.demoservlet.model.UserResponse" %>
<%@ page import="com.vladm.demoservlet.model.MessageResponse" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <%
        UserResponse user = (UserResponse) request.getAttribute("user");
    %>
    <title><%=user.getName()%></title>
    <style>
        .box {
            display: flex;
            flex-direction: row;
        }
        .tweet {
            border: 1px solid;
            max-height: 300px;
            display: flex;
            flex-direction: column;
        }
        .user-info {
            display: flex;
            flex-direction: column;
            font-size: 32px;
            font-weight: bold;
        }
        .tweets-box {
            margin-left: 30px;
            display: flex;
            flex-direction: column;
            flex: auto;
        }
        .user-tweets {
            display: flex;
            flex-direction: column-reverse;
        }
        .reply-user-info {
            font-size: 24px;
            font-weight: bold;
        }
        .message {
            justify-content: left;
            font-size: 48px;
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

            await fetch('../messages', {method: 'POST', body: msg})
            document.location.reload()
        }
    </script>
</head>
<body>
<a class="home" href="<%=request.getScheme() + "://" + request.getHeader("Host") + request.getContextPath()%>">Home</a>
<div class="box">
    <div class="user-info">
        User Info:
        <div>
            Name: <a href="../users/<%=user.getId()%>"><%=user.getName()%></a>
        </div>
        <div>
            Email: <a href="mailto:<%=user.getEmail()%>"><%=user.getEmail()%></a>
        </div>
    </div>
    <div class="tweets-box">
        <% if(user.getId().equals(request.getHeader("id"))) { %>
        <h1>What's on your mind</h1>
        <div class="send-tweet">
            <textarea id="message"></textarea>
            <button onclick="post()">Publish</button>
        </div>
        <% } %>
        Tweets:
        <div class="user-tweets">
            <% for (MessageResponse msg : user.getMessageResponses()) { %>
            <div class="tweet">
                <div class="user-info">
                    <a href="../users/<%=user.getId()%>"> <%=user.getName()%></a> says:
                </div>
                <div class="message">
                    <%=msg.getText()%>
                </div>
                <% if(msg.isReply()) { %>
                IN RESPONSE TO:
                <div class="reply-user-info">
                    <a href="<%=msg.getReplyTo().getUserId()%>"> <%=msg.getReplyTo().getUserName()%></a> <a href="../messages/<%=msg.getReplyTo().getId()%>">says:</a>
                </div>
                <div>
                    <%=msg.getReplyTo().getText()%>
                </div>
                <% } %>
                <a href="../messages/<%=msg.getId()%>">
                    <button>REPLY</button>
                </a>
            </div>
            <% } %>
        </div>
    </div>
</div>
</body>
</html>
