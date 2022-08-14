/**
 * 
 */
package cn.product.payment.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.product.payment.control.TransferService;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.controller.base.Result;

public abstract class BaseController implements Controller{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	@Autowired
	private TransferService transferService;
	
	public Result execute(InputParams params) {
		try {
			return this.transferService.execute(params);
		} catch (Exception e) {
			logger.error("", "Invoke Service Error.", params.getService() + "." + params.getMethod(), e);
		}
		return null;
	}
}
