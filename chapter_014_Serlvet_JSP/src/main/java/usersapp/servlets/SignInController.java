package usersapp.servlets;

import usersapp.Validate;
import usersapp.ValidateService;
import usersapp.items.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @autor aoliferov
 * @since 05.12.2018
 */
public class SignInController extends HttpServlet {

    private final Validate logic = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/pages/loginPage.jsp").forward(req, resp);
    }

    /**
     * Авторизация, выход из системы
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User user = logic.auth(login, password);
        if (user != null) {
            HttpSession session = req.getSession();
            synchronized (session) {
                session.setAttribute("currentUser", user);
                session.setAttribute("dataAccessReload", true);
            }
            resp.sendRedirect(String.format("%s/", req.getContextPath()));
        } else {
            req.setAttribute("error", "incorrect data");
            doGet(req, resp);
        }
    }

}
