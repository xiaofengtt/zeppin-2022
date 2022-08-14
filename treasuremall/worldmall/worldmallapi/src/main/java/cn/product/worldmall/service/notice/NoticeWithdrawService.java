package cn.product.worldmall.service.notice;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

public interface NoticeWithdrawService {
	
	public void byReapal(InputParams params, DataResult<Object> result);
	
	public void byUnion(InputParams params, DataResult<Object> result);
}
