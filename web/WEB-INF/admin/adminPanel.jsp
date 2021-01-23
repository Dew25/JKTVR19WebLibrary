<%-- 
    Document   : istBooks
    Created on : Dec 1, 2020, 9:59:17 AM
    Author     : Melnikov
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Панель администратора</title>
  </head>
  <body>
    <h1>Панель администратора</h1>
    <p>${info}</p>
    <form action="addNewRole" method="POST">
        <p>Список пользователей: 
            <select name="userId">
                <option value="" <c:if test="${userId == null}">selected</c:if>>Выберите пользователя</option>
                <c:forEach var="entry" items="${usersMap}">
                    <option value="${entry.key.id}" <c:if test="${userId == entry.key.id}">selected</c:if>>${entry.key.reader.firstname} ${entry.key.reader.lastname}. Логин: ${entry.key.login}. Роль: ${entry.value}</option>
                </c:forEach>
            </select>
        </p>
        <p>Список ролей: 
            <select name="roleId">
                <option value="" <c:if test="${roleId == null}">selected</c:if>>Выберите роль:</option>
                <c:forEach var="role" items="${listRoles}" varStatus="status">
                    <option value="${role.id}" <c:if test="${roleId == role.id}">selected</c:if>>${role.roleName}</option>
                </c:forEach>
            </select>
        </p>
      <br><br>
      <input type="submit" value="Назначить роль пользователю">
    </form>
  </body>
</html>
