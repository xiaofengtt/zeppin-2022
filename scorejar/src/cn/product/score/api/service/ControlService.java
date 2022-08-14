package cn.product.score.api.service;

import cn.product.score.api.base.InputParams;
import cn.product.score.api.base.Result;

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
