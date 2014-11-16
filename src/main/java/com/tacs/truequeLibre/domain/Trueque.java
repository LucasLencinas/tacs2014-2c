package com.tacs.truequeLibre.domain;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.tacs.truequeLibre.Utils.HandlerDS;
import com.tacs.truequeLibre.Utils.TruequeStatusConstants;
@Entity
public class Trueque {
	
	@Id private long id;
	
  private String description;
  @Index private Item itemOfrecido;
  @Index private Usuario usuarioSolicitante;
  @Index private Item itemSolicitado;
  @Index private Usuario usuarioSolicitado;
  private Date fecha;  
  private int estado;
  
  public Trueque(Item itemOfrecido, Item itemSolicitado, Usuario usuarioSolicitante, Usuario usuarioSolicitado, String unaDescripcion) {
  	this.setId(ListaDeTrueques.getNewID());
    this.setItemOfrecido(itemOfrecido);
    this.setUsuarioSolicitante(usuarioSolicitante);
    this.setItemSolicitado(itemSolicitado);
    this.setUsuarioSolicitado(usuarioSolicitado);
    this.setDescripcion(unaDescripcion);
    this.setEstado(TruequeStatusConstants.PENDING.getID());
  }
  
  public Trueque() {
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
   * Getters and Setters
   */
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Item getItemOfrecido() {
		return itemOfrecido;
	}

	public void setItemOfrecido(Item unItem1) {
		this.itemOfrecido = unItem1;
	}
	
	public Item getItemSolicitado() {
		return itemSolicitado;
	}

	public void setItemSolicitado(Item unItem2) {
		this.itemSolicitado = unItem2;
	}
	
	public Usuario getUsuarioSolicitado() {
		return usuarioSolicitado;
	}

	public void setUsuarioSolicitado(Usuario unUsuario2) {
		this.usuarioSolicitado = unUsuario2;
	}

	public Usuario getUsuarioSolicitante() {
		return usuarioSolicitante;
	}

	public void setUsuarioSolicitante(Usuario unUsuario1) {
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

	public void aceptarTrueque() throws Exception {
	
		this.estado = TruequeStatusConstants.ACCEPTED.getID();
		this.usuarioSolicitado.truequearItem(this.itemSolicitado, this.itemOfrecido);
		this.usuarioSolicitante.truequearItem(this.itemOfrecido, this.itemSolicitado);
		HandlerDS.guardarTrueque(this);
	}
	
	public void rechazarTrueque() {
		this.estado = TruequeStatusConstants.REJECTED.getID();
		HandlerDS.guardarTrueque(this);
	}
	







}
