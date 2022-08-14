package com.makati.common.entity;

public class PageEntity extends Entity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2277914369034077172L;
	
	private int page_no;    //第几页
	private int page_size;  //每页显示几条数据
	private int start;      //起始
	private int end;        //结束
	private String reserved;  //保留字段，留做特殊处理
	private String reserved2; //保留字段2
	private String reserved3; //


	private String startTime; //
	private String endTime; //

	
	
	
	public PageEntity(int page_no, int page_size) {
		this.page_no = page_no>1?page_no:1;
		this.page_size = page_size>0?page_size:10;
		this.start=(this.page_no-1)*this.page_size;
		this.end=page_size;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getPage_no() {
		return page_no;
	}
	public void setPage_no(int page_no) {
		this.page_no = page_no;
	}
	public int getPage_size() {
		return page_size;
	}
	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}
	public String getReserved() {
		return reserved;
	}
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}


	public String getReserved2() {
		return reserved2;
	}
	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}


	public String getReserved3() {
		return reserved3;
	}


	public void setReserved3(String reserved3) {
		this.reserved3 = reserved3;
	}
	
	
}
