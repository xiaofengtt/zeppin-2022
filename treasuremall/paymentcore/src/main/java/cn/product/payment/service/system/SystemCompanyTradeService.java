/**
 * 
 */
package cn.product.payment.service.system;

import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;

public interface SystemCompanyTradeService {
	
	public void list(InputParams params, DataResult<Object> result);
	
	public void get(InputParams params, DataResult<Object> result);
	
	public void add(InputParams params, DataResult<Object> result);
	
	public void check(InputParams params, DataResult<Object> result);
	
	public void fail(InputParams params, DataResult<Object> result);
	
	public void success(InputParams params, DataResult<Object> result);
	
	public void close(InputParams params, DataResult<Object> result);
}
