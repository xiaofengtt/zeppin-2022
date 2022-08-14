package cn.product.score.service.front;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;

public interface FrontLoginService {
	
	public void login(InputParams params, DataResult<Object> result);

	public void loginByPwd(InputParams params, DataResult<Object> result);

	public void auth(InputParams params, DataResult<Object> result);

	public void forgotPwd(InputParams params, DataResult<Object> result);
}
