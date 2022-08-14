package cn.product.score.service.notice.impl;

import org.springframework.stereotype.Service;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.service.impl.BaseServiceImpl;
import cn.product.score.service.notice.NoticeRechargeService;

@Service("noticeRechargeService")
public class NoticeRechargeServiceImpl extends BaseServiceImpl implements NoticeRechargeService{

	@Override
	public void byAliwap(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
	
}
