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
import java.util.List;

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
        resp.setContentType("text/html");
        List<User> users = null;
        try {
            users = logic.findAll();
        } catch (UserException e) {
            e.printStackTrace();
        }
        String html = "<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "  <meta charset=\"UTF-8\">\n"
                + "  <title>List users</title>\n"
                + "  <style type=\"text/css\">"
                + "     TD, TH {\n"
                + "     border: 2px solid black; } "
                + "  </style>"
                + "</head>\n"
                + "<body>\n"
                +       formTable(users)
                + "</body>\n"
                + "</html>";
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(html);
        writer.flush();
    }

    private String formTable(List<User> users) {
        StringBuilder sb = new StringBuilder("<table>");
        if (users != null) {
            sb.append("<tr><th>id</th><th>name</th><th>login</th><th>email</th></tr>");
            for (User u : users) {
                sb.append("<tr>"
                        + "<td>" + u.getId() + "</td>"
                        + "<td>" + u.getName() + "</td>"
                        + "<td>" + u.getLogin() + "</td>"
                        + "<td>" + u.getEmail() + "</td>"
                        + "</tr>");
            }
        } else {
            sb.append("<tr><td>List is empty</td></tr>");
        }
        sb.append("</table>");
        return sb.toString();
    }
}
