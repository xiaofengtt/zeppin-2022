package cn.product.treasuremall.service.back;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;

public interface UserService {
	
	public void get(InputParams params, DataResult<Object> result);

	public void list(InputParams params, DataResult<Object> result);

	public void changeStatus(InputParams params, DataResult<Object> result);

	public void addresslist(InputParams params, DataResult<Object> result);
}
