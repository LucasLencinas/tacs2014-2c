package tacs.grupo4.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/* 
 *  Por el momento asumo que la solicitud y el trueque son la misma entidad,
 *  siendo la solicitud un trueque en estado pendiente (no fue aceptado ni rechazado)
 */
@Path("/trueques")
public class Trueques {
	@GET
    @Produces("text/plain")
    public String index() {
        return "Listado de mis trueques realizados";
    }
    
	@GET
	@Path("/solicitudes")
	@Produces("text/plain")
	public String solicitudes(){
	 	return "Listado de mis solicitudes de trueque";
	}
	
    @GET
    @Path("/{id}")
    @Produces("text/plain")
    public Response show(@PathParam("id") Integer id){
    	String json = "{id:"+id+", item_1:{id: 2, user: 'Pepe'}, item_2: {id: 3, user: 'Juan'}, status: 'acepted'";
    	return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/new")
    @Produces("text/plain")
    public String _new(){
    	return "Formulario para crear un trueque. Parametros: item_1_id, item_2_id";
    }
    
    /*
     * Crea una solicitud de trueque. 
     */
    @POST
    @Produces("application/json")
	public Response create(@FormParam("item_1[id]") Integer item_1_id, @FormParam("item_2[id]") Integer item_2_id) {
    	String json = "{id: 2, item_1:{id:"+item_1_id+", user: 'Pepe'}, item_2: {id:" + item_2_id+ ", user: 'Juan'}, status: 'pending'}";
    	return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
    
    @PUT
    @Path("/{id}/accept")
	public String accept(@FormParam("id") Integer id) {
    	return "Trueque "+id+" aceptada";
    }
    
    @PUT
    @Path("/{id}/reject")
	public String reject(@FormParam("id") Integer id) {
    	return "Trueque "+id+" rechazado";
    }  
    
 
}
