<%-- 
    Document   : addProductForm
    Created on : 15.12.2020, 11:30:25
    Author     : nikita
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action = "createProduct" method="POST">
            <input placeholder="Name" name="name" value="${name}">    <br>
            <input placeholder="Price" name = "price"value="${price}"><br>
            <input placeholder="amount" name = "amount"value="${amount}"><br>
            <input style="" type="submit">
        </form>
            ${info}
    </body>
</html>
