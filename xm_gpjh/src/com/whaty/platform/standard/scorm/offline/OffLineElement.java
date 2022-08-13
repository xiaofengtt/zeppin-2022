package com.whaty.platform.standard.scorm.offline;

import java.io.Serializable;

public class OffLineElement implements Serializable{
	
	private String id;
	private String type;
	private Object value;
	private String io;
	
	private String toJSONString;
	private String parseJSON;
	
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
	
	public String getIo() {
		return io;
	}
	public void setIo(String io) {
		this.io = io;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getParseJSON() {
		return parseJSON;
	}
	public void setParseJSON(String parseJSON) {
		this.parseJSON = parseJSON;
	}
	public String getToJSONString() {
		return toJSONString;
	}
	public void setToJSONString(String toJSONString) {
		this.toJSONString = toJSONString;
	}
}
