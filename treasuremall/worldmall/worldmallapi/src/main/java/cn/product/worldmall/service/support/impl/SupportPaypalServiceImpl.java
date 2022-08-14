package cn.product.worldmall.service.support.impl;

import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.service.impl.BaseServiceImpl;
import cn.product.worldmall.service.support.SupportPaypalService;

@Service("supportPaypalService")
public class SupportPaypalServiceImpl extends BaseServiceImpl implements SupportPaypalService{

	@Override
	public void execute(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
}
