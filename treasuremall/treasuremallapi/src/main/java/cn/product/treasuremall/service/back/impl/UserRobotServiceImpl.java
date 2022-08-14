package cn.product.treasuremall.service.back.impl;

import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.service.back.UserRobotService;
import cn.product.treasuremall.service.impl.BaseServiceImpl;

@Service("userRobotService")
public class UserRobotServiceImpl extends BaseServiceImpl implements UserRobotService{

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);
	}

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);
	}

	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);
	}

	@Override
	public void delete(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
	
	@Override
	public void add(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);
	}

	@Override
	public void settingadd(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void isAll(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
	
	@Override
	public void settingget(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void settingedit(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void goldadd(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void goldsub(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);
	}
}
