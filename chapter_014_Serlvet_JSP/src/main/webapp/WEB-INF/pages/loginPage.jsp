<%--
  Created by IntelliJ IDEA.
  User: aoliferov
  Date: 05.12.2018
  Time: 20:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <c:if test="${param.unlogin == true}">
        <c:set scope="session" var="currentUser" value="${null}"/>
        <c:set scope="request" var="error" value="unlogin"/>
    </c:if>
    <c:if test="${currentUser == null}">
    <form action='${pageContext.servletContext.contextPath}/login' method='post'>
        <div class="form-group ">
            <label for="login">Login:</label>
            <input class="form-control" type='text' id='login' name='login' placeholder="Enter login" required/>
        </div>
        <div class="form-group ">
            <label for="password">Password:</label>
            <input class="form-control" type='password' id='password' name='password' placeholder="Enter password" required/>
        </div>
        <div class="form-group">
            <input  class="btn btn-default" type='submit' value='sign in'/>
        </div>
    </form>
    </c:if>
    <c:if test="${currentUser != null}">
        <form name="authorized" method="get">
            <div>Authorized: <c:out value="${currentUser.name}"/></div>
            <input type="hidden" name="unlogin" value="${true}"/>
            <input class="btn btn-danger" type="submit" value="unlogin"/>
        </form>
    </c:if>
    <c:if test="${error != null}">
        <div style="color: red" ><c:out value="${error}"/></div>
    </c:if>
</div>
</body>
</html>
