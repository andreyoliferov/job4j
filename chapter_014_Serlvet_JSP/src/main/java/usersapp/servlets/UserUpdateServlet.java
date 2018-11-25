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
        String html = createHtml(req.getContextPath(), user, result);
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(html);
        writer.flush();
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
        String html = createHtml(req.getContextPath(), user, result);
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(html);
        writer.flush();
    }

    /**
     * Создание Html страницы
     * @param contextPath ContextPath
     * @param user пользователь для отображения
     * @param result результат выполнения запроса
     * @return html страница
     */
    private String createHtml(String contextPath, User user, String result) {
        String id = "";
        String name = "";
        String login = "";
        String email = "";
        if (user != null) {
            id = user.getId().toString();
            name = user.getName();
            login = user.getLogin();
            email = user.getEmail();
        }
        return "<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <title>List users</title>\n"
                + "    <style type=\"text/css\">"
                + "    TD, TH {\n"
                + "    border: 2px solid black; } "
                + "    </style>"
                + "</head>\n"
                + "<body>\n"
                + "   <form action='" + contextPath +  "/edit' method='post'>"
                + "       <input name='id' type='hidden' value='" + id + "'/>"
                + "       Name: <input type='text' name='name' value='" + name + "'/>"
                + "       Login: <input type='text' name='login' value='" + login + "'/>"
                + "       Email: <input type='text' name='email' value='" + email + "'/>"
                + "       <input type='submit' value='edit'></input>"
                + "       </br>"
                +         result
                + "   </form>"
                + "</body>\n"
                + "</html>";
    }
}
