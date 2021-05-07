import { userModule } from "./UserModule.js";

class AuthModule{
    printLoginForm(){
        document.getElementById("context").innerHTML=
        `<h3 class="w-100 mt-5 text-center">Введите логин и пароль</h3>
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
                    <input id="btnEnter" type="button" value="Войти">
                </div>
            </div>
            <div class="row mx-auto my-4 w-25  ">
                <div class="col text-center">
                    Нет аккаунта? <a id="btnRegistration" href="#registrationForm">Зарегистрируйся</a>
                </div>
            </div>`;
        document.getElementById("btnEnter").onclick=function(){
            authModule.login();
        }
        document.getElementById("btnRegistration").onclick=function(){
            authModule.registration();
        }
        
    }
    async login(){
        //console.log("Нажата кнопка 'Login'");
        const login = document.getElementById('login').value;
        const password = document.getElementById('password').value;
        const credential = {
            "login":login,
            "password":password,
        }
        const response = await fetch('loginJson', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset:utf8'
            },
            body: JSON.stringify(credential)
        });
        if(response.ok){
            const result = await response.json();
            document.getElementById('info').innerHTML = result.info;
            console.log("Request status: "+result.requestStatus);
            document.getElementById('context').innerHTML='';
            authModule.toogleMenu();
            if(result.requestStatus){
                sessionStorage.setItem('token', result.token);
                sessionStorage.setItem('role', result.role);
            }else{
                if(sessionStorage.getItem('token') !== null){
                    sessionStorage.removeItem('token');
                    sessionStorage.removeItem('role');
                }
            }
        }

    }
    registration(){
       // console.log("Нажата кнопка 'registration'");
       userModule.printAddUserForm();
    }
    toogleMenu(){
        let role = null;
        if(sessionStorage.getItem('role') !== null){
            role = sessionStorage.getItem('role');
        }
        console.log('Auth: token - '+sessionStorage.getItem('token'));
        console.log('Auth: role - '+sessionStorage.getItem('role'));
        if(role === null){
            document.getElementById('loginForm').style.display = 'block';
            document.getElementById('addBook').style.display = 'none';
            document.getElementById('listBooks').style.display = 'none';
            document.getElementById('aRegistration').style.display = 'none';
            document.getElementById('listReaders').style.display = 'none';
            document.getElementById('takeOnBook').style.display = 'none';
            document.getElementById('returnBook').style.display = 'none';
            document.getElementById('adminPanel').style.display = 'none';
            document.getElementById('logout').style.display = 'none';
            document.getElementById('profile').style.display = 'none';
        }else if(role === 'READER'){
            document.getElementById('loginForm').style.display = 'none';
            document.getElementById('addBook').style.display = 'none';
            document.getElementById('listBooks').style.display = 'block';
            document.getElementById('aRegistration').style.display = 'none';
            document.getElementById('listReaders').style.display = 'none';
            document.getElementById('takeOnBook').style.display = 'block';
            document.getElementById('returnBook').style.display = 'block';
            document.getElementById('adminPanel').style.display = 'none';
            document.getElementById('logout').style.display = 'block';
            document.getElementById('profile').style.display = 'block';
            
        }else if(role==='MANAGER'){
            document.getElementById('loginForm').style.display = 'none';
            document.getElementById('addBook').style.display = 'block';
            document.getElementById('listBooks').style.display = 'block';
            document.getElementById('aRegistration').style.display = 'none';
            document.getElementById('listReaders').style.display = 'none';
            document.getElementById('takeOnBook').style.display = 'block';
            document.getElementById('returnBook').style.display = 'block';
            document.getElementById('adminPanel').style.display = 'none';
            document.getElementById('logout').style.display = 'block';
            document.getElementById('profile').style.display = 'block';
            
        }else if(role==='ADMIN'){
            document.getElementById('loginForm').style.display = 'none';
            document.getElementById('addBook').style.display = 'block';
            document.getElementById('listBooks').style.display = 'block';
            document.getElementById('aRegistration').style.display = 'block';
            document.getElementById('listReaders').style.display = 'block';
            document.getElementById('takeOnBook').style.display = 'block';
            document.getElementById('returnBook').style.display = 'block';
            document.getElementById('adminPanel').style.display = 'block';
            document.getElementById('logout').style.display = 'block';
            document.getElementById('profile').style.display = 'block';

        }
    }
    async logout(){
        const response = await fetch('logoutJson',{
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset:utf8'
            }
        });
        if(response.ok){
            const result = await response.json();
            if(result.requestStatus){
                sessionStorage.removeItem('token');
                sessionStorage.removeItem('role');
            }
        }
        authModule.toogleMenu();
    }
}
const authModule = new AuthModule();
export {authModule};