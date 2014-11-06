package com.tacs.truequeLibre.Utils;

public enum TruequeStatusConstants {

	 PENDING(0, "Pendiente"), ACCEPTED(1, "Aceptado"), REJECTED(2, "Rechazado");

	 private TruequeStatusConstants(int id, String name){
		 this.id = id;
		 this.name = name;
	 }
	 
	 private String name;
	 private int id;
	 
	 public String getName(){
		 return this.name;
	 }
	 
	 public int getID(){
		 return this.id;
	 }

}
