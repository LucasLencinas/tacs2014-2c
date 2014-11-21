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
	@Expose private ListaDeItems items;
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
		HandlerDS.guardarUsuario(this);
		return this.items;
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
		System.out.println("Agrego al usuario: " + this.getNombre()  +", el item: " + item.getTitulo() + " con Id: " + item.getId());
		System.out.println("ItemsID antes de agregar un item: " + this.printItemsId());
		if(this.itemsId == null)
				this.itemsId = new ArrayList<Long>();
		this.itemsId.add(item.getId());		//Lo agrego en la lista de ids tambien para guardarlo en el DS
		ofy().save().entity(this).now();	//Lo guardo de nuevo asi se actualiza
		System.out.println("ItemsID despues de agregar un item: " + this.printItemsId());
	}
	
	public void quitarItem(Item item){
		int index = this.itemsId.indexOf(item.getId());
		this.itemsId.remove(index);
		ofy().save().entity(this).now();	//Lo guardo de nuevo asi se actualiza
		System.out.println("Despues de quitarItem, ItemsId: " + this.printItemsId());
	}

	public void truequearItem(Item miViejoItem, Item miNuevoItem) {
		this.agregarItem(miNuevoItem);
		this.quitarItem(miViejoItem);
	}
	
	/*----------For Friends------------*/
	
	public void agregarAmigo(Usuario amigo){
		if(this.amigosId == null)
			this.amigosId = new ArrayList<String>();
		if(!this.amigosId.contains(amigo.getId()))
			this.amigosId.add(amigo.getId());		//Lo agrego en la lista de ids tambien para guardarlo en el DS
		ofy().save().entity(this).now();	//Lo guardo de nuevo asi se actualiza
		
		if(amigo.amigosId == null)
			amigo.amigosId = new ArrayList<String>();
		if(!amigo.amigosId.contains(this.getId()))
			amigo.amigosId.add(this.getId());	//Ahora hago lo mismo con el amigo	
		ofy().save().entity(amigo).now();	
		
	}
	
	public void quitarAmigo(Usuario amigo){
		int index = this.amigosId.indexOf(amigo.getId());
		this.amigosId.remove(index);
		
		index = amigo.amigosId.indexOf(this.getId());
		amigo.amigosId.remove(index);
	}

	public String printItemsId() {
		if(this.itemsId != null)
			return this.itemsId.toString();
		else
			return "null";
	}
}