package com.tacs.truequeLibre.Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.Parameter;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.FacebookType;
import com.restfb.types.User;
import com.tacs.truequeLibre.domain.ListaDeUsuarios;
import com.tacs.truequeLibre.domain.Usuario;

public class LlamadasFB implements ILlamadasFB {
	
	public static String appID = "347575272090580";
	public static String appSecret = "28f123fe638801f3f519663c4f747d0c";
	
	public Usuario getLoggedUser(HttpHeaders header){
		String token = header.getCookies().get("token").getValue();
  	DefaultFacebookClient facebookClient =  getFBClient(token);
    User user = facebookClient.fetchObject("me", User.class);
    	
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
    Connection<User> myFriends = facebookClient.fetchConnection("me/friends", User.class,
    		Parameter.with("fields", "id, name"));
    ListaDeUsuarios amigos = new ListaDeUsuarios();
    for (List<User> myFriendConnection : myFriends)
      for (User usuario : myFriendConnection ){
      	System.out.println("Amigo: "+ usuario.getName() +"," + usuario.getId() );
      	Usuario amigo = HandlerDS.findUsuarioById(usuario.getId());
      	if(amigo != null)
      		amigos.add(amigo);
      }
		return amigos;
	}
	
	public DefaultFacebookClient getFBClient(String accessToken){		
    return new DefaultFacebookClient(accessToken, LlamadasFB.appSecret);
	}
	
    public static void enviarNotificacionAlOtro(DefaultFacebookClient facebookClient, 
    	String externalUserId, String message, String href) {
    	String	app_access_token = facebookClient.obtainAppAccessToken("347575272090580", "28f123fe638801f3f519663c4f747d0c").getAccessToken();
    	//tengo que generar un facebookClient con el APP_access_token
    	DefaultFacebookClient facebookClientAppAccessToken = new DefaultFacebookClient( app_access_token );
        try {
        	facebookClientAppAccessToken.publish("/"+externalUserId+"/notifications?access_token="+URLEncoder.encode(app_access_token, "UTF-8"), FacebookType.class,
	                Parameter.with("template", message),
	                Parameter.with("href", href));
	    } catch (FacebookOAuthException e) {
        	System.out.println("Error: " + e.getErrorType() + e.getMessage());
	        if (e.getErrorCode() == 200) { //No es un usuario de la aplicacion
	        	System.out.println("Error: " + e.getErrorType() + e.getMessage());
	        } else if (e.getErrorCode() == 100) {//El mensaje no puede tener mas de 80 caracteres
	        
	        }
	    } catch (UnsupportedEncodingException e) {// Por lo del encoding, error de la barra
				e.printStackTrace();
			}
	}

}
