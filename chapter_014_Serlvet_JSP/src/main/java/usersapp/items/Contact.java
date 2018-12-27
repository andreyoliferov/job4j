package usersapp.items;

import usersapp.items.address.Address;

/**
 * @autor aoliferov
 * @since 28.12.2018
 */
public class Contact {

    private String phone;
    private String email;
    private Address address;

    public Contact(String phone, String email, Address address) {
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
