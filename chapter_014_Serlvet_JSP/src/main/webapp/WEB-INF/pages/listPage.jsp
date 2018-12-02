<!--Created by IntelliJ IDEA.
User: aoliferov
Date: 02.12.2018
Time: 12:56
To change this template use File | Settings | File Templates.
-->
<%@ page import="usersapp.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List</title>
    <style type="text/css">
        table { border-collapse: collapse; }
        th, td { border: 1px solid black; }
    </style>
</head>
<body>
<% List<User> users = (List<User>) request.getAttribute("users"); %>
<% if (users != null) {%>
<table>
    <tr>
        <th>id</th>
        <th>name</th>
        <th>login</th>
        <th>email</th>
    </tr>
    <% for (User user : users) {%>
    <tr>
        <td><%= user.getId()%></td>
        <td><%= user.getName()%></td>
        <td><%= user.getLogin()%></td>
        <td><%= user.getEmail()%></td>
    </tr>
    <% } %>
</table>
<% } else { %>
<table>
    <tr>
        <td>List is empty</td>
    </tr>
</table>
<% } %>
</body>
</html>

