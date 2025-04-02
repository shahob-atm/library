package uz.pdp.online.library.servlet.admin;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import uz.pdp.online.library.dao.BookDAO;
import uz.pdp.online.library.dao.UploadDAO;
import uz.pdp.online.library.entity.Book;
import uz.pdp.online.library.entity.Upload;
import uz.pdp.online.library.util.StringUtils;
import uz.pdp.online.library.util.ValidatorUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@WebServlet(name = "AdminBookCreateServlet", value = "/admin/book/create")
@MultipartConfig
public class AdminBookCreateServlet extends HttpServlet {
    private static final Path rootPath = Path.of(System.getProperty("user.dir"), "files");
    private static final BookDAO bookDAO = new BookDAO();
    private static final UploadDAO uploadDAO = new UploadDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/create_book.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        Part file = request.getPart("file");
        String originalName = file.getSubmittedFileName();
        String extension = StringUtils.fileExtension(originalName);

        Upload upload = Upload.childBuilder()
                .generatedName(UUID.randomUUID() + "." + extension)
                .originalName(originalName)
                .extension(extension)
                .size(file.getSize())
                .mimeType(file.getContentType())
                .build();

        Upload savedUpload = null;

        try {
            ValidatorUtils.validate(upload);
            savedUpload = uploadDAO.save(upload);
        } catch (jakarta.validation.ConstraintViolationException e) {
            throw new IllegalArgumentException("Ma'lumotlar noto‘g‘ri: " + e.getMessage());
        }

        Book book = Book.childBuilder()
                .title(title)
                .description(description)
                .file(savedUpload)
                .build();

        try {
            ValidatorUtils.validate(book);
            bookDAO.save(book);
        }catch (jakarta.validation.ConstraintViolationException e) {
            throw new IllegalArgumentException("Ma'lumotlar noto‘g‘ri: " + e.getMessage());
        }

        Path pathToUpload = rootPath.resolve(upload.getGeneratedName());
        System.out.println(pathToUpload);
        Files.copy(file.getInputStream(), pathToUpload, StandardCopyOption.REPLACE_EXISTING);
        response.sendRedirect("/book/list");
    }
}
