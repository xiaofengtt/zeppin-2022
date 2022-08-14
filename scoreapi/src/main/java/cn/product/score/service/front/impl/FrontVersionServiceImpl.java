package cn.product.score.service.front.impl;

import org.springframework.stereotype.Service;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.service.front.FrontVersionService;
import cn.product.score.service.impl.BaseServiceImpl;

@Service("frontVersionService")
public class FrontVersionServiceImpl extends BaseServiceImpl implements FrontVersionService{

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
}
