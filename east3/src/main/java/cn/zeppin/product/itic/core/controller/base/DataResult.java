package cn.zeppin.product.itic.core.controller.base;


/**
 * JSONResult : Response JSONResult for RESTful,封装返回JSON格式的数据
 *
 * @author Clark_Rong
 * @since 2016-03-23 11:17
 */

public class DataResult<T> extends BaseResult {

    private static final long serialVersionUID = 7880907731807860636L;
    

    /**
     * 数据
     */
    private T data;
    
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
    

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
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
    
    /**
     * 创建返回信息
     */
    public DataResult() {
    	setStatus(ResultStatusType.SUCCESS);
    }
}