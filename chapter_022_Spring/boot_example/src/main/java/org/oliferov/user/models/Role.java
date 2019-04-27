package org.oliferov.user.models;

import com.google.common.base.Objects;

import javax.persistence.*;

/**
 * @autor aoliferov
 * @since 24.03.2019
 */
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    public Role() {
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
        Role role = (Role) o;
        return id == role.id
                && Objects.equal(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name);
    }
}
