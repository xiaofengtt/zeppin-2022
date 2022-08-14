package cn.product.treasuremall.service.front.impl;

import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.service.front.FrontUserCommentService;
import cn.product.treasuremall.service.impl.BaseServiceImpl;

@Service("frontUserCommentService")
public class FrontUserCommentServiceImpl extends BaseServiceImpl implements FrontUserCommentService{

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
}
