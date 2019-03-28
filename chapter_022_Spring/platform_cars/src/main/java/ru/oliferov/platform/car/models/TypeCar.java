package ru.oliferov.platform.car.models;

import com.google.common.base.Objects;

import javax.persistence.*;

/**
 * @autor aoliferov
 * @since 18.03.2019
 */
@Entity
@Table(name = "type_car")
public class TypeCar {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Имя */
    @Column(name = "name")
    private String name;

    public TypeCar() {
    }

    public TypeCar(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TypeCar typeCar = (TypeCar) o;
        return id == typeCar.id
                && Objects.equal(name, typeCar.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name);
    }
}

