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

@Path("/items")
public class Items {

    @GET 
    @Produces("text/plain")
    public String index() {
        return "Listado items publicados por amigos";
    }
    
      
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response show(@PathParam("id") Integer id){
    	String json = "{id:"+id+ ",title:'example',description:'example',mercadolibre:{permalink: 'http://articulo.mercadolibre.com.ar/MLA-430387888-anteojos-ray-ban-wayfare-_JM',id:'MLA430387888'}}";
    	return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/new")
    @Produces("text/plain")
    public String _new(){
    	return "Formulario para crear un item. Parametros: id, title, description, mercadolibre[permalink], mercadolibre[id]";
    }
    
    @POST
    @Produces("application/json")
	public Response create(
			@FormParam("title") String title,
			@FormParam("description") String description, 
			@FormParam("mercadolibre[permalink]") String permalink, 
			@FormParam("mercadolibre[id]") String ma_id) {
    	String json = "{id: 2, title:'"+title+"',description:'"+description+"',mercadolibre:{permalink: '"+permalink+",id:'"+ma_id+"'}}";
    	return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    
    @GET
    @Path("/{id}/edit")
    @Produces("text/plain")
    public String edit(@PathParam("id") Integer id){
    	return "Form para editar item "+ id;
    }
    
    @PUT
    @Path("/{id}")
    @Produces("application/json")
	public Response update(
			@PathParam("id") Integer id, 			
			@FormParam("title") String title,
			@FormParam("description") String description, 
			@FormParam("mercadolibre[permalink]") String permalink, 
			@FormParam("mercadolibre[id]") String ma_id) {
    	String json = "{id: "+id+", title:'"+title+"',description:'"+description+"',mercadolibre:{permalink: '"+permalink+",id:'"+ma_id+"'}}";
    	return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response destroy(@PathParam("id") Integer id){
    	return Response.ok().build();
    }
}
