package cn.product.worldmall.aws.sns.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SnsApnsMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 733440262505255547L;

	private Map<String, String> alert;
	private String sound;
	
	public SnsApnsMessage() {
		
	}
	
	public SnsApnsMessage(String title, String message) {
		this(title, message, "default");
	}
	
	public SnsApnsMessage(String title, String message, String sound) {
		Map<String, String> alertMap = new HashMap<String, String>();
		alertMap.put("title", title);
		alertMap.put("body", message);
		this.alert = alertMap;
		this.sound = sound;
	}
	
	public Map<String, String> getAlert() {
		return alert;
	}

	public void setAlert(Map<String, String> alert) {
		this.alert = alert;
	}

	public String getSound() {
		return sound;
	}
	public void setSound(String sound) {
		this.sound = sound;
	}
	
	
}
