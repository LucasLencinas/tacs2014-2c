package com.tacs.truequeLibre.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class UsuarioXItems implements Serializable{

	private String nombreUsuario;
	private ListaDeItems listaDeItems;

	public UsuarioXItems(Usuario usuario)
	{
		this.setNombreUsuario(usuario.getNombre());
		this.setListaDeItems(usuario.getItems());
	}

	public String getNombreUsuario()
	{
		return nombreUsuario;
	}

	public ListaDeItems getListaDeItems()
	{
		return listaDeItems;
	}

	public void setListaDeItems(ListaDeItems listaDeItems)
	{
		this.listaDeItems= listaDeItems;
	}

	public void setNombreUsuario(String nombreUsuario)
	{
		this.nombreUsuario= nombreUsuario;
	}

  @Override
  public String toString(){
  	String nombreUsuarioString = nombreUsuario;
  	String listaItemsString = this.listaDeItems.toString();
  	return nombreUsuarioString+ ","+listaItemsString;
  	
  }
}