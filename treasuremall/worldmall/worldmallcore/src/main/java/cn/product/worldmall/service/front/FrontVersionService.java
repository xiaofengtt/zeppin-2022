package cn.product.worldmall.service.front;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

public interface FrontVersionService {
	
	public void get(InputParams params, DataResult<Object> result);
}
