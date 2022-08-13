package com.whaty.platform.test.lore;

import java.util.List;

public abstract class LoreDir implements com.whaty.platform.Items {

	private String id;

	private String name;

	private String note;

	private String fatherDir;

	private String creatDate;

	private String groupId;

	public String getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(String creatDate) {
		this.creatDate = creatDate;
	}

	public String getFatherDir() {
		return fatherDir;
	}

	public void setFatherDir(String fatherDir) {
		this.fatherDir = fatherDir;
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

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public abstract List getLoreList();

	public abstract List getSubLoreDirList();
}