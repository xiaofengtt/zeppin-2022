package cn.product.worldmall.service.front;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

public interface FrontAreaService {
	
	public void list(InputParams params, DataResult<Object> result);
	
	public void country(InputParams params, DataResult<Object> result);
	
	public void currency(InputParams params, DataResult<Object> result);
}
