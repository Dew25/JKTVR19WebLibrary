<%-- 
    Document   : menu
    Created on : Jan 28, 2021, 9:12:19 AM
    Author     : Melnikov
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
  <a class="navbar-brand" href="index.jsp">Наша библиотека</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarColor01">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item">
        <a class="nav-link" href="addBook">Добавить книгу
          <span class="sr-only">(current)</span>
        </a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="listBooks">Список книг</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="registrationForm">Добавить читателя</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="listReaders">Список читателей</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="takeOnBookForm">Взять книгу</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="returnBookForm">Вернуть книгу</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="adminPanel">Панель администратора</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="loginForm">Вход</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="logout">Выход</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="profileForm">Профайл</a>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">Dropdown</a>
        <div class="dropdown-menu">
          <a class="dropdown-item" href="loginForm">Вход</a>
          <a class="dropdown-item" href="logout">Выход</a>
          <a class="dropdown-item" href="listBooks">Список книг</a>
          <a class="dropdown-item" href="registrationForm">Добавить читателя</a>
          <a class="dropdown-item" href="listReaders">Список читателей</a>
          <a class="dropdown-item" href="takeOnBookForm">Выдать книгу</a>
          <a class="dropdown-item" href="returnBookForm">Вернуть книгу</a>
        </div>
      </li>
    </ul>
  </div>
</nav>
