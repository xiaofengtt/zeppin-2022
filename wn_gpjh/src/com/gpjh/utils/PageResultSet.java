package com.gpjh.utils;

import java.util.List;

public class PageResultSet<T> {

	private List<T> list;
	private PageInfo pageInfo;
	
	public List<T> getList(){
		return list;
	}
	
	public void setList(List<T> list){
		this.list = list;
	}
	
	public PageInfo getPageInof(){
		return pageInfo;
	}
	
	public void setPageInfo(PageInfo pageInfo){
		this.pageInfo = pageInfo;
	}
}

