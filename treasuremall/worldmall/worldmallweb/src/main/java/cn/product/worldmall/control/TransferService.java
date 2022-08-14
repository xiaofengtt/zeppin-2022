package cn.product.worldmall.control;

import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.Result;

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
