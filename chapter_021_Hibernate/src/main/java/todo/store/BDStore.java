package todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import todo.Item;
import todo.servlets.Controller;

import java.util.List;
import java.util.function.Function;

/**
 * @autor aoliferov
 * @since 26.02.2019
 */
public class BDStore implements Store {

    private SessionFactory sessionFactory;
    private static final BDStore INSTANCE = new BDStore();

    private BDStore() {
        init();
    }

    public static Store getInstance() {
        return INSTANCE;
    }

    private void init() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("todo.cfg.xml")
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            Controller.LOG.error(e);
        }
    }

    @Override
    public void addTask(Item item) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteTask(Item item) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(item);
            session.getTransaction().commit();
        }
    }

    @Override
    public void updateTask(Item item) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Item temp = session.get(Item.class, item.getId());
            if (item.getCreated() != null) {
                temp.setCreated(item.getCreated());
            }
            if (item.getName() != null) {
                temp.setName(item.getName());
            }
            if (item.getDesc() != null) {
                temp.setDesc(item.getDesc());
            }
            temp.setDone(item.getDone());
            session.update(temp);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Item> getAll() {
        return this.tx(
                session -> (List<Item>) session.createSQLQuery("SELECT * FROM items").addEntity(Item.class).list()
        );
    }

    @Override
    public List<Item> getUnfulfilled() {
        return this.tx(
                session -> (List<Item>) session.createSQLQuery("SELECT * FROM items WHERE done = false")
                        .addEntity(Item.class).list()
        );
    }

    /**
     * Pattern Wrapper
     * @param command
     * @param <T>
     * @return
     */
    private <T> T tx(final Function<Session, T> command) {
        T result = null;
        try (final Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                result = command.apply(session);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                Controller.LOG.error(e);
            }
        }
        return result;
    }
}
