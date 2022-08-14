package cn.product.score.service.front;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;

public interface FrontUserConcernService {

	public void list(InputParams params, DataResult<Object> result);

	public void check(InputParams params, DataResult<Object> result);

	public void add(InputParams params, DataResult<Object> result);

	public void cancel(InputParams params, DataResult<Object> result);
}
