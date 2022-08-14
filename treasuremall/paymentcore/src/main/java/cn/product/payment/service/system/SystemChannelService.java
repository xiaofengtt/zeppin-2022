/**
 * 
 */
package cn.product.payment.service.system;

import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;

public interface SystemChannelService {
	
	public void list(InputParams params, DataResult<Object> result);
	
	public void get(InputParams params, DataResult<Object> result);
	
	public void changeStatus(InputParams params, DataResult<Object> result);
}
