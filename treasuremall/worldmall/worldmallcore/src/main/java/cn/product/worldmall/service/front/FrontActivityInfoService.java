package cn.product.worldmall.service.front;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

public interface FrontActivityInfoService {
	
	public void get(InputParams params, DataResult<Object> result);
	
	public void list(InputParams params, DataResult<Object> result);
	
	/*
	 * 0元购活动
	 */
	public void buyfreeGet(InputParams params, DataResult<Object> result);
	
	public void buyfreeList(InputParams params, DataResult<Object> result);
	
	public void buyfreeHistoryList(InputParams params, DataResult<Object> result);
	
	public void buyfreeHistoryGet(InputParams params, DataResult<Object> result);
	
	public void buyfreePartake(InputParams params, DataResult<Object> result);
	
	/*
	 * 签到活动
	 */
	public void checkinList(InputParams params, DataResult<Object> result);
	
	public void checkinHistoryList(InputParams params, DataResult<Object> result);
	
	public void checkinHistoryGet(InputParams params, DataResult<Object> result);
	
	public void checkinPartake(InputParams params, DataResult<Object> result);
	
	
	/*
	 * 签到活动
	 */
	public void scorelotteryList(InputParams params, DataResult<Object> result);
	
	public void scorelotteryHistoryList(InputParams params, DataResult<Object> result);
	
	public void scorelotteryHistoryGet(InputParams params, DataResult<Object> result);
	
	public void scorelotteryPartake(InputParams params, DataResult<Object> result);
	
	/*
	 * 兑奖
	 */
	public void receive(InputParams params, DataResult<Object> result);
}
