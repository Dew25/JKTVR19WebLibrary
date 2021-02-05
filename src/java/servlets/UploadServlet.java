/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.BookPictures;
import entity.Picture;
import entity.Text;
import entity.User;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import session.PictureFacade;
import session.TextFacade;
import session.UserRolesFacade;

/**
 *
 * @author Melnikov
 */
@WebServlet(name = "UploadServlet", urlPatterns = {
    "/picturesBookUploadForm",
    "/uploadPucturesBook",
    "/textsBookUploadForm",
    "/uploadTextsBook",
})
@MultipartConfig()
public class UploadServlet extends HttpServlet {
    @EJB private UserRolesFacade userRolesFacade;
    @EJB private PictureFacade pictureFacade;
    @EJB private TextFacade textFacade;
    List<String>pathToUploadFiles;
    private User authUser;
    public static final ResourceBundle settingUpload = ResourceBundle.getBundle("property.settingUpload");
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
        HttpSession httpSession = request.getSession(false);
        if(httpSession == null){
            request.setAttribute("info", "У вас нет прав для доступа!");
            request.getRequestDispatcher("/loginForm").forward(request, response);
            return;
        }
        this.authUser = (User) httpSession.getAttribute("user");
        if(this.authUser == null){
            request.setAttribute("info", "У вас нет прав для доступа!");
            request.getRequestDispatcher("/loginForm").forward(request, response);
            return;
        }
        boolean isRole = userRolesFacade.isRole("MANAGER",this.authUser);
        if(!isRole){
            request.setAttribute("info", "У вас нет прав для доступа!");
            request.getRequestDispatcher("/loginForm").forward(request, response);
            return;
        }
        StringBuilder typeUploadFile = new StringBuilder();;
        String path = request.getServletPath();
        switch (path) {
            case "/picturesBookUploadForm":
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("picturesBookUploadForm")).forward(request, response);
                return;
            case "/uploadPucturesBook":
                typeUploadFile.append("Picture");
                setPathToUploadFiles(request, typeUploadFile.toString());
                break;
            case "/textsBookUploadForm":
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("textsBookUploadForm")).forward(request, response);
                return;
            case "/uploadTextsBook":
                typeUploadFile.append("Text");
                setPathToUploadFiles(request, typeUploadFile.toString());
                break;
        }
        // Получаем массив строк с описаниями файлов
        String[] descriptions = request.getParameterValues("descriptions");
        if(descriptions == null || descriptions.length == 0){
            request.setAttribute("info", "Заполните описание файла");
            request.getRequestDispatcher("/addBook").forward(request, response);
            return;
        }
        for(int i = 0; i < pathToUploadFiles.size(); i++){
            switch (typeUploadFile.toString()) {
                case "Picture":
                    Picture pictures = new Picture(descriptions[i]+" (file)", pathToUploadFiles.get(i), null);
                    pictureFacade.create(pictures);
                    break;
                case "Text":
                    Text text = new Text(descriptions[i]+" (file)", pathToUploadFiles.get(i), null);
                    textFacade.create(text);
                    break;
            }
           
        }
        request.setAttribute("info", "Файлы загружены");
        request.getRequestDispatcher("/addBook").forward(request, response);
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
    private void setPathToUploadFiles(HttpServletRequest request, String folderName) 
            throws IOException, ServletException{
        //Определяем путь до директории UploadDir
        String rootUploadFolder = UploadServlet.settingUpload.getString("dir");
        //Определяем имя приложения без ведущего слеша (/JKTVR19WebLibrary -> JKTVR19WebLibrary).
        String appName = request
                .getServletContext()
                .getContextPath()
                .substring(1, request.getServletContext().getContextPath().length()-1);
        // Определяем уникальный идентификатор читателя
        String readerId = this.authUser.getReader().getId().toString();
        StringBuilder uploadFolder = new StringBuilder();
        // Определяем путь к каталогу в котором будет храниться файл
        // (D:\UploadDir\JKTVR19WebLibrary\1\Picture 
        //  или
        // (D:\UploadDir\JKTVR19WebLibrary\1\Text
        // в зависимости от параметра folderName
        uploadFolder.append(rootUploadFolder)
                     .append(File.separator)
                     .append(appName)
                     .append(File.separator)
                     .append(readerId)
                     .append(File.separator)
                     .append(folderName);
        // Создаем цепочку вложенных директорий
        new File(uploadFolder.toString()).mkdirs();
        // получаем список екземпляров класса Part инициированного веб контейнером
        // в нем описан поток загрузки файла (их может быть больше 1, если мы загружаем сразу несколько файлов)
        List<Part> fileParts = request
                .getParts()// получаем список экземпляров класса Part
                .stream() // список превращаем в поток
                //отфильтровываем только данные загрузки файлов по имени
                .filter(part -> UploadServlet.settingUpload.getString("fileName").equals(part.getName()))//"fileName" - ключ файла свойств, указывающий на значение параметра name в теге <input type="file"...
                .collect(Collectors.toList()); // формируем список экземпляров класса Part в которых описываются потоки загрузки файлов
        pathToUploadFiles = new ArrayList<>(); // создаем пустой список строк
        for(Part filePart : fileParts){ // проходим по списку класса Part
            String fileName = getFileName(filePart);
            if("".equals(fileName)) continue;
            StringBuilder pathToUploadFile = new StringBuilder(); // создаем пустой экземпляр класса StringBuilder
            pathToUploadFile.append(uploadFolder) 
                            .append(File.separator)
                            .append(fileName);// формируем путь к сохраняемому файлу
            File file = new File(pathToUploadFile.toString()); // создаем объект класса File, 
                                                               // для получения екземпляра класса Path для параметра методу Files.copy 
            try(InputStream fileContent = filePart.getInputStream()){ // получаем ресурс - поток данных загружаемого файла
                Files.copy(
                        fileContent, // потод данных
                        file.toPath(), // путь сохранения файла
                        StandardCopyOption.REPLACE_EXISTING // опция: пересоздать файл, если такой уже есть на диске.
                );
            }
            pathToUploadFiles.add(pathToUploadFile.toString());// список путей к файлу (обрабатывается в processRequest())
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
