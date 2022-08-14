package cn.product.payment.service.notice.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.dao.ChannelAccountDao;
import cn.product.payment.dao.NoticeInfoDao;
import cn.product.payment.dao.UserRechargeDao;
import cn.product.payment.entity.ChannelAccount;
import cn.product.payment.entity.NoticeInfo;
import cn.product.payment.entity.NoticeInfo.NoticeInfoStatus;
import cn.product.payment.entity.NoticeInfo.NoticeInfoType;
import cn.product.payment.entity.UserRecharge;
import cn.product.payment.service.notice.NoticeAlipayService;
import cn.product.payment.util.JSONUtils;
import cn.product.payment.util.Utlity;
import cn.product.payment.util.alipay.AliUtlity;

import com.alipay.api.internal.util.AlipaySignature;

@Service("noticeAlipayService")
public class NoticeAlipayServiceImpl implements NoticeAlipayService{
	
	@Autowired
	private UserRechargeDao userRechargeDao;
	
	@Autowired
	private ChannelAccountDao channelAccountDao;
	
	@Autowired
	private NoticeInfoDao noticeInfoDao;
	
	/**
	 * 支付宝回调处理
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void companyNotice(InputParams params, DataResult<Object> result) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			Map<String, Object> paramsMap = params.getParams();
			Map<String,String> dataParams = (Map<String,String>)paramsMap.get("params");
			
			//取参数
			String orderNum = new String(dataParams.get("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			String alipayOrderNum = new String(dataParams.get("trade_no").getBytes("ISO-8859-1"),"UTF-8");
			String status = new String(dataParams.get("trade_status").getBytes("ISO-8859-1"),"UTF-8");
			String passbackParams = new String(dataParams.get("passback_params").getBytes("ISO-8859-1"),"UTF-8");
			
			if(Utlity.checkStringNull(passbackParams)){
				//没订单号
				resultMap.put("result", false);
				result.setData(resultMap);
				return;
			}
			
			//查记录
			UserRecharge ur = this.userRechargeDao.get(passbackParams);
			if(ur == null){
				//订单不存在
				resultMap.put("result", false);
				result.setData(resultMap);
				return;
			}
			
			ChannelAccount ca = this.channelAccountDao.get(ur.getChannelAccount());
			Map<String, Object> caMap = JSONUtils.json2map(ca.getData());
			
			//验签
			boolean verify_result = AlipaySignature.rsaCheckV1(dataParams, caMap.get("publicKey").toString(), AliUtlity.CHARSET, AliUtlity.SIGN_TYPE);
			if(verify_result) {
				//验签成功
				//封装交易信息
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("orderNum", orderNum);
				dataMap.put("alipayOrderNum", alipayOrderNum);
				dataMap.put("userRecharge", passbackParams);
				dataMap.put("status", status);
				
				//插入三方回调记录
				NoticeInfo notice = new NoticeInfo();
				notice.setUuid(UUID.randomUUID().toString());
				notice.setChannel(ur.getChannel());
				notice.setOrderNum(orderNum);
				notice.setData(JSONUtils.obj2json(dataMap));
				notice.setSource(JSONUtils.obj2json(dataParams));
				notice.setType(NoticeInfoType.RECHARGE);
				notice.setStatus(NoticeInfoStatus.NORMAL);
				notice.setCreatetime(new Timestamp(System.currentTimeMillis()));
				
				this.noticeInfoDao.insert(notice);
				
				resultMap.put("result", true);
				result.setData(resultMap);
				return;
			} else {
				//验签失败
				resultMap.put("result", false);
				result.setData(resultMap);
				return;
			}
		} catch (Exception e) {
			resultMap.put("result", false);
			result.setData(resultMap);
			return;
		}
	}
	
	
}
