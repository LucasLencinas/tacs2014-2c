package com.tacs.truequeLibre.Utils;

import com.google.gson.annotations.Expose;

public class TruequeRequest {
	@Expose public int idItemSolicitado;
	@Expose public int idItemOfrecido;
	@Expose public String idAmigo;
	
	
	public String toString(){
		return "Item Ofrecido: " + idItemOfrecido + ", Items Solicitado: "+ idItemSolicitado +", Amigo: "+ idAmigo;
	}
}
