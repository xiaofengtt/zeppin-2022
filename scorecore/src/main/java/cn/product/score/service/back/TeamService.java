package cn.product.score.service.back;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;

public interface TeamService {
	
	public void list(InputParams params, DataResult<Object> result);

	public void get(InputParams params, DataResult<Object> result);

	public void add(InputParams params, DataResult<Object> result);

	public void edit(InputParams params, DataResult<Object> result);
	
	public void delete(InputParams params, DataResult<Object> result);

	public void playerList(InputParams params, DataResult<Object> result);

	public void playerGet(InputParams params, DataResult<Object> result);

	public void playerEdit(InputParams params, DataResult<Object> result);
}
