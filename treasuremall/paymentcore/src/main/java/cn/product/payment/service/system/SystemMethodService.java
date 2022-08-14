package cn.product.payment.service.system;

import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;

public interface SystemMethodService {
    
	public void loadFilterChainDefinitions(InputParams params, DataResult<Object> result);
}
