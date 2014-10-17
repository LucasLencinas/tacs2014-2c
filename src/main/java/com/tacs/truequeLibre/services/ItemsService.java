package com.tacs.truequeLibre.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.tacs.truequeLibre.domain.Item;
import com.tacs.truequeLibre.domain.Usuario;

public class ItemsService {
	
	private static HashMap<Integer, Item> items;
	private static int next_id;
	private static HashMap<Integer, Item> getItems(){
		if(items==null){
			items = new HashMap<Integer,Item>();
			next_id = 0;
		}
		return items;
	}
	
	public static Item find(int id){
		return getItems().get(id);		
	}
	
	public static void add(int user_id, Item item){
		Usuario usuario = UsersService.find(user_id);
		item.setUsuario(usuario);
		item.setId(next_id++);
		getItems().put(item.getId(), item);				
	}
	
	public static Collection<Item> by_user(int user_id){
		ArrayList<Item> results = new ArrayList<Item>();
		for (Item item: ItemsService.all()) {
			  if (item.getUsuario().getId()==user_id) {
			    results.add(item);
			  }
			}
		return results;		
	}
	
	public static Collection<Item> all(){
		return new ArrayList<Item>(getItems().values());		
	}

	public static void clearAll() {
		items = new HashMap<Integer,Item>();
		next_id = 0;		
	}

	public static Item delete(Integer id) {	    
		return getItems().remove(id);
	}

}
