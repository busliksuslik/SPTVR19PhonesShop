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
        <form action = "login" method="POST">
            <input placeholder="name" name="name" value="${name}"><br>
            <input type="password" placeholder="Password" name = "password" value="${password}"><br>
            <input type="submit">
        </form>
        <h1>${info}</h1>
    </body>
</html>
