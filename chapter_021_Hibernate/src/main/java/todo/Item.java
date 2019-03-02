package todo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * @autor aoliferov
 * @since 25.02.2019
 */
public class Item implements Serializable {

    private UUID id;
    private String name;
    private String desc;
    private Timestamp created;
    private boolean done;

    public Item(String name, String desc) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.desc = desc;
        this.created = new Timestamp(System.currentTimeMillis());
        this.done = false;
    }

    public Item() {
        this.id = UUID.randomUUID();
        this.done = false;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public boolean getDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

}
