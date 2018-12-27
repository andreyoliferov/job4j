package usersapp;

import usersapp.items.Client;
import usersapp.items.Role;
import usersapp.items.Rule;
import usersapp.items.User;
import usersapp.items.address.Address;
import usersapp.items.address.City;
import usersapp.items.address.Country;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @autor aoliferov
 * @since 19.11.2018
 */
public interface Store {

    UUID add(User user);

    User update(User user);

    User delete(UUID id);

    List<User> findAll();

    User findById(UUID id);

    User findByLogin(String login);

    List<Role> findAllRoles();

    Role findRoleById(UUID id);

    User auth(String login, String password);

    HashMap<String, List<Rule>> accessData();

    List<Client> getClients(UUID user);

    List<Country> getCountries();

    List<City> getCitiesOfCountry(UUID id);

    boolean addClient(Client client);

    UUID findAddress(UUID country, UUID city, String data);

    UUID addAddress(Address address);

}
