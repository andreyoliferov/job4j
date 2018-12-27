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
    <%--<style type="text/css">--%>
        <%--table { border-collapse: collapse; }--%>
        <%--th, td { border: 1px solid black; }--%>
    <%--</style>--%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <form action='${pageContext.servletContext.contextPath}/create' method='post'>
        <div class="form-group ">
            Name:
            <input class="form-control" type='text' name='name' placeholder="Enter name" required/>
        </div>
        <div class="form-group ">
            Login:
            <input class="form-control" type='text' name='login' placeholder="Enter login" required/>
        </div>
        <div class="form-group ">
            Password:
            <input class="form-control" type='password' name='password' placeholder="Enter password" required/>
        </div>
        <div class="form-group ">
            Email:
            <input class="form-control" type='email' name='email' placeholder="Enter email"  required/>
        </div>
        <div class="form-group ">
            Role:
            <select class="form-control" name="role" required>
                <option selected disabled value=''>choose role</option>
                <c:forEach var="role" items="${roles}">
                    <option value="${role.id}"><c:out value="${role.name}"/></option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group ">
            <input class="btn btn-primary btn-lg" type='submit' value='create'/>
            </br>
            <c:out value="${result}"/>
        </div>
    </form>
</div>
</body>
</html>
