package com.tacs.truequeLibre.endpoints;


import javax.ws.rs.GET;
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
import com.tacs.truequeLibre.Utils.LlamadasFB;
import com.tacs.truequeLibre.domain.ListaDeUsuarios;
import com.tacs.truequeLibre.domain.Usuario;

@Path("/amigos")
public class Amigos {

	/**
	 * Listar Usuarios.
	 * @return: Un JSON que representa la lista de usuarios que tiene como amigos la persona que se logueo
	 */
	/*En el @Context HttpHeader hh estan los parametros que se envian, como headers, cookies y esas cosas.
	 *Desde javascript tengo que guardar previamente la cookie en la sesion, por ahora lo hago con:
	 *document.cookie = 'key=' + value;
	 * */
    @GET 
    @Produces("application/json")
    public Response index(@Context HttpHeaders header) {
      // NEGRADA FIXME
      if(Setup.isSet == false){

      	Setup.setup();
      }
      
      if(Setup.facebook == null)
      	Setup.facebook = new LlamadasFB();
      Usuario user = Setup.facebook.getLoggedUser(header);
      if(user == null)
      	return Response.status(500).build();
      
      System.out.println("me pidieron los amigos de " + user.getNombre());
      ListaDeUsuarios amigos = Setup.facebook.getAmigos(user, header);
      for (Usuario amigo : amigos) {				
      	user.agregarAmigo(amigo);
			}
      ListaDeUsuarios amigosConItems = amigosConItems(amigos);
    	String usuariosJson = new Gson().toJson(amigosConItems);
    	//System.out.println("Amigos con items: " + amigosConItems);
      return Response.ok(usuariosJson,MediaType.APPLICATION_JSON).build();
    }

		/**
      * FIXME (Lucas)--> Este lo modifique, ahora se le pasa un String id, ver si hay error
      *  Listar un determinado usuario.
      * @param id: El id de un usuario que quiero ver
      * @return: un usuario con ese id que se pasa por parametro
      */
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response show(@PathParam("id") String id){
    	
    	Usuario amigo= HandlerDS.findUsuarioById(id);	
    	Gson unGson = new Gson();
    	String unUsuarioJson = unGson.toJson(amigo);
    	return Response.ok(unUsuarioJson, MediaType.APPLICATION_JSON).build();
    }
    
    /**
     * FIXME(Lucas) --> Este lo modifique, ahora se le pasa un String id, ver si hay error
    **/
    @GET
    @Path("/{id}/items")
    @Produces("application/json")
    public Response showItems(@PathParam("id") String id){
    	Usuario amigo= HandlerDS.findUsuarioById(id);	
    	Gson unGson = new Gson();
    	String itemsDeUsuarioJson = unGson.toJson(amigo.getItems());
    	return Response.ok(itemsDeUsuarioJson, MediaType.APPLICATION_JSON).build();
    }
    
    private ListaDeUsuarios amigosConItems(ListaDeUsuarios amigos){
    	
    	for (Usuario amigo : amigos) {
    		//Hago que se rellene la lista posta de Items teninedo los ids de items
				amigo.setItems(amigo.getItems());
			}
    	return amigos;
    }

}
