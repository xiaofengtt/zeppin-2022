/**
 * Project Name:Self_Cool  File Name:ActionResult.java Package
 * Name:cn.zeppin.action.base Copyright (c) 2014, Zeppin All Rights Reserved.
 * 
 */
package com.zixueku.entity;


/**
 * 前端页面返回的值封装成标准ActionResult进行返回 ClassName: ActionResult <br/>
 */
public class ActionResult<T> {

	private int pageNUm;// 当前页面数
	private int pageSize;// 每页条数
	private int pageCount;// 总页数
	private int totalCount;// 总条数
	private String Message;
	private String Status;

	private T Records;

	public ActionResult() {

	}

	public int getPageNUm() {
		return pageNUm;
	}

	public void setPageNUm(int pageNUm) {
		this.pageNUm = pageNUm;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public T getRecords() {
		return Records;
	}

	public void setRecords(T records) {
		Records = records;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}
	
}
