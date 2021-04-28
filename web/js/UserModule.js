import { authModule } from "./AuthModule.js";

class UserModule{
    printAddUserForm(){
        document.getElementById('context').innerHTML=
        `<div class="w-50 mx-auto">
        <div class="h3 w-50 mx-auto my-5">Добавить читателя</div>
        
        <div class="row">
          <div class="col g-2">
            <input type="text" id="firstname" class="form-control" placeholder="First name" aria-label="First name">
          </div>
          <div class="col g-2">
            <input type="text" id="lastname" class="form-control" placeholder="Last name" aria-label="Last name">
          </div>
        </div>
        <div class="row">
          <div class="col-4 g-2">
            <input type="text" id="phone" class="form-control" placeholder="Phone" aria-label="Phone">
          </div>
        </div>
        <div class="row">
          <div class="col g-2">
            <input type="text" id="login" class="form-control" placeholder="Login" aria-label="Login">
          </div>
          <div class="col g-2">
            <input type="text" id="password" class="form-control" placeholder="Password" aria-label="Password">
          </div>
        </div>
        <div class="row my-3">
          <div class="col g-2">
            <input type="button" id="btnCreateUser" class="form-control bg-primary text-white" value="Зарегистрировать">
          </div>
        </div>
      </div>`;
      document.getElementById('btnCreateUser').onclick=function(){
          userModule.registration();
      }
    }

    async registration(){
        const firstname = document.getElementById('firstname').value;
        const lastname = document.getElementById('lastname').value;
        const phone = document.getElementById('phone').value;
        const login = document.getElementById('login').value;
        const password = document.getElementById('password').value;
        const user = {
            'firstname': firstname,
            'lastname': lastname,
            'phone': phone,
            'login': login,
            'password': password,
        };
        const response = await fetch('registrationUserJson',{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(user)
        });
        if(response.ok){
            let result = await response.json();
            authModule.printLoginForm();
            document.getElementById('info').innerHTML=result.info;
        }else{
            console.log("Error: Ошибка сервера");
        }
    }
}
const userModule = new UserModule();
export {userModule};