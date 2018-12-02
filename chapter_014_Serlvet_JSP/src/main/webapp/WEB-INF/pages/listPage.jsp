<!--Created by IntelliJ IDEA.
User: aoliferov
Date: 02.12.2018
Time: 12:56
To change this template use File | Settings | File Templates.
-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>List</title>
    <style type="text/css">
        table { border-collapse: collapse; }
        th, td { border: 1px solid black; }
    </style>
</head>
<body>
<c:if test="${users != null}">
<table>
    <tr>
        <th>id</th>
        <th>name</th>
        <th>login</th>
        <th>email</th>
    </tr>
    <c:forEach var="user" items="${users}">
    <tr>
        <td><c:out value="${user.id}"/></td>
        <td><c:out value="${user.name}"/></td>
        <td><c:out value="${user.login}"/></td>
        <td><c:out value="${user.email}"/></td>
    </tr>
    </c:forEach>
</table>
</c:if>
<c:if test="${users == null}">
<table>
    <tr>
        <td>List is empty</td>
    </tr>
</table>
</c:if>
</body>
</html>