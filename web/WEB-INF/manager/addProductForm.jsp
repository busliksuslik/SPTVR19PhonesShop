<%-- 
    Document   : addProductForm
    Created on : 15.12.2020, 11:30:25
    Author     : nikita
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

        <h3 class="w-100 text-center  my-5">Добавить продукт</h3>
        <div class="" style="width: 50rem; margin: 0 auto">
            <a class="btn btn-primary my-2" href="uploadForm">Загрузить картинку</a>
            <form action="addProduct" method="POST">
                  <div class="mb-3 row">
                    <label for="name" class="col-sm-3 col-form-label">Название продукта:</label>
                    <div class="col-sm-9">
                      <input type="text" class="form-control" id="name" name="name" value="${name}">
                    </div>
                  </div>
                  <div class="mb-3 row">
                    <label for="price" class="col-sm-3 col-form-label">Цена:</label>
                    <div class="col-sm-9">
                      <input type="text" class="form-control" id="price" name="price" value="${price}">
                    </div>
                  </div>
                  <div class="mb-3 row">
                    <label for="amount" class="col-sm-3 col-form-label">Кол-во: </label>
                    <div class="col-sm-9">
                      <input type="text" class="form-control" name="amount" id="amount" value="${amount}">
                    </div>
                  </div>
                  <div class="mb-3 row">
                    <label for="picture" class="col-sm-3 col-form-label">Картинка: </label>
                    <div class="col-sm-9">
                        <select class="form-select" name="pictureId">
                            <option value="">Выберите картинку</option>
                            <c:forEach var="picture" items="${listPictures}">
                                <option value="${picture.id}">${picture.description}</option>
                            </c:forEach>
                        </select>
                    </div>
                  </div>
                  <div class="mb-3 row">
                    <label for="tag" class="col-sm-3 col-form-label">Тэг </label>
                    <div class="col-sm-9">
                        <select class="form-select" name="tagId">
                            <option value="">Выберите тэг</option>
                            <c:forEach var="tag" items="${listTags}">
                                <option value="${tag.id}">${tag.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                  </div>
                <div class="col-sm-12">
                  <button type="submit" class="btn btn-primary mb-3 w-100">Отправить</button>
                </div>
            </form>
        </div>
