<%-- 
    Document   : products
    Created on : 15.12.2020, 12:12:00
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
        <h1>Products</h1>
        <br>
        <ul>
            <c:forEach var="product" items="${listProducts}">
                <li>${product.name} | ${product.price}</li>
            </c:forEach>
        </ul>
    </body>
</html>
