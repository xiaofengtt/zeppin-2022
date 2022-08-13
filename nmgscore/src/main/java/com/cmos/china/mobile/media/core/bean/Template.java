package com.cmos.china.mobile.media.core.bean;

public class Template extends Entity {
	
	private static final long serialVersionUID = 5703096467321125198L;
	private String id;
	private String name;
	private String status;
	private String imgurl;
	

	public Template() {
		super();
	}

	public Template(String id, String name, String status,String imgurl) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
		this.imgurl = imgurl;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

}
