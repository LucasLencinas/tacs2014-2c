package com.tacs.truequeLibre.Utils;

import com.google.gson.annotations.Expose;

//Armo una clase para que el Gson me transforme un JSON a esta clase, no se si 
//se puede hacer sin la necesidad de crear una clase. Capaz que se puede usar
//un enum o algo asi. Preguntar. FIXME
public class DeleteRequest {
	
	@Expose public int idItem;
	@Expose public int idUsuario;

}
