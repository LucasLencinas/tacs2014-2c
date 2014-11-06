package com.tacs.truequeLibre.endpoints;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;








import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.Gson;
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
      
      guardarItemDePrueba(Setup.trueque1.getItemOfrecido());
      guardarItemDePrueba(Setup.trueque1.getItemSolicitado());
      guardarUsuarioDePrueba(Setup.trueque1.getUsuarioSolicitado());
      guardarUsuarioDePrueba(Setup.trueque1.getUsuarioSolicitante());
      guardarTruequeDePrueba(Setup.trueque1);
      
      
    	
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
    
    private void guardarItemDePrueba(Item item) {

    	
      Key itemKey = KeyFactory.createKey("Item", item.getId());
      Entity entityItem = new Entity("Item", itemKey);
      entityItem.setProperty("id", item.getId());
      entityItem.setProperty("title", item.getTitulo());
      entityItem.setProperty("description", item.getDescripcion());
      
      entityItem.setProperty("objML",crearObjetoMLDePrueba(item.getObjML()));
      
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      datastore.put(entityItem);
	}
    
    private void guardarUsuarioDePrueba(Usuario usuario) {

      Key usuarioKey = KeyFactory.createKey("Usuario", usuario.getId());
      Entity entityUsuario = new Entity("Usuario", usuarioKey);
      entityUsuario.setProperty("id", usuario.getId());
      entityUsuario.setProperty("nombre", usuario.getNombre());
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      datastore.put(entityUsuario);
	}
    
    private EmbeddedEntity crearObjetoMLDePrueba(ObjetoML objetoML) {

    	
    	EmbeddedEntity embeddedObjetoML = new EmbeddedEntity();

    	embeddedObjetoML.setProperty("id", objetoML.getId());
    	embeddedObjetoML.setProperty("permalink", objetoML.getPermalink());
    	embeddedObjetoML.setProperty("thumbnail", objetoML.getThumbnail());
			
    	return embeddedObjetoML;
	}
    
    private void guardarTruequeDePrueba(Trueque trueque) {

      Key truequeKey = KeyFactory.createKey("Trueque", trueque.getId());
      Entity entityTrueque = new Entity("Trueque", truequeKey);
      entityTrueque.setProperty("id", trueque.getId());
      entityTrueque.setProperty("estado", trueque.getEstado());
      entityTrueque.setProperty("description", trueque.getDescripcion());
      entityTrueque.setProperty("itemOfrecido", trueque.getItemOfrecido().getId());
      entityTrueque.setProperty("itemSolicitado", trueque.getItemSolicitado().getId());
      entityTrueque.setProperty("usuarioSolicitado", trueque.getUsuarioSolicitado().getId());
      entityTrueque.setProperty("usuarioSolicitante", trueque.getUsuarioSolicitante().getId());
      
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      datastore.put(entityTrueque);
	}
    
}
