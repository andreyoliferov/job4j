package ru.oliferov.models;

import com.google.common.base.Objects;

import javax.persistence.*;

/**
 * @autor aoliferov
 * @since 10.03.2019
 */
public class Car {

    private int id;
    private Engine engine;
    private Body body;
    private Transmission transmission;

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

                && Objects.equal(engine, car.engine)
                && Objects.equal(body, car.body)
                && Objects.equal(transmission, car.transmission);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, engine, body, transmission);
    }
}
