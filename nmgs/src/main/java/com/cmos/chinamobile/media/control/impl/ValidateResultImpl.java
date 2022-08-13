package com.cmos.chinamobile.media.control.impl;

import com.cmos.core.bean.xml.ValidateResult;

public class ValidateResultImpl extends ValidateResult {
	
	private String message;
	
	private String status;

	public ValidateResultImpl(String returnCode, String returnMessage) {
		super(returnCode, returnMessage);
		this.message = returnMessage;
		if(!"0".equals(returnCode)){
			this.status = "fail";
		}else{
			this.status = "success";
		}
		
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	@Override
	public String toJson() {
		return "{\"status\":\"" + this.status + "\",\"message\":\"" + this.message + "\"}";
	}

	
}
