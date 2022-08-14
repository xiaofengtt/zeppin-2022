package cn.product.worldmall.service.notice;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

public interface NoticeRechargeService {
	
	public void byAliwap(InputParams params, DataResult<Object> result);
	
	public void byUnion(InputParams params, DataResult<Object> result);

	public void byAcicpay(InputParams params, DataResult<Object> result);
	
	public void byJinzun(InputParams params, DataResult<Object> result);
	
	public void byStripe(InputParams params, DataResult<Object> result);
}
