package cn.product.score.service.back;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;

public interface GuessingMatchOddsService {
	
	public void controlList(InputParams params, DataResult<Object> result);

	public void get(InputParams params, DataResult<Object> result);

	public void getBetSum(InputParams params, DataResult<Object> result);

	public void addType(InputParams params, DataResult<Object> result);
	
	public void editType(InputParams params, DataResult<Object> result);
	
	public void publishType(InputParams params, DataResult<Object> result);
	
	public void deleteType(InputParams params, DataResult<Object> result);
	
	public void historyList(InputParams params, DataResult<Object> result);
	
	public void update(InputParams params, DataResult<Object> result);
}
