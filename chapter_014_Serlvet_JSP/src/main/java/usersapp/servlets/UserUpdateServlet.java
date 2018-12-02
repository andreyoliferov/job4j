package usersapp.servlets;

import usersapp.User;
import usersapp.UserException;
import usersapp.Validate;
import usersapp.ValidateService;

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
public class UserUpdateServlet extends HttpServlet {

    private final Validate logic = ValidateService.getInstance();

    /**
     * Get запрос
     * отображает страницу с заполненными полями пользователя для редактирования
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String result = "";
        User user = null;
        try {
            user = logic.findById(req.getParameterMap());
        } catch (UserException e) {
            result = e.getMessage();
        }
        req.setAttribute("user", user);
        req.setAttribute("result", result);
        req.getRequestDispatcher("WEB-INF/pages/updatePage.jsp").forward(req, resp);
    }

    /**
     * Post запрос
     * на редактирование пользователя
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> parameters = req.getParameterMap();
        String result;
        User user = null;
        try {
            user = logic.update(parameters);
            result = "User successfully updated!";
        } catch (UserException e) {
            result = e.getMessage();
        }
        req.setAttribute("user", user);
        req.setAttribute("result", result);
        req.getRequestDispatcher("WEB-INF/pages/updatePage.jsp").forward(req, resp);
    }
}
