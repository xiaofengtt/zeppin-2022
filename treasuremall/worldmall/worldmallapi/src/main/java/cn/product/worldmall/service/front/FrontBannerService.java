package cn.product.worldmall.service.front;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

public interface FrontBannerService {
	
	public void list(InputParams params, DataResult<Object> result);
}
