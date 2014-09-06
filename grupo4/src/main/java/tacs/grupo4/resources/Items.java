package tacs.grupo4.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/items")
public class Items {

    // TODO: update the class to suit your needs
    
    // The Java method will process HTTP GET requests
    @GET 
    // The Java method will produce content identified by the MIME Media
    // type "text/plain"
    @Produces("text/plain")
    public String getIt() {
        return "Listado items";
    }
    
    @GET
    @Path("/{item_id}")
    public String item(@PathParam("item_id") Integer id){
    	return "Item "+id.toString();
    }
    
}
