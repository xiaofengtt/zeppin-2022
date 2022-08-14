package cn.product.payment.controller.base;


/**
 * ErrorResult : 用于响应错误的请求的对象
 */
public class ErrorResult extends BaseResult {
   
	private static final long serialVersionUID = 8567221653356186674L;

	/**
	 * 错误类型码
	 */
    private String errorCode;
    
    
    public ErrorResult(){
    	setStatus(ResultStatusType.ERROR);
    }
    
	public String getErrorCode() {
		return errorCode;
	}


	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
}
