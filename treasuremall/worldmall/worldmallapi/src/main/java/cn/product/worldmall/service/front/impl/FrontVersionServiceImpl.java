package cn.product.worldmall.service.front.impl;

import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.service.front.FrontVersionService;
import cn.product.worldmall.service.impl.BaseServiceImpl;

@Service("frontVersionService")
public class FrontVersionServiceImpl extends BaseServiceImpl implements FrontVersionService{

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);	
	}
}
