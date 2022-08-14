/**
 * 
 */
package cn.product.treasuremall.controller.base;

/**
 * @author elegantclack
 *
 */
public class SuccessResult extends BaseResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4580271888473940834L;

	/**
	 * 
	 */
	public SuccessResult() {
		this.setStatus(ResultStatusType.SUCCESS);
	}

}
