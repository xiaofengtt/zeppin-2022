package cn.product.worldmall.service.notice.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.dao.FrontUserHistoryDao;
import cn.product.worldmall.dao.PayNotifyInfoDao;
import cn.product.worldmall.entity.PayNotifyInfo.PayNotifyInfoPayType;
import cn.product.worldmall.service.notice.NoticeWithdrawService;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.reapal.ReapalUtil;
import cn.product.worldmall.util.unionpay.UnionPayUtlity;

@Service("noticeWithdrawService")
public class NoticeWithdrawServiceImpl implements NoticeWithdrawService{
	
	@Autowired
    private FrontUserHistoryDao frontUserHistoryDao;
	
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

	@SuppressWarnings("unchecked")
	@Override
	public void byUnion(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Map<String, String> paramsls = (Map<String, String>) paramsMap.get("paramsls"); 
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Boolean resultFlag = false;
		try {
			//验签
			if(UnionPayUtlity.verify(paramsls)) {//通过
				resultFlag = true;
				//进行上账操作
				resultMap = this.frontUserHistoryDao.withdrawNoticeByUnion(paramsls);
				result.setData(resultMap);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		resultMap.put("result", resultFlag);
		result.setData(resultMap);
		return;
	}
	
}
