package cn.product.score.service.notice.impl;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.controller.base.TransactionException;
import cn.product.score.dao.FrontUserHistoryCheckDao;
import cn.product.score.service.notice.NoticeRechargeService;

@Service("noticeRechargeService")
public class NoticeRechargeServiceImpl implements NoticeRechargeService{
	
	@Autowired
    private FrontUserHistoryCheckDao frontUserHistoryCheckDao;

	@SuppressWarnings("unchecked")
	@Override
	public void byAliwap(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Map<String, String> paramsls = (Map<String, String>) paramsMap.get("paramsls"); 
		try {
			Map<String, Object> resultMap = this.frontUserHistoryCheckDao.rechargeNoticeByAlipay(paramsls);
			result.setData(resultMap);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (TransactionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
