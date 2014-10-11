package com.tacs.truequeLibre.domain;

public class ObjetoML {
	private String ml_permalink;
	 
  private String ml_id;
  
  private String ml_thumbnail;

  public ObjetoML(String unPermalink, String unId, String unThumbnail) {
      this.setPermalink(unPermalink);
      this.setId(unId);
      this.setThumbnail(unThumbnail);
      
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



	public String getThumbnail() {
		return ml_thumbnail;
	}



	public void setThumbnail(String ml_thumbnail) {
		this.ml_thumbnail = ml_thumbnail;
	}


}
