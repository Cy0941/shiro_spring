<%--
  Created by IntelliJ IDEA.
  User: Charls
  Date: 2017/1/12
  Time: 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
</head>
<body>
    <div style="text-align: center">
        <h3>Login JSP</h3>
        <form action="shiro/login" method="post">
            username:<input type="text" name="username">
            <br><br>
            password:<input type="password" name="password">
            <br><br>
            <input type="submit" value="Submit">
        </form>
    </div>
</body>
</html>
