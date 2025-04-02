package uz.pdp.online.library.servlet.file;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.pdp.online.library.dao.UploadDAO;
import uz.pdp.online.library.entity.Upload;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet(name = "FileStorageDownloadServlet", urlPatterns = "/file/download")
public class FileStorageDownloadServlet extends HttpServlet {
    private static final Path rootPath = Path.of(System.getProperty("user.dir"), "files");
    private static final UploadDAO uploadDAO = new UploadDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String fileID = request.getParameter("fileID");

        Upload upload = uploadDAO.findById(fileID);
        if (upload == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Fayl bazada topilmadi!");
            return;
        }

        Path path = rootPath.resolve(upload.getGeneratedName());
        if (!Files.exists(path)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Fayl tizimda topilmadi!");
            return;
        }

        String encodedFileName = URLEncoder.encode(upload.getOriginalName(), StandardCharsets.UTF_8).replaceAll("\\+", "%20");

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);
        response.setContentLength((int) Files.size(path));

        try (OutputStream out = response.getOutputStream()) {
            Files.copy(path, out);
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
