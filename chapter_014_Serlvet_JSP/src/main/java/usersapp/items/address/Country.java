package usersapp.items.address;

import java.util.UUID;

/**
 * @autor aoliferov
 * @since 23.12.2018
 */
public class Country {

    private UUID id;
    private String name;

    public Country(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public Country() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setId(String  id) {
        this.id = UUID.fromString(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
