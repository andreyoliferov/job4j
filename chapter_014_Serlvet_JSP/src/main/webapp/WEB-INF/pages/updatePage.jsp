<%--
  Created by IntelliJ IDEA.
  User: aoliferov
  Date: 02.12.2018
  Time: 12:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Update</title>
    <style type="text/css">
        table { border-collapse: collapse; }
        th, td { border: 1px solid black; }
    </style>
</head>

<body>
  <form action='${pageContext.servletContext.contextPath}/edit' method='post'>
      <input name='id' type='hidden' value='${user.id}'/>
      Name: <input type='text' name='name' value='${user.name}'/>
      Login: <input type='text' name='login' value='${user.login}'/>
      Password: <input type='password' name='password' value='${user.password}'/>
      Email: <input type='text' name='email' value='${user.email}'/>
      Role:
      <select name="role">
          <option value="${user.role.id}" selected><c:out value="${user.role.name}"/></option>
          <c:forEach var="role" items="${roles}">
              <c:if test="${user.role.id != role.id}">
                <option value="${role.id}"><c:out value="${role.name}"/></option>
              </c:if>
          </c:forEach>
      </select>
      <input type='submit' value='edit'/>
      </br>
      <c:out value="${result}"/>
   </form>
</body>
</html>
