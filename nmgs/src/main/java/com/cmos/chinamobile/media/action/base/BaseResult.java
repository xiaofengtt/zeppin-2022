package com.cmos.chinamobile.media.action.base;

import com.cmos.core.bean.OutputObject;


/**
 * Result : 响应的结果对象
 *
 * @author Clark_Rong
 * @since 2016-03-23 11:17
 */
public class BaseResult extends OutputObject implements Result {
	
    private static final long serialVersionUID = 6288374846131788743L;
    
    /**
     * 信息
     */
    private String message;

    /**
     * 状态码
     */
    private String status;
    
    /**
     * 当前页码
     */
    private int pageNum;
    
    /**
     * 页面容量
     */
    private int pageSize;
    
    /**
     * 页面总数
     */
    private int totalPageCount;
    
    /**
     * 总结果数
     */
    private int totalResultCount;
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public int getTotalResultCount() {
		return totalResultCount;
	}

	public void setTotalResultCount(int totalResultCount) {
		this.totalResultCount = totalResultCount;
	}

	public BaseResult() {

    }
}
