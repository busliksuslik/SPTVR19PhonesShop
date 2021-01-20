<%-- 
    Document   : addMoney
    Created on : 15.12.2020, 13:26:30
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
        <form action = "createMoney" method="POST">
            <input placeholder="Name" name="name" value="${name}"><br>
            <input placeholder="Money" name = "money"value="${money}"><br>
            <input style="" type="submit">
        </form>
    </body>
</html>
