<%-- 
    Document   : page1
    Created on : Nov 24, 2020, 10:43:02 AM
    Author     : Melnikov
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Добавление книги</title>
  </head>
  <body>
    <div>Добавить книгу</div>
    <p>${info}</p>
    <br>
    <a href="index.jsp">Home</a><br>
    <p><a href="picturesUploadForm">Загрузить файл обложки книги на сервер</a></p>
    <p><a href="picturesUrlForm">Загрузить изображение обложки книги из сети</a></p>
    <p><a href="textUploadForm">Загрузить файл с текстом книги на сервер</a></p>
    <p><a href="textUrlForm">Загрузить текст книги из сети</a></p>
    <form action="createBook" method="POST">
        Название книги: <input type="text" name="name" value="${name}"><br>
        Автор книги: <input type="text" name="author" value="${author}"><br>
        Год издания книги: <input type="text" name="publishedYear" value="${publishedYear}"><br>
        Файл обложки: <select name="pictureId">
          <c:forEach var="picture" items="${listPictures}">
              <option value="${picture.id}" <c:if test="${picture.id eq selectedPictureId}">selected</c:if>>${picture.description}</option>
          </c:forEach>
        </select>
        <br>
        Текст книги: <select name="textId">
          <c:forEach var="text" items="${listTexts}">
              <option value="${text.id}" <c:if test="${text.id eq selectedTextId}">selected</c:if>>${text.description}</option>
          </c:forEach>
        </select>
        <br>
        <input type="submit" name="submit" value="Добавить книгу">
    </form>
  </body>
</html>
