package cn.product.treasuremall.service.back;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;

public interface AdminOffsetOrderService {
	
	public void get(InputParams params, DataResult<Object> result);

	public void list(InputParams params, DataResult<Object> result);

	public void add(InputParams params, DataResult<Object> result);

	public void close(InputParams params, DataResult<Object> result);
	
	public void check(InputParams params, DataResult<Object> result);
}
