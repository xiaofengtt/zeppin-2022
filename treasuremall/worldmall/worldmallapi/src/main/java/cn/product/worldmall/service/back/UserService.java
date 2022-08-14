package cn.product.worldmall.service.back;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

public interface UserService {
	
	public void get(InputParams params, DataResult<Object> result);

	public void list(InputParams params, DataResult<Object> result);

	public void changeStatus(InputParams params, DataResult<Object> result);

	public void addresslist(InputParams params, DataResult<Object> result);

	public void group(InputParams params, DataResult<Object> result);

	public void blackadd(InputParams params, DataResult<Object> result);
	
	public void blacklist(InputParams params, DataResult<Object> result);

	public void blackdrop(InputParams params, DataResult<Object> result);
}
