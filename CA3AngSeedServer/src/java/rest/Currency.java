/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import entity.ExchangeRate;
import facades.CurrencyFacade;
import java.text.ParseException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
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
@Path("currency")
@RolesAllowed("User")
public class Currency {
    CurrencyFacade facade = new CurrencyFacade();
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Currency
     */
    public Currency() {
    }

    /**
     * Retrieves representation of an instance of rest.Currency
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    @Path("dailyrates")
    public String getCurrency() {
        List<ExchangeRate> ers = facade.getExchangeRates();
        if (ers.isEmpty()) {
            new NotFoundException("No exchange rates for today or yesterday found");
        }
        return new Gson().toJson(ers);
    }
    
    @GET
    @Produces("application/json")
    @Path("calculator/{amount}/{from}/{to}")
    public String calculator(@PathParam("amount") int amount, @PathParam("from") String from, @PathParam("to") String to) throws NotFoundException {
        Double fromRate = facade.getExchangeRate(from);
        Double toRate = facade.getExchangeRate(to);
        if (fromRate == 0.0 || toRate == 0.0) {
            throw new NotFoundException("One or both exchange rates not found");
        }
        else {
            Double r = amount*fromRate/toRate;
            return "{\"result\":"+r+"}";
        }
    }
}
