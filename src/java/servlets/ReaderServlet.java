/**
 * Алгоритм создания Web приложения на Java
 * 
 * 1. Создать WebApplication в NetBeans
 * 2. Создать сущностные классы c аннотациями в пакете entity раздела Source Packages
 * 3. Создать базу данных и настроить persistence.xml.
 * 4. Создать сессионные Java Enterprice Beans для каждого сущностного класса 
 *    с помощью помощника NetBeans
 * 5. Создать странички jsp в разделе Web Pages (/web). 
 *    Обязательная папка WEB-INF служит для сокрытия ресурсов от прямого доступа
 * 6. Создать сервлет "MyServlet" в пакете servlets раздела Source Packages.
 * 7. Настроить параметр аннотации @WebServlet(urlPatterns={...})
 *    При запросе от клиента содержащего эти параметры будет выполняться метод
 *    ProcessRequest сервлета "MyServlet", который управляется веб контейнером
 * 8. Получить текущий запрос из запроса "path"
 * 9. Обработать запрос в switch и с помощью метода getRequestDispatcher()
 *    отдать страничку jsp с данными клиенту. 
 *    Например:
 *    request.getRequestDispatcher("/WEB-INF/addBookForm.jsp").forward(request, response);
 * 10. Чтобы получить объект класса -фасада, необходимо применять аннотацию @EJB 
 *    для полей класса "MyServlet". С помощью этой аннотации контейнер сервера приложений внедрит
 *    указанный объект в поле.  
 * 
 */
package servlets;

import entity.Book;
import entity.History;
import entity.Reader;
import entity.User;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.BookFacade;
import session.HistoryFacade;
import session.ReaderFacade;
import session.UserRolesFacade;

/**
 *
 * @author Melnikov
 */
@WebServlet(name = "ReaderServlet", urlPatterns = {
    "/listBooks",
    "/takeOnBookForm",
    "/takeOnBook",
    "/returnBookForm",
    "/returnBook",
    
})
public class ReaderServlet extends HttpServlet {
    @EJB
    private BookFacade bookFacade;
    @EJB
    private ReaderFacade readerFacade;
    @EJB
    private HistoryFacade historyFacade;
    @EJB private UserRolesFacade userRolesFacade;
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
            request.setAttribute("info", "Авторизуйтесь, пожалуйста!");
            request.getRequestDispatcher("/loginForm").forward(request, response);
            return;
        }
        User authUser = (User) httpSession.getAttribute("user");
        if(authUser == null){
            request.setAttribute("info", "Авторизуйтесь, пожалуйста!");
            request.getRequestDispatcher("/loginForm").forward(request, response);
            return;
        }
        boolean isRole = userRolesFacade.isRole("READER",authUser);
        if(!isRole){
            request.setAttribute("info", "У вас нет прав для доступа!");
            request.getRequestDispatcher("/loginForm").forward(request, response);
            return;
        }
        String path = request.getServletPath();
        switch (path) {
            case "/listBooks":
                List<Book> listBooks = bookFacade.findAll();
                request.setAttribute("listBooks", listBooks);
                request.getRequestDispatcher("/WEB-INF/listBooks.jsp").forward(request, response);
                break;
            case "/takeOnBookForm":
                listBooks = bookFacade.findAll();
                request.setAttribute("listBooks", listBooks);
                request.getRequestDispatcher("/WEB-INF/takeOnBookForm.jsp").forward(request, response);
                break;
            case "/takeOnBook":
                String bookId = request.getParameter("bookId");
                Book book = bookFacade.find(Long.parseLong(bookId));
                Reader reader = authUser.getReader();
                History history = new History(book, reader, new GregorianCalendar().getTime(), null);
                historyFacade.create(history);
                request.setAttribute("info", "Книга \""+book.getName()+"\" выдана читателю");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                break;
            case "/returnBookForm":
                List<History> listReadBooks = historyFacade.findReadBook(authUser.getReader());
                request.setAttribute("listReadBooks", listReadBooks);
                request.getRequestDispatcher("/WEB-INF/returnBookForm.jsp").forward(request, response);
                break;    
            case "/returnBook":
                String historyId = request.getParameter("historyId");
                history = historyFacade.find(Long.parseLong(historyId));
                history.setReturnDate(new GregorianCalendar().getTime());
                historyFacade.edit(history);
                request.setAttribute("info", "Книга \""+history.getBook().getName()+"\" возвращена в библиотеку");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
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
