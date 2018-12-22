package usersapp.items;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * @autor aoliferov
 * @since 20.12.2018
 */
public class Client {

    private UUID id;
    private String firstName;
    private String secondName;
    private String login;
    private String password;
    private String email;
    private String sex;

    public Client() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    @JsonProperty("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("second_name")
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getSex() {
        return sex;
    }
}
