package cn.product.worldmall.service.back;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

public interface WinningInfoService {
	
	public void list(InputParams params, DataResult<Object> result);

	public void receivelist(InputParams params, DataResult<Object> result);
	
	public void receive(InputParams params, DataResult<Object> result);
	
	public void confirm(InputParams params, DataResult<Object> result);
	
	public void reset(InputParams params, DataResult<Object> result);
}
