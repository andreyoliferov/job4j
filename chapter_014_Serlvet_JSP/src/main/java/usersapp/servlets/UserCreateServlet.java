package usersapp.servlets;

import usersapp.UserException;
import usersapp.Validate;
import usersapp.ValidateService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @autor aoliferov
 * @since 24.11.2018
 */
public class UserCreateServlet extends HttpServlet {

    private final Validate logic = ValidateService.getInstance();

    /**
     * Get запрос
     * Отображает страницу для создания пользователя
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", logic.findAllRoles());
        req.getRequestDispatcher("WEB-INF/pages/createPage.jsp").forward(req, resp);
    }

    /**
     * Post запрос
     * на создание пользователя
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> parameters = req.getParameterMap();
        String result;
        try {
            result = "user created with id: " + logic.add(parameters).toString();
        } catch (UserException e) {
            result = e.getMessage();
        }
        req.setAttribute("result", result);
        req.getRequestDispatcher("WEB-INF/pages/createPage.jsp").forward(req, resp);
    }
}
