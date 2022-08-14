package cn.product.worldmall.service.back;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

public interface ResourceService {
	
	public void insert(InputParams params, DataResult<Object> result);

}
