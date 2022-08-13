package com.whaty.platform.campus.user;

import com.whaty.platform.campus.base.CampusNews;
import com.whaty.platform.entity.user.EntityUser;

public abstract class Docent extends EntityUser implements
		com.whaty.platform.Items {

	private String id = null;

	private String name = null;

	private String detail = null;
	
	private String photo_link = null;

	private CampusNews campusNews = null;
	

	public CampusNews getCampusNews() {
		return campusNews;
	}

	public void setCampusNews(CampusNews campusNews) {
		this.campusNews = campusNews;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	 
	public String getPhoto_link() {
		return photo_link;
	}

	public void setPhoto_link(String photo_link) {
		this.photo_link = photo_link;
	}
	
	public abstract int isExist(String docentId);
	//***********************************************
	
}
