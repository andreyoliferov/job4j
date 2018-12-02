package usersapp.servlets;

import usersapp.UserException;
import usersapp.Validate;
import usersapp.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @autor aoliferov
 * @since 24.11.2018
 */
public class UserListServlet extends HttpServlet {

    private final Validate logic = ValidateService.getInstance();

    /**
     * Get запрос
     * отображение списка пользователей
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("users", logic.findAll());
        } catch (UserException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("WEB-INF/pages/listPage.jsp").forward(req, resp);
    }
}
