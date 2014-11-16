package com.tacs.truequeLibre.Utils;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.tacs.truequeLibre.domain.Item;
import com.tacs.truequeLibre.domain.ListaDeTrueques;
import com.tacs.truequeLibre.domain.Trueque;
import com.tacs.truequeLibre.domain.Usuario;

public class HandlerDS {
	
	public static long guardarItem(Item item){
		ofy().save().entity(item).now();
		return item.getId();
	}
	
	public static String guardarUsuario(Usuario usuario){
		ofy().save().entity(usuario).now();
		return usuario.getId();
	}
	
	public static long guardarTrueque(Trueque trueque){
		ofy().save().entity(trueque).now();
		return trueque.getId();
	}
	
	public static Trueque findTruequeById(long id){
		return ofy().load().type(Trueque.class).id(id).now();
	}
	
	public static Item findItemById(long id){
		return ofy().load().type(Item.class).id(id).now();
	}
	
	public static Usuario findUsuarioById(String id){
		return ofy().load().type(Usuario.class).id(id).now();
	}
	
	public static ListaDeTrueques findTruequeByUser(Usuario usuario){
		ListaDeTrueques truequesBuscados = new ListaDeTrueques();
    Iterable<Trueque> trueques = ofy().load().type(Trueque.class);
    for (Trueque trueque : trueques) 
			if(trueque.getUsuarioSolicitado().getId() == usuario.getId() || 
					trueque.getUsuarioSolicitante().getId() == usuario.getId())
				truequesBuscados.add(trueque);
		
		return truequesBuscados;
	}
	
	public static ListaDeTrueques findTruequeByItem (Item item){
		ListaDeTrueques truequesBuscados = new ListaDeTrueques();
    Iterable<Trueque> trueques = ofy().load().type(Trueque.class);
    for (Trueque trueque : trueques) 
			if(trueque.getItemOfrecido().getId() == item.getId() || 
					trueque.getItemSolicitado().getId() == item.getId())
				truequesBuscados.add(trueque);
		
		return truequesBuscados;
	}
	
}
