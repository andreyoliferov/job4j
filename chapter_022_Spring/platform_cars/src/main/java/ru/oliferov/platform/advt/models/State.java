package ru.oliferov.platform.advt.models;

import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @autor aoliferov
 * @since 25.03.2019
 */
@Entity
@Table(name = "state")
public class State {

    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;

    public State() {
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
        State state = (State) o;
        return id == state.id
                && Objects.equal(name, state.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name);
    }
}
