package cn.product.payment.aotutask;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.product.payment.entity.Channel;
import cn.product.payment.entity.ChannelAccount;
import cn.product.payment.entity.NoticeInfo;
import cn.product.payment.entity.NoticeInfo.NoticeInfoStatus;
import cn.product.payment.entity.NoticeInfo.NoticeInfoType;
import cn.product.payment.entity.UserRecharge;
import cn.product.payment.entity.UserRecharge.UserRechargeStatus;
import cn.product.payment.locker.Locker;
import cn.product.payment.locker.ZkCuratorLocker;
import cn.product.payment.service.ChannelAccountService;
import cn.product.payment.service.ChannelService;
import cn.product.payment.service.NoticeInfoService;
import cn.product.payment.service.UserRechargeService;
import cn.product.payment.util.JSONUtils;
import cn.product.payment.util.Utlity;
import cn.product.payment.util.alipay.AliUtlity;

@Component
public class UserRechargeTask {
	
	@Autowired
	private UserRechargeService userRechargeService;
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private ChannelAccountService channelAccountService;
	
	@Autowired
	private NoticeInfoService noticeInfoService;
	
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
		List<NoticeInfo> list = this.noticeInfoService.getListByParams(searchMap);
		
		for(NoticeInfo notice : list){
			UserRecharge ur = this.userRechargeService.getByOrderNum(notice.getOrderNum());
			if(ur == null){
				notice.setStatus(NoticeInfoStatus.FAIL);
				this.noticeInfoService.update(notice);
				continue;
			}
			
			Map<String, Object> dataMap = JSONUtils.json2map(notice.getData());
			Channel channel = this.channelService.get(ur.getChannel());
			if(Utlity.CHANNEL_RECHARGE_ALIPAY_COMPANY.equals(channel.getCode())){
				//支付宝对公
				String result = dataMap.get("status").toString();
				if(AliUtlity.TRADE_FINISHED.equals(result) || AliUtlity.TRADE_SUCCESS.equals(result)){
					//付款成功
					try{
						locker.lock(ZkCuratorLocker.ACCOUNT_UPDATE_LOCK, ()-> {
							this.userRechargeService.processOrder(ur, notice, UserRechargeStatus.SUCCESS);
						});
						continue;
					} catch (Exception e) {
						//处理失败
						this.userRechargeService.processOrder(ur, notice, UserRechargeStatus.FAIL);
						continue;
					}
				}else if(AliUtlity.TRADE_CLOSED.equals(result)){
					//交易关闭
					this.userRechargeService.processOrder(ur, notice, UserRechargeStatus.CLOSE);
					continue;
				}
			}else{
				//渠道未知
				notice.setStatus(NoticeInfoStatus.FAIL);
				this.noticeInfoService.update(notice);
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
		
		List<UserRecharge> urList = this.userRechargeService.getListByParams(searchMap);
		for(UserRecharge ur : urList){
			Channel channel = this.channelService.get(ur.getChannel());
			ChannelAccount ca = this.channelAccountService.get(ur.getChannelAccount());
			if(Utlity.CHANNEL_RECHARGE_ALIPAY_COMPANY.equals(channel.getCode())){
				//支付宝对公
				Map<String, Object> acParams = JSONUtils.json2map(ca.getData());
				acParams.put("pid", ca.getAccountNum());
				
				Map<String, Object> aliResult = AliUtlity.getOrderInfo(acParams, ur.getOrderNum());
				if ((boolean)aliResult.get("request") && (boolean)aliResult.get("result")) {
					try{
						Map<String, Object> dataMap = JSONUtils.json2map(aliResult.get("response").toString());
						String result = dataMap.get("trade_status").toString();
						if(AliUtlity.TRADE_FINISHED.equals(result) || AliUtlity.TRADE_SUCCESS.equals(result)){
							locker.lock(ZkCuratorLocker.ACCOUNT_UPDATE_LOCK, ()-> {
								this.userRechargeService.processOrder(ur, null, UserRechargeStatus.SUCCESS);
							});
							continue;
						}else{
							this.userRechargeService.processOrder(ur, null, UserRechargeStatus.CLOSE);
							continue;
						}
					}catch (Exception e){
						this.userRechargeService.processOrder(ur, null, UserRechargeStatus.CLOSE);
						continue;
					}
				} else {
					this.userRechargeService.processOrder(ur, null, UserRechargeStatus.CLOSE);
					continue;
				}
			}else{
				this.userRechargeService.processOrder(ur, null, UserRechargeStatus.CLOSE);
				continue;
			}
		}
	}
}