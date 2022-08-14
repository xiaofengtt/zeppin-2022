package cn.product.score.service.back.impl;

import org.springframework.stereotype.Service;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.service.back.MatchService;
import cn.product.score.service.impl.BaseServiceImpl;

@Service("matchService")
public class MatchServiceImpl extends BaseServiceImpl implements MatchService{

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
	
}
