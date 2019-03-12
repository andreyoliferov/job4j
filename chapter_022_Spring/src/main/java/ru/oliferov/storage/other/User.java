package ru.oliferov.storage.other;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.UUID;

/**
 * @autor aoliferov
 * @since 12.03.2019
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "login")
    private String login;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public User() {
    }

    public User(String name, String login) {
        this.name = name;
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equal(id, user.id)
                && Objects.equal(name, user.name)
                && Objects.equal(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, login);
    }
}
