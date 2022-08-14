package cn.product.treasuremall.control;

import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;

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
