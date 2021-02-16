
package servlets;

import entity.Book;
import entity.BookPictures;
import entity.BookTexts;
import entity.Picture;
import entity.Text;
import entity.User;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.BookFacade;
import session.BookPicturesFacade;
import session.BookTextsFacade;
import session.HistoryFacade;
import session.PictureFacade;
import session.ReaderFacade;
import session.TextFacade;
import session.UserRolesFacade;

/**
 *
 * @author Melnikov
 */
@WebServlet(name = "ManagerServlet", urlPatterns = {
    "/addBook",
    "/createBook",
    "/editBookForm",
    "/editBook",
  
    
    
    
        
})
public class ManagerServlet extends HttpServlet {
    @EJB
    private BookFacade bookFacade;
    @EJB
    private ReaderFacade readerFacade;
    @EJB
    private HistoryFacade historyFacade;
    @EJB private UserRolesFacade userRolesFacade;
    @EJB private TextFacade textFacade;
    @EJB private PictureFacade pictureFacade;
    @EJB private BookTextsFacade bookTextsFacade;
    @EJB private BookPicturesFacade bookPicturesFacade;
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
        User authUser = (User) httpSession.getAttribute("user");
        if(authUser == null){
            request.setAttribute("info", "У вас нет прав для доступа!");
            request.getRequestDispatcher("/loginForm").forward(request, response);
            return;
        }
        boolean isRole = userRolesFacade.isRole("MANAGER",authUser);
        if(!isRole){
            request.setAttribute("info", "У вас нет прав для доступа!");
            request.getRequestDispatcher("/loginForm").forward(request, response);
            return;
        }
        String path = request.getServletPath();
        switch (path) {
            case "/addBook":
                List<Picture> listPictures = pictureFacade.findAll();
                request.setAttribute("listPictures", listPictures);
                List<Text> listTexts = textFacade.findAll();
                request.setAttribute("listTexts", listTexts);
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("addBook")).forward(request, response);
                break;
            case "/createBook":
                String name = request.getParameter("name");
                String author = request.getParameter("author");
                String publishedYear = request.getParameter("publishedYear");
                String pictureId = request.getParameter("pictureId");
                String textId = request.getParameter("textId");
                if("".equals(name) || name == null
                        || "".equals(author) || author == null
                        || "".equals(publishedYear) || publishedYear == null
                        || "".equals(pictureId) || pictureId == null
                        || "".equals(textId) || textId == null){
                    request.setAttribute("name", name);
                    request.setAttribute("author", author);
                    request.setAttribute("publishedYear", publishedYear);
                    request.setAttribute("selectedPictureId", pictureId);
                    request.setAttribute("selectedTextId", textId);
                    request.setAttribute("info", "Заполните все поля");
                    request.getRequestDispatcher("/addBook").forward(request, response);
                    break;
                }
                Picture picture = pictureFacade.find(Long.parseLong(pictureId));
                Text text = textFacade.find(Long.parseLong(textId));
                Book book = new Book(name, author, publishedYear);
                bookFacade.create(book);
                BookPictures bookPictures = new BookPictures(book, picture);
                bookPicturesFacade.create(bookPictures);
                BookTexts bookTexts = new BookTexts(book, text);
                bookTextsFacade.create(bookTexts);
                request.setAttribute("info", "Книга \"" + book.getName() + "\" добавлена");
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("index")).forward(request, response);
                break;
            case "/editBookForm":
                String bookId = request.getParameter("bookId");
                book = bookFacade.find(Long.parseLong(bookId));
                request.setAttribute("book", book);
                picture = bookPicturesFacade.findPictureByBook(book);
                request.setAttribute("selectedPictureId", picture.getId());
                text = bookTextsFacade.findTextByBook(book);
                request.setAttribute("selectedTextId", text.getId());
                listPictures = pictureFacade.findAll();
                request.setAttribute("listPictures", listPictures);
                listTexts = textFacade.findAll();
                request.setAttribute("listTexts", listTexts);
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("editBook")).forward(request, response);
                break;
            case "/editBook":
                bookId = request.getParameter("bookId");
                name = request.getParameter("name");
                author = request.getParameter("author");
                publishedYear = request.getParameter("publishedYear");
                pictureId = request.getParameter("pictureId");
                textId = request.getParameter("textId");
                if("".equals(name) || name == null
                        || "".equals(bookId) || bookId == null
                        || "".equals(name) || name == null
                        || "".equals(author) || author == null
                        || "".equals(publishedYear) || publishedYear == null
                        || "".equals(pictureId) || pictureId == null
                        || "".equals(textId) || textId == null){
                    request.setAttribute("name", name);
                    request.setAttribute("author", author);
                    request.setAttribute("publishedYear", publishedYear);
                    request.setAttribute("selectedPictureId", pictureId);
                    request.setAttribute("selectedTextId", textId);
                    request.setAttribute("info", "Заполните все поля");
                    request.setAttribute("info", "Поля не должны быть пустыми");
                    request.getRequestDispatcher("/editBookForm").forward(request, response);
                    break;
                }
                book = bookFacade.find(Long.parseLong(bookId));
                picture = pictureFacade.find(Long.parseLong(pictureId));
                bookPicturesFacade.replacePicture(picture, book);
                text = textFacade.find(Long.parseLong(textId));
                bookTextsFacade.replaceText(text, book);
                book.setName(name);
                book.setAuthor(author);
                book.setPublishedYear(publishedYear);
                bookFacade.edit(book);
                request.setAttribute("bookId", bookId);
                request.setAttribute("info", "Книга отредактирована");
                request.getRequestDispatcher("/editBookForm").forward(request, response);
                break;
            case "/picturesBookUrlForm":
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("picturesBookUrlForm")).forward(request, response);
                break;
            case "/saveUrlToPicturesBook":
                // Получаем массив строк с описаниями файлов
                String[] descriptions = request.getParameterValues("description");
                String[] urls = request.getParameterValues("fileName");
                if(descriptions == null || descriptions.length == 0
                       || urls == null || urls.length == 0){
                    request.setAttribute("info", "Заполните описание изображения и вставьте url");
                    request.getRequestDispatcher("/addBook").forward(request, response);
                    return;
                }
                for(int i = 0; i < urls.length; i++){
                    if("".equals(urls[i])) continue;
                    picture = new Picture(descriptions[i]+" (url)", null, urls[i]);
                    pictureFacade.create(picture);
                }
                request.setAttribute("info", "Изображения по url добавлены");
                request.getRequestDispatcher("/addBook").forward(request, response);
                break;
            case "/textsBookUrlForm":
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("textsBookUrlForm")).forward(request, response);
                break;
            case "/saveUrlToTextsBook":
                // Получаем массив строк с описаниями файлов
                descriptions = request.getParameterValues("descriptions");
                urls = request.getParameterValues("urls");
                if(descriptions == null || descriptions.length == 0
                        || urls == null || urls.length == 0){
                    request.setAttribute("info", "Заполните хотя бы одну пару: описание и url");
                    request.getRequestDispatcher("/addBook").forward(request, response);
                    return;
                }
                
                for(int i = 0; i < urls.length; i++){
                    if("".equals(urls[i])) continue;
                    text = new Text(descriptions[i]+" (url)", null, urls[i]);
                    textFacade.create(text);
                }
                request.setAttribute("info", "Тексты по url добавлены");
                request.getRequestDispatcher("/addBook").forward(request, response);
                break;
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
