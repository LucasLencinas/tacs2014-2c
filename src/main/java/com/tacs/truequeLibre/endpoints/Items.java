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
import com.tacs.truequeLibre.domain.Item;
import com.tacs.truequeLibre.domain.ObjetoML;;

@Path("/items")
public class Items {

	/**
	 * Listar Items.
	 * @return: Un JSON que representa la lista de items que esta en la aplicacion
	 */
    @GET 
    @Produces("application/json")
    public Response index() {
    	String itemsJson = new Gson().toJson(Main.items);
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
    	
    	Item unItem= Main.items.findById(id);	
    	Gson unGson = new Gson();
    	String unItemJson = unGson.toJson(unItem);
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
	public Response create(String item_json) {
    	Gson parser = new Gson();
    	Item unItem = parser.fromJson(item_json, Item.class);
    	Main.items.add(unItem);
    	return Response.ok(new Gson().toJson(unItem), MediaType.APPLICATION_JSON).build();
    }

    
    /**
     * Actualiza un item de la base de datos del sistema dado un determinado id. TESTEAR
     * @param id: del producto a actualizar
     * @param title: dato a actualizar
     * @param description: dato a actualizar
     * @param permalink: dato a actualizar
     * @param ma_id: dato a actualizar
     * @return: el mismo objeto en formato json actualizado
     */
    @PUT
    @Path("/{id}")
    @Produces("application/json")
   	public Response update(
			@PathParam("id") Integer id, 			
			@FormParam("title") String title,
			@FormParam("description") String description, 
			@FormParam("ml_permalink") String permalink, 
			@FormParam("ml_id") String ml_id,
			@FormParam("ml_thumbnail") String ml_thumbnail) {
    	Item unItemDeLista = Main.items.findById(id);
    	unItemDeLista.setId(id);
    	unItemDeLista.setDescripcion(description);;
    	unItemDeLista.setTitulo(title);;
    	unItemDeLista.setObjML(new ObjetoML(permalink, ml_id, ml_thumbnail));
    	return Response.ok(new Gson().toJson(unItemDeLista), MediaType.APPLICATION_JSON).build();
    }
    
    /**
     * Borra un elemento determinado por el id que se le pasa como parametro
     * @param id: del item a borrar
     * @return: el json del objeto borrado.
     */
    @DELETE
    @Path("/{id}")
    public Response destroy(@PathParam("id") Integer id){
    	Item unItem = Main.items.findById(id);
    	Main.items.remove(unItem);
    	return Response.ok("Item "+ new Gson().toJson(unItem)+" eliminado", MediaType.TEXT_PLAIN).build();
    }
}
