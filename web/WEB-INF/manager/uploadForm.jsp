<%-- 
    Document   : uploadForm
    Created on : 11.02.2021, 1:17:27
    Author     : nikita
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

        <h3>Загрузить файл</h3>
        <h2>${info}</h2>
        <form action="uploadPicture" method="POST"  enctype="multipart/form-data">
            <div class="row mb-3">
              <label for="file" class="form-label">Выберите локальный файл</label>
              <input class="form-control" type="file" name="file" id="file">
            </div>
            <div class=" row mb-3">
              <label for="description" class="form-label">Описание</label>
              <input class="form-control" type="text" name="description" id="description">
            </div>
            <div class="row">
                <button type="submit" class="btn btn-primary">Загрузить файл</button>
            </div>
        </form>
