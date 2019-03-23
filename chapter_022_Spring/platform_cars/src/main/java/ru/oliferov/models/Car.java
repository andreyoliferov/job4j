package ru.oliferov.models;

import com.google.common.base.Objects;

import javax.persistence.*;

/**
 * @autor aoliferov
 * @since 10.03.2019
 */
@Entity
@Table(name = "car")
public class Car {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    /** Модель авто. */
    @JoinColumn(name = "model")
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Model model;


    /** Тип авто. */
    @JoinColumn(name = "type_car")
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private TypeCar type;


    /** Двигатель. */
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "engine")
    private Engine engine;


    /** Кузов. */
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "body")
    private Body body;

    /** Коробка передач. */
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "transmission")
    private Transmission transmission;


    /** Год выпуска. */
    @Column(name = "year")
    private int year;


    /** Пробег. */
    @Column(name = "mileage")
    private int mileage;


    public Car(Engine engine, Body body, Transmission transmission) {
        this.engine = engine;
        this.body = body;
        this.transmission = transmission;
    }

    public Car() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public TypeCar getType() {
        return type;
    }

    public void setType(TypeCar type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return id == car.id
                && year == car.year
                && mileage == car.mileage
                && Objects.equal(model, car.model)
                && Objects.equal(type, car.type)
                && Objects.equal(engine, car.engine)
                && Objects.equal(body, car.body)
                && Objects.equal(transmission, car.transmission);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, model, type, engine, body, transmission, year, mileage);
    }
}
