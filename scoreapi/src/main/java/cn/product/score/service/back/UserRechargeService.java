package cn.product.score.service.back;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;

public interface UserRechargeService {
	
	public void get(InputParams params, DataResult<Object> result);

	public void list(InputParams params, DataResult<Object> result);

	public void checkList(InputParams params, DataResult<Object> result);

	public void checkStatusList(InputParams params, DataResult<Object> result);
	
	public void statusList(InputParams params, DataResult<Object> result);
	
	public void add(InputParams params, DataResult<Object> result);
	
	public void edit(InputParams params, DataResult<Object> result);
	
	public void confirm(InputParams params, DataResult<Object> result);
	
	public void resubmit(InputParams params, DataResult<Object> result);
	
	public void close(InputParams params, DataResult<Object> result);
	
	public void check(InputParams params, DataResult<Object> result);
}
