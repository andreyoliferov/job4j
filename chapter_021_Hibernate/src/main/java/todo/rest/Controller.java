package todo.rest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import todo.store.BDStore;
import todo.Item;
import todo.store.Store;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


/**
 * @autor aoliferov
 * @since 27.02.2019
 */
@Path("catalog")
public class Controller {

    private final Store store = new BDStore();
    private final ObjectMapper mapper = new ObjectMapper();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("tasks")
    public String getAllTasks() throws JsonProcessingException {
        List<Item> all = store.getAll();
        return mapper.writeValueAsString(all);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("tasks/unfulfilled")
    public String getUnfulfilledTasks() throws JsonProcessingException {
        List<Item> unfulfilled = store.getUnfulfilled();
        return mapper.writeValueAsString(unfulfilled);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("tasks2")
    public String addTask(Item item) {
        String result = "{ \"result\" : \"success\" }";
        try {
            store.addTask(item);
        } catch (Exception e) {
            e.printStackTrace();
            result = result.replace("success", e.getMessage());
        }
        return result;
    }
}
