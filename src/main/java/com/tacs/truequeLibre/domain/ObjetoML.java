package com.tacs.truequeLibre.domain;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class ObjetoML implements Serializable {
 @SerializedName("permalink") private String ml_permalink;
	 
 @SerializedName("id") private String ml_id;

  public ObjetoML(String unPermalink, String unId) {
      this.setPermalink(unPermalink);
      this.setId(unId);
  }
  
  public ObjetoML(){
	  
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
