package com.whaty.platform.courseware;

public abstract class FileManagerPriv {
	
	public int creatDir=0;
	
	public int uploadFile=0;
	
	public int deleteFile=0;
	
	public int deleteDir=0;
	
	public int unzipFile=0;
	
	public int zipFile=0;
	
	public int listDir=0;
	
	public int editFile=0;
	
	private String rootDir;
	
	

	public FileManagerPriv() {
		
	}



	public String getRootDir() {
		return rootDir;
	}



	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}
	
	

}
