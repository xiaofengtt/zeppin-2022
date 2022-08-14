package com.product.worldpay.control;

import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.controller.base.Result;

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
