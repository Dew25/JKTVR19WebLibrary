<%-- 
    Document   : uploadForm
    Created on : Jan 29, 2021, 12:28:33 PM
    Author     : Melnikov
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

    <h3>Загрузка файла с текстом книги</h3>
    <form action="uploadTextsBook" method="POST" enctype="multipart/form-data">
      Описание: <input type="text" name="descriptions"><br><input type="text" name="descriptions"><br>
      <input type="file" name="fileName"><br>
      Описание: <input type="text" name="descriptions"><br><input type="text" name="descriptions"><br>
      <input type="file" name="fileName"><br>
      Описание: <input type="text" name="descriptions"><br><input type="text" name="descriptions"><br>
      <input type="file" name="fileName"><br>
      <input type="submit" value="Загрузить">
    </form>
    
  
