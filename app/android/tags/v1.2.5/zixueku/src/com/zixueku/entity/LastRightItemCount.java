package com.zixueku.entity;

import java.io.Serializable;

/** 
 * 类说明 
 * @author  Email: huangweidong@zeppin.cn
 * @version V1.0  创建时间：2015年7月3日 下午2:06:40 
 */
public class LastRightItemCount implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1643754917908631145L;
	private int id;
	private int count;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "LastRightItemCount [id=" + id + ", count=" + count + "]";
	}
	
}
