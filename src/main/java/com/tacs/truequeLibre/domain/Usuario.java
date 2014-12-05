package com.tacs.truequeLibre.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.tacs.truequeLibre.Utils.HandlerDS;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Entity
@SuppressWarnings("serial")
public class Usuario implements Serializable{

	
	@Expose @Id private String id;
	@Expose private String nombre;
	@Ignore @Expose private ListaDeItems items;
	@Ignore @Expose private ListaDeUsuarios amigos;
  
	private List<String> amigosId;
	private List<Long> itemsId;
  
  public Usuario(String unNombre,String id) {
  	this.setId(id);
    this.setNombre(unNombre);
    this.setItems(new ListaDeItems());
    this.setAmigos(new ListaDeUsuarios());  
  	amigosId = new ArrayList<String>();
  	itemsId = new ArrayList<Long>();
  }
  
  public Usuario() {
  }
 
  @Override
  public String toString(){
  	
  	String stringItemsId = "[";
  	String stringAmigosId = "[";
  	if(this.itemsId != null)
	  	for (Long itemId : this.itemsId) {
				stringItemsId += itemId + ", ";
			}
  	if(this.amigosId != null)
	  	for (String amigoId : this.amigosId) {
				stringAmigosId += amigoId + ", ";
			}
  	stringAmigosId += "]";
  	stringAmigosId = stringAmigosId.replace(", ]", "]");
  	
  	stringItemsId += "]";
  	stringItemsId = stringItemsId.replace(", ]", "]");
  	return this.nombre + ": " + "Amigos: " + stringAmigosId + ",Items: " + stringItemsId;
  	
  }
  
  /**
   * 
   * Getters and Setters
   */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String unNombre) {
		this.nombre= unNombre;
	}

	public ListaDeItems getItems() {
		ListaDeItems items = new ListaDeItems();
		if(this.itemsId != null){
			for (long itemId : this.itemsId) {
				items.add(ofy().load().type(Item.class).id(itemId).now());
			}
		}
		this.setItems(items);
		//HandlerDS.guardarUsuario(this);
		return items;
	}

	public void setItems(ListaDeItems unosItems) {
		this.items= unosItems;
	}

	public ListaDeUsuarios getAmigos() {
		ListaDeUsuarios amigos = new ListaDeUsuarios();
		for (String amigosId : this.amigosId) {
			amigos.add(ofy().load().type(Usuario.class).id(amigosId).now());
		}
		return this.amigos = amigos;
	}

	public void setAmigos(ListaDeUsuarios unosAmigos) {
		this.amigos= unosAmigos;
	}

	
  /*----------For Items------------*/
	public void agregarItem(Item item){
		System.out.print("Item: " + item.toString() + " agregado al usuario: " + this.toString());
		if(this.itemsId == null){
			System.out.print(" --El usuario tiene itemsId == null, entonces la creo --");
			this.itemsId = new ArrayList<Long>();
		}
		this.itemsId.add(item.getId());		//Lo agrego en la lista de ids tambien para guardarlo en el DS
		
		/*HandlerDS.guardarUsuario(this);*/	ofy().save().entity(this).now();
		System.out.println("   OK");
	}
	
	public void quitarItem(Item item){
		int index = this.itemsId.indexOf(item.getId());
		System.out.print("Item: " + item.toString() + " quitado del usuario: " + this.toString());
		this.itemsId.remove(index);
		HandlerDS.guardarUsuario(this);
		System.out.println("   OK");
	}

	public void truequearItem(Item miViejoItem, Item miNuevoItem) {
		System.out.print("Truequear Items: " +miViejoItem.toString() +", " + miNuevoItem.toString());
		this.agregarItem(miNuevoItem);
		this.quitarItem(miViejoItem);
		System.out.println("   OK");
	}
	
	/*----------For Friends------------*/
	
	public void agregarAmigo(Usuario amigo){
		System.out.print("Agrego al usuario: " + this.toString()  +", el amigo: " + amigo.toString());
		if(this.amigosId == null){
			System.out.print(" --El usuario tiene amigosId == null, entonces la creo --");
			this.amigosId = new ArrayList<String>();
		}
		if(!this.amigosId.contains(amigo.getId()))
			this.amigosId.add(amigo.getId());		//Lo agrego en la lista de ids tambien para guardarlo en el DS
		HandlerDS.guardarUsuario(this);	//Lo guardo de nuevo asi se actualiza
		
		if(amigo.amigosId == null)
			amigo.amigosId = new ArrayList<String>();
		if(!amigo.amigosId.contains(this.getId()))
			amigo.amigosId.add(this.getId());	//Ahora hago lo mismo con el amigo	
		HandlerDS.guardarUsuario(amigo);
		
		System.out.println("   OK");
		
	}
	
	public void quitarAmigo(Usuario amigo){
		System.out.print("Amigo: " + amigo.toString() + "quitado del usuario: " + this.toString());
		int index = this.amigosId.indexOf(amigo.getId());
		this.amigosId.remove(index);
		ofy().save().entity(this).now();
		
		index = amigo.amigosId.indexOf(this.getId());
		amigo.amigosId.remove(index);
		ofy().save().entity(amigo).now();
		System.out.print("   OK");
	}

	public String printItemsId() {
		if(this.itemsId != null)
			return this.itemsId.toString();
		else
			return "null";
	}
}