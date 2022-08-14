package cn.product.worldmall.service.back;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

public interface UserRobotService {
	
	public void get(InputParams params, DataResult<Object> result);

	public void list(InputParams params, DataResult<Object> result);
	
	public void add(InputParams params, DataResult<Object> result);
	
	public void edit(InputParams params, DataResult<Object> result);

	public void changeStatus(InputParams params, DataResult<Object> result);
	
	public void delete(InputParams params, DataResult<Object> result);

	public void settingadd(InputParams params, DataResult<Object> result);
	
	public void isAll(InputParams params, DataResult<Object> result);

	public void settingget(InputParams params, DataResult<Object> result);

	public void goldadd(InputParams params, DataResult<Object> result);

	public void goldsub(InputParams params, DataResult<Object> result);

	public void upload(InputParams params, DataResult<Object> result);

}
