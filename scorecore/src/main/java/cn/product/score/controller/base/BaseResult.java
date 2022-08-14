package cn.product.score.controller.base;


/**
 * Result : 响应的结果对象
 *
 * @author Clark_Rong
 * @since 2016-03-23 11:17
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
     * 下一步操作
     */
    private String operation;
    
    /**
     * 源地址
     */
    private String url;
    
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

    public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}
}
