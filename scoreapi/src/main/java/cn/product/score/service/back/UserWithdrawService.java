package cn.product.score.service.back;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;

public interface UserWithdrawService {
	
	public void get(InputParams params, DataResult<Object> result);

	public void list(InputParams params, DataResult<Object> result);

	public void checkList(InputParams params, DataResult<Object> result);

	public void checkStatusList(InputParams params, DataResult<Object> result);
	
	public void statusList(InputParams params, DataResult<Object> result);
	
	public void rollback(InputParams params, DataResult<Object> result);
	
	public void rerollback(InputParams params, DataResult<Object> result);
	
	public void delete(InputParams params, DataResult<Object> result);
	
	public void redelete(InputParams params, DataResult<Object> result);
	
	public void apply(InputParams params, DataResult<Object> result);
	
	public void reapply(InputParams params, DataResult<Object> result);
	
	public void confirm(InputParams params, DataResult<Object> result);
	
	public void resubmit(InputParams params, DataResult<Object> result);
	
	public void check(InputParams params, DataResult<Object> result);
}
