package cn.product.worldmall.service.front;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

public interface FrontRecommendService {
	
	public void rankingList(InputParams params, DataResult<Object> result);
	
	public void agentList(InputParams params, DataResult<Object> result);
	
	public void awardList(InputParams params, DataResult<Object> result);
}
