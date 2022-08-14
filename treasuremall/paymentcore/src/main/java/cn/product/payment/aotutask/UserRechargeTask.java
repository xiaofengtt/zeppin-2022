package cn.product.payment.aotutask;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.product.payment.dao.ChannelAccountDao;
import cn.product.payment.dao.ChannelDao;
import cn.product.payment.dao.NoticeInfoDao;
import cn.product.payment.dao.UserRechargeDao;
import cn.product.payment.entity.Channel;
import cn.product.payment.entity.ChannelAccount;
import cn.product.payment.entity.NoticeInfo;
import cn.product.payment.entity.NoticeInfo.NoticeInfoStatus;
import cn.product.payment.entity.NoticeInfo.NoticeInfoType;
import cn.product.payment.entity.UserRecharge;
import cn.product.payment.entity.UserRecharge.UserRechargeStatus;
import cn.product.payment.locker.Locker;
import cn.product.payment.locker.ZkCuratorLocker;
import cn.product.payment.util.HttpClientUtil;
import cn.product.payment.util.JSONUtils;
import cn.product.payment.util.Utlity;
import cn.product.payment.util.alipay.AliUtlity;
import cn.product.payment.util.wechat.WechatUtlity;

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
	public void noticeProcess() {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("status", NoticeInfoStatus.NORMAL);
		searchMap.put("type", NoticeInfoType.RECHARGE);
		List<NoticeInfo> list = this.noticeInfoDao.getListByParams(searchMap);
		
		for(NoticeInfo notice : list){
			UserRecharge ur = this.userRechargeDao.getByOrderNum(notice.getOrderNum());
			if(ur == null){
				notice.setStatus(NoticeInfoStatus.FAIL);
				this.noticeInfoDao.update(notice);
				continue;
			}
			
			Map<String, Object> dataMap = JSONUtils.json2map(notice.getData());
			Channel channel = this.channelDao.get(ur.getChannel());
			if(Utlity.CHANNEL_RECHARGE_ALIPAY_COMPANY.equals(channel.getCode())){
				//支付宝对公
				String result = dataMap.get("status").toString();
				if(AliUtlity.TRADE_FINISHED.equals(result) || AliUtlity.TRADE_SUCCESS.equals(result)){
					//付款成功
					try{
						locker.lock(ZkCuratorLocker.ACCOUNT_UPDATE_LOCK, ()-> {
							this.userRechargeDao.processOrder(ur, notice, UserRechargeStatus.SUCCESS);
						});
						continue;
					} catch (Exception e) {
						//处理失败
						this.userRechargeDao.processOrder(ur, notice, UserRechargeStatus.FAIL);
						continue;
					}
				}else if(AliUtlity.TRADE_CLOSED.equals(result)){
					//交易关闭
					this.userRechargeDao.processOrder(ur, notice, UserRechargeStatus.CLOSE);
					continue;
				}
			}else if(Utlity.CHANNEL_RECHARGE_WECHAT_COMPANY.equals(channel.getCode())){
				//微信对公
				String result = dataMap.get("status").toString();
				if("SUCCESS".equals(result)){
					//付款成功
					try{
						locker.lock(ZkCuratorLocker.ACCOUNT_UPDATE_LOCK, ()-> {
							this.userRechargeDao.processOrder(ur, notice, UserRechargeStatus.SUCCESS);
						});
						continue;
					} catch (Exception e) {
						//处理失败
						this.userRechargeDao.processOrder(ur, notice, UserRechargeStatus.FAIL);
						continue;
					}
				}else if(AliUtlity.TRADE_CLOSED.equals(result)){
					//交易关闭
					this.userRechargeDao.processOrder(ur, notice, UserRechargeStatus.CLOSE);
					continue;
				}
			}else{
				//渠道未知
				notice.setStatus(NoticeInfoStatus.FAIL);
				this.noticeInfoDao.update(notice);
				continue;
			}
		}
	}
	
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
			if(Utlity.CHANNEL_RECHARGE_ALIPAY_COMPANY.equals(channel.getCode())){
				//支付宝对公
				Map<String, Object> acParams = JSONUtils.json2map(ca.getData());
				acParams.put("pid", ca.getAccountNum());
				
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("acParamsStr", JSONUtils.obj2json(acParams));
				paramMap.put("orderNum", ur.getOrderNum());
				
				//获取支付宝订单信息
				String resultStr = null;
				try {
					resultStr = HttpClientUtil.post(ca.getTransferUrl() + AliUtlity.ALI_TRANSFER_QUERY_ORDER_API, paramMap);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				Map<String, Object> resultMap = JSONUtils.json2map(resultStr);
				if(!"SUCCESS".equals(resultMap.get("status").toString())){
					continue;
				}
				
				Map<String, Object> aliResult = JSONUtils.json2map(resultMap.get("data").toString());
				if ((boolean)aliResult.get("request") && (boolean)aliResult.get("result")) {
					//支付宝订单获取成功
					try{
						Map<String, Object> dataMap = JSONUtils.json2map(aliResult.get("response").toString());
						String result = dataMap.get("trade_status").toString();
						if(AliUtlity.TRADE_FINISHED.equals(result) || AliUtlity.TRADE_SUCCESS.equals(result)){
							//如果订单已成功 补充上账
							locker.lock(ZkCuratorLocker.ACCOUNT_UPDATE_LOCK, ()-> {
								this.userRechargeDao.processOrder(ur, null, UserRechargeStatus.SUCCESS);
							});
							continue;
						}else{
							//否则关闭订单
							this.userRechargeDao.processOrder(ur, null, UserRechargeStatus.CLOSE);
							continue;
						}
					}catch (Exception e){
						//出错关闭订单
						this.userRechargeDao.processOrder(ur, null, UserRechargeStatus.CLOSE);
						continue;
					}
				} else {
					//否则关闭订单
					this.userRechargeDao.processOrder(ur, null, UserRechargeStatus.CLOSE);
					continue;
				}
			}else if(Utlity.CHANNEL_RECHARGE_WECHAT_COMPANY.equals(channel.getCode())){
				//微信对公
				Map<String, Object> acParams = JSONUtils.json2map(ca.getData());
				acParams.put("mchid", ca.getAccountNum());
				
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("acParamsStr", JSONUtils.obj2json(acParams));
				paramMap.put("orderNum", ur.getOrderNum());
				
				//获取微信订单信息
				String resultStr = null;
				try {
					resultStr = HttpClientUtil.post(ca.getTransferUrl() + WechatUtlity.WECHAT_TRANSFER_QUERY_ORDER_API, paramMap);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				Map<String, Object> resultMap = JSONUtils.json2map(resultStr);
				if(!"SUCCESS".equals(resultMap.get("status").toString())){
					continue;
				}
				
				Map<String, Object> wechatResult = JSONUtils.json2map(resultMap.get("data").toString());
				Map<String, Object> wechatData = JSONUtils.json2map(wechatResult.get("response").toString());
				if ("SUCCESS".equals(wechatData.get("result_code"))) {
					//微信订单获取成功
					try{
						String result = wechatData.get("trade_state").toString();
						if("SUCCESS".equals(result)){
							//如果订单已成功 补充上账
							locker.lock(ZkCuratorLocker.ACCOUNT_UPDATE_LOCK, ()-> {
								this.userRechargeDao.processOrder(ur, null, UserRechargeStatus.SUCCESS);
							});
							continue;
						}else{
							//否则关闭订单
							this.userRechargeDao.processOrder(ur, null, UserRechargeStatus.CLOSE);
							continue;
						}
					}catch (Exception e){
						//出错关闭订单
						this.userRechargeDao.processOrder(ur, null, UserRechargeStatus.CLOSE);
						continue;
					}
				} else {
					//否则关闭订单
					this.userRechargeDao.processOrder(ur, null, UserRechargeStatus.CLOSE);
					continue;
				}
			}else if(Utlity.CHANNEL_RECHARGE_ALIPAY_CODE.equals(channel.getCode()) || Utlity.CHANNEL_RECHARGE_WECHAT_CODE.equals(channel.getCode())){
				//支付宝 微信 扫码充值 直接关闭
				this.userRechargeDao.processOrder(ur, null, UserRechargeStatus.CLOSE);
			}else if(Utlity.CHANNEL_RECHARGE_BANKCARD_PERSON.equals(channel.getCode()) || Utlity.CHANNEL_RECHARGE_BANKCARD_COMPANY.equals(channel.getCode())
					|| Utlity.CHANNEL_RECHARGE_ALIPAY_BANKCARD.equals(channel.getCode())){
				//银行卡交易 直接关闭
				this.userRechargeDao.processOrder(ur, null, UserRechargeStatus.CLOSE);
			}else{
				//脏记录
				this.userRechargeDao.processOrder(ur, null, UserRechargeStatus.CLOSE);
				continue;
			}
		}
	}
}