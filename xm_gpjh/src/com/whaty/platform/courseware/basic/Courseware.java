package com.whaty.platform.courseware.basic;



import com.whaty.platform.Items;
import com.whaty.platform.entity.basic.Dep;

public abstract class Courseware implements Items{
	
	private String id;
	
	private String name;
	
	private boolean active;
	
	private String coursewareType; 
	
	private String foundDate;
	
	private String founderId;

	private String note;
	
	private String author;
	
	private String publisher;
	
	private String coursewareDirId;
	
	private Dep dep;
	
	public String getCoursewareDirId() {
		return coursewareDirId;
	}

	public void setCoursewareDirId(String coursewareDirId) {
		this.coursewareDirId = coursewareDirId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getCoursewareType() {
		return coursewareType;
	}

	public void setCoursewareType(String coursewareType) {
		this.coursewareType = coursewareType;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	

	public String getFoundDate() {
		return foundDate;
	}

	public void setFoundDate(String foundDate) {
		this.foundDate = foundDate;
	}

	public String getFounderId() {
		return founderId;
	}

	public void setFounderId(String founderId) {
		this.founderId = founderId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Dep getDep() {
		return dep;
	}

	public void setDep(Dep dep) {
		this.dep = dep;
	}

	//public abstract String getEnterURL();
}
