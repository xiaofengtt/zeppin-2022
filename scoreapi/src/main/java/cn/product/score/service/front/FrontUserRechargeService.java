package cn.product.score.service.front;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;

public interface FrontUserRechargeService {
	
	public void byBankcard(InputParams params, DataResult<Object> result);

	public void byAlipay(InputParams params, DataResult<Object> result);
}
