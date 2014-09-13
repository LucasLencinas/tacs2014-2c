package tacs.grupo4.resources;

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

import tacs.grupo4.Item;
import tacs.grupo4.Main;
import tacs.grupo4.ObjetoML;

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
    
    /*TODO: Hacer una clase de nueva ListaItems para poder usarla mejor.
     * Ahora estoy buscando un item haciendo get(id -1) pero no va a servir despues.
     * O hacer algo que extienda la interfaz de un ArrayList para poder hacer un
     * lista.findById(unId) o findByDescripcion(unaDescripcion)
     * */
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response show(@PathParam("id") Integer id){
    	Item unItem= Main.items.get(id-1);	
    	Gson unGson = new Gson();
    	String unItemJson = unGson.toJson(unItem);
    	return Response.ok(unItemJson, MediaType.APPLICATION_JSON).build();
    }
    
    
    /**
     * Arma un formulario para poder crear un item y me lo devuelve. TESTEAR
     * @return: un formulario en html o json(todavia no se sabe) -- TODO
     */
    @GET
    @Path("/new")
    @Produces("text/plain")
    public String _new(){
    	return "Formulario para crear un item. Parametros: id, title, description, mercadolibre[permalink], mercadolibre[id]";
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
	public Response create(
			@FormParam("title") String title,
			@FormParam("description") String description, 
			@FormParam("ml_permalink") String ml_permalink, 
			@FormParam("ml_id") String ml_id) {
    	Item unItem = new Item(++Main.contadorItems, title, description, new ObjetoML(ml_permalink, ml_id));
    	Main.items.add(unItem);
    	return Response.ok(new Gson().toJson(unItem), MediaType.APPLICATION_JSON).build();
    }

    /**
     * Me da un formulario para poder modificar despues el item con el id que le paso. TESTEAR
     * @param id: Id del elemento que quiero modificar
     * @return: un formulario en html o json(TODO) 
     */
    @GET
    @Path("/{id}/edit")
    @Produces("text/plain")
    public String edit(@PathParam("id") Integer id){
    	Item unItem= Main.items.get(id-1);
    	return "Form para editar item "+ new Gson().toJson(unItem);
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
			@FormParam("ml_id") String ma_id) {
    	Item unItemDeLista = Main.items.get(id-1);
    	unItemDeLista.setId(id);
    	unItemDeLista.setDescripcion(description);;
    	unItemDeLista.setTitulo(title);;
    	unItemDeLista.setObjML(new ObjetoML(permalink, ma_id));
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
    	Item unItem = Main.items.remove(id-1);
    	return Response.ok("Item "+ new Gson().toJson(unItem)+" eliminado", MediaType.TEXT_PLAIN).build();
    }
}
