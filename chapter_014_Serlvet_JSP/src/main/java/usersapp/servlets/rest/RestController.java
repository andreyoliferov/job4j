package usersapp.servlets.rest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import usersapp.Validate;
import usersapp.ValidateService;
import usersapp.items.Client;
import usersapp.items.address.City;
import usersapp.items.address.Country;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

/**
 * !!!! ВНИМАНИЕ
 * !!!! Jersey выкидывает ошибку при работе с JAVA 11, проблема известная, исправление ожидается в версии 2.29
 * !!!! выход ориентировочно апрель 2019
 * @autor aoliferov
 * @since 18.12.2018
 */
@Path("catalog")
public class RestController {

    private final Validate logic = ValidateService.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("clients")
    public String getClientsOfUser(@QueryParam("user") String user) throws JsonProcessingException {
        List<Client> list = logic.getClients(UUID.fromString(user));
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(list);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("clients")
    public String addClient(Client request) throws JsonProcessingException {
        String result = "{ \"result\" : \"success\" }";
        try {
            logic.addClient(request);
        } catch (ClientErrorException e) {
            result = result.replace("success", e.getMessage());
        }
        return result;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("countries")
    public String getCountries() throws JsonProcessingException {
        List<Country> list = logic.getCountries();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(list);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cities")
    public String getCitiesOfCountry(@QueryParam("country") String country) throws JsonProcessingException {
        List<City> list = logic.getCitiesOfCountry(UUID.fromString(country));
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(list);
    }
}
