package todo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * @autor aoliferov
 * @since 25.02.2019
 */
public class Item {

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

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }


    public static void main(String[] args) {
        SessionFactory sessionFactory = null;
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("todo.cfg.xml") // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
        }
        assert sessionFactory != null;
        Item one = new Item("One","sdfsdf sdfsdf");
        Item two = new Item("A follow up event","sfsdfsdfe43r");
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(one);
            session.save(two);
            session.getTransaction().commit();
        }
    }
}
