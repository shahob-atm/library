package uz.pdp.online.library.servlet.admin;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.pdp.online.library.dao.BookDAO;
import uz.pdp.online.library.entity.Book;
import uz.pdp.online.library.util.ValidatorUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

@WebServlet(name = "AdminBookUpdateServlet", urlPatterns = "/admin/book/update/*")
public class AdminBookUpdateServlet extends HttpServlet {
    private static final BookDAO bookDAO = new BookDAO();
    private static final Path rootPath = Path.of(System.getProperty("user.dir"), "files");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookId = request.getParameter("bookId");

        if (bookId == null) {
            response.sendRedirect("/book/list");
            return;
        }

        Book book = bookDAO.findById(bookId);

        if (book == null) {
            response.sendRedirect("/book/list");
            return;
        }

        request.setAttribute("book", book);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/update_book.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookId = request.getParameter("bookId");
        String title = request.getParameter("title");
        String description = request.getParameter("description");

        Book book = bookDAO.findById(bookId);

        if (book == null) {
            response.sendRedirect("/book/list");
            return;
        }

        book.setTitle(title);
        book.setDescription(description);
        book.setUpdatedAt(LocalDateTime.now());

        try {
            ValidatorUtils.validate(book);
            bookDAO.update(book);
        }catch (jakarta.validation.ConstraintViolationException e) {
            throw new IllegalArgumentException("Ma'lumotlar noto‘g‘ri: " + e.getMessage());
        }

        response.sendRedirect("/book/list");
    }
}
