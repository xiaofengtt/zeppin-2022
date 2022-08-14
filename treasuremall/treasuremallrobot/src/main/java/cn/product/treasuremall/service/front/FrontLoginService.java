package cn.product.treasuremall.service.front;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;

public interface FrontLoginService {
	
	public void loginByCode(InputParams params, DataResult<Object> result);

	public void loginByPwd(InputParams params, DataResult<Object> result);

	public void auth(InputParams params, DataResult<Object> result);

	public void resetPwd(InputParams params, DataResult<Object> result);
	
	public void resetCheck(InputParams params, DataResult<Object> result);
	
}
