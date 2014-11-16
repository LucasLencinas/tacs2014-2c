package com.tacs.truequeLibre.Utils;

import javax.ws.rs.core.HttpHeaders;

import com.restfb.DefaultFacebookClient;
import com.tacs.truequeLibre.setup.Setup;
import com.tacs.truequeLibre.domain.ListaDeUsuarios;
import com.tacs.truequeLibre.domain.Usuario;

public class LlamadasMockFB implements ILlamadasFB {

	@Override
	public Usuario getLoggedUser(HttpHeaders header) {
		return Setup.usuarios.findById(Setup.miUsuario.getId());
	}

	@Override
	public ListaDeUsuarios getAmigos(Usuario user, HttpHeaders header) {
		return Setup.usuarios;
	}

	@Override
	public DefaultFacebookClient getFBClient(String accessToken) {
		return new DefaultFacebookClient();
	}

}
