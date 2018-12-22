package usersapp.servlets;


import com.fasterxml.jackson.databind.ObjectMapper;
import usersapp.items.Client;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @autor aoliferov
 * @since 18.12.2018
 */
public class JsonController extends HttpServlet {

    private final ConcurrentHashMap<UUID, Client> data = new ConcurrentHashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        PrintWriter pw = new PrintWriter(resp.getOutputStream());
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(pw, data.values());
        pw.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        String jsonStr = toStringBuilder(reader).toString();
        ObjectMapper mapper = new ObjectMapper();
        Client client = mapper.readValue(jsonStr, Client.class);
        data.put(client.getId(), client);
        super.doPost(req, resp);
    }

    private StringBuilder toStringBuilder(BufferedReader bReader) throws IOException {
        StringBuilder strBuilder = new StringBuilder();
        String str;
        while ((str = bReader.readLine()) != null) {
            strBuilder.append(str);
        }
        return strBuilder;
    }

}
