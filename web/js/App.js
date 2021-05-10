 import {authModule} from './AuthModule.js';
 import {bookModule} from './BookModule.js';

 document.getElementById("addBook").onclick = function(){
     document.getElementById('info').innerHTML='&nbsp;';
     bookModule.printFormAddBook();
 }
 document.getElementById("loginForm").onclick = function(){
     document.getElementById('info').innerHTML='&nbsp;';
     authModule.printLoginForm();
 }
 document.getElementById("aRegistration").onclick = function(){
    document.getElementById('info').innerHTML='&nbsp;';
     authModule.registration();
 }
 document.getElementById("logout").onclick = function(){
    document.getElementById('info').innerHTML='&nbsp;';
     authModule.logout();
 }

 authModule.toogleMenu();
