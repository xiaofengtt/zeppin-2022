package cn.product.worldmall.service.front.impl;

import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.service.front.FrontBankService;
import cn.product.worldmall.service.impl.BaseServiceImpl;

@Service("frontBankService")
public class FrontBankServiceImpl extends BaseServiceImpl implements FrontBankService{

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
	
}
