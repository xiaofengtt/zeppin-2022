package cn.product.treasuremall.service.notice.impl;

import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.service.impl.BaseServiceImpl;
import cn.product.treasuremall.service.notice.NoticeWithdrawService;

@Service("noticeWithdrawService")
public class NoticeWithdrawServiceImpl extends BaseServiceImpl implements NoticeWithdrawService{

	@Override
	public void byReapal(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void byUnion(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);	
	}
	
}
