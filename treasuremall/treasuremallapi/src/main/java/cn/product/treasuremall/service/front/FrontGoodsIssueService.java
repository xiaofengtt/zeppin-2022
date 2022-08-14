package cn.product.treasuremall.service.front;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;

public interface FrontGoodsIssueService {
	
	public void get(InputParams params, DataResult<Object> result);
	
	public void list(InputParams params, DataResult<Object> result);
	
	public void paymentList(InputParams params, DataResult<Object> result);
	
	public void winningInfoList(InputParams params, DataResult<Object> result);

	public void paymentTop(InputParams params, DataResult<Object> result);
	
	public void goodsType(InputParams params, DataResult<Object> result);
}
