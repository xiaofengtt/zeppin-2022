package cn.product.worldmall.api.base;


/**
 * JSONResult : Response JSONResult for RESTful,封装返回JSON格式的数据
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
    private Integer pageNum;
    
    /**
     * 页面容量
     */
    private Integer pageSize;
    
    /**
     * 页面总数
     */
    private Integer totalPageCount;
    
    /**
     * 总结果数
     */
    private Integer totalResultCount;
    

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    
	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(Integer totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public Integer getTotalResultCount() {
		return totalResultCount;
	}

	public void setTotalResultCount(Integer totalResultCount) {
		this.totalResultCount = totalResultCount;
	}
    
    /**
     * 创建返回信息
     */
    public DataResult() {
    	setStatus(ResultStatusType.SUCCESS);
    }
}