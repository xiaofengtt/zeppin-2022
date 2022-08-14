/**
 * 
 */
package cn.product.treasuremall.service.back;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;

/**
 *
 */
public interface ActivityInfoBuyfreeService {
	
	public void goodslist(InputParams params, DataResult<Object> result);

	public void get(InputParams params, DataResult<Object> result);
}
