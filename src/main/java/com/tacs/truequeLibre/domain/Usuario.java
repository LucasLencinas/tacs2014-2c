package com.tacs.truequeLibre.domain;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.tacs.truequeLibre.Main;

@SuppressWarnings("serial")
public class Usuario implements Serializable{

	
	@Expose private int id;

	@Expose private String nombre;

	@Expose private ListaDeItems items;
  
	@Expose private ListaDeTrueques trueques;
	
	@Expose private ListaDeUsuarios amigos;
  
  //Falta ListaDeAmigos TODO
  
  public Usuario(String unNombre) {
  	this.setId(ListaDeUsuarios.getNewID());
    this.setNombre(unNombre);
    this.setItems(new ListaDeItems());
    this.setTrueques(new ListaDeTrueques());
    this.setAmigos(new ListaDeUsuarios());
  }
 
  /**
   * 
   * Getters and Setters
   */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String unNombre) {
		this.nombre= unNombre;
	}

	public ListaDeItems getItems() {
		return items;
	}

	public void setItems(ListaDeItems unosItems) {
		this.items= unosItems;
	}

	public ListaDeUsuarios getAmigos() {
		return amigos;
	}

	public void setAmigos(ListaDeUsuarios unosAmigos) {
		this.amigos= unosAmigos;
	}
	
	
	public ListaDeTrueques getTrueques() {
		return trueques;
	}
	
	public void setTrueques(ListaDeTrueques unosTrueques) {
		this.trueques= unosTrueques;
	}

	public void setObjML(ListaDeTrueques unosTrueques) {
		this.trueques = unosTrueques;
	}
	
  //Items
	public void agregarItem(Item item){
		//Analizar si un item debe conocer a su dueño tambien, no creo y me hace mucho ruido pero hablarlo con los chicos.
		this.items.add(item);
	}
	
	public void quitarItem(Item item){
		this.items.remove(item);
	}

	public void truequearItem(Item miViejoItem, Item miNuevoItem) {
		this.agregarItem(miNuevoItem);
		this.quitarItem(miViejoItem);
		// TODO Auto-generated method stub
		
	}

}