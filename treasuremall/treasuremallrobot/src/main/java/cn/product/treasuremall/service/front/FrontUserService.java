package cn.product.treasuremall.service.front;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;

public interface FrontUserService {
	
	public void rate(InputParams params, DataResult<Object> result);
	
	public void getByMobile(InputParams params, DataResult<Object> result);
	
	public void editNickname(InputParams params, DataResult<Object> result);
	
	public void editImage(InputParams params, DataResult<Object> result);
	
	public void editPwd(InputParams params, DataResult<Object> result);
	
	public void checkMobile(InputParams params, DataResult<Object> result);
	
	public void editMobile(InputParams params, DataResult<Object> result);
	
	public void certification(InputParams params, DataResult<Object> result);
}
