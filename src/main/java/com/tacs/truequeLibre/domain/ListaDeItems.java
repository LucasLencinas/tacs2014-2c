package com.tacs.truequeLibre.domain;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class ListaDeItems extends ArrayList<Item>{
	
	public Item findById(int unId){
		
		for (Item unItem : this) {
			if(unItem.getId() == unId)
				return unItem;
		}
			return null;
	}

}
