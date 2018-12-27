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
    <%--<style type="text/css">--%>
        <%--table { border-collapse: collapse; }--%>
        <%--th, td { border: 1px solid black; }--%>
    <%--</style>--%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</head>
<body>
<c:if test="${users != null}">
<div class="container">
    <table class="table my-lg-5">
        <thead class="thead-dark">
        <tr>
            <th>id</th>
            <th>name</th>
            <th>login</th>
            <th>email</th>
            <th>role</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}">
            <tr>
                <td><c:out value="${user.id}"/></td>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.login}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.role.name}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </c:if>
    <c:if test="${users == null}">
    <table class="table my-lg-5">
        <tr>
            <td>List is empty</td>
        </tr>
    </table>
</div>
</c:if>
</body>
</html>