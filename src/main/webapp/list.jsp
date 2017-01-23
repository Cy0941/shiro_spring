<%--
  Created by IntelliJ IDEA.
  User: Charls
  Date: 2017/1/12
  Time: 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>list</title>
</head>
<body>
    <div style="text-align: center">
        Welcome&nbsp;<shiro:principal property="username"/>&nbsp;Login&nbsp;In
        <br>
        <h3>List JSP</h3>
        <shiro:hasRole name="admin">
            <br>
            <a href="/admin.jsp">Admin JSP</a>
        </shiro:hasRole>
        <shiro:hasAnyRoles name="admin,user">
            <br>
            <a href="/user.jsp">User JSP</a>
            <br>
            <a href="/add.jsp">Add JSP</a>
        </shiro:hasAnyRoles>
        <br>
        <a href="/shiro/testAnnotation">Test Annotation</a>
        <br>
        <a href="/guest.jsp">Guest JSP</a>
        <br>
        <%--TODO WHY /shiro/logout --%>
        <a href="/shiro/logout">Logout</a>
    </div>
</body>
</html>
