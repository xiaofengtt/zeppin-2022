package cn.product.treasuremall.service.back;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;

public interface MethodService {
    
	public void loadFilterChainDefinitions(InputParams params, DataResult<Object> result);
}
