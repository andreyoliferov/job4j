package tracker;

/**
 * @autor Андрей Олиферов
 * @since 18.05.2018
 */
public class Item {
    private String id;
    private String name;
    private String desc;
    private long created;
    private String[] comments;

    public Item(String name, String desc, long created) {
        this.name = name;
        this.desc = desc;
        this.created = created;
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
}
