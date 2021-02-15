<%-- 
    Document   : addUserForm
    Created on : Nov 26, 2020, 1:59:40 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1 style = "text-align: center;"> Add user</h1>
        <form action = "createUser" method="POST" style = "text-align: center;">
            <input placeholder="Name" name="name" value="${name}"><br>
            <input placeholder="Password" name = "password"value="${password}"><br>
            <input style="" type="submit">
        </form>
        ${info}
    </body>
</html>
