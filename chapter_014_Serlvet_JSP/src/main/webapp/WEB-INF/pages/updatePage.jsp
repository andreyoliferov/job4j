<%--
  Created by IntelliJ IDEA.
  User: aoliferov
  Date: 02.12.2018
  Time: 12:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="usersapp.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update</title>
    <style type="text/css">
        table { border-collapse: collapse; }
        th, td { border: 1px solid black; }
    </style>
</head>
<body>

<body>
<% User user = (User) request.getAttribute("user"); %>
  <form action='<%=request.getContextPath()%>/edit' method='post'>
      <input name='id' type='hidden' value='<%= user != null ? user.getId().toString() : ""%>'/>
      Name: <input type='text' name='name' value='<%= user != null ? user.getName() : "empty"%>'/>
      Login: <input type='text' name='login' value='<%= user != null ? user.getLogin() : "empty"%>'/>
      Email: <input type='text' name='email' value='<%= user != null ? user.getEmail() : "empty"%>'/>
      <input type='submit' value='edit'/>
      </br>
      <%=request.getAttribute("result")%>
   </form>
</body>
</html>
