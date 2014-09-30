package com.tacs.truequeLibre.domain;

public class Usuario {

	
	private int id;

  private String nombre;

  private ListaDeItems items;
  
  private ListaDeTrueques trueques;
  
  public Usuario(String unNombre) {
	this.setId(ListaDeItems.getNewID());
    this.setNombre(unNombre);
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

	public ListaDeTrueques getTrueques() {
		return trueques;
	}

	public void setObjML(ListaDeTrueques unosTrueques) {
		this.trueques = unosTrueques;
	}


}