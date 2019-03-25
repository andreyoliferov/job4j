package ru.oliferov.platform.car.models;


import com.google.common.base.Objects;

import javax.persistence.*;

/**
 * @autor aoliferov
 * @since 10.03.2019
 */
@Entity
@Table(name = "body")
public class Body {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Имя */
    @Column(name = "type")
    private String type;

    public Body(String type) {
        this.type = type;
    }

    public Body() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Body body = (Body) o;
        return id == body.id && Objects.equal(type, body.type);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, type);
    }
}
