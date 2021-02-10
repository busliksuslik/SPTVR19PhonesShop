<%-- 
    Document   : products
    Created on : 15.12.2020, 12:12:00
    Author     : nikita
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h3 class="w-100 text-center  my-5">Продукты:</h3>
<div class="w-100 d-flex justify-content-center">
    <c:forEach var="product" items="${listProducts}">
      <div class="card m-2" style="min-width: 12rem;">
          <img src="insertFile/${product.picture.path}"  class="card-img-top" alt="..." style="max-width: 12rem; max-height: 15rem">
        <div class="card-body">
          <h5 class="card-title">${product.name}</h5>
          <p class="card-text">${product.price}</p>
          <p class="card-text">${product.count}</p>
          <a href="#" class="btn btn-primary">Купить</a>
        </div>
      </div>
    </c:forEach>
</div>
