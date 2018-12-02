<%-- Created by IntelliJ IDEA.
  User: aoliferov
  Date: 02.12.2018
  Time: 12:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create</title>
    <style type="text/css">
        table { border-collapse: collapse; }
        th, td { border: 1px solid black; }
    </style>
</head>
<body>
<form action='${pageContext.servletContext.contextPath}/create' method='post'>
    Name: <input type='text' name='name'/>
    Login: <input type='text' name='login'/>
    Email: <input type='text' name='email'/>
    <input type='submit' value='create'/>
    </br>
    <c:out value="${result}"/>
</form>
</body>
</html>
