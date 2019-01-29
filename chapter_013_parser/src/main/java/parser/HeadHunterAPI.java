package parser;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static parser.HunterApp.LOG;

/**
 * @autor aoliferov
 * @since 13.11.2018
 */
public class HeadHunterAPI extends Hunter {

    private String method = "https://api.hh.ru/vacancies";

    protected HeadHunterAPI(StoreSQL store) {
        super(store, "hh.ru");
    }

    /**
     * Поиск на hh.ru и запись вакансий в бд.
     */
    @Override
    public void perform() {
        String req = createRequest();
        int pages = new JSONObject(requestGet(String.format(req, 0))).getInt("pages");
        for (int i = 0; i < pages; i++) {
            String response = requestGet(String.format(req, i));
            JSONObject main = new JSONObject(response);
            JSONArray arr = main.getJSONArray("items");
            for (Object o : arr) {
                JSONObject vacancy = (JSONObject) o;
                String id = vacancy.getString("id");
                String name = vacancy.getString("name");
                String author = vacancy.getJSONObject("employer").getString("name");
                String dateStr = vacancy.getString("created_at").split("\\+")[0];
                LocalDateTime date = LocalDateTime.parse(dateStr).truncatedTo(ChronoUnit.MINUTES);
                String link = vacancy.getString("alternate_url");
                String responseFull = requestGet(new StringBuilder(method).append("/").append(id).toString());
                String desc = new JSONObject(responseFull).getString("description");
                store.addVacancy(new Vacancy(name, author, link, date, desc, source));
            }
        }
    }

    /**
     * Формирование GET запроса к методу API.
     * @return строка запроса.
     */
    private String createRequest() {
        return new StringBuilder(method)
                .append("?area=2")
                .append("&text=NAME:(java+NOT+script+NOT+android)")
                .append("&employment=full")
                .append("&order_by=publication_time")
                .append(String.format("&date_from=%s", last))
                .append("&page=%s")
                .toString();
    }

    /**
     * Базовый метод выполнения GET запроса
     * @param urlToRead запрос
     * @return ответ
     */
    public static String requestGet(String urlToRead) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(urlToRead);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return result.toString();
    }

    /**
     * Mетод выполнения GET запроса посредством библиотеки okhttp
     * @param urlToRead запрос
     * @return ответ
     */
    public static String requestGetOkHttp(String urlToRead) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlToRead)
                .build();
        String result = null;
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                result = response.body().string();
            } else {
                LOG.error(String.format("Error of GET to :%s, error code: %s", urlToRead, response.code()));
            }
        }
        return result;
    }
}
