package cn.product.score.service.notice.impl;

import org.springframework.stereotype.Service;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.service.impl.BaseServiceImpl;
import cn.product.score.service.notice.NoticeWithdrawService;

@Service("noticeWithdrawService")
public class NoticeWithdrawServiceImpl extends BaseServiceImpl implements NoticeWithdrawService{

	@Override
	public void byReapal(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
	
}
