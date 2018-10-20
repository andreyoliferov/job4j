package tracker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

/**
 * @autor Андрей Олиферов
 * @since 18.05.2018
 */
public class Item {
    private String id;
    private String name;
    private String desc;
    private LocalDateTime created;
    private ArrayList<String> comments;

    public Item(String name, String desc) {
        this.name = name;
        this.desc = desc;
        this.id = UUID.randomUUID().toString();
        this.created = LocalDateTime.now().withNano(0); // DateFormat.getDateTimeInstance(); //.format(new Date());
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String name) {
        this.desc = desc;
    }

    public String getCreated() {
        return created.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public LocalDateTime getCreatedLocalDate() {
        return created;
    }

    @Override
    public String toString() {
        return "Заявка{"
                + "id='" + id + '\''
                + ", name='" + name + '\''
                + ", desc='" + desc + '\''
                + ", created='" + getCreated() + '\''
                + '}';
    }
}
