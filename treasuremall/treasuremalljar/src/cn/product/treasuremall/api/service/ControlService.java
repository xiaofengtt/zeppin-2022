package cn.product.treasuremall.api.service;

import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;

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
}
