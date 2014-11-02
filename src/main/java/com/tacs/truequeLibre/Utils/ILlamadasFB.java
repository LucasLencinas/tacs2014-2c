package com.tacs.truequeLibre.Utils;

import javax.ws.rs.core.HttpHeaders;
import com.restfb.DefaultFacebookClient;
import com.tacs.truequeLibre.domain.ListaDeUsuarios;
import com.tacs.truequeLibre.domain.Usuario;

public interface ILlamadasFB {
	


	
	public static String appID = "347575272090580";
	public static String appSecret = "28f123fe638801f3f519663c4f747d0c";
	
	public Usuario getLoggedUser(HttpHeaders header);
	
	public ListaDeUsuarios getAmigos(Usuario user, HttpHeaders header);
	
	public DefaultFacebookClient getFBClient(String accessToken);
	

}

