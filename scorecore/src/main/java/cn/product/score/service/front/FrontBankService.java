package cn.product.score.service.front;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;

public interface FrontBankService {
	
	public void list(InputParams params, DataResult<Object> result);
}
