/**
 * 
 */
package cn.product.treasuremall.service.back;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;

/**
 *
 */
public interface ActivityInfoFirstchargeService {
	
	public void prizelist(InputParams params, DataResult<Object> result);

	public void get(InputParams params, DataResult<Object> result);
}
