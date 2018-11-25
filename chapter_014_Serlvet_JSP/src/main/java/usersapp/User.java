package usersapp;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * @autor aoliferov
 * @since 19.11.2018
 */
public class User {

    private UUID id;
    private String name;
    private String login;
    private String email;
    private LocalDate createDate;

    public String getEmail() {
        return email;
    }

    public User(UUID id, String name, String login, String email) {
        this(name, login, email);
        this.id = id;
    }

    public User(User user) {
        this(user.getId(), user.getName(), user.getLogin(), user.getEmail());
    }

    public User(String name, String login, String email) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.login = login;
        this.email = email;
        this.createDate = LocalDate.now();
    }

    public UUID getId() {
        return id;
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
        return Objects.equals(id, user.id)
                && Objects.equals(name, user.name)
                && Objects.equals(login, user.login)
                && Objects.equals(email, user.email)
                && Objects.equals(createDate, user.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, login, email, createDate);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id
                + ", name='" + name + '\''
                + ", login='" + login + '\''
                + ", email='" + email + '\''
                + ", createDate=" + createDate
                + '}';
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }
}
