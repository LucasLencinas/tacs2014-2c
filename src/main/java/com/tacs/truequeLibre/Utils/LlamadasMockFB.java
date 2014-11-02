package com.tacs.truequeLibre.Utils;

import javax.ws.rs.core.HttpHeaders;

import com.restfb.DefaultFacebookClient;
import com.tacs.truequeLibre.Main;
import com.tacs.truequeLibre.domain.ListaDeUsuarios;
import com.tacs.truequeLibre.domain.Usuario;

public class LlamadasMockFB implements ILlamadasFB {

	@Override
	public Usuario getLoggedUser(HttpHeaders header) {
		// TODO Auto-generated method stub
		return Main.usuarios.findById(Main.miUsuario.getId());
	}

	@Override
	public ListaDeUsuarios getAmigos(Usuario user, HttpHeaders header) {
		// TODO Auto-generated method stub
		return Main.usuarios;
	}

	@Override
	public DefaultFacebookClient getFBClient(String accessToken) {
		// TODO Auto-generated method stub
		return new DefaultFacebookClient();
	}

}
