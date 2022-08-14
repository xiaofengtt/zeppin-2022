package cn.product.worldmall.service.front;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

public interface FrontExplanationService {
	
	public void recharge(InputParams params, DataResult<Object> result);
	
	public void withdraw(InputParams params, DataResult<Object> result);
}
