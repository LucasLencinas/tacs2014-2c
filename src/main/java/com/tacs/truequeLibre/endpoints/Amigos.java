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
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import static com.googlecode.objectify.ObjectifyService.ofy;

import com.tacs.truequeLibre.setup.Setup;
import com.tacs.truequeLibre.domain.Item;
import com.tacs.truequeLibre.domain.ListaDeUsuarios;
import com.tacs.truequeLibre.domain.ObjetoML;
import com.tacs.truequeLibre.domain.Trueque;
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
	 *
	 *Lo empiezo a probar aca porque es la primer llamada que se hace desde javascript hasta el servidor, supongo que tendria que ser
	 *en otra pero despues los cambiamos
	 *
	 * */
    @GET 
    @Produces("application/json")
    public Response index(@Context HttpHeaders header) {
      // NEGRADA FIXME
      if(Setup.isSet == false){
      	Setup.isSet = true;
      	Setup.setup();
      }
      

      System.out.println("Guardo el primer item del trueque");
      ofy().save().entity(Setup.trueque1.getItemOfrecido()).now(); 
      System.out.println("Traigo el primer item del trueque");
      Item item = ofy().load().type(Item.class).id(Setup.trueque1.getItemOfrecido().getId()).now();
      System.out.println("El item es: " + item.getTitulo() + "  -->" +item.getDescripcion());
      System.out.println("Su item de mercado libre es : " + item.getObjML().getPermalink());
      
      
      System.out.println("Guardo el segundo item del trueque");
      ofy().save().entity(Setup.trueque1.getItemSolicitado()).now(); 
      System.out.println("Traigo el segundo item del trueque");
      item = ofy().load().type(Item.class).id(Setup.trueque1.getItemSolicitado().getId()).now();
      System.out.println("El item es: " + item.getTitulo() + "  -->" + item.getDescripcion());
      System.out.println("Su item de mercado libre es : " + item.getObjML().getPermalink());
      
      System.out.println("Guardo el primer usuario del trueque");
      ofy().save().entity(Setup.trueque1.getUsuarioSolicitado()).now(); 
      System.out.println("Traigo el primer usuario del trueque");
      Usuario usuario = ofy().load().type(Usuario.class).id(Setup.trueque1.getUsuarioSolicitado().getId()).now();
      System.out.println("El item es: " + usuario.getNombre() + "  -->" + usuario.getId());
      

      //guardarTruequeDePrueba(Setup.trueque1);
      
      
    	
    	System.out.println(header.toString());
      Usuario user = Setup.facebook.getLoggedUser(header);
      if(user == null)
      return Response.status(500).build();
      
      System.out.println("me pidieron los amigos de " + user.getNombre());
      ListaDeUsuarios amigos = Setup.facebook.getAmigos(user, header);
    	String usuariosJson = new Gson().toJson(amigos);
    	
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
    	
    	Usuario unUsuario= Setup.usuarios.findById(id);	
    	Gson unGson = new Gson();
    	String unUsuarioJson = unGson.toJson(unUsuario);
    	return Response.ok(unUsuarioJson, MediaType.APPLICATION_JSON).build();
    }
    
    /**
     * FIXME(Lucas) --> Este lo modifique, ahora se le pasa un String id, ver si hay error
    **/
    @GET
    @Path("/{id}/items")
    @Produces("application/json")
    public Response showItems(@PathParam("id") String id){
    	Usuario unUsuario= Setup.usuarios.findById(id);	
    	Gson unGson = new Gson();
    	String itemsDeUsuarioJson = unGson.toJson(unUsuario.getItems());	//No se si va a andar
    	return Response.ok(itemsDeUsuarioJson, MediaType.APPLICATION_JSON).build();
    }


    /*Funciones de prueba para el GAE DataStore*/
    
   
    
}
