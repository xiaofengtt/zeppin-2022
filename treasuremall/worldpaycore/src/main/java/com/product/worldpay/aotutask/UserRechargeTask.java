package com.product.worldpay.aotutask;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paypal.http.HttpResponse;
import com.paypal.orders.Order;
import com.product.worldpay.dao.ChannelAccountDao;
import com.product.worldpay.dao.ChannelDao;
import com.product.worldpay.dao.NoticeInfoDao;
import com.product.worldpay.dao.UserRechargeDao;
import com.product.worldpay.entity.Channel;
import com.product.worldpay.entity.ChannelAccount;
import com.product.worldpay.entity.UserRecharge;
import com.product.worldpay.entity.UserRecharge.UserRechargeStatus;
import com.product.worldpay.locker.Locker;
import com.product.worldpay.util.JSONUtils;
import com.product.worldpay.util.Utlity;
import com.product.worldpay.util.paypal.PaypalAccount;
import com.product.worldpay.util.paypal.PaypalClient;

@Component
public class UserRechargeTask {
	
	@Autowired
	private UserRechargeDao userRechargeDao;
	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private ChannelAccountDao channelAccountDao;
	
	@Autowired
	private NoticeInfoDao noticeInfoDao;
	
	@Autowired
    private Locker locker;
	
	/**
	 * 处理通知回调
	 */
//	@Scheduled(cron="0/30 * *  * * * ")
//	public void noticeProcess() {
//		Map<String, Object> searchMap = new HashMap<String, Object>();
//		searchMap.put("status", NoticeInfoStatus.NORMAL);
//		searchMap.put("type", NoticeInfoType.RECHARGE);
//		List<NoticeInfo> list = this.noticeInfoDao.getListByParams(searchMap);
//		
//		for(NoticeInfo notice : list){
//			UserRecharge ur = this.userRechargeDao.getByOrderNum(notice.getOrderNum());
//			if(ur == null){
//				notice.setStatus(NoticeInfoStatus.FAIL);
//				this.noticeInfoDao.update(notice);
//				continue;
//			}
//			
//			Map<String, Object> dataMap = JSONUtils.json2map(notice.getData());
//			Channel channel = this.channelDao.get(ur.getChannel());
//			if(Utlity.CHANNEL_RECHARGE_PAYPAL.equals(channel.getCode())){
//				//支付宝对公
//				String result = dataMap.get("status").toString();
//				if(AliUtlity.TRADE_FINISHED.equals(result) || AliUtlity.TRADE_SUCCESS.equals(result)){
//					//付款成功
//					try{
//						locker.lock(ZkCuratorLocker.ACCOUNT_UPDATE_LOCK, ()-> {
//							this.userRechargeDao.processOrder(ur, notice, UserRechargeStatus.SUCCESS);
//						});
//						continue;
//					} catch (Exception e) {
//						//处理失败
//						this.userRechargeDao.processOrder(ur, notice, UserRechargeStatus.FAIL);
//						continue;
//					}
//				}else if(AliUtlity.TRADE_CLOSED.equals(result)){
//					//交易关闭
//					this.userRechargeDao.processOrder(ur, notice, UserRechargeStatus.CLOSE);
//					continue;
//				}
//			}else{
//				//渠道未知
//				notice.setStatus(NoticeInfoStatus.FAIL);
//				this.noticeInfoDao.update(notice);
//				continue;
//			}
//		}
//	}
	
	/**
	 * 处理超时订单
	 */
//	@Scheduled(cron="1 * *  * * * ")
	public void timeoutProcess() {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("status", UserRechargeStatus.NORMAL);
		searchMap.put("timeout", new Timestamp(System.currentTimeMillis()));
		
		List<UserRecharge> urList = this.userRechargeDao.getListByParams(searchMap);
		for(UserRecharge ur : urList){
			Channel channel = this.channelDao.get(ur.getChannel());
			ChannelAccount ca = this.channelAccountDao.get(ur.getChannelAccount());
			if(Utlity.CHANNEL_RECHARGE_PAYPAL.equals(channel.getCode())){
				//paypal
				Map<String, Object> acParams = JSONUtils.json2map(ca.getData());
				PaypalAccount paypalAccount = new PaypalAccount(acParams.get("clientId").toString(), acParams.get("secret").toString(), ca.getTransferUrl());
				
				Map<String, Object> transDataMap = JSONUtils.json2map(ur.getTransData());
				try {
					HttpResponse<Order> response = PaypalClient.queryOrder(paypalAccount, transDataMap.get("payperId").toString());
					if("COMPLETED".equals(response.result().status())){
						this.userRechargeDao.processOrder(ur, null, UserRechargeStatus.SUCCESS);
					}else{
						this.userRechargeDao.processOrder(ur, null, UserRechargeStatus.CLOSE);
						continue;
					}
				} catch (Exception e) {
					this.userRechargeDao.processOrder(ur, null, UserRechargeStatus.CLOSE);
					continue;
				}
			}else{
				this.userRechargeDao.processOrder(ur, null, UserRechargeStatus.CLOSE);
				continue;
			}
		}
	}
}