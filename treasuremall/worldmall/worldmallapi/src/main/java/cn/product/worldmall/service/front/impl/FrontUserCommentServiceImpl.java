package cn.product.worldmall.service.front.impl;

import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.service.front.FrontUserCommentService;
import cn.product.worldmall.service.impl.BaseServiceImpl;

@Service("frontUserCommentService")
public class FrontUserCommentServiceImpl extends BaseServiceImpl implements FrontUserCommentService{

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
}
