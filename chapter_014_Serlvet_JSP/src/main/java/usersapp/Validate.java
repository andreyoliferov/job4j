package usersapp;

import usersapp.items.Client;
import usersapp.items.Role;
import usersapp.items.User;
import usersapp.items.address.City;
import usersapp.items.address.Country;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @autor aoliferov
 * @since 19.11.2018
 */
public interface Validate {

    UUID add(Map<String, String[]> parameters);

    User update(Map<String, String[]> parameters);

    User delete(Map<String, String[]> parameters);

    List<User> findAll();

    List<Role> findAllRoles();

    User findById(Map<String, String[]> parameters);

    User auth(String login, String password);

    List<Client> getClients(UUID user);

    List<Country> getCountries();

    UUID addClient(Client client);

    List<City> getCitiesOfCountry(UUID id);

}
