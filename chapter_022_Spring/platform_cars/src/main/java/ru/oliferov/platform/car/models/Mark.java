package ru.oliferov.platform.car.models;

import com.google.common.base.Objects;

import javax.persistence.*;

/**
 * @autor aoliferov
 * @since 18.03.2019
 */
@Entity
@Table(name = "marks")
public class Mark {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Имя */
    @Column(name = "name")
    private String name;

    public Mark() {
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
        Mark mark = (Mark) o;
        return id == mark.id
                && Objects.equal(name, mark.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name);
    }
}
