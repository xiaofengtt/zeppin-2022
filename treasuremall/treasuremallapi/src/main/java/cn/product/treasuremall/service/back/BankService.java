/**
 * 
 */
package cn.product.treasuremall.service.back;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;

/**
 *
 */
public interface BankService {

	public void list(InputParams params, DataResult<Object> result);

	public void get(InputParams params, DataResult<Object> result);

	public void add(InputParams params, DataResult<Object> result);

	public void edit(InputParams params, DataResult<Object> result);
	
	public void delete(InputParams params, DataResult<Object> result);
}
