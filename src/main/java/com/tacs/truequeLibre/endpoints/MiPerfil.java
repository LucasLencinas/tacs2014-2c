package com.tacs.truequeLibre.endpoints;


import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.tacs.truequeLibre.setup.Setup;
import com.tacs.truequeLibre.Utils.HandlerDS;
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
      String itemsJson = new Gson().toJson(Setup.facebook.getLoggedUser(header).getItems());
      return Response.ok(itemsJson,MediaType.APPLICATION_JSON).build();
    }
    
    @DELETE
    @Produces("application/json")
    @Path("/items/{id}")
	public Response deleteItem(@PathParam("id") Integer idItem, @Context HttpHeaders header) {
    	Usuario usuarioLogueado = Setup.facebook.getLoggedUser(header);
    	Item itemABorrar = usuarioLogueado.getItems().findById(idItem);
    	System.out.println("Se borra el item:" +itemABorrar.getTitulo() + " del usuario " + usuarioLogueado.getNombre());
    	usuarioLogueado.quitarItem(itemABorrar);
    	HandlerDS.deleteItem(itemABorrar);
    	return Response.ok("ok", MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/trueques")
    public Response getMyTrueques(@Context HttpHeaders header){
  		Usuario user = Setup.facebook.getLoggedUser(header);
      String truequesJson = new Gson().toJson(HandlerDS.findTruequeByUser(user));
      return Response.ok(truequesJson,MediaType.APPLICATION_JSON).build();
    }
    
    @POST
    @Path("/trueques")
      public Response postularTrueque(String jsonTrueque, @Context HttpHeaders header) {
    	TruequeRequest unTruequeRequest = new Gson().fromJson(jsonTrueque, TruequeRequest.class);
      Usuario usuarioLogueado = Setup.facebook.getLoggedUser(header);
      Usuario usuarioAmigo = HandlerDS.findUsuarioById(unTruequeRequest.idAmigo);
      Item itemSolicitado = HandlerDS.findItemById(unTruequeRequest.idItemSolicitado);
      Item itemOfrecido = HandlerDS.findItemById(unTruequeRequest.idItemOfrecido);
      Trueque trueque = new Trueque(itemOfrecido,itemSolicitado,usuarioLogueado,usuarioAmigo, "");
      HandlerDS.guardarTrueque(trueque);
    	return Response.ok("ok", MediaType.TEXT_PLAIN).build();
    }
}
