package tracker;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @autor Андрей Олиферов
 * @since 18.05.2018
 */
public class Item {
    private String id;
    private String name;
    private String desc;
    private String created;
    private String[] comments;

    public Item(String name, String desc) {
        this.name = name;
        this.desc = desc;
        this.id = UUID.randomUUID().toString();
        this.created = DateFormat.getDateTimeInstance().format(new Date());
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
        return this.created;
    }
}
