/**
 * 
 */
package cn.product.worldmall.service.back;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

/**
 *
 */
public interface UserGroupService {
	
	public void list(InputParams params, DataResult<Object> result);

	public void get(InputParams params, DataResult<Object> result);

	public void add(InputParams params, DataResult<Object> result);

	public void edit(InputParams params, DataResult<Object> result);
}
