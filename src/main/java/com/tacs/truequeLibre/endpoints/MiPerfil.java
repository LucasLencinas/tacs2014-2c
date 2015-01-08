package com.tacs.truequeLibre.endpoints;


import java.util.Map;

import javax.ws.rs.DELETE;
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
import com.tacs.truequeLibre.setup.Setup;
import com.tacs.truequeLibre.Utils.HandlerDS;
import com.tacs.truequeLibre.Utils.LlamadasFB;
import com.tacs.truequeLibre.Utils.TruequeRequest;
import com.tacs.truequeLibre.domain.Item;
import com.tacs.truequeLibre.domain.Trueque;
import com.tacs.truequeLibre.domain.Usuario;

@Path("/miPerfil")
public class MiPerfil {

	/**
	 * Listar Items.
	 * @return: Un JSON que representa la lista de items que esta en la aplicacion
	 */
	
    @GET 
    @Path("/items")
    @Produces("application/json")
    public Response index(@Context HttpHeaders header) {
    	Usuario usuario = Setup.facebook.getLoggedUser(header);
    	System.out.println("Request --> Items del usuario: " + usuario.toString() );
      String itemsJson = new Gson().toJson(usuario.getItems());
      System.out.println("Response OK--> Items del usuario: " + itemsJson);
      return Response.ok(itemsJson,MediaType.APPLICATION_JSON).build();
    }
    
    @DELETE
    @Produces("application/json")
    @Path("/items/{id}")
	public Response deleteItem(@PathParam("id") Integer idItem, @Context HttpHeaders header) {
    	Usuario usuario = Setup.facebook.getLoggedUser(header);
    	System.out.println("Request --> Delete Item "+idItem +" del usuario: " + usuario.toString() );
    	Item itemABorrar = HandlerDS.findItemById(idItem);
    	

    	boolean deleteOK = HandlerDS.deleteItem(itemABorrar,usuario);
    	if(deleteOK == true){
    		System.out.println("Response OK--> Delete Item:" +itemABorrar.toString() + " del usuario " + usuario.toString());
    		return Response.ok("ok", MediaType.APPLICATION_JSON).build();
    	}
    	else{
    		System.out.println("Response CONFLICT--> Delete Item:" +itemABorrar.toString() + " del usuario " + usuario.toString());
    		return Response.status(Response.Status.CONFLICT).build();
    	}
    }
    
    @GET
    @Path("/trueques")
    public Response getMyTrueques(@Context HttpHeaders header){
    	Usuario usuario = Setup.facebook.getLoggedUser(header);
    	System.out.println("Request --> Trueques del usuario " + usuario.toString());
      String truequesJson = new Gson().toJson(HandlerDS.findTruequeByUser(usuario));
      System.out.println("Response OK--> Trueques del usuario, " + truequesJson);
      return Response.ok(truequesJson,MediaType.APPLICATION_JSON).build();
    }
    
    @POST
    @Path("/trueques")
      public Response postularTrueque(String jsonTrueque, @Context HttpHeaders header) {
      Usuario usuario = Setup.facebook.getLoggedUser(header);
    	System.out.println("Request --> Nuevo Trueque del usuario " + usuario.toString());      
      TruequeRequest unTruequeRequest = new Gson().fromJson(jsonTrueque, TruequeRequest.class);
      System.out.println("Trueque Request: " + unTruequeRequest.toString());
      Usuario usuarioAmigo = HandlerDS.findUsuarioById(unTruequeRequest.idAmigo);
      if(usuarioAmigo == null)
      	System.out.println("Usuario Solicitado: null");
      System.out.println("Usuario Solicitado: " + usuarioAmigo.toString());
      Item itemSolicitado = HandlerDS.findItemById(unTruequeRequest.idItemSolicitado);
      System.out.println("Item Solicitado: " + itemSolicitado.toString());
      Item itemOfrecido = HandlerDS.findItemById(unTruequeRequest.idItemOfrecido);
      System.out.println("Item Ofrecido: " + itemOfrecido.toString());
      Trueque trueque = new Trueque(itemOfrecido,itemSolicitado,usuario,usuarioAmigo, "Prueba");
      HandlerDS.guardarTrueque(trueque);
      System.out.println("Response --> Nuevo Trueque del usuario, " + usuario.toString());     

	  Map<String, Cookie> pathParams = header.getCookies();
	  String accessToken = pathParams.get("token").getValue();
	  DefaultFacebookClient facebookClient = new DefaultFacebookClient(accessToken, LlamadasFB.appSecret);
  	  String mensaje = trueque.getUsuarioSolicitante().getNombre()+" te ha solicitado un trueque. Ofrece su  "+
  			trueque.getItemOfrecido().getTitulo() + " por tu " + trueque.getItemSolicitado().getTitulo()+".";
  	  LlamadasFB.enviarNotificacionAlOtro(facebookClient, trueque.getUsuarioSolicitado().getId(), mensaje,"");
      
    	return Response.ok("ok", MediaType.TEXT_PLAIN).build();
    }
}
