/**
 * 
 */
package cn.product.payment.service.system;

import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;

public interface SystemRoleMenuService {

	public void all(InputParams params, DataResult<Object> result);
	
	public void list(InputParams params, DataResult<Object> result);
	
	public void edit(InputParams params, DataResult<Object> result);
}
