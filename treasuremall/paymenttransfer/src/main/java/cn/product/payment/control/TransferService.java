package cn.product.payment.control;

import cn.product.payment.controller.base.InputParams;
import cn.product.payment.controller.base.Result;

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
}
