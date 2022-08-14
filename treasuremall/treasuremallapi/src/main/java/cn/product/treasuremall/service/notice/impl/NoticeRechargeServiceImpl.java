package cn.product.treasuremall.service.notice.impl;

import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.service.impl.BaseServiceImpl;
import cn.product.treasuremall.service.notice.NoticeRechargeService;

@Service("noticeRechargeService")
public class NoticeRechargeServiceImpl extends BaseServiceImpl implements NoticeRechargeService{

	@Override
	public void byAliwap(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void byUnion(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);
	}

	@Override
	public void byAcicpay(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void byJinzun(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);
	}
	
}
