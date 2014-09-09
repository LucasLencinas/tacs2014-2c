package tacs.grupo4.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/trueques")
public class Trueques {
	@GET
    @Produces("text/plain")
    public String index() {
        return "Listado de mis trueques realizados";
    }
    
    @GET
    @Path("/solicitudes")
    public String show(){
    	return "Listado de mis solicitudes de trueque";
    }
   /*  no logro hacerme llegar el parametro
    @POST
    @Path("/solicitudes")
	public String create(@FormParam("respuesta") Boolean respuesta) {
    	if(respuesta == true){
    		return "Solicitud de trueque aceptada";
    	} else {
    		return "Solicitud de trueque rechazada";
    	}
    }
	*/
}
