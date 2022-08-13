package com.whaty.platform.courseware.basic;

import java.io.File;
import java.io.IOException;

import com.whaty.platform.Items;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.util.Exception.FileManageException;
import com.whaty.util.file.FileManage;
import com.whaty.util.file.FileManageFactory;

public abstract class WhatyCoursewareTemplate implements Items{
	
	private String id;
	
	private String name;
	
	private String founderId;
	
	private String founderDate;
	
	private String imageFileName;
	
	private boolean active;
	
	private String coursewareTemplateAbsPath;
	
	private String imageRefPath;  //œ‡∂‘”⁄coursewareTemplateAbsPath
	
	private String note;
	
	
	
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public String getFounderDate() {
		return founderDate;
	}

	public void setFounderDate(String founderDate) {
		this.founderDate = founderDate;
	}

	public String getFounderId() {
		return founderId;
	}

	public void setFounderId(String founderId) {
		this.founderId = founderId;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	
	public String getCoursewareTemplateAbsPath() {
		return coursewareTemplateAbsPath;
	}

	public void setCoursewareTemplateAbsPath(String coursewareTemplateAbsPath) {
		this.coursewareTemplateAbsPath = coursewareTemplateAbsPath;
	}

	public String getImageRefPath() {
		return imageRefPath;
	}

	public void setImageRefPath(String imageRefPath) {
		this.imageRefPath = imageRefPath;
	}

	public void deleteTemplateFiles() throws PlatformException
	{
		FileManage fileManage=FileManageFactory.creat();
		try {
			fileManage.delete(this.getCoursewareTemplateAbsPath()+this.getId());
		} catch (FileManageException e) {
			throw new PlatformException("deleteTemplateFiles error!");
		}
	}
	
	public void deleteTemplateImageFiles() throws PlatformException
	{
		FileManage fileManage=FileManageFactory.creat();
		try {
			fileManage.delete(this.getCoursewareTemplateAbsPath()+this.getId()+File.separator+this.getImageRefPath()+this.getImageFileName());
		} catch (FileManageException e) {
			throw new PlatformException("deleteTemplateImageFiles error!");
		}
	}
	
	public void addTemplateFiles(String zipFileAbsPath) throws PlatformException
	{
		FileManage fileManage=FileManageFactory.creat();
		String targetDir=this.getCoursewareTemplateAbsPath()+this.getId();
		try {
			fileManage.unZip(zipFileAbsPath,targetDir);
			} catch (IOException e) {
			throw new PlatformException("addTemplateFiles unzip error!");
		} catch (FileManageException e) {
			throw new PlatformException("addTemplateFiles unzip error!");
		}
		try {
			fileManage.delete(zipFileAbsPath);
		} catch (FileManageException e) {
			throw new PlatformException("addTemplateFiles delete zipfile error!");
		}
		
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}
