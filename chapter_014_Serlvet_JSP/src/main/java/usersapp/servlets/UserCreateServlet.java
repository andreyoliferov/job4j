package usersapp.servlets;

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
public class UserCreateServlet extends HttpServlet {

    private final Validate logic = ValidateService.getInstance();

    /**
     * Get запрос
     * Отображает страницу для создания пользователя
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String html = createHtml(req.getContextPath(), "");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(html);
        writer.flush();
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
        String html = createHtml(req.getContextPath(), result);
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(html);
        writer.flush();
    }

    /**
     * Создание html страницы
     * @param contexPath contexPath
     * @param result результат запроса
     * @return html страница
     */
    private String createHtml(String contexPath, String result) {
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
                + "   <form action='" + contexPath +  "/create' method='post'>"
                + "       Name: <input type='text' name='name'/>"
                + "       Login: <input type='text' name='login'/>"
                + "       Email: <input type='text' name='email'/>"
                + "       <input type='submit' value='create'></input>"
                + "       </br>"
                +         result
                + "   </form>"
                + "</body>\n"
                + "</html>";
    }
}
