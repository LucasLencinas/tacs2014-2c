package com.tacs.truequeLibre.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.tacs.truequeLibre.domain.Usuario;

public class UsersService {
	
	private static HashMap<Integer, Usuario> usuarios;
	private static int next_id;
	private static HashMap<Integer, Usuario> getUsuarios(){
		if(usuarios==null){
			usuarios = new HashMap<Integer,Usuario>();
			next_id = 0;
		}
		return usuarios;
	}
	
	public static Usuario find(int id){
		return getUsuarios().get(id);		
	}
	
	public static void add(Usuario usuario){		
		usuario.setId(next_id++);
		getUsuarios().put(usuario.getId(), usuario);				
	}
	
	
	public static Collection<Usuario> all(){
		return new ArrayList<Usuario>(getUsuarios().values());		
	}

	public static void clearAll() {
		usuarios = new HashMap<Integer,Usuario>();
		next_id = 0;		
	}

}
