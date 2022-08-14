package cn.product.treasuremall.service.front;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;

public interface FrontCapitalAccountService {
	
	public void platformList(InputParams params, DataResult<Object> result);
	
	public void accountList(InputParams params, DataResult<Object> result);

	public void get(InputParams params, DataResult<Object> result);

	public void explanation(InputParams params, DataResult<Object> result);

}
