package cn.product.worldmall.service.support;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

public interface SupportStripeService {
	
	public void load(InputParams params, DataResult<Object> result);
	
	public void checkout(InputParams params, DataResult<Object> result);
}
