/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import entity.Book;
import entity.BookPictures;
import entity.BookTexts;
import entity.Picture;
import entity.Text;
import entity.User;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import json.builders.JsonBookBuilder;
import json.builders.JsonUserBuilder;
import session.BookFacade;
import session.BookPicturesFacade;
import session.BookTextsFacade;
import session.PictureFacade;
import session.ReaderFacade;
import session.TextFacade;
import session.UserFacade;
import session.UserRolesFacade;
import tools.EncryptPassword;

/**
 *
 * @author jvm
 */
@MultipartConfig
@WebServlet(name = "JsonAdminServlet", urlPatterns = {
    "/listUsersJson",
   
        
})
public class JsonAdminServlet extends HttpServlet {
    @EJB private TextFacade textFacade;
    @EJB private PictureFacade pictureFacade;
    @EJB private BookFacade bookFacade;
    @EJB private BookPicturesFacade bookPicturesFacade;
    @EJB private BookTextsFacade bookTextsFacade;
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
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String uploadFolder = JsonManagerServlet.pathToUploadDir.getString("dir");
        JsonObjectBuilder job = null;
        String json = null;
        String path = request.getServletPath();
        switch (path) {
            case "/listUsersJson":
                List<User> listUsers = userFacade.findAll();
                JsonArrayBuilder jab = Json.createArrayBuilder();
                for(User u : listUsers){
                    jab.add(new JsonUserBuilder().createUserJson(u));
                }  
                job = Json.createObjectBuilder();
                    job.add("info", "Список пользователей")
                    .add("requestStatus","true")
                    .add("listUsers", jab.build());
                 
                json = job.build().toString();
                break;
        }
        if(json == null || "".equals(json)){
            job = Json.createObjectBuilder();
            job.add("info", "Ошибка обработки запроса");
            job.add("requestStatus","false");     
            JsonObject jsonResponse = job.build();
            json = jsonResponse.toString();
        }
        try (PrintWriter out = response.getWriter()) {
            out.println(json);
        }
    }
    private String getFileName(Part part){
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")){
            if(content.trim().startsWith("filename")){
                return content
                        .substring(content.indexOf('=')+1)
                        .trim()
                        .replace("\"",""); 
            }
        }
        return null;
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
