package cn.product.score.service.notice.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.dao.FrontUserHistoryCheckDao;
import cn.product.score.dao.PayNotifyInfoDao;
import cn.product.score.entity.PayNotifyInfo.PayNotifyInfoPayType;
import cn.product.score.service.notice.NoticeWithdrawService;
import cn.product.score.util.JSONUtils;
import cn.product.score.util.reapal.ReapalUtil;

@Service("noticeWithdrawService")
public class NoticeWithdrawServiceImpl implements NoticeWithdrawService{
	
	@Autowired
    private FrontUserHistoryCheckDao frontUserHistoryCheckDao;
	
	@Autowired
	private PayNotifyInfoDao payNotifyInfoDao;

	@Override
	public void byReapal(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String encryptkey = paramsMap.get("encryptkey") == null ? "" : paramsMap.get("encryptkey").toString();
		String data = paramsMap.get("data") == null ? "" : paramsMap.get("data").toString();
		try {
			String resultStr = ReapalUtil.pubkey(encryptkey,data);
			LoggerFactory.getLogger(getClass()).info("推送回调接口payback_result:"+resultStr);
	    	/*
	    	 * 根据解析后的result做后续处理
	    	 * */
	    	Map<String, Object> map = JSONUtils.json2map(resultStr);
	    	LoggerFactory.getLogger(getClass()).info(map.get("data")+"");
	    	HashMap<String, Object> resultMap = this.payNotifyInfoDao.insertPayNotifyInfo(map, PayNotifyInfoPayType.WITHDRAW_REAPAL_COM);
	    	result.setData(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
	}
	
}
