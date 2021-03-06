package com.tacs.truequeLibre.endpoints;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

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
import com.tacs.truequeLibre.setup.Setup;
import com.tacs.truequeLibre.Utils.HandlerDS;
import com.tacs.truequeLibre.Utils.LlamadasFB;
import com.tacs.truequeLibre.domain.Trueque;
import com.tacs.truequeLibre.domain.Usuario;


@Path("/trueques")
public class Trueques {

	/**
	 * Listar Trueques.
	 * @return: Un JSON que representa la lista de trueques que estan en la aplicacion
	 */
    @GET 
    @Produces("application/json")
    public Response index(@Context HttpHeaders header) {
    	System.out.println("Request --> All Trueques");
  		Usuario miUsuario = Setup.facebook.getLoggedUser(header);
    	String itemsJson = new Gson().toJson(HandlerDS.findTruequeByUser(miUsuario));
    	System.out.println("Response OK --> All Trueques: " + itemsJson);
    	return Response.ok(itemsJson,MediaType.APPLICATION_JSON).build();
    }
    
	@GET
	@Path("/pendientes")
	@Produces("text/plain")
	public Response solicitudes(@Context HttpHeaders header){
  		Usuario miUsuario = Setup.facebook.getLoggedUser(header);
  		System.out.println("Request --> Trueques pendientes del usuario: " + miUsuario.toString());
    	String truequesJson = new Gson().toJson(HandlerDS.findPendingTruequesByUser(miUsuario));
    	System.out.println("Response OK--> Trueques pendientes del usuario, " + truequesJson);
    	return Response.ok(truequesJson,MediaType.APPLICATION_JSON).build();
	}
    
    @POST
    @Path("/accept/{id}")
	public Response accept(@PathParam("id") Integer truequeId, @Context HttpHeaders hh) throws Exception {
			Map<String, Cookie> pathParams = hh.getCookies();
			String accessToken = pathParams.get("token").getValue();
		  DefaultFacebookClient facebookClient = new DefaultFacebookClient(accessToken, LlamadasFB.appSecret);
		  Usuario usuario = Setup.facebook.getLoggedUser(hh);
  		System.out.println("Request --> Aceptar trueque " + truequeId + " del usuario: " + usuario.toString());
		  
    	Trueque trueque = HandlerDS.findTruequeById(truequeId);
    	trueque.aceptarTrueque();
    	Usuario usuarioANotificar = trueque.getUsuarioSolicitante();
    	String mensaje = trueque.getUsuarioSolicitado().getNombre()+" Ha aceptado tu solicitud. "+
    			"El item "+trueque.getItemSolicitado().getTitulo()+" es tuyo.";
    	LlamadasFB.enviarNotificacionAlOtro(facebookClient,usuarioANotificar.getId(), mensaje,"");
    	
    	System.out.println("Response OK--> Aceptar trueque: " + trueque.toString());
    	return Response.ok(new Gson().toJson(trueque), MediaType.APPLICATION_JSON).build();
    }
    
    @POST
    @Path("/reject/{id}")
	public String reject(@PathParam("id") Integer truequeId, @Context HttpHeaders hh) {
    	Usuario usuario = Setup.facebook.getLoggedUser(hh);
    	System.out.println("Request --> Rechazar trueque " + truequeId + " del usuario: " + usuario.toString());
    	Trueque trueque = HandlerDS.findTruequeById(truequeId);
    	trueque.rechazarTrueque();
    	System.out.println("Response OK--> Rechazar trueque: " + trueque.toString());
    	return "Trueque "+truequeId+" rechazado";
    }  
}
