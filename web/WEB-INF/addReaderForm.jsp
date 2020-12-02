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
    <title>Добавление читателя</title>
  </head>
  <body>
    <div>Добавить читателя</div>
    <p>${info}</p>
    <br>
    <a href="index.jsp">Home</a><br>
    <form action="createReader" method="POST">
        Имя читателя: <input type="text" name="firstname" value="${firstname}"><br>
        Фамилия читателя: <input type="text" name="lastname" value="${lastname}"><br>
        Телефон: <input type="text" name="phone" value="${phone}"><br>
        <input type="submit" name="submit" value="Добавить читателя">
    </form>
  </body>
</html>
