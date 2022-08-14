package cn.product.payment.service.system;

import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;

public interface SystemRoleService{
	
	public void all(InputParams params, DataResult<Object> result);
}
