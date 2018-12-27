package usersapp.items.address;

import java.util.UUID;

/**
 * @autor aoliferov
 * @since 23.12.2018
 */
public class Address {

    private UUID id;
    private Country country;
    private City city;
    private String data;

    public Address(UUID id, Country country, City city, String data) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.data = data;
    }

    public Address(Country country, City city, String data) {
        this.id = UUID.randomUUID();
        this.country = country;
        this.city = city;
        this.data = data;
    }

    public Address() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return country + ", " + city + ", " + data;
    }
}
