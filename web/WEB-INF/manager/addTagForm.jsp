<%-- 
    Document   : addTagsForm
    Created on : Feb 11, 2021, 12:48:31 PM
    Author     : user
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

        <h3 class="w-100 text-center  my-5">Добавить тэг</h3>
        <h2>${info}</h2>
        <div class="" style="width: 50rem; margin: 0 auto">
            <form action="addTag" method="POST">
                  <div class="mb-3 row">
                    <label for="name" class="col-sm-3 col-form-label">Название тэг:</label>
                    <div class="col-sm-9">
                      <input type="text" class="form-control" id="name" name="name" value="${name}">
                    </div>
                  </div>
                <div class="col-sm-12">
                  <button type="submit" class="btn btn-primary mb-3 w-100">Отправить</button>
                </div>
            </form>
        </div>