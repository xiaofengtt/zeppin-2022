package cn.product.score.service.front;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;

public interface FrontGuessingMatchService {
	
	public void list(InputParams params, DataResult<Object> result);

	public void categoryList(InputParams params, DataResult<Object> result);
}
