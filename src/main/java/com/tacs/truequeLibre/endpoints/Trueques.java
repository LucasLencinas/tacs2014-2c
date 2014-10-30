package com.tacs.truequeLibre.endpoints;


import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import com.restfb.DefaultFacebookClient;
import com.restfb.Parameter;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.FacebookType;
import com.tacs.truequeLibre.Main;
import com.tacs.truequeLibre.Utils.PropiedadesFB;
import com.tacs.truequeLibre.domain.Trueque;


/* 
 *  Por el momento asumo que la solicitud y el trueque son la misma entidad,
 *  siendo la solicitud un trueque en estado pendiente (no fue aceptado ni rechazado)
 */
@Path("/trueques")
public class Trueques {

	/**
	 * Listar Trueques.
	 * @return: Un JSON que representa la lista de trueques que estan en la aplicacion
	 */
    @GET 
    @Produces("application/json")
    public Response index() {
    	System.out.println("Me pidieron los Trueques");
    	String itemsJson = new Gson().toJson(Main.trueques.getByUser());
      return Response.ok(itemsJson,MediaType.APPLICATION_JSON).build();
    }
    
	@GET
	@Path("/pendientes")
	@Produces("text/plain")
	public Response solicitudes(){
    	System.out.println("Me pidieron las solicitudes");
    	String itemsJson = new Gson().toJson(Main.trueques.getPending());
      return Response.ok(itemsJson,MediaType.APPLICATION_JSON).build();
	}

    
    /*
     * Crea una solicitud de trueque. 
     */
    @POST
    @Path("/new")
    @Produces("application/json")
	public Response create(@FormParam("item_1[id]") Integer item_1_id, @FormParam("item_2[id]") Integer item_2_id) {
    	String json = "{id: 2, item_1:{id:"+item_1_id+", user: 'Pepe'}, item_2: {id:" + item_2_id+ ", user: 'Juan'}, status: 'pending'}";
    	return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
    
    @POST
    @Path("/accept/{id}")
	public String accept(@PathParam("id") Integer truequeId, @Context HttpHeaders hh) throws Exception {
			Map<String, Cookie> pathParams = hh.getCookies();
			String accessToken = pathParams.get("token").getValue();			
		  DefaultFacebookClient facebookClient = new DefaultFacebookClient(accessToken, PropiedadesFB.appSecret);
		  
    	Trueque trueque = Trueque.getById(truequeId);
    	trueque.aceptarTrueque();
    	enviarNotificacionAlOtro(facebookClient,"1492722194320819", "Te acepte el trueque Tom A. Esto es una notificacion de FB","https://www.youtube.com/");
    	
    	return "Trueque "+truequeId+" aceptado";
    }
    
    @POST
    @Path("/reject/{id}")
	public String reject(@PathParam("id") Integer truequeId) {
    	Trueque trueque = Trueque.getById(truequeId);
    	trueque.rechazarTrueque();
    	return "Trueque "+truequeId+" aceptado";
    }  
    
 
    public void enviarNotificacionAlOtro(DefaultFacebookClient facebookClient, 
    		String externalUserId, String message, String href) {

	    try {
	        facebookClient.publish(externalUserId
	                + "/notifications", FacebookType.class,
	                Parameter.with("template", message),
	                Parameter.with("href", href));
	    } catch (FacebookOAuthException e) {
	        if (e.getErrorCode() == 200) { //No es un usuario de la aplicacion
	        	System.out.println("Error: " + e.getErrorType() + e.getMessage());
	        } else if (e.getErrorCode() == 100) {//El mensaje no puede tener mas de 80 caracteres
	        
	        }
	    }
	}
    
}
