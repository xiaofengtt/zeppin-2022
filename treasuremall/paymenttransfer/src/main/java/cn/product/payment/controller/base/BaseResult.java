package cn.product.payment.controller.base;


/**
 * Result : 响应的结果对象
 *
 */
public class BaseResult implements Result {
	
    private static final long serialVersionUID = 6288374846131788743L;
    
    public enum ResultStatusType {
    	SUCCESS("success"), FAILED("failed"), WARNING("warning"), ERROR("error");
    	
    	 private String name;
    	 
    	 private ResultStatusType(String name) {
             this.name = name;
         }
    	 
    	 @Override
    	 public String toString() {
    		 return this.name;
    	 }
    }

    /**
     * 信息
     */
    private String message;

    /**
     * 状态码
     */
    private ResultStatusType status;
    
    /**
     * 跳转地址
     */
    private String redirect;
    

	public BaseResult() {

    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultStatusType getStatus() {
        return status;
    }

    public void setStatus(ResultStatusType status) {
        this.status = status;
    }

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}
}
