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
import com.tacs.truequeLibre.Utils.LlamadasFB;
import com.tacs.truequeLibre.domain.Trueque;
import com.tacs.truequeLibre.domain.Usuario;


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
    public Response index(@Context HttpHeaders header) {
    	System.out.println("Me pidieron los Trueques");
  		Usuario miUsuario = Main.facebook.getLoggedUser(header);
    	String itemsJson = new Gson().toJson(Main.trueques.getByUser(miUsuario));
      return Response.ok(itemsJson,MediaType.APPLICATION_JSON).build();
    }
    
	@GET
	@Path("/pendientes")
	@Produces("text/plain")
	public Response solicitudes(@Context HttpHeaders header){
    	System.out.println("Me pidieron las solicitudes");
  		Usuario miUsuario = Main.facebook.getLoggedUser(header);
    	String itemsJson = new Gson().toJson(Main.trueques.getPending(miUsuario));
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
	public Response accept(@PathParam("id") Integer truequeId, @Context HttpHeaders hh) throws Exception {
			Map<String, Cookie> pathParams = hh.getCookies();
			String accessToken = pathParams.get("token").getValue();
		  DefaultFacebookClient facebookClient = new DefaultFacebookClient(accessToken, LlamadasFB.appSecret);
		  
    	Trueque trueque = Trueque.getById(truequeId);
    	trueque.aceptarTrueque();
    	Usuario usuarioANotificar = trueque.getUsuarioSolicitante();
    	String mensaje = trueque.getUsuarioSolicitado().getNombre()+" Ha aceptado tu solicitud. "+
    			"El item "+trueque.getItemSolicitado().getTitulo()+" es tuyo.";
    	enviarNotificacionAlOtro(facebookClient,usuarioANotificar.getId(), mensaje,"");
    	
    	return Response.ok(new Gson().toJson(trueque), MediaType.APPLICATION_JSON).build();
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
    	String	app_access_token = facebookClient.obtainAppAccessToken("347575272090580", "28f123fe638801f3f519663c4f747d0c").getAccessToken();
    	//tengo que generar un facebookClient con el APP_access_token
    	DefaultFacebookClient facebookClientAppAccessToken = new DefaultFacebookClient( app_access_token );
        try {
        	facebookClientAppAccessToken.publish(externalUserId
	                + "/notifications?access_token="+app_access_token, FacebookType.class,
	                Parameter.with("template", message),
	                Parameter.with("href", href));
	    } catch (FacebookOAuthException e) {
        	System.out.println("Error: " + e.getErrorType() + e.getMessage());
	        if (e.getErrorCode() == 200) { //No es un usuario de la aplicacion
	        	System.out.println("Error: " + e.getErrorType() + e.getMessage());
	        } else if (e.getErrorCode() == 100) {//El mensaje no puede tener mas de 80 caracteres
	        
	        }
	    }
	}
    
}
