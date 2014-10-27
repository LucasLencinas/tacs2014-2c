package com.tacs.truequeLibre.endpoints;

import java.util.List;
import java.util.Map;




import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.types.User;
import com.tacs.truequeLibre.Main;
import com.tacs.truequeLibre.Utils.PropiedadesFB;
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
	 *
	 *Lo empiezo a probar aca porque es la primer llamada que se hace desde javascript hasta el servidor, supongo que tendria que ser
	 *en otra pero despues los cambiamos
	 *
	 * */
    @GET 
    @Produces("application/json")
    public Response index(@Context HttpHeaders hh) {
    	Map<String, Cookie> pathParams = hh.getCookies();
    	String accessToken = pathParams.get("token").getValue();			
      DefaultFacebookClient facebookClient = new DefaultFacebookClient(accessToken, PropiedadesFB.appSecret);
      User user = facebookClient.fetchObject("me", User.class);

      System.out.println("User name: " + user.getName());
      Connection<User> myFriends = facebookClient.fetchConnection("me/friends", User.class);
      /*Solo funciona con los usuarios que estan "usando" la aplicacion, osea, los de test, con
       * Probe con mi usuario pero no funciono. FIXME
       * */
    	Usuario usuarioLogueado = Main.getLoggedUser();
    	ListaDeUsuarios amigos = usuarioLogueado.getAmigos();
    	String usuariosJson = new Gson().toJson(amigos);
    	
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
    	Usuario unUsuario= Main.usuarios.findById(id);	
    	Gson unGson = new Gson();
    	String itemsDeUsuarioJson = unGson.toJson(unUsuario.getItems());	//No se si va a andar
    	return Response.ok(itemsDeUsuarioJson, MediaType.APPLICATION_JSON).build();
    }


}
