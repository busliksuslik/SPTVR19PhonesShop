<%-- 
    Document   : changeProduct
    Created on : 15.12.2020, 14:07:36
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
        <form action = "changeProduct" method="POST">
            <select name="product" size = "10">
                <c:forEach var="product" items="${listProducts}">
                    <option value="${product.id}">${product.name}|${product.price}$|${product.count}</option>
                </c:forEach>
            </select>
            <input placeholder="Name" name="name" value="${name}">    <br>
            <input placeholder="Price" name = "price"value="${price}"><br>
            <input placeholder="amount" name = "amount"value="${amount}"><br>
            <input style="" type="submit">
        </form>
    </body>
</html>
