package usersapp.servlets.soap;

import usersapp.Validate;
import usersapp.ValidateService;
import usersapp.items.Client;

import javax.jws.WebService;
import java.util.List;
import java.util.UUID;

/**
 * Пример реализации SOAP веб-сервиса.
 * @autor aoliferov
 * @since 09.09.2019
 */
@WebService(endpointInterface = "usersapp.servlets.soap.SoapController",
        serviceName = "UserSoap")
public class SoapControllerImpl implements SoapController {

    private final Validate logic = ValidateService.getInstance();

    @Override
    public List<Client> getClientsOfUser(String user) {
        return logic.getClients(UUID.fromString(user));
    }

    @Override
    public String testService() {
        return "SOAP WORKS!";
    }

}
