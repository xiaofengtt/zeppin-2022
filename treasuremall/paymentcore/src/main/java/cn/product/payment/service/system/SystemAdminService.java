package cn.product.payment.service.system;

import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;

public interface SystemAdminService {
	
	public void get(InputParams params, DataResult<Object> result);
	
	public void list(InputParams params, DataResult<Object> result);
	
	public void add(InputParams params, DataResult<Object> result);

	public void edit(InputParams params, DataResult<Object> result);
	
	public void password(InputParams params, DataResult<Object> result);
	
	public void getByUsername(InputParams params, DataResult<Object> result);
	
	public void delete(InputParams params, DataResult<Object> result);
	
	public void getNotice(InputParams params, DataResult<Object> result);
}
