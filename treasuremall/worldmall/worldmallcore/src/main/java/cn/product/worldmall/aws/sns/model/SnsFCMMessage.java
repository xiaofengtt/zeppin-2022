package cn.product.worldmall.aws.sns.model;

import java.io.Serializable;

public class SnsFcmMessage implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6691637141649713406L;
	
	private String title;
	private String body;
	private String sound;
	private String click_action;
	
	public SnsFcmMessage(String titile, String body) {
		this(titile, body, "default", "FCM_OPEN_MAIN_ACTIVITY");
	}
	
	public SnsFcmMessage(String title, String body, String sound) {
		this.body = body;
		this.title = title;
		this.sound = sound;
	}
	
	public SnsFcmMessage(String title, String body, String sound, String click_action) {
		this.body = body;
		this.title = title;
		this.sound = sound;
		this.click_action = click_action;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getSound() {
		return sound;
	}
	public void setSound(String sound) {
		this.sound = sound;
	}

	public String getClick_action() {
		return click_action;
	}

	public void setClick_action(String click_action) {
		this.click_action = click_action;
	}

}
