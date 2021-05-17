<%-- 
    Document   : loginForm
    Created on : 14.01.2021, 10:20:24
    Author     : nikita
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>login</title>
    </head>
    <body>
        <form action = "login" method="POST" style = "text-align: center;">
            <input placeholder="name" name="login" id="login" value="${name}"><br>
            <input type="password" placeholder="Password" id="password" name = "password" value="${password}"><br>
            <input id="submit" type="submit">
        </form>
    </body>
</html>
