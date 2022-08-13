package com.cmos.china.mobile.media.core.bean;

import java.math.BigInteger;

public class Resource extends Entity {

	private static final long serialVersionUID = -4229089826896417390L;
	private String id;
	private String type;
	private String path;
	private String filename;
	private String url;
	private String filetype;
	private BigInteger size;
	private String dpi;
	private String status;
	
	public Resource() {
		super();
	}

	public Resource(String id, String type, String path, String filename,
			String url, String filetype, BigInteger size, String dpi,
			String status) {
		super();
		this.id = id;
		this.type = type;
		this.path = path;
		this.filename = filename;
		this.url = url;
		this.filetype = filetype;
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
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
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
