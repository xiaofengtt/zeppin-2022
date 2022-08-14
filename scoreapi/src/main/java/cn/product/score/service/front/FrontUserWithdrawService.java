package cn.product.score.service.front;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;

public interface FrontUserWithdrawService {
	
	public void poundage(InputParams params, DataResult<Object> result);

	public void withdraw(InputParams params, DataResult<Object> result);
}
