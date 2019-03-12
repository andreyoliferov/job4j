package ru.oliferov.storage.other;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import java.util.UUID;

/**
 * @autor aoliferov
 * @since 12.03.2019
 */
@Component("hbStorage")
@Scope("singleton")
public class HBStorage implements Storage {

    private SessionFactory sessionFactory;

    public HBStorage() {
        init();
    }

    public void init() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("storage.cfg.xml")
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            e.printStackTrace();
        }
    }

    @Override
    public UUID add(User user) {
        UUID id;
        try (final Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                session.save(user);
                id = user.getId();
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                e.printStackTrace();
                id = null;
            }
        }
        return id;
    }

    @Override
    public void delete(UUID id) {
        try (final Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                String hql = "delete User where id = :id";
                Query q = session.createQuery(hql).setParameter("id", id);
                q.executeUpdate();
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                e.printStackTrace();
            }
        }
    }
}
