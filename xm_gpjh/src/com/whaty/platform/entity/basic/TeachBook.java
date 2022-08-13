package com.whaty.platform.entity.basic;

import com.whaty.platform.Items;

public abstract class TeachBook implements Items {
	private String id;

	private String teachbook_name;

	private String publishhouse;

	private String maineditor;

	private String isbn;

	private String publish_date;

	private String price;

	private String note;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getMaineditor() {
		return maineditor;
	}

	public void setMaineditor(String maineditor) {
		this.maineditor = maineditor;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPublish_date() {
		return publish_date;
	}

	public void setPublish_date(String publish_date) {
		this.publish_date = publish_date;
	}

	public String getPublishhouse() {
		return publishhouse;
	}

	public void setPublishhouse(String publishhouse) {
		this.publishhouse = publishhouse;
	}

	public String getTeachbook_name() {
		return teachbook_name;
	}

	public void setTeachbook_name(String teachbook_name) {
		this.teachbook_name = teachbook_name;
	}

}
