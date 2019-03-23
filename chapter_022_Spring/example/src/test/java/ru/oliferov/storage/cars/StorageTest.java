package ru.oliferov.storage.cars;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeClass;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.testng.Assert.*;

/**
 * @autor aoliferov
 * @since 10.03.2019
 */
public class StorageTest {

    private SessionFactory sessionFactory;

    @BeforeClass
    public void start() {
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

    private <T> T tx(final Function<Session, T> command) {
        T result;
        try (final Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                result = command.apply(session);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                e.printStackTrace();
                result = null;
            }
        }
        return result;
    }

    private void tc(Consumer<Session> command) {
        tx(session -> {
            command.accept(session);
            return null;
        });
    }

    //TODO изменить тесты в связи с изменением структуры БД
    /*

    @Test
    public void whenEngineAddThenEngineGet() {
        Engine engine = new Engine(120);
        int id = (int) tx(session -> session.save(engine));
        Engine expect = tx(session -> session.get(Engine.class, id));
        assertEquals(expect.getPower(), 120);
        tc(session -> session.delete(expect));
    }

    @Test
    public void whenBodyAddThenBodyGet() {
        Body body = new Body("20т");
        int id = (int) tx(session -> session.save(body));
        Body expect = tx(session -> session.get(Body.class, id));
        assertEquals(expect.getType(), "20т");
        tc(session -> session.delete(expect));
    }

    @Test
    public void whenTransmissionAddThenTransmissionGet() {
        Transmission transmission = new Transmission(true);
        int id = (int) tx(session -> session.save(transmission));
        Transmission expect = tx(session -> session.get(Transmission.class, id));
        assertTrue(expect.isAutomatic());
        tc(session -> session.delete(expect));
    }

    @Test
    public void whenCarAddThenCarGet() {
        Car car = new Car(new Engine(99), new Body("5т"), new Transmission(false));
        int id = (int) tx(session -> session.save(car));
        Car expect = tx(session -> session.get(Car.class, id));
        assertFalse(expect.getTransmission().isAutomatic());
        assertEquals(expect.getBody().getType(), "5т");
        assertEquals(expect.getEngine().getPower(), 99);
        tc(session -> session.delete(expect));
    }

    @Test
    public void whenEngineUpdateThenEngineGet() {
        Engine engine = new Engine(120);
        int id = (int) tx(session -> session.save(engine));
        engine.setPower(150);
        engine.setId(id);
        tc(session -> session.update(engine));
        Engine expect = tx(session -> session.get(Engine.class, id));
        assertEquals(expect.getPower(), 150);
        tc(session -> session.delete(expect));
    }

    @Test
    public void whenEngineUpdatePersistThenEngineGet() {
        Engine engine = new Engine(120);
        int id = tx(session -> {
            int res = (int) session.save(engine);
            engine.setPower(150);
            return res;
        });
        tc(session -> session.update(engine));
        Engine expect = tx(session -> session.get(Engine.class, id));
        assertEquals(expect.getPower(), 150);
        tc(session -> session.delete(expect));
    }

    @Test
    public void whenCarUpdateNewObjectThenCarGet() {
        Body body = new Body("12т");
        Engine engine = new Engine(180);
        Transmission transmission = new Transmission(true);
        int idBody = (int) tx(session -> session.save(body));
        int idEngine = (int) tx(session -> session.save(engine));
        int idTr = (int) tx(session -> session.save(transmission));
        body.setId(idBody);
        engine.setId(idEngine);
        transmission.setId(idTr);
        Car car = new Car(engine, body, transmission);
        int idCar = (int) tx(session -> session.save(car));
        car.setId(idCar);
        car.setBody(new Body("12тт"));
        car.setEngine(new Engine(181));
        car.setTransmission(new Transmission(false));
        tc(session -> session.update(car));
        Car expect = tx(session -> session.get(Car.class, idCar));
        assertEquals(expect.getEngine().getPower(), 181);
        assertNotEquals(expect.getEngine().getId(), engine.getId());
        assertEquals(expect.getBody().getType(), "12тт");
        assertNotEquals(expect.getBody().getId(), body.getId());
        assertFalse(expect.getTransmission().isAutomatic());
        assertNotEquals(expect.getTransmission().getId(), transmission.getId());
        tc(session -> {
            session.delete(expect);
            session.delete(body);
            session.delete(engine);
            session.delete(transmission);
        });
    }

    @Test
    public void whenCarUpdateThenCarGet() {
        Body body = new Body("12т");
        Engine engine = new Engine(180);
        Transmission transmission = new Transmission(true);
        int idBody = (int) tx(session -> session.save(body));
        int idEngine = (int) tx(session -> session.save(engine));
        int idTr = (int) tx(session -> session.save(transmission));
        body.setId(idBody);
        engine.setId(idEngine);
        transmission.setId(idTr);
        Car car = new Car(engine, body, transmission);
        int idCar = (int) tx(session -> session.save(car));
        car.setId(idCar);
        car.getEngine().setPower(200);
        car.getBody().setType("12тт");
        car.getTransmission().setAutomatic(false);
        tc(session -> session.update(car));
        Car expect = tx(session -> session.get(Car.class, idCar));
        assertEquals(expect.getEngine().getPower(), 200);
        assertEquals(expect.getEngine().getId(), engine.getId());
        assertEquals(expect.getBody().getType(), "12тт");
        assertEquals(expect.getBody().getId(), body.getId());
        assertFalse(expect.getTransmission().isAutomatic());
        assertEquals(expect.getTransmission().getId(), transmission.getId());
        tc(session -> session.delete(expect));
    }

    */


}
