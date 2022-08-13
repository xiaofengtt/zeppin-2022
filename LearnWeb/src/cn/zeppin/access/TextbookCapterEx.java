package cn.zeppin.access;

import java.util.LinkedList;
import java.util.List;

public class TextbookCapterEx {
	
	private int id;
	private String name;
	private String textbookName;
	private List<TextbookCapterEx> childs = new LinkedList<TextbookCapterEx>();
	
	public TextbookCapterEx(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTextbookName() {
		return textbookName;
	}

	public void setTextbookName(String textbookName) {
		this.textbookName = textbookName;
	}

	public List<TextbookCapterEx> getChilds() {
		return childs;
	}

	public void setChilds(List<TextbookCapterEx> childs) {
		this.childs = childs;
	}
	
	
	
}
