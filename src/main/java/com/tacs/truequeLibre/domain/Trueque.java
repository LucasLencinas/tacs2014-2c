package com.tacs.truequeLibre.domain;

import java.util.Date;

import com.tacs.truequeLibre.Main;
import com.tacs.truequeLibre.Utils.TruequeStatusConstants;

public class Trueque {
	
	private int id;
	
	//No se todavia si tendria que tener los dos usuarios, ya que con el 
	//id del item podrias buscar el usuario que lo tiene en la base de datos final.
	
	//Puede que un item conozca a su propietario/usuario, entonces no haria falta poner aca el usuario que lo tiene.
  private String description;
  private Item itemOfrecido;
  private Usuario usuarioSolicitante;
  private Item itemSolicitado;
  private Usuario usuarioSolicitado;
  
  //Todavia no lo use, pero despues capaz que quiero hacer un filtro
  // u ordenar algun trueque por fecha
  private Date fecha;  
  //definidos en TruequeStatusConstants
  private int estado;
  
  public Trueque(Item unItem1, Item unItem2, Usuario unUsuario1, Usuario unUsuario2, String unaDescripcion) {
  	this.setId(ListaDeTrueques.getNewID());
    this.setItem1(unItem1);
    this.setUsuario1(unUsuario1);
    this.setItem2(unItem2);
    this.setUsuario2(unUsuario2);
    this.setDescripcion(unaDescripcion);
    this.setEstado(TruequeStatusConstants.PENDING.getID());
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
		return itemOfrecido;
	}

	public void setItem1(Item unItem1) {
		this.itemOfrecido = unItem1;
	}
	
	public Item getItem2() {
		return itemSolicitado;
	}

	public void setItem2(Item unItem2) {
		this.itemSolicitado = unItem2;
	}
	
	

	public Usuario getUsuario2() {
		return usuarioSolicitado;
	}

	public void setUsuario2(Usuario unUsuario2) {
		this.usuarioSolicitado = unUsuario2;
	}


	public Usuario getUsuario1() {
		return usuarioSolicitante;
	}

	public void setUsuario1(Usuario unUsuario1) {
		this.usuarioSolicitante = unUsuario1;
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
	public void setEstado(int estadoID){
		this.estado = estadoID;
	}
	
	public int getEstado(){
		return this.estado;
	}

	//Aceptar y rechazar trueques
	public void aceptarTrueque() throws Exception {
		if(/*this.usuarioSolicitado == Main.miUsuario*/true){
			this.estado = TruequeStatusConstants.ACCEPTED.getID();
			this.usuarioSolicitado.truequearItem(this.itemSolicitado, this.itemOfrecido);
			this.usuarioSolicitante.truequearItem(this.itemOfrecido, this.itemSolicitado);
			//TODO: Hacer que notifique la aceptacion/rechazo
		} else {
			throw new Exception("No me corresponde aceptar dicha solicitud porque no me lo solicitaron a mí");
		}
	}
	
	public void rechazarTrueque() {
		this.estado = TruequeStatusConstants.REJECTED.getID();
	}
	
	//BD
	public static Trueque getById(int truequeID){
		return Main.trueques.findById(truequeID);
	}
	






}
