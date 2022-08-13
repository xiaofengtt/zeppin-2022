package cn.zeppin.product.ntb.core.exception;

import org.apache.shiro.authc.ExcessiveAttemptsException;

public class CustomExcessiveAttemptsException extends ExcessiveAttemptsException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1470059529259640940L;
	
	/**
	 * 错误输入次数
	 * 根据错误输入次数的不同，可以实现锁定时间的不同逻辑
	 */
	private int retryCount;

    
	public CustomExcessiveAttemptsException(int retryCount, String message) {
		super(message);
		this.retryCount = retryCount;
	}
	
	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}
	
	


}


