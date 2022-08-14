package cn.product.worldmall.service.front;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

public interface FrontUserRechargeService {
	
	public void getAmountSet(InputParams params, DataResult<Object> result);
	
	public void byBankcard(InputParams params, DataResult<Object> result);

	public void byAlipay(InputParams params, DataResult<Object> result);

	public void byUnion(InputParams params, DataResult<Object> result);
	
	public void byAcicpay(InputParams params, DataResult<Object> result);
	
	public void byJinzun(InputParams params, DataResult<Object> result);

	public void byWorldpay(InputParams params, DataResult<Object> result);

	public void byCreditcard(InputParams params, DataResult<Object> result);

	public void byPaypal(InputParams params, DataResult<Object> result);

	public void byStripe(InputParams params, DataResult<Object> result);
}
