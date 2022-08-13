package com.whaty.platform.entity.basic;

public abstract class SiteDiqu  implements com.whaty.platform.Items{

	private String id;
	
	private String name;
	
	private String rightCode;

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

	public String getRightCode() {
		return rightCode;
	}

	public void setRightCode(String rightCode) {
		this.rightCode = rightCode;
	}
}
