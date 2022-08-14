package cn.product.worldmall.service.front;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

public interface FrontCapitalAccountService {
	
	public void platformList(InputParams params, DataResult<Object> result);
	
	public void accountList(InputParams params, DataResult<Object> result);

	public void get(InputParams params, DataResult<Object> result);
}
