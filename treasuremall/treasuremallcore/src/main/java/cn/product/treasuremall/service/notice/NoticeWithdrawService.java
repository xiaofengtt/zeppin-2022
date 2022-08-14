package cn.product.treasuremall.service.notice;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;

public interface NoticeWithdrawService {
	
	public void byReapal(InputParams params, DataResult<Object> result);
	
	public void byUnion(InputParams params, DataResult<Object> result);
}
