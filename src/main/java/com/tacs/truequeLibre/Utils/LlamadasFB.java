package com.tacs.truequeLibre.Utils;

import java.util.List;

import javax.ws.rs.core.HttpHeaders;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.Parameter;
import com.restfb.types.User;
import com.tacs.truequeLibre.domain.ListaDeUsuarios;
import com.tacs.truequeLibre.domain.Usuario;

public class LlamadasFB implements ILlamadasFB {
	
	public static String appID = "347575272090580";
	public static String appSecret = "28f123fe638801f3f519663c4f747d0c";
	
	public Usuario getLoggedUser(HttpHeaders header){
		System.out.println("Function: getLoggedUser");
		String token = header.getCookies().get("token").getValue();
  	DefaultFacebookClient facebookClient =  getFBClient(token);
    User user = facebookClient.fetchObject("me", User.class);
    System.out.println("Id user: " + user.getId());
    	
    Usuario usuario = HandlerDS.findUsuarioById(user.getId());
    if(usuario == null){
    	usuario = new Usuario(user.getName(), user.getId());
    	HandlerDS.guardarUsuario(usuario);
    	System.out.println("Se agrega el usuario: " + usuario.getNombre());
		}
    System.out.println("Logged User: " + usuario.getNombre());
  	return usuario;
  }
	
	public ListaDeUsuarios getAmigos(Usuario user, HttpHeaders header){
		
		String token = header.getCookies().get("token").getValue();
		DefaultFacebookClient facebookClient =  getFBClient(token);
		System.out.println("User name: " + user.getNombre());
    Connection<User> myFriends = facebookClient.fetchConnection("me/friends", User.class,
    		Parameter.with("fields", "id, name"));
    ListaDeUsuarios amigos = new ListaDeUsuarios();
    for (List<User> myFriendConnection : myFriends)
      for (User usuario : myFriendConnection ){
      	System.out.println("Amigo: "+ usuario.getName() +"," + usuario.getId() );
      	amigos.add(HandlerDS.findUsuarioById(usuario.getId()));
      }
		return amigos;
	}
	
	public DefaultFacebookClient getFBClient(String accessToken){		
    return new DefaultFacebookClient(accessToken, LlamadasFB.appSecret);
	}
	

}
