package com.whaty.platform.standard.aicc.model;

public class CommentsLMS implements DataModel{

	/*GetParam: CMI Optional, AU Optional*/
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	 public String toStrData() {
	        StringBuffer sb = new StringBuffer();
	        sb.append("[Comments]").append("\r\n");
	        sb.append(getValue()).append("\r\n");
	        return sb.toString();
	    }
	
}
