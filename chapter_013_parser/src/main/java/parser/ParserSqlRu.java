package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
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
                    LocalDateTime date = parseDate(e);
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
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Распарсить дату последней активности в вакансии.
     * @param e html элемент
     * @return дата
     */
    private LocalDateTime parseDate(Element e) {
        var lookup = lookupMonth();
        DateTimeFormatterBuilder baseBilder = new DateTimeFormatterBuilder()
                .appendPattern("d ")
                .appendText(ChronoField.MONTH_OF_YEAR, lookup)
                .appendPattern(" yy");
        DateTimeFormatterBuilder fullBilder = new DateTimeFormatterBuilder()
                .appendPattern("d ")
                .appendText(ChronoField.MONTH_OF_YEAR, lookup)
                .appendPattern(" yy, HH:mm");
        String dateText = e.getElementsByClass("altCol").get(1).text().replace("май", "мая");
        LocalDateTime date;
        if (dateText.contains("сегодня")) {
            dateText = dateText.replace("сегодня",
                    LocalDateTime.now().format(baseBilder.toFormatter()));
        } else if (dateText.contains("вчера")) {
            dateText = dateText.replace("вчера",
                    LocalDateTime.now().minus(Period.ofDays(1))
                            .format(baseBilder.toFormatter()));
        }
        date = LocalDateTime.parse(dateText, fullBilder.toFormatter());
        return date;
    }

    private Map<Long, String> lookupMonth() {
        Map<Long, String> lookup = new HashMap<>();
        lookup.put(1L, "янв");
        lookup.put(2L, "фев");
        lookup.put(3L, "мар");
        lookup.put(4L, "апр");
        lookup.put(5L, "мая");
        lookup.put(6L, "июн");
        lookup.put(7L, "июл");
        lookup.put(8L, "авг");
        lookup.put(9L, "сен");
        lookup.put(10L, "окт");
        lookup.put(11L, "ноя");
        lookup.put(12L, "дек");
        return lookup;
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
