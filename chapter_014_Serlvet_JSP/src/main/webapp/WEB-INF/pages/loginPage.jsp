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
</head>
<body>
<c:if test="${param.unlogin == true}">
    <c:set scope="session" var="currentUser" value="${null}"/>
    <c:set scope="request" var="error" value="unlogin"/>
</c:if>
<c:if test="${currentUser == null}">
<form action='${pageContext.servletContext.contextPath}/login' method='post'>
    Login: <input type='text' name='login'/>
    Password: <input type='password' name='password'/>
    <input type='submit' value='login'/>
</form>
</c:if>
<c:if test="${currentUser != null}">
    <form name="authorized" method="get">
        <div>Authorized: <c:out value="${currentUser.name}"/></div>
        <input type="hidden" name="unlogin" value="${true}"/>
        <input type="submit" value="unlogin"/>
    </form>
</c:if>
<c:if test="${error != null}">
    <div><c:out value="${error}"/></div>
</c:if>
</body>
</html>
