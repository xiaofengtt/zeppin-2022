package cn.product.score.service.back;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;

public interface GuessingMatchService {
	
	public void get(InputParams params, DataResult<Object> result);

	public void list(InputParams params, DataResult<Object> result);

	public void add(InputParams params, DataResult<Object> result);

	public void delete(InputParams params, DataResult<Object> result);
	
	public void finish(InputParams params, DataResult<Object> result);

	public void statusList(InputParams params, DataResult<Object> result);
}
