package cn.product.worldmall.service.front;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

public interface FrontUserService {
	
	public void rate(InputParams params, DataResult<Object> result);
	
	public void getByMobile(InputParams params, DataResult<Object> result);
	
	public void editNickname(InputParams params, DataResult<Object> result);
	
	public void editImage(InputParams params, DataResult<Object> result);
	
	public void editPwd(InputParams params, DataResult<Object> result);
	
	public void checkMobile(InputParams params, DataResult<Object> result);
	
	public void editMobile(InputParams params, DataResult<Object> result);
	
	public void editMobilePassword(InputParams params, DataResult<Object> result);
	
	public void certification(InputParams params, DataResult<Object> result);
	
	public void dailyStatistics(InputParams params, DataResult<Object> result);
}
