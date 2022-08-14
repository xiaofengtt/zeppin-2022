package cn.product.payment.service.store;

import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;

public interface StoreCompanyAdminService {
	
	public void get(InputParams params, DataResult<Object> result);
	
	public void password(InputParams params, DataResult<Object> result);
	
	public void getByMobile(InputParams params, DataResult<Object> result);
}
