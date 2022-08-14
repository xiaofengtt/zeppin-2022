/**
 * 
 */
package cn.product.payment.controller.base;

public class FailResult extends BaseResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = 52030928402835587L;

	
	public FailResult() {
		setStatus(ResultStatusType.FAILED);
	}
	
}
