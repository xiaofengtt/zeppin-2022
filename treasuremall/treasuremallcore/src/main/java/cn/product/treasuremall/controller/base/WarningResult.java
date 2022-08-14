package cn.product.treasuremall.controller.base;



/**
 * JSONResult : Response JSONResult for RESTful,封装返回JSON格式的数据
 *
 * @author Clark_Rong
 * @since 2016-03-23 11:17
 */

public class WarningResult<T> extends BaseResult {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 3175104271271311584L;
	
	/**
     * 数据
     */
    private T data;
    private T error;
    

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    
    public T getError() {
		return error;
	}

	public void setError(T error) {
		this.error = error;
	}

	/**
     * 创建返回信息
     */
    public WarningResult() {
    	setStatus(ResultStatusType.WARNING);
    }
}