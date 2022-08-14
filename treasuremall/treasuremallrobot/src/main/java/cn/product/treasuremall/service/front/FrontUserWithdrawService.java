package cn.product.treasuremall.service.front;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;

public interface FrontUserWithdrawService {

	public void withdraw(InputParams params, DataResult<Object> result);
}
