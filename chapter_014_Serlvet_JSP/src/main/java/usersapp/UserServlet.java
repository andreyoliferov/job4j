package usersapp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

/**
 * @autor aoliferov
 * @since 19.11.2018
 */
public class UserServlet extends HttpServlet {

    private final Validate logic = ValidateService.getInstance();
    private final DispatchActions actions = new DispatchActions();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(logic.findAll().toString());
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Map<String, String[]> parameters = req.getParameterMap();
        String result;
        try {
            result = actions.execute(action, parameters);
        } catch (NullPointerException e) {
            result = "error: incorrect request";
        } catch (UserException e) {
            result = e.getMessage();
        }
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(result);
        writer.flush();
    }

    private class DispatchActions {

        private HashMap<String, Function<Map<String, String[]>, String>> dispatch =  new HashMap<>();

        private DispatchActions() {
            dispatch.put(
                    "add",
                    (value)-> {
                        logic.add(
                                new User(
                                        value.get("name")[0],
                                        value.get("login")[0],
                                        value.get("email")[0]
                                ));
                        return "User created successfully!";
                    });
            dispatch.put(
                    "update",
                    (value)-> {
                        logic.update(
                                new User(
                                        UUID.fromString(value.get("id")[0]),
                                        value.get("name")[0],
                                        value.get("login")[0],
                                        value.get("email")[0]
                                ));
                        return "User successfully updated!";
                    });
            dispatch.put(
                    "delete",
                    (value)-> {
                        logic.delete(UUID.fromString(value.get("id")[0]));
                        return "User successfully deleted!";
                    });
        }

        private String execute(String name, Map<String, String[]> param) {
            return dispatch.get(name).apply(param);
        }
    }
}
