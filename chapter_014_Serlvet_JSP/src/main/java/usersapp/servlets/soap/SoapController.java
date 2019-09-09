package usersapp.servlets.soap;

import usersapp.items.Client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

/**
 * @autor aoliferov
 * @since 09.09.2019
 */
@WebService
public interface SoapController {

    @WebMethod
    List<Client> getClientsOfUser(@WebParam(name = "user") String user);

    @WebMethod
    String testService();

}
