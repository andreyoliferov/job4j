package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static parser.HunterApp.LOG;

/**
 * @autor aoliferov
 * @since 12.11.2018
 */
public class ParserSqlRu extends Hunter {

    public ParserSqlRu(StoreSQL store) {
        super(store, "sql.ru");
    }

    @Override
    public void perform() {
        boolean parse = true;
        Document html;
        Elements vacancies;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yy, HH:mm", Locale.forLanguageTag("ru"));
        int i = 1;
        try {
            while (parse) {
                html = Jsoup.connect(String.format("http://www.sql.ru/forum/job-offers/%s", i++)).get();
                vacancies = html.select(".forumTable tr");
                for (Element e : vacancies) {
                    Elements columnTheme = e.getElementsByClass("postslisttopic");
                    String name = columnTheme.text().split("\\(1")[0].replaceAll("\\[.+]", "");
                    if (toNext(name)) {
                        continue;
                    }
                    LocalDateTime date = parseDate(e, formatter);
                    if (stop(date)) {
                        parse = false;
                        break;
                    }
                    String author = e.getElementsByClass("altCol").get(0).text();
                    String link = e.select(".postslisttopic>a").attr("href");
                    String desc = Jsoup.connect(link).get().select(".msgBody").get(1).text();
                    store.addVacancy(new Vacancy(name, author, link, date, desc, source));
                }
            }
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * Распарсить дату последней активности в вакансии.
     * @param e html элемент
     * @param formatter формат даты
     * @return дата
     */
    private LocalDateTime parseDate(Element e, DateTimeFormatter formatter) {
        String dateText = e.getElementsByClass("altCol").get(1).text().replace("май", "мая");
        LocalDateTime date;
        if (dateText.contains("сегодня")) {
            dateText = dateText.replace("сегодня",
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("d MMM yy", Locale.forLanguageTag("ru"))));
        } else if (dateText.contains("вчера")) {
            dateText = dateText.replace("вчера",
                    LocalDateTime.now().minus(Period.ofDays(1))
                            .format(DateTimeFormatter.ofPattern("d MMM yy", Locale.forLanguageTag("ru"))));
        }
        date = LocalDateTime.parse(dateText, formatter);
        return date;
    }

    /**
     * Взвращает булево условие перехода к следующей вакансии при парсинге.
     * @param name имя вакансии
     * @return true если вакансия не подходит по параметрам.
     */
    private boolean toNext(String name) {
        String nameToLower = name.toLowerCase();
        return (name.contains("Важно:") || name.length() == 0)
                || !(nameToLower.contains("java") && !nameToLower.contains("javascript")
                    && !nameToLower.contains("java script"));
    }

    /**
     * Взвращает булево условие прекращения работы функции.
     * @param date дата последней активности по вакансии.
     * @return true если вакансия не подходит по параметрам.
     */
    private boolean stop(LocalDateTime date) {
        return date.getYear() != LocalDate.now().getYear() || date.isBefore(last);
    }
}
