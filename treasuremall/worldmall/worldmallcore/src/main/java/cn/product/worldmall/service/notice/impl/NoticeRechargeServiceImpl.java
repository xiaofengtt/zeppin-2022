package cn.product.worldmall.service.notice.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.dao.CapitalAccountDao;
import cn.product.worldmall.dao.FrontUserHistoryDao;
import cn.product.worldmall.entity.CapitalAccount;
import cn.product.worldmall.service.notice.NoticeRechargeService;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.jinzun.JinzunUtlity;
import cn.product.worldmall.util.unionpay.UnionPayUtlity;
import cn.product.worldmall.util.xingda.AcicpayUtlity;

@Service("noticeRechargeService")
public class NoticeRechargeServiceImpl implements NoticeRechargeService{
	
	private final static Logger log = LoggerFactory.getLogger(NoticeRechargeServiceImpl.class);
	
	@Autowired
    private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
    private CapitalAccountDao capitalAccountDao;
	
//	@Autowired
//	private FrontUserRechargeOrderDao frontUserRechargeOrderDao;

	@Override
	public void byAliwap(InputParams params, DataResult<Object> result) {
		//{amount=10000, channel=8c41c2ff-2c36-4f90-92e0-6c30618dd92d, sign=N%2Fd%2Fud4kpGMfJYLFzTM3ux%2F%2F%2FPNwsv%2BSlAGAzE2LvMezKfab5ASuP68I8M3RbQAsIyXcGMAtHXp9SMCeECAG2B4fLH7ytCL3DB6ZoTccqOhnxZ4GTePBcJ0soIIrjIdpk1l%2BkI739MG8UDSLaIs1r%2BaIlap9II2HRNjbUB13pFcfpNOcSJIV4Y2dt81OEE963hKXUUIPg5CgB9x%2FcCSoi%2F91If3CdIy8625dyNSCzD4jtorhlaydqvfPC6yXc0ElQEICe8lOInkmm2zxP6jIpM5LeeidNWOnt0O%2BU9llIAO7WKIpo6rvm26xk67ftE0kzlkq5tZIBW9IVWoOG0JmAg%3D%3D, orderNum=1231111213, company=2c41c2ff-2c36-4f90-92e0-6c30618dd91d, transData=111211345, paymentOrderNum=10126646600552186777600, timestamp=1585645990154, status=close}
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
				resultMap = this.frontUserHistoryDao.rechargeNoticeByUnion(paramsls);
				resultMap.put("result", resultFlag);
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

	@SuppressWarnings("unchecked")
	@Override
	public void byAcicpay(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Map<String, String> paramsls = (Map<String, String>) paramsMap.get("paramsls"); 
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Boolean resultFlag = false;
		try {
			//验签
			if(AcicpayUtlity.verify(paramsls)) {//通过
				resultFlag = true;
				//进行上账操作
				resultMap = this.frontUserHistoryDao.rechargeNoticeByAcicpay(paramsls);
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void byJinzun(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Map<String, String> paramsls = (Map<String, String>) paramsMap.get("paramsls"); 
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Boolean resultFlag = false;
		try {
			//验签
			if(JinzunUtlity.verify(paramsls)) {//通过
				resultFlag = true;
				//进行上账操作
				resultMap = this.frontUserHistoryDao.rechargeNoticeByJinzun(paramsls);
				resultMap.put("result", resultFlag);
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

	@Override
	public void byStripe(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String signHeader = paramsMap.get("signHeader") == null ? "" : paramsMap.get("signHeader").toString();
		String payload = paramsMap.get("payload") == null ? "" : paramsMap.get("payload").toString();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//获取webhook密钥
		CapitalAccount ca = this.capitalAccountDao.get(uuid);
		Map<String, Object> acParams = JSONUtils.json2map(ca.getData());
		Boolean resultFlag = false;
		//验签并获取请求内容
		try {
			Event event = Webhook.constructEvent(payload, signHeader, acParams.get("whsec").toString());
			switch(event.getType()) {
				case "checkout.session.completed"://
					System.out.println("-------------checkout.session.completed-----------------");
					resultFlag = true;
	                break;
				case "payment_intent.succeeded"://成功
					System.out.println("-------------payment_intent.succeeded-----------------");
					//进行上账操作
//					resultMap = this.frontUserHistoryDao.rechargeNoticeByJinzun(paramsls);
					PaymentIntent intent = (PaymentIntent) event
					          .getDataObjectDeserializer()
					          .getObject()
					          .get();
					String objectStr = JSONUtils.obj2json(intent);
					log.info("-----------Stripe异步通知信息-----------------"+objectStr);
					
					Map<String, String> paramsls = new HashMap<String, String>();
					paramsls.put("sourceStr", objectStr);
					Map<String,String> metaData = intent.getMetadata();//自定义传入的参数
					String orderid = metaData == null ? "" : metaData.isEmpty() ? "" : metaData.get("orderid");
					String ordernum = metaData == null ? "" : metaData.isEmpty() ? "" : metaData.get("ordernum");
					paramsls.put("orderid", orderid);
					paramsls.put("ordernum", ordernum);
					//直接入库
					this.frontUserHistoryDao.rechargeNoticeByStripe(paramsls);
					
//					if(intent.getStatus().equals("succeeded")) {
////						String orderid = intent.getMetadata()
//						Map<String,String> metaData = intent.getMetadata();//自定义传入的参数
//						String orderid = metaData == null ? "" : metaData.isEmpty() ? "" : metaData.get("orderid");
//						if(Utlity.checkStringNull(orderid)){
//							resultFlag = false;
//							break;
//						}
//						resultFlag = true;
//		                break;
//					}
					resultFlag = true;
					break;
				case "payment_intent.payment_failed"://失败
					System.out.println("-------------payment_intent.payment_failed-----------------");
					//直接充值失败，重新下单或者不处理，等超时自动关闭订单
					resultFlag = true;
	                break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultFlag = false;
		}
		resultMap.put("result", resultFlag);
		result.setData(resultMap);
		return;
	}
}
