package cn.product.score.service.back;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;

public interface CapitalAccountService {
	
	public void get(InputParams params, DataResult<Object> result);

	public void list(InputParams params, DataResult<Object> result);

	public void add(InputParams params, DataResult<Object> result);

	public void edit(InputParams params, DataResult<Object> result);
	
	public void changeStatus(InputParams params, DataResult<Object> result);

}
