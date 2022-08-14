package cn.product.worldmall.service.front;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

public interface FrontLoginService {
	
	public void loginByCode(InputParams params, DataResult<Object> result);

	public void loginByPwd(InputParams params, DataResult<Object> result);

	public void loginByApple(InputParams params, DataResult<Object> result);

	public void loginByFacebook(InputParams params, DataResult<Object> result);

	public void auth(InputParams params, DataResult<Object> result);

	public void resetPwd(InputParams params, DataResult<Object> result);
	
	public void resetCheck(InputParams params, DataResult<Object> result);
	
	public void checkin(InputParams params, DataResult<Object> result);
	
	public void demoNotice(InputParams params, DataResult<Object> result);
}
