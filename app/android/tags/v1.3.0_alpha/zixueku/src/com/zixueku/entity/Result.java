package com.zixueku.entity;

import java.io.Serializable;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-4-8 下午1:11:03
 */
public class Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 215576936938711725L;
	private String score;    
	private String inx;   //1,2,3,4...代表A，B，C,D.....如果为多选题则该字段为逗号分隔的字符串
	private String content;
	private String mce;

	public Result() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
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

	public void setContent(String content) {
		this.content = content;
	}

	public String getMce() {
		return mce;
	}

	public void setMce(String mce) {
		this.mce = mce;
	}

	@Override
	public String toString() {
		return "Result [score=" + score + ", inx=" + inx + ", content=" + content + ", mce=" + mce + "]";
	}
}
