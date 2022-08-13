package com.whaty.platform.entity.recruit;

import com.whaty.platform.Items;

public abstract class RecruitPhotoRecord implements Items {

	private String user_id;

	private String photo_link;

	private String photo_status;

	private String note;

	public String getPhoto_link() {
		return photo_link;
	}

	public void setPhoto_link(String photo_link) {
		this.photo_link = photo_link;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPhoto_status() {
		return photo_status;
	}

	public void setPhoto_status(String photo_status) {
		this.photo_status = photo_status;
	}

}
