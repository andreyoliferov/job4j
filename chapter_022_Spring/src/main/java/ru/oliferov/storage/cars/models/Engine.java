package ru.oliferov.storage.cars.models;


import javax.persistence.*;

/**
 * @autor aoliferov
 * @since 10.03.2019
 */
@Entity
@Table(name = "engine")
public class Engine {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "power")
    private int power;

    public Engine() {
    }

    public Engine(int power) {
        this.power = power;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
