/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import entity.Reader;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.ReaderFacade;
import session.UserFacade;
import session.UserRolesFacade;
import tools.EncryptPassword;

/**
 *
 * @author jvm
 */
@WebServlet(name = "JsonLoginServlet", urlPatterns = {
    "/registrationUserJson",
    
})
public class JsonLoginServlet extends HttpServlet {
    @EJB private ReaderFacade readerFacade;
    @EJB private UserFacade userFacade;
    @EJB private UserRolesFacade userRolesFacade;
    
    private EncryptPassword encryptPassword = new EncryptPassword();
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String json = null;
        String path = request.getServletPath();
        switch (path) {
            case "/registrationUserJson":
                JsonReader jsonReader = Json.createReader(request.getReader());
                JsonObject jsonObject = jsonReader.readObject();
                String firstname = jsonObject.getString("firstname", "");
                String lastname = jsonObject.getString("lastname", "");
                String phone = jsonObject.getString("phone", "");
                String login = jsonObject.getString("login", "");
                String password = jsonObject.getString("password", "");
                if(firstname == null || "".equals(firstname)
                        || lastname == null || "".equals(lastname) 
                        || phone == null || "".equals(phone)
                        || login == null || "".equals(login)
                        || password == null || "".equals(password)
                        ){
                    JsonObjectBuilder job = Json.createObjectBuilder();
                    job.add("info", "Пользователь не создан");
                    job.add("requestStatus","false");     
                    JsonObject jsonResponse = job.build();
                    json = jsonResponse.toString();
                    break;
                }
                Reader reader = new Reader(firstname, lastname, phone);
                readerFacade.create(reader);
                String salt = encryptPassword.createSalt();
                password = encryptPassword.createHash(password, salt);
                User user= new User(login, password, salt, reader);
                try {
                    userFacade.create(user);
                } catch (Exception e) {
                    readerFacade.remove(reader);
                    JsonObjectBuilder job = Json.createObjectBuilder();
                    job.add("info", "Может такой пользователь уже есть");
                    job.add("requestStatus","false");     
                    JsonObject jsonResponse = job.build();
                    json = jsonResponse.toString();
                    break;
                }
                userRolesFacade.setNewRole("READER", user);
                JsonObjectBuilder job = Json.createObjectBuilder();
                job.add("info", "Пользователь "+user.getLogin()+" создан");
                job.add("requestStatus","true");     
                JsonObject jsonResponse = job.build();
                json = jsonResponse.toString();
                break;
        }
        if(json == null || "".equals(json)){
            JsonObjectBuilder job = Json.createObjectBuilder();
            job.add("info", "Ошибка обработки запроса");
            job.add("requestStatus","false");     
            JsonObject jsonResponse = job.build();
            json = jsonResponse.toString();
        }
        try (PrintWriter out = response.getWriter()) {
            out.println(json);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
