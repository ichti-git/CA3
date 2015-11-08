/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import entity.UserRole;
import entity.User;
import facades.UserFacade;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

/**
 * REST Web Service
 *
 * @author ichti
 */
@Path("make")
public class MakeUsers {
    
    @Context
    private UriInfo context;
    private UserFacade facade = new UserFacade();

    /**
     * Creates a new instance of MakeUsers
     */
    public MakeUsers() {
    }

    /**
     * Makes the initial set of roles and users
     * Only one time call
     */
    @GET
    @Produces("application/json")
    public String make() {
        facade.makeUsers();
        return "{\"msg\":\"succes\"}";
    }
    
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public String createUser(String content) {
        entity.User u = new Gson().fromJson(content, entity.User.class);
        boolean succes = facade.createUser(u);
        String msg;
        if (succes) {
            msg = "You have succesfully signed up";
        }
        else {
            msg = "User with that username already exist";
        }
        return "{\"msg\":\""+ msg +"\"}";
        
    }
    
    /**
     * PUT method for updating or creating an instance of MakeUsers
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
}
