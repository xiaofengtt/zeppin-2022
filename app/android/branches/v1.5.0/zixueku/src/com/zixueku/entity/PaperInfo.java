package com.zixueku.entity;

import java.io.Serializable;

public class PaperInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1956718477157914679L;
	/*
	 * "aveScore":0, "id":72, "isFree":true, "name":"2011年下半年幼儿综合素质真题",
	 * "price":0, "testCount":0, "totalScore":150, "type":0, "useable":1,
	 * "year":"2011"
	 */
	public int aveScore;
	public int id;
	public boolean isFree;
	public String name;
	public double price;
	public int testCount;
	public short type;
	public boolean useable;
	public String year;

	@Override
	public String toString() {
		return "PaperInfo [aveScore=" + aveScore + ", id=" + id + ", isFree=" + isFree + ", name=" + name + ", price=" + price + ", testCount=" + testCount + ", type=" + type + ", useable=" + useable
				+ ", year=" + year + "]";
	}

}
