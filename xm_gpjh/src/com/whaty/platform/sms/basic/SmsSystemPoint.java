package com.whaty.platform.sms.basic;

import com.whaty.platform.Items;

public abstract class SmsSystemPoint implements Items {
	private String id;

	private String name;

	private String content;

	private String status;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
