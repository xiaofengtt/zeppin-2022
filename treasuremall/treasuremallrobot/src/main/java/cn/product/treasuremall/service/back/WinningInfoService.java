package cn.product.treasuremall.service.back;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;

public interface WinningInfoService {
	
	public void list(InputParams params, DataResult<Object> result);

	public void receivelist(InputParams params, DataResult<Object> result);
	
	public void receive(InputParams params, DataResult<Object> result);
	
	public void confirm(InputParams params, DataResult<Object> result);
	
}
