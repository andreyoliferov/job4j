package ru.oliferov.storage.cars.models;


import com.google.common.base.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transmission that = (Transmission) o;
        return id == that.id && automatic == that.automatic;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, automatic);
    }
}
