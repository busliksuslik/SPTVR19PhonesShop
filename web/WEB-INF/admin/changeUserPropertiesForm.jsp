<%-- 
    Document   : changeUserProperties
    Created on : 02.02.2021, 4:30:15
    Author     : nikita
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h3 class="w-100 m-2 text-center">Панель администратора</h3>
        
<p>
    <form action="setRole" method="POST">
        <div class="row">
            <div class="col m-2 text-end">
                Пользователь:
            </div>
            <div class="col">
                <select name="userId">
                    <option value="">Выберите пользователя</option>
                    <c:forEach var="entry" items="${usersMap}">
                        <option value="${entry.key.id}" <c:if test="${userId eq entry.key.id}"> selected </c:if>>
                            Логин: ${entry.key.login}. Роль: 
                                <c:forEach var="role" items="${entry.value}"><span>${role.name} </span></c:forEach>
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
                <select name="roleId">
                    <option value="">Выберите роль</option>
                    <c:forEach var="r" items="${listRoles}">
                        <option value="${r.id}" <c:if test="${roleId eq r.id}"> selected </c:if>>
                            ${r.name} 
                        </option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="row w-100">
            <div class="col text-end">
                <input class="form-check-input" name="changeRole" type="radio" value="0" checked>
            </div>
            <div class="col  text-start">
                Добавить роль
            </div>
        </div>
        <div class="row w-100">
            <div class="col text-end">
                <input class="form-check-input" name="changeRole" type="radio" value="1">
            </div>
            <div class="col  text-start">
                Удалить роль
            </div>
        </div>
        <div class="row">
            <div class="col m-2 text-center">
                <input type="submit" value="Изменить роль пользователю">
            </div>
        </div>
    </form>
</p>
