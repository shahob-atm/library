package uz.pdp.online.library.servlet.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import uz.pdp.online.library.dao.AuthUserDAO;
import uz.pdp.online.library.dao.AuthUserOTPDAO;
import uz.pdp.online.library.entity.AuthUser;
import uz.pdp.online.library.entity.AuthUserOTP;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

@WebServlet(name = "AuthActivateAccountServlet", value = "/activation")
public class AuthActivateAccountServlet extends HttpServlet {
    private final AuthUserDAO authUserDAO = new AuthUserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");

        AuthUserOTPDAO authUserOTPDAO = AuthUserOTPDAO.getInstance();
        AuthUserOTP authUserOTP = authUserOTPDAO.findByUserID(token);

        if (authUserOTP == null) {
            response.sendError(400, "Token is invalid");
            return;
        }

        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("Asia/Tashkent"));

        if (currentTime.isAfter(authUserOTP.getValidTill())) {
            response.sendError(400, "Token is expired");
            return;
        }

        AuthUser authUser = authUserDAO.findById(token);

        authUser.setStatus(AuthUser.Status.ACTIVE);
        authUserDAO.update(authUser);

        response.sendRedirect("/auth/login");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
