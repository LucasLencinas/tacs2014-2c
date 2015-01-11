package com.tacs.truequeLibre.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Estadistica implements Serializable{

	private int cantidadUsuarios;
	private ArrayList<UsuarioXItems> listaDeUsuarios;
	private ListaDeTrueques listaDeTrueques;

	public Estadistica(int cantidadUsuarios, ArrayList<UsuarioXItems> listaDeUsuarios, ListaDeTrueques listaDeTrueques)
	{
		this.setCantidadUsuarios(cantidadUsuarios);
		this.setListaDeUsuarios(listaDeUsuarios);
		this.setListaDeTrueques(listaDeTrueques);
	}

	public int getCantidadUsuarios()
	{
		return cantidadUsuarios;
	}

	public ArrayList<UsuarioXItems> getListaDeUsuarios()
	{
		return listaDeUsuarios;
	}

	public ListaDeTrueques getListaDeTrueques()
	{
		return listaDeTrueques;
	}

	public void setListaDeUsuarios(ArrayList<UsuarioXItems> listaDeUsuarios)
	{
		this.listaDeUsuarios= listaDeUsuarios;
	}

	public void setListaDeTrueques(ListaDeTrueques listaDeTrueques){
		this.listaDeTrueques=listaDeTrueques;
	}

	public void setCantidadUsuarios(int cantidadUsuarios)
	{
		this.cantidadUsuarios= cantidadUsuarios;
	}

  @Override
  public String toString(){
  	String estadisticaString = Integer.toString(this.cantidadUsuarios);
  	String stringTrueques = this.listaDeTrueques.toString();
  	
  	String stringUsuariosId = "[";
  	if(this.listaDeUsuarios != null)
	  	for (UsuarioXItems usuario : this.listaDeUsuarios) {
				stringUsuariosId += usuario.toString() + ", ";
			}
  	
  	stringUsuariosId += "]";
  	stringUsuariosId = stringUsuariosId.replace(", ]", "]");
  	
  	return estadisticaString+ ","+stringUsuariosId+ ","+stringTrueques;
  	
  }
}