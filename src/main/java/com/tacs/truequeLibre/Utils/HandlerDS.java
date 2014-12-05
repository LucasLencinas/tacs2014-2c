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
		System.out.print("Guardar Item: " +item.toString());
		System.out.print(" --> Ofy = " + ofy().save().entity(item).now().getId());
		System.out.println("  OK");
		return item.getId();
	}
	
	public static String guardarUsuario(Usuario usuario){
		System.out.print("Guardar Usuario : " +usuario.toString());
		System.out.print(" --> Ofy = " + ofy().save().entity(usuario).now().getId());
		System.out.println("  OK"); 	
		return usuario.getId();
	}
	
	public static long guardarTrueque(Trueque trueque){
		System.out.print("Guardar Trueque :" + trueque.toString());
		System.out.print(" --> Ofy = " + ofy().save().entity(trueque).now().getId());
		System.out.println("  OK");
		return trueque.getId();
	}
	
	public static Trueque findTruequeById(long id){
		Trueque trueque;
		try{
		  trueque = ofy().load().type(Trueque.class).id(id).now();
		} catch (NullPointerException e){
		  trueque = null;
		}
		return trueque;
	}
	
	public static Item findItemById(long id){
		Item item;
		try{
		  item = ofy().load().type(Item.class).id(id).now();
		} catch (NullPointerException e){
		  item = null;
		}
		return item;
		
	}
	
	public static Usuario findUsuarioById(String id){
		Usuario user;
		 ofy();
		 ofy().load();
		 ofy().load().type(Usuario.class);
		 ofy().load().type(Usuario.class).id(id);
		 ofy().load().type(Usuario.class).id(id).now();
		 
		user = ofy().load().type(Usuario.class).id((String)id).now();
		if(user == null)
			System.out.println("EL USUARIO ES NULL!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11");
		return user;

	}
	
	public static ListaDeTrueques findTruequeByUser(Usuario usuario){
		ListaDeTrueques truequesBuscados = new ListaDeTrueques();
    Iterable<Trueque> trueques = ofy().load().type(Trueque.class);
    for (Trueque trueque : trueques) {
    	//System.out.println("Un trueque traido del DS" + trueque.toString());
    	if(trueque.getUsuarioSolicitado().getId().equals(usuario.getId()) || 
    			trueque.getUsuarioSolicitante().getId().equals(usuario.getId()))
    		truequesBuscados.add(trueque);
    }
    System.out.println("Trueques de " +usuario.toString() +": " + truequesBuscados.toString());
		return truequesBuscados;
	}
	
	public static ListaDeTrueques findTruequeByItem (Item item){
		ListaDeTrueques truequesBuscados = new ListaDeTrueques();
    Iterable<Trueque> trueques = ofy().load().type(Trueque.class);
    for (Trueque trueque : trueques) 
			if(trueque.getItemOfrecido().getId() == item.getId() || 
					trueque.getItemSolicitado().getId() == item.getId())
				truequesBuscados.add(trueque);
    System.out.println("Trueques con " +item.toString() +": " + truequesBuscados.toString());
		return truequesBuscados;
	}
	
	
	public static ListaDeTrueques findPendingTruequesByUser(Usuario usuario){
	ListaDeTrueques truequesBuscados = new ListaDeTrueques();
	Iterable<Trueque> trueques = ofy().load().type(Trueque.class);
	/*		Negrada para saber donde rompe. FIXME		*/
	if(trueques == null){
		int a = 5/0;
	}
	for (Trueque trueque: trueques) {
			if(trueque == null) {
				int x = 3/0;
			}
		  if (trueque.getEstado() == TruequeStatusConstants.PENDING.getID() && trueque.getUsuarioSolicitado().getId().equals(usuario.getId())) {
		    truequesBuscados.add(trueque);
		  }
		}
	System.out.println("Trueques Pendientes: " + truequesBuscados.toString());
	return truequesBuscados;
	}

	public static boolean deleteItem(Item item, Usuario user) {
		ListaDeTrueques truequesPendientes = findPendingTruequesByUser(user);
		for (Trueque trueque : truequesPendientes) {
			if(trueque.getItemOfrecido().getId() == item.getId() || trueque.getItemSolicitado().getId() == item.getId())
				return false;
		}
		user.quitarItem(item);
		System.out.print("Item: " + item.toString() + "  Borrado");
		ofy().delete().entity(item).now();
		System.out.println("   OK");
		return true;
	}

	public static void deleteAll() {
		System.out.print("Borrado de Usuarios");
		List<Key<Usuario>> userKeys = ofy().load().type(Usuario.class).keys().list();
		ofy().delete().keys(userKeys).now();
		System.out.println("   OK");
		System.out.print("Borrado de Items");
		List<Key<Item>> itemKeys= ofy().load().type(Item.class).keys().list();
		ofy().delete().keys(itemKeys).now();
		System.out.println("   OK");
		System.out.print("Borrado de Trueques");
		List<Key<Trueque>> truequeKeys= ofy().load().type(Trueque.class).keys().list();
		ofy().delete().keys(truequeKeys).now();
		System.out.println("   OK");
	}

	public static ListaDeUsuarios findAllUsers() {
		return (ListaDeUsuarios) ofy().load().type(Usuario.class).list();
	}
}
