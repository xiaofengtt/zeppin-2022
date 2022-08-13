/**
 * Project Name:CETV_TEST File Name:ActionResult.java Package
 * Name:cn.zeppin.action.base Copyright (c) 2014, Zeppin All Rights Reserved.
 * 
 */
package cn.zeppin.action.base;

import java.util.HashMap;

/**
 * 前端页面返回的值封装成标准ActionResult进行返回 ClassName: ActionResult <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月20日 下午3:14:09 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class ActionResult extends HashMap<String, Object>
{
	/**
	 *  
	 */
	private static final long serialVersionUID = 327072770122988704L;
	/**
	 * Key的枚举
	 */
	private final static String STATUS = "Status";
	private final static String MESSAGE = "Message";
	private final static String DATA = "Records";
	private final static String OPERATION = "Operation";
	private final static String REDIRECT = "URL";
	private final static String PAGE_NUM = "PageNum";
	private final static String PAGE_SIZE = "PageSize";
	private final static String PAGE_COUNT = "PageCount";
	private final static String TOTAL_COUNT = "TotalRecordCount";
	@SuppressWarnings("unused")
	private int pageNUm;// 当前页面数
	@SuppressWarnings("unused")
	private int pageSize;// 每页条数
	@SuppressWarnings("unused")
	private int pageCount;// 总页数
	@SuppressWarnings("unused")
	private int totalCount;// 总条数
	
	public ActionResult()
	{
		
	}
	
	/**
	 * 通过参数创建ActionResult
	 * 
	 * @author Clark
	 * @date: 2014年6月20日 下午3:35:13 <br/>
	 */
	public ActionResult(String status, String message, Object data)
	{
		super();
		this.setData(data);
		this.setMessage(message);
		this.setStatus(status);
	}
	
	public void setStatus(String status)
	{
		this.put(STATUS, status);
	}
	
	public void setMessage(String message)
	{
		this.put(MESSAGE, message);
	}
	
	public String getMessage()
	{
		return (String) this.get(MESSAGE);
	}
	
	public void setOperation(String operation)
	{
		// TODO Auto-generated method stub
		this.put(OPERATION, operation);
	}
	
	public void setData(Object data)
	{
		this.put(DATA, data);
	}
	
	public void setRedirect(String url)
	{
		this.put(REDIRECT, url);
	}
	
	public void init(String status, String message, Object data)
	{
		this.setData(data);
		this.setMessage(message);
		this.setStatus(status);
	}
	
	public void init(String status, String message, Object data, String url)
	{
		this.setData(data);
		this.setMessage(message);
		this.setStatus(status);
		this.setRedirect(url);
	}
	
	/**
	 * @param pageNUm
	 *            the pageNUm to set
	 */
	public void setPageNum(int pageNum)
	{
		this.put(PAGE_NUM, pageNum);
	}
	
	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize)
	{
		this.put(PAGE_SIZE, pageSize);
	}
	
	/**
	 * @param pageCount
	 *            the pageCount to set
	 */
	public void setPageCount(int pageCount)
	{
		this.put(PAGE_COUNT, pageCount);
	}
	
	/**
	 * @param totalCount
	 *            the totalCount to set
	 */
	public void setTotalCount(int totalCount)
	{
		this.put(TOTAL_COUNT, totalCount);
	}
	
}
