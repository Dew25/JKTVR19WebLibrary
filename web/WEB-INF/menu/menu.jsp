<%-- 
    Document   : menu
    Created on : Jan 28, 2021, 9:12:19 AM
    Author     : Melnikov
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
  <div class="container-fluid">
    <a class="navbar-brand" href="index.jsp">Наша библиотека</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
      <div class="navbar-nav">
        <a class="nav-link <c:if test="${active=='addBook'}">active</c:if>" aria-current="page" href="addBook">Добавить книгу</a>
        <a class="nav-link <c:if test="${active=='listBooks'}">active</c:if>" href="listBooks">Список книг</a>
        <a class="nav-link <c:if test="${active=='registration'}">active</c:if>" href="registrationForm">Добавить читателя</a>
        <a class="nav-link" href="listReaders">Список читателей</a>
        <a class="nav-link" href="takeOnBookForm">Взять книгу</a>
        <a class="nav-link" href="returnBookForm">Вернуть книгу</a>
        <a class="nav-link" href="adminPanel">Панель администратора</a>
        <a class="nav-link <c:if test="${active=='login'}">active</c:if>" href="loginForm">Вход</a>
        <a class="nav-link" href="logout">Выход</a>
        <a class="nav-link" href="profileForm">Профайл</a>
        
      </div>
    </div>
  </div>
</nav>

