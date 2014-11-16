package com.tacs.truequeLibre.Utils;

import com.google.gson.annotations.Expose;

//Armo una clase para que el Gson me transforme un JSON a esta clase
public class DeleteRequest {
	
	@Expose public int idItem;
	@Expose public int idUsuario;

}
