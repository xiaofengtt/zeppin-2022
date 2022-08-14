package cn.product.worldmall.service.front;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

public interface FrontUserMessageService {

	public void list(InputParams params, DataResult<Object> result);
	
	public void get(InputParams params, DataResult<Object> result);	
	
	public void readAll(InputParams params, DataResult<Object> result);
	
	public void unRead(InputParams params, DataResult<Object> result);
	
}
