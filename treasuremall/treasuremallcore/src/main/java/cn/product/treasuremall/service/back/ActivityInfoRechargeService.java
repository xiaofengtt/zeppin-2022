/**
 * 
 */
package cn.product.treasuremall.service.back;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;

/**
 *
 */
public interface ActivityInfoRechargeService {
	
	public void configlist(InputParams params, DataResult<Object> result);

	public void get(InputParams params, DataResult<Object> result);
}
