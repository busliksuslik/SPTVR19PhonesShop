<%-- 
    Document   : cart
    Created on : 17.03.2021, 10:39:27
    Author     : nikita
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart</title>
    </head>
    <body>
        <div class="container" style="text-align: center"><h1>${info}</h1></div>
        <form method="POST" action="buy" class="container py-5" style="text-align: center">
            <c:forEach var="entry" items="${cart}">
                <div class="container p-5 m-5 border">
                    <div class="row">
                        <div class="col-3">
                            <img src="insertFile/${entry.key.picture.path}"  class="card-img-top px-2" alt="..." style="max-width: 12rem; max-height: 15rem">
                        </div>
                        <div class="col-6" style="text-align: left">
                            <h5 class="card-title">${entry.key.name}</h5><br>
                            <p class="card-text">Цена: ${entry.key.price/100}</p>
                        </div>
                        <div class="col-3">
                            <input name="quantity" type="number" class="form-control" min="0" max="${entry.key.count}" value="${entry.value}">
                        </div>
                    </div>
                </div>
            </c:forEach>
            <input style="" type="submit">
        </form>

