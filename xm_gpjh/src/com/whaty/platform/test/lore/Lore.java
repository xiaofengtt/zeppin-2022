package com.whaty.platform.test.lore;

public abstract class Lore implements com.whaty.platform.Items {

	private String id;

	private String name;

	private String content;

	private String loreDir;

	private String createrId;

	private String creatDate;

	private boolean active;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(String creatDate) {
		this.creatDate = creatDate;
	}

	public String getCreaterId() {
		return createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoreDir() {
		return loreDir;
	}

	public void setLoreDir(String loreDir) {
		this.loreDir = loreDir;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}