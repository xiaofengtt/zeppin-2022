package cn.product.treasuremall.service.notice;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;

public interface NoticeRechargeService {
	
	public void byAliwap(InputParams params, DataResult<Object> result);
	
	public void byUnion(InputParams params, DataResult<Object> result);
	
	public void byAcicpay(InputParams params, DataResult<Object> result);
	
	public void byJinzun(InputParams params, DataResult<Object> result);
}
