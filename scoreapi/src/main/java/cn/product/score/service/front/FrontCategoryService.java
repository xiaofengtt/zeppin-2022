package cn.product.score.service.front;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;

public interface FrontCategoryService {
	
	public void list(InputParams params, DataResult<Object> result);

	public void get(InputParams params, DataResult<Object> result);

	public void standingList(InputParams params, DataResult<Object> result);

	public void topscoreList(InputParams params, DataResult<Object> result);
	
	public void teamList(InputParams params, DataResult<Object> result);
}
