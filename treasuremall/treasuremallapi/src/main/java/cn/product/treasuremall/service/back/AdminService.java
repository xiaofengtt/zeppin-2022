package cn.product.treasuremall.service.back;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;

public interface AdminService{

	public void get(InputParams params, DataResult<Object> result);

	public void list(InputParams params, DataResult<Object> result);

	public void add(InputParams params, DataResult<Object> result);

	public void update(InputParams params, DataResult<Object> result);
	
	public void password(InputParams params, DataResult<Object> result);

	public void getByUsername(InputParams params, DataResult<Object> result);

	public void resetPwd(InputParams params, DataResult<Object> result);

	public void delete(InputParams params, DataResult<Object> result);
	
	public void getNotice(InputParams params, DataResult<Object> result);
}
