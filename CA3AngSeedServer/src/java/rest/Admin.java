package rest;

import com.google.gson.Gson;
import facades.UserFacade;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("admin")
@RolesAllowed("Admin")
public class Admin {
    private UserFacade facade = new UserFacade();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/users")
    public String getUsers() throws NotFoundException {
        List<entity.User> us = facade.getUsers();
        if (us.isEmpty()) {
            throw new NotFoundException("No users found");
        }
        return new Gson().toJson(us);
    }
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user/{id}")
    public String getUserById(@PathParam("id") String id) throws NotFoundException {
        entity.User u = facade.deleteUserByUserId(id);
        if (u == null) {
            throw new NotFoundException("User with the given id ("+id+") not found");
        }
        return new Gson().toJson(u);
    }
}
