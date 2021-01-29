<%-- 
    Document   : uploadForm
    Created on : Jan 29, 2021, 12:28:33 PM
    Author     : Melnikov
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

    <h3>Загрузка файла</h3>
    <form action="uploadFile" method="POST" enctype="multipart/form-data">
      <input type="text" name="description"><br>
      <input type="file" name="file"><br>
      <input type="submit" value="Загрузить">
    </form>
    
  
