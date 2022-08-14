package cn.product.payment.control;

import cn.product.payment.controller.base.InputParams;
import cn.product.payment.controller.base.Result;

/**
 *
 * 公共接口调用类
 *
 */
public interface ControlService {
	
	/**
	 * 公共调用接口
	 * @param params
	 * @return
	 */
	Result execute(InputParams params);
	
	Result apiExecute(InputParams params);
}
