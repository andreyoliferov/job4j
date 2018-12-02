<%-- Created by IntelliJ IDEA.
  User: aoliferov
  Date: 02.12.2018
  Time: 12:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create</title>
    <style type="text/css">
        table { border-collapse: collapse; }
        th, td { border: 1px solid black; }
    </style>
</head>
<body>
<form action='<%=request.getContextPath()%>/create' method='post'>
    Name: <input type='text' name='name'/>
    Login: <input type='text' name='login'/>
    Email: <input type='text' name='email'/>
    <input type='submit' value='create'/>
    </br>
    <%=request.getAttribute("result")%>
</form>
</body>
</html>
