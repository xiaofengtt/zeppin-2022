package cn.product.worldmall.service.front.impl;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

public interface FrontVersionService {
	
	public void get(InputParams params, DataResult<Object> result);
}
