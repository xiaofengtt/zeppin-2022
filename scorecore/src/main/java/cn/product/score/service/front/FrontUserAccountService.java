package cn.product.score.service.front;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;

public interface FrontUserAccountService {
	
	public void get(InputParams params, DataResult<Object> result);

	public void historyList(InputParams params, DataResult<Object> result);

}
