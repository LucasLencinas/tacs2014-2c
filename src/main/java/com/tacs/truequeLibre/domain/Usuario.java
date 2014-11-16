package com.tacs.truequeLibre.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Persistent;

import com.google.gson.annotations.Expose;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.tacs.truequeLibre.setup.Setup;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Entity
@SuppressWarnings("serial")
public class Usuario implements Serializable{

	
	@Expose
	@Id
	private String id;
	
	@Expose private String nombre;
	@Expose private ListaDeItems items;
	
	@Ignore
	@Expose private ListaDeUsuarios amigos;
  
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
		for (long itemId : this.itemsId) {
			items.add(ofy().load().type(Item.class).id(itemId).now());
		}
		return items;
	}

	public void setItems(ListaDeItems unosItems) {
		this.items= unosItems;
	}

	//Cambiarlo FIXME
	public ListaDeUsuarios getAmigos() {
		return amigos;
	}

	public void setAmigos(ListaDeUsuarios unosAmigos) {
		this.amigos= unosAmigos;
	}

	
  //Items
	public void agregarItem(Item item){
		
		this.itemsId.add(item.getId());		//Lo agrego en la lista de ids tambien para guardarlo en el DS
		ofy().save().entity(this).now();	//Lo guardo de nuevo asi se actualiza
		//this.items.add(item);
		
	}
	
	public void quitarItem(Item item){
		int index = this.itemsId.indexOf(item.getId());
		this.itemsId.remove(index);
	}

	public void truequearItem(Item miViejoItem, Item miNuevoItem) {
		this.agregarItem(miNuevoItem);
		this.quitarItem(miViejoItem);
		
	}

}