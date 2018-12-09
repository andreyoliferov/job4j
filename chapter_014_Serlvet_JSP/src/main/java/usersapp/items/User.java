package usersapp.items;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Пользователь системы
 * @autor aoliferov
 * @since 19.11.2018
 */
public class User {

    private UUID id;
    private String name;
    private String login;
    private String password;
    private String email;
    private Role role;
    private LocalDateTime createDate;

    public String getEmail() {
        return email;
    }

    public User(UUID id, String name, String login, String password, String email, Role role, LocalDateTime createDate) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
        this.createDate = createDate;
    }

    public User(String name, String login, String password, String email, Role role) {
        this (UUID.randomUUID(), name, login, password, email, role, LocalDateTime.now());
    }

    public User(User user) {
        this(
                user.getId(),
                user.getName(),
                user.getLogin(),
                user.getPassword(),
                user.getEmail(),
                user.getRole(),
                user.getCreateDate()
        );
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
                && Objects.equals(role, user.role)
                && Objects.equals(createDate, user.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, login, email, role, createDate);
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
