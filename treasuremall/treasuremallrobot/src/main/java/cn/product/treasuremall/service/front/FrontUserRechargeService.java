package cn.product.treasuremall.service.front;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;

public interface FrontUserRechargeService {
	
	public void getAmountSet(InputParams params, DataResult<Object> result);
	
	public void byBankcard(InputParams params, DataResult<Object> result);

	public void byAlipay(InputParams params, DataResult<Object> result);

	public void byUnion(InputParams params, DataResult<Object> result);
}
