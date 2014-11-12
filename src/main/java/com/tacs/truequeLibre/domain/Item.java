package com.tacs.truequeLibre.domain;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Item implements Serializable{
	
	@Id
  @Expose private long id;
	 
  @Expose 
  private String title;
  //Sirve para describir por que tipo de item se quiere truequear
  @Expose 
  private String description;
  
  @SerializedName("ml") 
  private ObjetoML objML;
  
  public Item(){
	  
  }
  public Item(String unTitulo, String unaDescripcion, ObjetoML unObjML) {
  	this.setId(ListaDeItems.getNewID());
    this.setTitulo(unTitulo);
    this.setDescripcion(unaDescripcion);
    this.setObjML(unObjML);
  }
  
  
  /**
   * Solo para poder saber si una lista contiene
   * un item, basandose en el id.
   * Despues habria que cambiarlo si se necesita comparar algo mas
   */
  @Override
  public boolean equals(Object otroItem){

    if (otroItem != null && otroItem instanceof Item)
        return (this.id == ((Item) otroItem).id);

    return false;
  }
  
  /**
   * 
   * Getters and Setters
   */
	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return title;
	}

	public void setTitulo(String titulo) {
		this.title = titulo;
	}

	public String getDescripcion() {
		return description;
	}

	public void setDescripcion(String descripcion) {
		this.description = descripcion;
	}

	public ObjetoML getObjML() {
		return objML;
	}

	public void setObjML(ObjetoML objML) {
		this.objML = objML;
	}


}
