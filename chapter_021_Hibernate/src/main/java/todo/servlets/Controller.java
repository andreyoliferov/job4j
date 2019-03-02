package todo.servlets;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import todo.Item;
import todo.store.BDStore;
import todo.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * @autor aoliferov
 * @since 27.02.2019
 */
public class Controller extends HttpServlet {

    public static final Logger LOG = LogManager.getLogger(Controller.class);
    private final Store store = BDStore.getInstance();
    private final ObjectMapper mapper = new ObjectMapper();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String scope = req.getParameter("scope");
        List<Item> result;
        if ("unfulfilled".equals(scope)) {
            result = store.getUnfulfilled();
        } else {
            result = store.getAll();
        }
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(mapper.writeValueAsString(result));
        writer.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Response response = new Response();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
            String json = reader.lines().reduce(String::concat).orElseThrow(Exception::new);
            Item item = mapper.readValue(json, Item.class);
            item.setCreated(new Timestamp(System.currentTimeMillis()));
            store.addTask(item);
            response.setSuccessResult();
            response.setId(item.getId().toString());
        } catch (Exception e) {
            response.setErrorResult();
            response.setMessage(e.getMessage());
            LOG.error(e);
        }
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(mapper.writeValueAsString(response));
        writer.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Response response = new Response();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
            String json = reader.lines().reduce(String::concat).orElseThrow(Exception::new);
            Item item = mapper.readValue(json, Item.class);
            store.deleteTask(item);
            response.setSuccessResult();
        } catch (Exception e) {
            response.setErrorResult();
            response.setMessage(e.getMessage());
            LOG.error(e);
        }
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(mapper.writeValueAsString(response));
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Response response = new Response();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
            String json = reader.lines().reduce(String::concat).orElseThrow(Exception::new);
            executePost(json, response);
        } catch (Exception e) {
            response.setErrorResult();
            response.setMessage(e.getMessage());
            LOG.error(e);
        }
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(mapper.writeValueAsString(response));
        writer.flush();
    }

    private void executePost(String json, Response response) throws Exception {
        JsonNode node = mapper.readTree(json);
        String func = node.get("function").textValue();
        if ("update".equals(func)) {
            String taskStr = node.get("task").toString();
            Item task = mapper.readValue(taskStr, Item.class);
            store.updateTask(task);
            response.setSuccessResult();
        } else {
            response.setErrorResult();
            response.setMessage("Unexpected function");
        }
    }

    @JsonInclude(Include.NON_NULL)
    private static class Response {

        private String result;
        private String id;
        private String message;

        public void setResult(String result) {
            this.result = result;
        }

        public String getResult() {
            return result;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setSuccessResult() {
            this.result = "success";
        }

        public void setErrorResult() {
            this.result = "error";
        }
    }
}
