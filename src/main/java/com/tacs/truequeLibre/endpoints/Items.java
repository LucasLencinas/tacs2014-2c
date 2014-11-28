package com.tacs.truequeLibre.endpoints;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.tacs.truequeLibre.setup.Setup;
import com.tacs.truequeLibre.Utils.HandlerDS;
import com.tacs.truequeLibre.domain.Item;
import com.tacs.truequeLibre.domain.ListaDeItems;
import com.tacs.truequeLibre.domain.ListaDeTrueques;
import com.tacs.truequeLibre.domain.Usuario;

@Path("/items")
public class Items {

	/**
	 * Listar Items.
	 * @return: Un JSON que representa la lista de items que esta en la aplicacion
	 */
    @GET 
    @Produces("application/json")
    public Response index(){
    	System.out.println("Request --> All Items");
      String itemsJson = new Gson().toJson(HandlerDS.items());
      System.out.println("Response --> All Items   OK");
      return Response.ok(itemsJson,MediaType.APPLICATION_JSON).build();
    }
    
     /**   
      *  Listar un determinado item.
      * @param id: El id de un item que quiero ver
      * @return: un item con ese id que se pasa por parametro
      */
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response show(@PathParam("id") Integer id){
    	System.out.println("Request --> Items con id: " + id);
    	Item unItem= HandlerDS.findItemById(id);	
    	Gson unGson = new Gson();
    	String unItemJson = unGson.toJson(unItem);
    	System.out.println("Response OK-->Item: " + unItemJson);
    	return Response.ok(unItemJson, MediaType.APPLICATION_JSON).build();
    }
    
    
    /**
     * Le paso todos los parametros que necesita y me crea un articulo nuevo y lo guarda en el
     * sistema(por ahora una lista de articulos en memoria) TESTEAR
     * @param title: titulo del articulo
     * @param description: descripcion del articulo 
     * @param ml_permalink: link al articulo.
     * @param ma_id: id de merdadoLibre del articulo
     * @return: el mismo json.
     */
    @POST
    @Produces("application/json")
    @Consumes("application/json")
	public Response create(String item_json, @Context HttpHeaders header) {
      Usuario user = Setup.facebook.getLoggedUser(header);
      System.out.println("Request --> Nuevo Item: " + item_json  + ", para el Usuario: " + user.toString());
      Gson parser = new Gson();
    	Item unItem = parser.fromJson(item_json, Item.class);
    	Usuario actual = HandlerDS.findUsuarioById(user.getId());
    	unItem.setId(ListaDeItems.getNewID());
    	HandlerDS.guardarItem(unItem);
    	actual.agregarItem(unItem);
    	//System.out.println("Cantidad de items del usuario "+actual.getId()+" despues de agregarlo: " + Setup.facebook.getLoggedUser(header).getItems().size());
    	System.out.println("Response OK --> Nuevo Item creado para el Usuario: " + user.toString());
    	return Response.ok(new Gson().toJson(unItem), MediaType.APPLICATION_JSON).build();
    }
    
    /**
     * Borra un elemento determinado por el id que se le pasa como parametro
     * @param id: del item a borrar
     * @return: el json del objeto borrado.
     */
    @DELETE
    @Path("/{id}")
    public Response destroy(@PathParam("id") Integer id, @Context HttpHeaders header) {
      Usuario user = Setup.facebook.getLoggedUser(header);
      System.out.println("Request --> Delete Item " + id + " del Usuario: " + user.toString());
    	Item unItem = HandlerDS.findItemById(id);
    	HandlerDS.deleteItem(unItem,user);
    	System.out.println("Request --> Delete Item   OK" + id);
    	return Response.ok("Item "+ new Gson().toJson(unItem)+" eliminado", MediaType.TEXT_PLAIN).build();
    }
}
