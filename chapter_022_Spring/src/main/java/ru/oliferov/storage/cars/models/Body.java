package ru.oliferov.storage.cars.models;


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
}
