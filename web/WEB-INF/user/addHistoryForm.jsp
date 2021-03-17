
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

    </head>
    <body>
        <h1 style = "color: #FF00FF; font-family: comic sans ms;"> Add History</h1>
        ${info}
        <label for="books">Выберете продукт:</label>
        <form action = "addHistory" method="POST" style="text-align: center;">
        <div class="w-100 d-flex justify-content-center">
            <c:forEach var="entry" items="${productMap}">
                <div class="card m-2" style="min-width: 12rem;">
                    <img src="insertFile/${entry.key.picture.path}"  class="card-img-top" alt="..." style="max-width: 12rem; max-height: 15rem">
                    <div class="card-body">
                      <h5 class="card-title">${entry.key.name}</h5>
                      <p class="card-text">Цена: ${entry.key.price/100}</p>
                      <p class="card-text">Кол-во: ${entry.key.count}</p>
                      <p class="card-text"><c:forEach var="tag" items="${entry.value.key}"><span>${tag.name}</span> <br></c:forEach></p>
                    </div>
                    <div class="row w-100">
                        <div class="col text-end">
                            <input name="quantity" type="number" class="form-control" min="0" max="${entry.key.count}" value="${entry.value}">
                        </div>
                        <div class="col  text-start">
                            Этот
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
            <input placeholder="Count" name = "count"value="${count}"><br>
            <input style="" type="submit">
        </form>
    </body>
</html>

