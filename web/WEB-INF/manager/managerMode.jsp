<%-- 
    Document   : managerMode
    Created on : 10.02.2021, 23:31:07
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
        <h1>Manager mode</h1>
        <h3>${info}</h3>
        <a class="btn btn-primary" href="addProductForm" role="button">Add product</a>
        <a class="btn btn-primary" href="changeProductForm" role="button">Change product</a>
        <a class="btn btn-primary" href="users" role="button">users</a>
        <a class="btn btn-primary" href="histories" role="button">histories</a>
        <a class="btn btn-primary" href="addTagForm" role="button">Add tag</a>
        <a class="btn btn-primary" href="changeProductTagsForm" role="button">Change product tag</a>
        <a class="btn btn-primary" href="changeTagForm" role="button">Change tag</a>
    </body>
</html>
