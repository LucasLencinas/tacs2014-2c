package com.tacs.truequeLibre.domain;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class ListaDeUsuarios extends ArrayList<Usuario>{

	public static int contador = 0;
	
	public Usuario findById(String idUser){
		
		for (Usuario unUsuario : this) {
			if(unUsuario.getId().equalsIgnoreCase(idUser))
				return unUsuario;
		}
			return null;
	}
	
	
	
	public void clear(){
		super.clear();
		ListaDeUsuarios.contador = 0;
	}

	
}