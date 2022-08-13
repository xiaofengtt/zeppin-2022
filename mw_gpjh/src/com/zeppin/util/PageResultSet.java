package com.zeppin.util;

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
	
	/**
	 * @category 判断是否全是数字
	 * @param s
	 *            要判断的字符串
	 * @return true|false
	 */
	public final static boolean isNumeric(String s) {
		if (s != null && !"".equals(s.trim()))
			return s.matches("^[0-9]*$");
		else
			return false;
	}
}

