package com.tacs.truequeLibre.domain;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class ListaDeItems extends ArrayList<Item>{

	public static int contador = 0;
	
	public Item findById(int unId){
		
		for (Item unItem : this) {
			if(unItem.getId() == unId)
				return unItem;
		}
			return null;
	}
	
	
	
	public void clear(){
		super.clear();
		ListaDeItems.contador = 0;
	}

	public static int getNewID() {
		return ++contador;
	}

	
}
