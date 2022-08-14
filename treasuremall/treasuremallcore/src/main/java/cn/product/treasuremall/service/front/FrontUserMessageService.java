package cn.product.treasuremall.service.front;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;

public interface FrontUserMessageService {
	
	public void list(InputParams params, DataResult<Object> result);
	
	public void get(InputParams params, DataResult<Object> result);	
	
	public void readAll(InputParams params, DataResult<Object> result);
	
	public void unRead(InputParams params, DataResult<Object> result);
}
