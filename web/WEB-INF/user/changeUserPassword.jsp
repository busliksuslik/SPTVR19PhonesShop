<%-- 
    Document   : changeUserProperties
    Created on : 06.04.2021, 9:56:54
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
        <form action = "addMoney" method="POST">
            <input placeholder="old password" name = "oldPassword"value="${oldPassword}"><br>
            <input placeholder="new password" name = "newPassword"value="${newPassword}"><br>
            <input style="" type="submit">
        </form>
    </body>
</html>