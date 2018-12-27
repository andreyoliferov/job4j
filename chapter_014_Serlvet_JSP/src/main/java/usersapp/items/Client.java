package usersapp.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import usersapp.items.address.Address;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @autor aoliferov
 * @since 20.12.2018
 */
public class Client {

    private UUID id;
    private String firstName;
    private String secondName;
    private String phone;
    private String email;
    private String sex;
    private Address address;
    private LocalDateTime createDate;
    private UUID owner;

    public Client() {
        this.id = UUID.randomUUID();
        this.createDate = LocalDateTime.now();
    }

    public Client(UUID id, String firstName, String secondName, Contact contact, String sex, LocalDateTime createDate, UUID owner) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.phone = contact.getPhone();
        this.email = contact.getEmail();
        this.sex = sex;
        this.address = contact.getAddress();
        this.createDate = createDate;
        this.owner = owner;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @JsonProperty("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("second_name")
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getSex() {
        return sex;
    }

    public Address getAddress() {
        return address;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public void setOwner(String owner) {
        this.owner = UUID.fromString(owner);
    }
}
