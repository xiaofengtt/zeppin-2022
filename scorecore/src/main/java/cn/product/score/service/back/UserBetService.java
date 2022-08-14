package cn.product.score.service.back;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;

public interface UserBetService {
	
	public void get(InputParams params, DataResult<Object> result);

	public void list(InputParams params, DataResult<Object> result);

	public void confirm(InputParams params, DataResult<Object> result);

	public void refund(InputParams params, DataResult<Object> result);
	
	public void lottery(InputParams params, DataResult<Object> result);
	
	public void statusList(InputParams params, DataResult<Object> result);
}
