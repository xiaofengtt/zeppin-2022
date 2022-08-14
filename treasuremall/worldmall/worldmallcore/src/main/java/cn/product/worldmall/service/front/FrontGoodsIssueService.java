package cn.product.worldmall.service.front;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

public interface FrontGoodsIssueService {
	
	public void get(InputParams params, DataResult<Object> result);
	
	public void list(InputParams params, DataResult<Object> result);
	
	public void paymentList(InputParams params, DataResult<Object> result);
	
	public void winningInfoList(InputParams params, DataResult<Object> result);

	public void paymentTop(InputParams params, DataResult<Object> result);

	public void goodsType(InputParams params, DataResult<Object> result);
}
