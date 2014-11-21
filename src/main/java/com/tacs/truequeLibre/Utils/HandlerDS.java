package com.tacs.truequeLibre.Utils;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.tacs.truequeLibre.domain.Item;
import com.tacs.truequeLibre.domain.ListaDeItems;
import com.tacs.truequeLibre.domain.ListaDeTrueques;
import com.tacs.truequeLibre.domain.ListaDeUsuarios;
import com.tacs.truequeLibre.domain.Trueque;
import com.tacs.truequeLibre.domain.Usuario;

public class HandlerDS {
	
	
	public static ListaDeItems items(){
		ListaDeItems itemsResult = new ListaDeItems();
		Iterable<Item> itemsDS = new ArrayList<Item>() ;
		try {
			itemsDS = ofy().load().type(Item.class).list();
		} catch(NotFoundException ex){
			System.out.println("Item no encontrado!!!!");
		}
		
		for (Item item : itemsDS) 
			itemsResult.add(item);
		return itemsResult;
	}
	
	public static long guardarItem(Item item){
		System.out.println("Agrego el item: " + item.getTitulo() + " con Id: " + item.getId());
		ofy().save().entity(item).now();
		return item.getId();
	}
	
	public static String guardarUsuario(Usuario usuario){
		ofy().save().entity(usuario).now();
		return usuario.getId();
	}
	
	public static long guardarTrueque(Trueque trueque){
		System.out.println("Antes de guardar el trueque:" + trueque.getUsuarioSolicitado().getId());
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
    for (Trueque trueque : trueques) {
    	System.out.println("Un trueque traido del DS" + trueque.toString());
    	if(trueque.getUsuarioSolicitado().getId().equals(usuario.getId()) || 
    			trueque.getUsuarioSolicitante().getId().equals(usuario.getId()))
    		truequesBuscados.add(trueque);
    }
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
	
	
	public static ListaDeTrueques findPendingTruequesByUser(Usuario usuario){
	ListaDeTrueques result = new ListaDeTrueques();
	Iterable<Trueque> trueques = ofy().load().type(Trueque.class);
	if(trueques == null){
		int a = 5/0;
	}
	for (Trueque trueque: trueques) {
			if(trueque == null) {
				int x = 3/0;
			}
		  if (trueque.getEstado() == TruequeStatusConstants.PENDING.getID() && trueque.getUsuarioSolicitado().getId().equals(usuario.getId())) {
		    result.add(trueque);
		  }
		}
	return result;
	}

	public static boolean deleteItem(Item item, Usuario user) {
		ListaDeTrueques truequesPendientes = findPendingTruequesByUser(user);
		for (Trueque trueque : truequesPendientes) {
			if(trueque.getItemOfrecido().getId() == item.getId() || trueque.getItemSolicitado().getId() == item.getId())
				return false;
		}
		ofy().delete().entity(item).now();
  	user.quitarItem(item);
		return true;
	}

	public static void deleteAll() {
		List<Key<Usuario>> userKeys = ofy().load().type(Usuario.class).keys().list();
		ofy().delete().keys(userKeys).now();
		List<Key<Item>> itemKeys= ofy().load().type(Item.class).keys().list();
		ofy().delete().keys(itemKeys).now();
		List<Key<Trueque>> truequeKeys= ofy().load().type(Trueque.class).keys().list();
		ofy().delete().keys(truequeKeys).now();
	}

	public static ListaDeUsuarios findAllUsers() {
		return (ListaDeUsuarios) ofy().load().type(Usuario.class).list();
	}
}
