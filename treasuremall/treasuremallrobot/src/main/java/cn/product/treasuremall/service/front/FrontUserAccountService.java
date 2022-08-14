package cn.product.treasuremall.service.front;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;

public interface FrontUserAccountService {
	
	public void get(InputParams params, DataResult<Object> result);

	public void historyList(InputParams params, DataResult<Object> result);

	public void historyGet(InputParams params, DataResult<Object> result);
	
	public void voucherStatusList(InputParams params, DataResult<Object> result);
	
	public void voucherList(InputParams params, DataResult<Object> result);
	
	public void paymentList(InputParams params, DataResult<Object> result);
	
	public void paymentGet(InputParams params, DataResult<Object> result);
	
	public void luckyNumList(InputParams params, DataResult<Object> result);
	
	public void winningInfoList(InputParams params, DataResult<Object> result);
	
	public void receive(InputParams params, DataResult<Object> result);
	
	public void receiveList(InputParams params, DataResult<Object> result);
	
	public void receiveGet(InputParams params, DataResult<Object> result);
	
	public void commentList(InputParams params, DataResult<Object> result);
	
	public void commentGet(InputParams params, DataResult<Object> result);
	
	public void commentAdd(InputParams params, DataResult<Object> result);
}
