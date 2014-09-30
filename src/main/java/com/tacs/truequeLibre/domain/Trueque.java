package com.tacs.truequeLibre.domain;

import java.util.Date;

public class Trueque {
	
	private int id;
	
	//No se todavia si tendria que tener los dos usuarios, ya que con el 
	//id del item podrias buscar el usuario que lo tiene en la base de datos final.
  private String description;
  private Item item1;
  private Usuario usuario1;
  private Item item2;
  private Usuario usuario2;
  
  //Todavia no lo use, pero despues capaz que quiero hacer un filtro
  // u ordenar algun trueque por fecha
  private Date fecha;  
  
  public Trueque(Item unItem1, Item unItem2, Usuario unUsuario1, Usuario unUsuario2, String unaDescripcion) {
  	this.setId(ListaDeTrueques.getNewID());
    this.setItem1(unItem1);
    this.setUsuario1(unUsuario1);
    this.setItem2(unItem2);
    this.setUsuario2(unUsuario2);
    this.setDescripcion(unaDescripcion);
  }
  
  
  /**
   * Solo para poder saber si una lista contiene
   * un trueque, basandose en el id.
   * Despues habria que cambiarlo si se necesita comparar algo mas
   */
  @Override
  public boolean equals(Object otroTrueque){

    if (otroTrueque != null && otroTrueque instanceof Item)
        return (this.id == ((Trueque) otroTrueque).id);

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

	public Item getItem1() {
		return item1;
	}

	public void setItem1(Item unItem1) {
		this.item1 = unItem1;
	}
	
	public Item getItem2() {
		return item2;
	}

	public void setItem2(Item unItem2) {
		this.item2 = unItem2;
	}
	
	

	public Usuario getUsuario2() {
		return usuario2;
	}

	public void setUsuario2(Usuario unUsuario2) {
		this.usuario2 = unUsuario2;
	}


	public Usuario getUsuario1() {
		return usuario1;
	}

	public void setUsuario1(Usuario unUsuario1) {
		this.usuario1 = unUsuario1;
	}
	
	
	

	public String getDescripcion() {
		return description;
	}

	public void setDescripcion(String descripcion) {
		this.description = descripcion;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}




}
