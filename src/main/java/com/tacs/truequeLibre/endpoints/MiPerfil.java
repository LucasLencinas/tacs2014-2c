package com.tacs.truequeLibre.endpoints;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tacs.truequeLibre.Main;
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
    public Response index() {
      String itemsJson = new Gson().toJson(Main.getLoggedUser().getItems());
      return Response.ok(itemsJson,MediaType.APPLICATION_JSON).build();
    }
    
    @DELETE
    @Produces("application/json")
    @Path("/items/{id}")
	public Response deleteItem(@PathParam("id") Integer idItem) {
    	Usuario usuarioLogueado = Main.getLoggedUser();
    	Item itemABorrar = usuarioLogueado.getItems().findById(idItem);
    	System.out.println("Se borra el item:" +itemABorrar.getTitulo() + " del usuario " + usuarioLogueado.getNombre());
    	usuarioLogueado.getItems().remove(itemABorrar);
    	Main.items.remove(itemABorrar);
    	return Response.ok("{status:ok}", MediaType.APPLICATION_JSON).build();
    }
    
    /*    @PUT
    @Path("/trueques")
      public Response postularTrueque(String jsonData) {
    	System.out.println("empiezo postulacion con:"+jsonData);
    	JsonObject myjson = new JsonObject();
        Usuario usuarioLogueado = Main.getLoggedUser();
        Usuario usuarioAmigo = Main.usuarios.findById(idAmigo);
        Item itemSolicitado = Main.items.findById(idItemSolicitado);
        Item itemOfrecido = Main.items.findById(idItemOfrecido);
   		Trueque trueque = new Trueque(itemOfrecido,itemSolicitado,usuarioLogueado,usuarioAmigo,"descripcion");
       
    	return Response.ok(new Gson().toJson(trueque), MediaType.APPLICATION_JSON).build();
    }*/
}
