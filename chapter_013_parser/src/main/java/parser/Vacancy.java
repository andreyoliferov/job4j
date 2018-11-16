package parser;

import java.time.LocalDateTime;

/**
 * @autor aoliferov
 * @since 12.11.2018
 */
public class Vacancy {

    private int id;
    private String name;
    private String author;
    private String link;
    private LocalDateTime date;
    private String desc;
    private String source;

    public Vacancy(String name, String author, String link, LocalDateTime date, String desc, String source) {
        this.name = name;
        this.author = author;
        this.link = link;
        this.date = date;
        this.desc = desc;
        this.source = source;
    }

    public Vacancy(int id, String name, String author, String link, LocalDateTime date, String desc, String source) {
        this.id = id;
        new Vacancy(name, author, link, date, desc, source);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDesc() {
        return desc;
    }

    public String getSource() {
        return source;
    }

    public String getAuthor() {
        return author;
    }

    public void setId(int id) {
        this.id = id;
    }
}
