package tacs.grupo4;

public class ObjetoML {
	private String ml_permalink;
	 
  private String ml_id;

  public ObjetoML(String unPermalink, String unId) {
      this.setPermalink(unPermalink);
      this.setId(unId);
  }

  
  
  /**
   * Getters and Setters
   * 
   */
	public String getPermalink() {
		return ml_permalink;
	}

	public void setPermalink(String permalink) {
		this.ml_permalink = permalink;
	}

	public String getId() {
		return ml_id;
	}

	public void setId(String id) {
		this.ml_id = id;
	}


}
