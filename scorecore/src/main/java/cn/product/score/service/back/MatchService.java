package cn.product.score.service.back;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;

public interface MatchService {
	
	public void list(InputParams params, DataResult<Object> result);

}
