package com.tacs.truequeLibre.endpoints;


import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tacs.truequeLibre.domain.Trueque;

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
    @Path("/view/{id}")
    @Produces("text/plain")
    public Response show(@PathParam("id") Integer id){
    	String json = "{id:"+id+", item_1:{id: 2, user: 'Pepe'}, item_2: {id: 3, user: 'Juan'}, status: 'acepted'}";
    	return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/new")
    @Produces("text/plain")
    public String _new(){
    	return "Formulario para crear un trueque. Parametros: item_1[id], item_2[id]";
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
    
    @POST
    @Path("/accept/{id}")
	public String accept(@PathParam("id") Integer truequeId) throws Exception {
    	Trueque trueque = Trueque.getById(truequeId);
    	trueque.aceptarTrueque();
    	return "Trueque "+truequeId+" aceptado";
    }
    
    @POST
    @Path("/reject/{id}")
	public String reject(@PathParam("id") Integer id) {
    	return "Trueque "+id+" rechazado";
    }  
    
 
}
