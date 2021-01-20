<%-- 
    Document   : histories
    Created on : 15.12.2020, 13:31:12
    Author     : nikita
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>History</h1>
        <br>
        <ul>
            <c:forEach var="history" items="${listHistories}">
                <li>${history.user.login} | ${history.product.name} | ${history.product.price} | ${history.count} |${history.takeOn}</li>
            </c:forEach>
        </ul>
    </body>
</html>
