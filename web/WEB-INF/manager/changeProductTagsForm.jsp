<%-- 
    Document   : changeProductTagsForm
    Created on : 15.02.2021, 23:29:39
    Author     : nikita
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<h3 class="w-100 m-2 text-center">Изменение тэгов у продукта</h3>
        
<p>
    <form action="changeProductTags" method="POST">
        <div class="row">
            <div class="col m-2 text-end">
                Продукт:
            </div>
            <div class="col">
                <select name="productId">
                    <option value="">Выберите продукт</option>
                    <c:forEach var="entry" items="${productMap}">
                        <option value="${entry.key.id}" <c:if test="${productId eq entry.key.id}"> selected </c:if>>
                            Название: ${entry.key.name}. Тэг: 
                                <c:forEach var="tag" items="${entry.value}"><span>${tag.name} </span></c:forEach>
                        </option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="row">
            <div class="col m-2 text-end">
                Роли:
            </div>
            <div class="col">
                <select name="tagId">
                    <option value="">Выберите тэг</option>
                    <c:forEach var="r" items="${listTags}">
                        <option value="${r.id}" <c:if test="${roleId eq r.id}"> selected </c:if>>
                            ${r.name} 
                        </option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="row w-100">
            <div class="col text-end">
                <input class="form-check-input" name="changeTag" type="radio" value="0" checked>
            </div>
            <div class="col  text-start">
                Добавить тэг
            </div>
        </div>
        <div class="row w-100">
            <div class="col text-end">
                <input class="form-check-input" name="changeTag" type="radio" value="1">
            </div>
            <div class="col  text-start">
                Удалить тэг
            </div>
        </div>
        <div class="row">
            <div class="col m-2 text-center">
                <input type="submit" value="Изменить тэги продукта">
            </div>
        </div>
    </form>
</p>
