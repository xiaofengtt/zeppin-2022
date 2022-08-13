package com.zixueku.entity;

import java.io.Serializable;

/**
 * 类说明 试题选项
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-16 上午10:46:39
 */
public class Option implements Serializable{
	private static final long serialVersionUID = -2743593966842617981L;
	private String inx;    //选项顺序1，2，3，4   (在判断题中1,正确2错误)
	private String content;  //选项内容
	private boolean selected;
	
	public Option(String inx, String content, boolean selected) {
		super();
		this.inx = inx;
		this.content = content;
		this.selected = selected;
	}

	public Option() {
		super();
		setSelected(false);
	}
	
	public String getInx() {
		return inx;
	}

	public void setInx(String inx) {
		this.inx = inx;
	}

	public String getContent() {
		return content;
	}

	public void setContext(String content) {
		this.content = content;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public String toString() {
		return "Option [inx=" + inx + ", content=" + content + ", selected=" + selected + "]";
	}

}
