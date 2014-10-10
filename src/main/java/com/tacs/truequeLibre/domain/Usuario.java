package com.tacs.truequeLibre.domain;

public class Usuario {

	
	private int id;

  private String nombre;

  private ListaDeItems items;
  
  private ListaDeTrueques trueques;
  
  //Falta ListaDeAmigos TODO
  
  public Usuario(String unNombre) {
  	this.setId(ListaDeItems.getNewID());
    this.setNombre(unNombre);
    this.setItems(new ListaDeItems());
    this.setTrueques(new ListaDeTrueques());
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String unNombre) {
		this.nombre= unNombre;
	}

	public ListaDeItems getItems() {
		return items;
	}

	public void setItems(ListaDeItems unosItems) {
		this.items= unosItems;
	}

	public ListaDeTrueques getTrueques() {
		return trueques;
	}
	
	public void setTrueques(ListaDeTrueques unosTrueques) {
		this.trueques= unosTrueques;
	}

	public void setObjML(ListaDeTrueques unosTrueques) {
		this.trueques = unosTrueques;
	}
	
  //Items
	public void agregarItem(Item item){
		//Analizar si un item debe conocer a su dueño tambien, no creo y me hace mucho ruido pero hablarlo con los chicos.
		this.items.add(item);
	}
	
	public void quitarItem(Item item){
		this.items.remove(item);
	}

	public void truequearItem(Item miViejoItem, Item miNuevoItem) {
		this.agregarItem(miNuevoItem);
		this.quitarItem(miViejoItem);
		// TODO Auto-generated method stub
		
	}


}