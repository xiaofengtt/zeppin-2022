package com.whaty.platform.standard.aicc.model;

import java.util.Map;

public class StudentPreferences implements DataModel{

	public static final String WINDOW_PREFIX = "window.";
	
	/*GetParam (response): CMI Optional, AU Optional
	PutParam: CMI Optional, AU Optional*/
	private String audio;
	
	/*GetParam (response): CMI Optional, AU Optional*/
	private String language;
	
	/*GetParam (response): CMI Optional, AU Optional
	PutParam: CMI Optional, AU Optional*/
	private String lessonType;
	
	/*GetParam (response): CMI Optional, AU Optional
	PutParam: CMI Optional, AU Optional
	An integer value from ¨C100 to 100. Values are as follows:
	-1 to -100 : Slower speeds
	0 : Default ¨C Speed based on AU¡¯s internal defaults
	1 to 100 : Faster speeds
	*/
	private int speed;
	
	/*GetParam (response): CMI Optional, AU Optional
	PutParam: CMI Optional, AU Optional
	An integer with 3 possible values (¨C1, 0, and 1)*/
	private int text;
	
	/*GetParam (response): CMI Optional, AU Optional
	PutParam: CMI Optional, AU Optional*/
	private String textColor;
	
	/*GetParam (response): CMI Optional, AU Optional
	PutParam: CMI Optional, AU Optional*/
	private String textLocation;
	
	/*GetParam (response): CMI Optional, AU Optional
	PutParam: CMI Optional, AU Optional*/
	private String textSize;
	
	/*GetParam (response): CMI Optional, AU Optional
	PutParam: CMI Optional, AU Optional*/
	private String video;
	
	/*GetParam (response): CMI Optional, AU Optional
	PutParam: CMI Optional, AU Optional*/
	private Map windowMap;

	public String getAudio() {
		return audio;
	}

	public void setAudio(String audio) {
		this.audio = audio;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLessonType() {
		return lessonType;
	}

	public void setLessonType(String lessonType) {
		this.lessonType = lessonType;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getText() {
		return text;
	}

	public void setText(int text) {
		this.text = text;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public String getTextLocation() {
		return textLocation;
	}

	public void setTextLocation(String textLocation) {
		this.textLocation = textLocation;
	}

	public String getTextSize() {
		return textSize;
	}

	public void setTextSize(String textSize) {
		this.textSize = textSize;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public Map getWindowMap() {
		return windowMap;
	}

	public void setWindowMap(Map windowMap) {
		this.windowMap = windowMap;
	}

	public String toStrData() {
		//to implement
		return null;
	}
	
}
