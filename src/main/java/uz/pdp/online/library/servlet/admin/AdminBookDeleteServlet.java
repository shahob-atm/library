package uz.pdp.online.library.servlet.admin;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.pdp.online.library.dao.BookDAO;
import uz.pdp.online.library.entity.Book;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet(name = "AdminBookDeleteServlet", urlPatterns = "/admin/book/delete/*")
public class AdminBookDeleteServlet extends HttpServlet {
    private static final BookDAO bookDAO = new BookDAO();
    private static final Path rootPath = Path.of(System.getProperty("user.dir"), "files");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookId = request.getParameter("bookId");

        if (bookId != null) {
            Book book = bookDAO.findById(bookId);
            if (book != null) {
                request.setAttribute("book", book);
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/delete_book.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookId = request.getParameter("bookId");

        if (bookId != null) {

            Book book = bookDAO.findById(bookId);

            if (book != null) {

                String generatedName = book.getFile().getGeneratedName();
                Path filePath = rootPath.resolve(generatedName);


                bookDAO.deleteById(bookId);

                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                }

                response.sendRedirect("/book/list");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Book not found");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid book ID");
        }
    }
}
