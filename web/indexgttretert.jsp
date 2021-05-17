<%-- 
    Document   : index
    Created on : Nov 24, 2020, 2:31:44 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <title>shop</title>
    </head>
    <body>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js" integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0" crossorigin="anonymous"></script>
        <h1 style="color: #FF00FF; font-family: comic sans ms; text-align: center;">Добро пожаловать</h1>
        
        <h3 class="w-100 text-center  my-5" style = "text-align: center;">Продукты:</h3>
        <div class="w-100 d-flex justify-content-center">
            <c:forEach var="entry" items="${productMap}">
                <div class="card m-2" style="min-width: 12rem;">
                    <img src="insertFile/${entry.key.picture.path}"  class="card-img-top" alt="..." style="max-width: 12rem; max-height: 15rem">
                    <div class="card-body">
                      <h5 class="card-title">${entry.key.name}</h5>
                      <p class="card-text">Цена: ${entry.key.price/100}</p>
                      <p class="card-text">Кол-во: ${entry.key.count}</p>
                      <p class="card-text"><c:forEach var="tag" items="${entry.value}"><span>${tag.name}</span> <br></c:forEach></p>
                    </div>
                </div>

            </c:forEach>
            <!--<c:forEach var="product" items="${listProducts}">
              <div class="card m-2" style="min-width: 12rem;">
                  <img src="insertFile/${product.picture.path}"  class="card-img-top" alt="..." style="max-width: 12rem; max-height: 15rem">
                <div class="card-body">
                  <h5 class="card-title">${product.name}</h5>
                  <p class="card-text">${product.price}</p>
                  <p class="card-text">${product.count}</p>
                </div>
              </div>
            </c:forEach> -->
        </div>
    </body>
</html>
