package rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("user")
@RolesAllowed("User")
public class User {
 
    @GET
    @Produces("application/json")
    @Path("cvr/{option}/{search}/{country}")
    public String getCompanyInfo(@PathParam("option") String option, @PathParam("search") String search, @PathParam("country") String country) 
            throws MalformedURLException, IOException, NotFoundException {
        URL url = new URL("http://cvrapi.dk/api?"+ option +"="+ search +"&country="+ country);
        String out = "";
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out += inputLine;
            }
            in.close();
        }
        catch (Exception e) {
            throw new NotFoundException("No match for the query found");
        }
        return out;
    }
}