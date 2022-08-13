package com.zixueku.entity;

import java.io.Serializable;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-4-13 下午6:38:02
 */
public class CustomerAnswer implements Comparable<Object>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1259406292980680700L;
	private String inx;
	private String content;

	public CustomerAnswer(String inx) {
		super();
		this.inx = inx;
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

	@Override
	public String toString() {
		return "CustomerAnswer [inx=" + inx + ", content=" + content + "]";
	}

	@Override
	public int compareTo(Object another) {
		return Integer.parseInt(this.inx) - Integer.parseInt(((CustomerAnswer) another).getInx());
	}

	@Override
	public int hashCode() {
		return new Short(inx).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		CustomerAnswer answer = (CustomerAnswer) obj;
		if (this.inx.equals(answer.getInx())) {
			return true;
		}
		return false;
	}

}
