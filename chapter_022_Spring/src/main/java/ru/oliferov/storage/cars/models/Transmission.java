package ru.oliferov.storage.cars.models;


import javax.persistence.*;

/**
 * @autor aoliferov
 * @since 10.03.2019
 */
@Entity
@Table(name = "transmission")
public class Transmission {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "automatic")
    private boolean automatic;

    public Transmission(boolean automatic) {
        this.automatic = automatic;
    }

    public Transmission() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
    }
}
