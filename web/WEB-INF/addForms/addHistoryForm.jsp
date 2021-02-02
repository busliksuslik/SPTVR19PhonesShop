
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

    </head>
    <body>
        <h1 style = "color: #FF00FF; font-family: comic sans ms;"> Add History</h1>
        ${info}
        <label for="books">Choose a book:</label>
        <form action = "createHistory" method="POST">
            <select name="product" size = "10">
                <c:forEach var="product" items="${listProducts}">
                    <option value="${product.id}">${product.name}|${product.price}$|${product.count}</option>
                </c:forEach>
            </select>
            <input placeholder="Count" name = "count"value="${count}"><br>
            <input style="" type="submit">
        </form>
    </body>
</html>

