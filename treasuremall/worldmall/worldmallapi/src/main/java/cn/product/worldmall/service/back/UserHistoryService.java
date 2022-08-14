package cn.product.worldmall.service.back;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

public interface UserHistoryService {
	
	public void get(InputParams params, DataResult<Object> result);

	public void list(InputParams params, DataResult<Object> result);
	
	public void partakelist(InputParams params, DataResult<Object> result);
	
	public void winlist(InputParams params, DataResult<Object> result);
	
	public void rechargelist(InputParams params, DataResult<Object> result);
	
	public void withdrawlist(InputParams params, DataResult<Object> result);

	public void voucherlist(InputParams params, DataResult<Object> result);

	public void scorelist(InputParams params, DataResult<Object> result);
}
