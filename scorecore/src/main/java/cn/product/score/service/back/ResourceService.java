package cn.product.score.service.back;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;

public interface ResourceService {
	
	public void insert(InputParams params, DataResult<Object> result);

}
