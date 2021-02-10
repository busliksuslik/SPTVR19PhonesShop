<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Список книг</title>

    </head>
    <body>
        <h1>Users</h1>
        <br>
        <ul>
            <c:forEach var="user" items="${listUsers}">
                <li>${user.login} | ${user.password}</li>
            </c:forEach>
        </ul>
    </body>
</html>
