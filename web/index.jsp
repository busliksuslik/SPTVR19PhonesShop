<%-- 
    Document   : index
    Created on : Nov 24, 2020, 2:31:44 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>shop</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h1 style="color: #FF00FF; font-family: comic sans ms">admin</h1>
        <h1>${info}</h1>
        <form action = "adminMode" method="POST">
            <input placeholder="Name" name="name" value="${name}"><br>
            <input placeholder="Password" name = "password"value="${password}"><br>
            <input style="" type="submit">
        </form>
        <h1>${info}</h1>
        
    </body>
</html>
