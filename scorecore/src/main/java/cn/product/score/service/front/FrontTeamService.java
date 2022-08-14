package cn.product.score.service.front;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;

public interface FrontTeamService {
	
	public void getHistory(InputParams params, DataResult<Object> result);

	public void get(InputParams params, DataResult<Object> result);
	
	public void playerGet(InputParams params, DataResult<Object> result);
}
