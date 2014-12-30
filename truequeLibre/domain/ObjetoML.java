package com.tacs.truequeLibre.domain;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class ObjetoML implements Serializable {
 @SerializedName("permalink") private String ml_permalink;	 
 @SerializedName("id") private String ml_id;
 @SerializedName("thumbnail") private String ml_thumbnail;
 @SerializedName("source") private String ml_source;

  public ObjetoML(String unPermalink, String unId, String unThumbnail, String source) {
      this.setPermalink(unPermalink);
      this.setId(unId);
      this.setThumbnail(unThumbnail);
      this.setSource(source);
      
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


	public void setSource(String source) {
		this.ml_source = source;
	}

	public String getSource() {
		return ml_source;
	}

	public String getThumbnail() {
		return ml_thumbnail;
	}



	public void setThumbnail(String ml_thumbnail) {
		this.ml_thumbnail = ml_thumbnail;
	}


}
