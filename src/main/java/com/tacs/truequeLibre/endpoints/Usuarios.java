package com.tacs.truequeLibre.endpoints;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.tacs.truequeLibre.Main;
import com.tacs.truequeLibre.domain.Usuario;

@Path("/usuarios")
public class Usuarios {

	/**
	 * Listar Usuarios.
	 * @return: Un JSON que representa la lista de usuarios que estan en la aplicacion
	 */
    @GET 
    @Produces("application/json")
    public Response index() {
    	System.out.println("Me pidieron los Usuarios");
    	String usuariosJson = new Gson().toJson(Main.usuarios);
    	
      return Response.ok(usuariosJson,MediaType.APPLICATION_JSON).build();
    }
    
     /**
      *  Listar un determinado usuario.
      * @param id: El id de un usuario que quiero ver
      * @return: un usuario con ese id que se pasa por parametro
      */
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response show(@PathParam("id") Integer id){
    	
    	Usuario unUsuario= Main.usuarios.findById(id);	
    	Gson unGson = new Gson();
    	String unUsuarioJson = unGson.toJson(unUsuario);
    	return Response.ok(unUsuarioJson, MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/{id}/items")
    @Produces("application/json")
    public Response showItems(@PathParam("id") Integer id){
    	System.out.println("Me pidieron los items de un usuario");
    	Usuario unUsuario= Main.usuarios.findById(id);	
    	Gson unGson = new Gson();
    	String itemsDeUsuarioJson = unGson.toJson(unUsuario.getItems());	//No se si va a andar
    	System.out.println(itemsDeUsuarioJson);
    	return Response.ok(itemsDeUsuarioJson, MediaType.APPLICATION_JSON).build();
    }
    

}
