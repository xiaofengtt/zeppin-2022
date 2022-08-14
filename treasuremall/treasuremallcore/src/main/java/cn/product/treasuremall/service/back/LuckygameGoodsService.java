package cn.product.treasuremall.service.back;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;

public interface LuckygameGoodsService {
	
	public void list(InputParams params, DataResult<Object> result);
	
	public void get(InputParams params, DataResult<Object> result);
	
	public void issuelist(InputParams params, DataResult<Object> result);
	
	public void issueget(InputParams params, DataResult<Object> result);

	public void add(InputParams params, DataResult<Object> result);

	public void edit(InputParams params, DataResult<Object> result);
	
	public void changeStatus(InputParams params, DataResult<Object> result);
	
	public void sorts(InputParams params, DataResult<Object> result);
	
	public void delete(InputParams params, DataResult<Object> result);
}
