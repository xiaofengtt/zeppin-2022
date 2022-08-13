package com.cmos.china.mobile.media.core.bean;

import java.math.BigInteger;
import java.sql.Timestamp;

public class UploadTemp extends Entity {
	
	private static final long serialVersionUID = 632546955732035286L;
	private String id;
	private String name;
	private String path;
	private Integer complete;
	private Integer length;
	private BigInteger size;
	private String type;
	private String status;
	private String creator;
	private Timestamp createtime;
	
	public UploadTemp() {
		super();
	}
	
	public UploadTemp(String id, String name, String path, Integer complete, Integer length, BigInteger size, 
			String type, String status, String creator, Timestamp createtime) {
		super();
		this.id = id;
		this.name = name;
		this.path = path;
		this.complete = complete;
		this.length = length;
		this.size = size;
		this.type = type;
		this.status = status;
		this.creator = creator;
		this.createtime = createtime;
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
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public Integer getComplete() {
		return complete;
	}
	
	public void setComplete(Integer complete) {
		this.complete = complete;
	}

	public Integer getLength() {
		return length;
	}
	
	public void setLength(Integer length) {
		this.length = length;
	}
	
	public BigInteger getSize() {
		return size;
	}

	public void setSize(BigInteger size) {
		this.size = size;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}
