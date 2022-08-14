/**
 * 
 */
package cn.product.worldmall.controller.base;


/**
 * @author Clark.R 2016年9月26日
 *
 */
public class FailResult extends BaseResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = 52030928402835587L;

	
	public FailResult() {
		setStatus(ResultStatusType.FAILED);
	}
	
}
