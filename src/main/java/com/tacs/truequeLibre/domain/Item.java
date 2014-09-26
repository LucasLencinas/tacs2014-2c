package com.tacs.truequeLibre.domain;

public class Item {
	
	public static int contador = 0;
	
	private int id;
	 
  private String title;

  private String description;

  private ObjetoML objML;
  
  public Item(String unTitulo, String unaDescripcion, ObjetoML unObjML) {
    this.setId(++contador);
    this.setTitulo(unTitulo);
    this.setDescripcion(unaDescripcion);
    this.setObjML(unObjML);
  }
  
  
  /**
   * Solo para poder saber si una lista continee
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
	public int getId() {
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
