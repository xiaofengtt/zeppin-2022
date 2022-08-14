package cn.product.score.service.back;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;

public interface AdminService{

	public void get(InputParams params, DataResult<Object> result);

	public void list(InputParams params, DataResult<Object> result);

	public void add(InputParams params, DataResult<Object> result);

	public void update(InputParams params, DataResult<Object> result);
	
	public void password(InputParams params, DataResult<Object> result);

	public void getByUsername(InputParams params, DataResult<Object> result);
}
