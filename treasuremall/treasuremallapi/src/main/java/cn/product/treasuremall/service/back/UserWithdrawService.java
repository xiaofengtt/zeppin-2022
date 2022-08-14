package cn.product.treasuremall.service.back;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;

public interface UserWithdrawService {
	
	public void get(InputParams params, DataResult<Object> result);

	public void list(InputParams params, DataResult<Object> result);

	public void firstCheck(InputParams params, DataResult<Object> result);

	public void finalCheck(InputParams params, DataResult<Object> result);	
	
	public void cancel(InputParams params, DataResult<Object> result);
}
