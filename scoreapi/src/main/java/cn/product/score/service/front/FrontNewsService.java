package cn.product.score.service.front;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;

public interface FrontNewsService {
	
	public void list(InputParams params, DataResult<Object> result);

	public void get(InputParams params, DataResult<Object> result);

	public void commentList(InputParams params, DataResult<Object> result);

	public void comment(InputParams params, DataResult<Object> result);
}
