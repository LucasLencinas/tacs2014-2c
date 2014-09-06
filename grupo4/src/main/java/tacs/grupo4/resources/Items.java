package tacs.grupo4.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.sun.jersey.core.header.MediaTypes;

@Path("/items")
public class Items {

    // TODO: update the class to suit your needs
    
    // The Java method will process HTTP GET requests
    @GET 
    // The Java method will produce content identified by the MIME Media
    // type "text/plain"
    @Produces("text/plain")
    public String index() {
        return "Listado items";
    }
    
    @GET
    @Path("/{id}")
    public String show(@PathParam("id") Integer id){
    	return "Item "+id.toString();
    }
    
    @POST
    @Produces("text/plain")
	public String create(@FormParam("name") String nombre) {
   	
    	return "Item "+nombre+" created";
    }

    @PUT
    @Path("/{id}")
    @Produces("text/plain")
	public String update(@PathParam("id") Integer id, @FormParam("name") String name) {
   	
    	return "Item "+id+" name changed to: "+ name;
    }
}
