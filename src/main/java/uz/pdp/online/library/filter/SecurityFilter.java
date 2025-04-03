package uz.pdp.online.library.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import uz.pdp.online.library.dao.AuthUserDAO;
import uz.pdp.online.library.entity.AuthUser;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@WebFilter(filterName = "SecurityFilter", value = "/*")
public class SecurityFilter implements Filter {
    private static final AuthUserDAO authUserDAO = new AuthUserDAO();
    private static final Predicate<String> isAdminPages = (uri) -> uri.startsWith("/admin");

    private static final List<String> WHITE_LIST = List.of(
            "/",
            "/book/list",
            "/auth/login",
            "/auth/register",
            "/activation.*"
    );

    private static final Predicate<String> isOpen = o -> {
        for (String s : WHITE_LIST) {
            if (o.matches(s)) {
                return true;
            }
        }
        return false;
    };

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String requestURI = request.getRequestURI();
        System.out.println("requestURI = " + requestURI);

        if (checkForRememberMe(request)) {
            chain.doFilter(request, response);

        } else {
            if (!isOpen.test(requestURI)) {
                HttpSession session = request.getSession();

                Object id = session.getAttribute("id");
                Object role = session.getAttribute("role");

                if (Objects.isNull(id)) {
                    response.sendRedirect("/auth/login?next=" + requestURI);

                } else {
                    if (Objects.equals(AuthUser.Role.USER, role) && isAdminPages.test(requestURI)) {
                        response.sendError(403, "Permission denied");

                    } else {
                        chain.doFilter(request, response);
                    }
                }

            } else {
                chain.doFilter(request, response);
            }
        }
    }

    private boolean checkForRememberMe(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id") != null) return true;
        for (Cookie cookie : request.getCookies()) {
            String cookieName = cookie.getName();
            if (cookieName.equals("rememberMe")) {
                AuthUser authUser = authUserDAO.findById(cookie.getValue());
                session.setAttribute("email", authUser.getEmail());
                session.setAttribute("role", authUser.getRole());
                session.setAttribute("id", authUser.getId());
                return true;
            }
        }
        return false;
    }
}
