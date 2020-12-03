<%-- 
    Document   : index
    Created on : Nov 27, 2020, 9:14:10 AM
    Author     : Melnikov
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JKTVR19WebLibrary</title>
  </head>
  <body>
    <h1>Наша библиотека</h1>
    <p>${info}</p>
    <a href="logout">Выход</a><br>
    <a href="loginForm">Вход</a><br>
    <a href="addBook">Добавить книгу</a><br>
    <a href="listBooks">Список книг</a><br>
    <a href="registrationForm">Добавить читателя</a><br>
    <a href="listReaders">Список читателей</a><br>
    <a href="takeOnBookForm">Выдать книгу</a><br>
    <a href="returnBookForm">Вернуть книгу</a><br>
    
  </body>
</html>
