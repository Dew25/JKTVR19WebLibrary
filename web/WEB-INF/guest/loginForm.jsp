<%-- 
    Document   : loginForm
    Created on : Dec 3, 2020, 8:50:52 AM
    Author     : Melnikov
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

    <h3 class="w-100 mt-5 text-center">Введите логин и пароль</h3>
    
    <form action="login" method="POST">
        <div class="row mx-auto mt-5 w-25 align-items-center">
            <div class="col-3  text-end">
              <label for="login" class="col-form-label">Логин: </label>
            </div>
            <div class="col-9 text-start">
              <input type="text" name="login" id="login" class="form-control" aria-describedby="login" value="">
            </div>
        </div>
        <div class="row my-2 mx-auto w-25 align-items-center">
            <div class="col-3 text-end">
              <label for="password" class="col-form-label">Пароль: </label>
            </div>
            <div class="col-9 text-start">
              <input type="password" name="password" id="password" class="form-control" aria-describedby="password" value="">
            </div>
        </div>
        
        <div class="row mx-auto my-3 w-25  ">
            <div class="col-12 text-end">
                <input type="submit" value="Войти">
            </div>
        </div>
    </form>
        <div class="row mx-auto my-4 w-25  ">
            <div class="col text-center">
                Нет аккаунта? <a href="registrationForm">Зарегистрируйся</a>
            </div>
        </div>
  
