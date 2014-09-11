package tacs.grupo4;

public class ObjetoML {
	private String permalink;
	 
  private String id;

  public ObjetoML(String unPermalink, String unId) {
      this.setPermalink(unPermalink);
      this.setId(unId);
  }

  
  
  /**
   * Getters and Setters
   * 
   */
	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


}
