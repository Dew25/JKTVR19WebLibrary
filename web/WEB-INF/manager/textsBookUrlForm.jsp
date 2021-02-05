<%-- 
    Document   : uploadForm
    Created on : Jan 29, 2021, 12:28:33 PM
    Author     : Melnikov
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

    <h3>Загрузка текста книги по url из сети</h3>
    <form action="saveUrlToTextsBook" method="POST">
      Описание: <input type="text" name="descriptions"><br>
      Url: <input type="text" name="urls"><br>
      Описание: <input type="text" name="descriptions"><br>
      Url: <input type="text" name="urls"><br>
      Описание: <input type="text" name="descriptions"><br>
      Url: <input type="text" name="urls"><br>
      <input type="submit" value="Загрузить">
    </form>
    
  
