package cn.product.treasuremall.service.front;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;

public interface FrontRecommendService {
	
	public void rankingList(InputParams params, DataResult<Object> result);
	
	public void agentList(InputParams params, DataResult<Object> result);
	
	public void awardList(InputParams params, DataResult<Object> result);
}
