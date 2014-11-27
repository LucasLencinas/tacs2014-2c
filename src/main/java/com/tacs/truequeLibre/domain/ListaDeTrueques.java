package com.tacs.truequeLibre.domain;

import java.util.ArrayList;

import com.tacs.truequeLibre.setup.Setup;

@SuppressWarnings("serial")
public class ListaDeTrueques extends ArrayList<Trueque>{

	public static int contador = 0;
	
	public Trueque findById(int unId){
		for (Trueque unTrueque : this) {
			if(unTrueque.getId() == unId)
				return unTrueque;
		}
			return null;
	}
	
	public void clear(){
		super.clear();
		ListaDeTrueques.contador = 0;
	}

	public static int getNewID() {
		return ++contador;
	}

	public String toString(){
		String stringTrueques = "[";
		String stringSolicitante;
		String stringSolicitado;
		for (Trueque trueque : this) {
			stringSolicitante =  trueque.getUsuarioSolicitante().getNombre() +"-"+ trueque.getItemOfrecido().getTitulo();
			stringSolicitado = trueque.getUsuarioSolicitado().getNombre() +"-"+ trueque.getItemSolicitado().getTitulo();
			stringTrueques += trueque.getId() + "-" + trueque.getEstado() + ":" + stringSolicitante +"-->" +stringSolicitado + ", "  ;
		}
  	stringTrueques += "]";
  	stringTrueques = stringTrueques.replace(", ]", "]");
		return stringTrueques;
				
	}
	

}