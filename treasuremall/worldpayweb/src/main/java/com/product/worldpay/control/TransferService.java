package com.product.worldpay.control;

import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.controller.base.Result;

/**
 *
 * 公共接口调度类
 *
 */
public interface TransferService {
	
	/**
	 * 公共调度接口
	 * @param params
	 * @return
	 */
	Result execute(InputParams params);
	
	Result apiExecute(InputParams params);
}
