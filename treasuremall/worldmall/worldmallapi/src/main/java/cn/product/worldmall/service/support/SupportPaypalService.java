package cn.product.worldmall.service.support;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

public interface SupportPaypalService {
	
	public void execute(InputParams params, DataResult<Object> result);
}
