package com.cmos.china.mobile.media.core.bean;

import java.math.BigInteger;

public class OtherResource extends Entity {

	private static final long serialVersionUID = -3113096390569320312L;
	private String id;
	private String type;
	private String name;
	private String url;
	private BigInteger size;
	private String dpi;
	private String status;
	
	public OtherResource() {
		super();
	}

	public OtherResource(String id, String type, String name,
			String url, BigInteger size, String dpi,
			String status) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		this.url = url;
		this.size = size;
		this.dpi = dpi;
		this.status = status;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public BigInteger getSize() {
		return size;
	}

	public void setSize(BigInteger size) {
		this.size = size;
	}
	
	public String getDpi() {
		return dpi;
	}

	public void setDpi(String dpi) {
		this.dpi = dpi;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
