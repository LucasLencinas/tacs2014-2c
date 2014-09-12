package tacs.grupo4;

public class Item {
	private int id;
	 
  private String titulo;

  private String descripcion;

  private ObjetoML objML;
  
  public Item(int unId, String unTitulo, String unaDescripcion, ObjetoML unObjML) {
      this.setId(unId);
      this.setTitulo(unTitulo);
      this.setDescripcion(unaDescripcion);
      this.setObjML(unObjML);
      
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public ObjetoML getObjML() {
		return objML;
	}

	public void setObjML(ObjetoML objML) {
		this.objML = objML;
	}


}
