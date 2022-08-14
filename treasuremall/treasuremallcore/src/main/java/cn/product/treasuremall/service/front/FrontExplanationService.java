package cn.product.treasuremall.service.front;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;

public interface FrontExplanationService {
	
	public void recharge(InputParams params, DataResult<Object> result);
	
	public void withdraw(InputParams params, DataResult<Object> result);
}
