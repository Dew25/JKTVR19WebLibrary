 import {authModule} from './AuthModule.js';

 document.getElementById("loginForm").onclick = function(){
     authModule.printLoginForm();
 }
 document.getElementById("aRegistration").onclick = function(){
     authModule.registration();
 }

