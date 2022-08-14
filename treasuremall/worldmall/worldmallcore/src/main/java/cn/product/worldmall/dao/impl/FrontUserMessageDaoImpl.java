package cn.product.worldmall.dao.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.worldmall.dao.FrontDeviceTokenDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserMessageDao;
import cn.product.worldmall.dao.SystemParamDao;
import cn.product.worldmall.entity.FrontDeviceToken;
import cn.product.worldmall.entity.FrontDeviceTokenMessage;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUser.FrontUserType;
import cn.product.worldmall.entity.FrontUserMessage;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageSourceType;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageStatus;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageType;
import cn.product.worldmall.entity.FrontUserRechargeOrder;
import cn.product.worldmall.entity.FrontUserRechargeOrder.FrontUserRechargeOrderStatus;
import cn.product.worldmall.entity.FrontUserWithdrawOrder;
import cn.product.worldmall.entity.FrontUserWithdrawOrder.FrontUserWithdrawOrderStatus;
import cn.product.worldmall.entity.SystemParam;
import cn.product.worldmall.entity.SystemParam.SystemParamKey;
import cn.product.worldmall.mapper.FrontUserMessageMapper;
import cn.product.worldmall.rabbit.send.RabbitSenderService;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.SendSmsNew;
import cn.product.worldmall.util.Utlity;

@Component
public class FrontUserMessageDaoImpl implements FrontUserMessageDao{
	
	@Autowired
	private FrontUserMessageMapper frontUserMessageMapper;
	
	@Autowired
	private RabbitSenderService rabbitSenderService;
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private SystemParamDao systemParamDao;
	
	@Autowired
	private FrontDeviceTokenDao frontDeviceTokenDao;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserMessageMapper.getCountByParams(params);
	}
	
	@Override
	public List<FrontUserMessage> getListByParams(Map<String, Object> params) {
		return frontUserMessageMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="frontUserMessage",key="'frontUserMessage:' + #key")
	public FrontUserMessage get(String key) {
		return frontUserMessageMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(FrontUserMessage frontUserMessage) {
		return frontUserMessageMapper.insert(frontUserMessage);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserMessage", key = "'frontUserMessage:' + #key")})
	public int delete(String key) {
		return frontUserMessageMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserMessage", key = "'frontUserMessage:' + #frontUserMessage.uuid")})
	public int update(FrontUserMessage frontUserMessage) {
		return frontUserMessageMapper.updateByPrimaryKey(frontUserMessage);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "frontUserMessage", allEntries=true)})
	public void readAll(List<FrontUserMessage> updateList) {
		if(updateList != null && updateList.size() > 0) {
			for(FrontUserMessage fum : updateList) {
				this.frontUserMessageMapper.updateByPrimaryKey(fum);
			}
			
		}
	}

	@Override
	public void sendMessage(FrontUserMessage fum) {
		this.rabbitSenderService.messageStartMessageSend(fum);
	}

	@Override
	public void sendMessage(FrontUserMessage fum, String temp_id) {
		//暂时不发送通知短信
//		this.rabbitSenderService.smsStartMessageSend(fum, temp_id);
		//改为发送SNS通知
		FrontDeviceToken fdt = this.frontDeviceTokenDao.getByFrontUser(fum.getFrontUser());
		FrontDeviceTokenMessage fdtm = new FrontDeviceTokenMessage();
		fdtm.setUuid(UUID.randomUUID().toString());
		fdtm.setCreatetime(new Timestamp(System.currentTimeMillis()));
		fdtm.setType(fum.getSourceType());
		fdtm.setStatus(FrontUserMessageStatus.NORMAL);
		fdtm.setTitle(fum.getTitle());
		fdtm.setContent(fum.getContent());
		fdtm.setFrontDeviceToken(fdt.getUuid());
		fdtm.setFlagUserSend(false);
		this.rabbitSenderService.messageStartSnsMessageSend(fdtm);
	}

	@Override
	public void sendMessage(List<FrontUserMessage> fumList, String temp_id) {
		if(fumList != null && fumList.size() > 0) {
			for(FrontUserMessage fum : fumList) {
				//判断是否是机器人，机器人不予发送中将短信通知
				FrontUser fu = this.frontUserDao.get(fum.getFrontUser());
				if(fu != null && FrontUserType.NORMAL.equals(fu.getType())) {
					//暂时不发送通知短信
//					this.rabbitSenderService.smsStartMessageSend(fum, temp_id);
					this.sendMessage(fum, temp_id);
				}
			}
			
		}
	}

	@Override
	@Transactional
	public void sendMessage(List<FrontUserMessage> fumList) {
		if(fumList != null && fumList.size() > 0) {
			for(FrontUserMessage fum : fumList) {
//				this.rabbitSenderService.messageStartMessageSend(fum);
				this.frontUserMessageMapper.insert(fum);
			}
			
		}
	}

	@Override
	@Transactional
	public void sendMessage(FrontUserRechargeOrder furo, String sourceId) {
		//获取货币和符号
		SystemParam spcurrency = this.systemParamDao.get(SystemParamKey.INTERNATIONAL_CURRENCIES_SYMBOL);
		String symbol = "";
		if(spcurrency != null) {
			Map<String, Object> currencies = JSONUtils.json2map(spcurrency.getParamValue());
			if(currencies != null && !currencies.isEmpty()) {
				symbol = currencies.get(furo.getCurrency()) == null ? "" : currencies.get(furo.getCurrency()).toString();
			}
		}
		if(Utlity.checkStringNull(symbol)) {
			symbol = furo.getCurrency();
		}
		//充值通知消息
		FrontUserMessage fum = new FrontUserMessage();
		if(FrontUserRechargeOrderStatus.CHECKED.equals(furo.getStatus())) {
			fum.setUuid(UUID.randomUUID().toString());
			fum.setFrontUser(furo.getFrontUser());
			fum.setFrontUserShowId(furo.getFrontUserShowId());
			fum.setTitle("Top-up Successful");
//			fum.setContent("您在"+Utlity.timeSpanToChinaDateString(furo.getCreatetime())+"充值"+furo.getAmount()+"元已到账，请注意账户余额变动信息！");
			fum.setContent("Your recharge of " + symbol + furo.getCurrencyAmount() 
//			+ " at " + Utlity.timeSpanToUsString(furo.getCreatetime()) 
								+ " was successful,please pay attention to the account balance.");
			fum.setSourceId(sourceId);
			fum.setStatus(FrontUserMessageStatus.NORMAL);
			fum.setType(FrontUserMessageType.USER_ORDER);
			fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_RECHARGE);
			fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
		} else if(FrontUserRechargeOrderStatus.CANCEL.equals(furo.getStatus()) || FrontUserRechargeOrderStatus.CLOSE.equals(furo.getStatus())) {
			fum.setUuid(UUID.randomUUID().toString());
			fum.setFrontUser(furo.getFrontUser());
			fum.setFrontUserShowId(furo.getFrontUserShowId());
			fum.setTitle("Order Canceled");
			fum.setContent("Your recharge of " + symbol + furo.getCurrencyAmount() 
//			+ " at " + Utlity.timeSpanToUsString(furo.getCreatetime()) 
								+ " was failed, the order was closed.");
			fum.setSourceId(furo.getUuid());
			fum.setStatus(FrontUserMessageStatus.NORMAL);
			fum.setType(FrontUserMessageType.USER_ORDER);
			fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_RECHARGE);
			fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
		}
		this.rabbitSenderService.messageStartMessageSend(fum);
//		this.rabbitSenderService.smsStartMessageSend(fum, SendSmsNew.TREASUREMALL_SH_TEMP_RECHARGE);
		this.sendMessage(fum, SendSmsNew.TREASUREMALL_SH_TEMP_RECHARGE);
	}

	@Override
	@Transactional
	public void sendMessage(FrontUserWithdrawOrder fuwo, String sourceId) {
		
		//获取货币和符号
		SystemParam spcurrency = this.systemParamDao.get(SystemParamKey.INTERNATIONAL_CURRENCIES_SYMBOL);
		String symbol = "";
		if(spcurrency != null) {
			Map<String, Object> currencies = JSONUtils.json2map(spcurrency.getParamValue());
			if(currencies != null && !currencies.isEmpty()) {
				symbol = currencies.get(fuwo.getCurrency()) == null ? "" : currencies.get(fuwo.getCurrency()).toString();
			}
		}
		if(Utlity.checkStringNull(symbol)) {
			symbol = fuwo.getCurrency();
		}
		
		//提现通知消息
		FrontUserMessage fum = new FrontUserMessage();
		if(FrontUserWithdrawOrderStatus.CHECKED.equals(fuwo.getStatus())) {//成功
			
			fum.setUuid(UUID.randomUUID().toString());
			fum.setFrontUser(fuwo.getFrontUser());
			fum.setFrontUserShowId(fuwo.getFrontUserShowId());
			fum.setTitle("Withdrawl Request Successful");
//					fum.setContent("您在"+Utlity.timeSpanToChinaDateString(fuwo.getCreatetime())+"提现"+fuwo.getActualAmount()+"元已到账，请注意账户余额变动信息！");
			fum.setContent("Your withdrawal of " + symbol + fuwo.getCurrencyAmount() 
//			+ " at " + Utlity.timeSpanToUsString(fuwo.getCreatetime()) 
			+ " was successful,please pay attention to the account balance.");
			fum.setSourceId(sourceId);
			fum.setStatus(FrontUserMessageStatus.NORMAL);
			fum.setType(FrontUserMessageType.USER_ORDER);
			fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_WITHDRAW);
			fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			this.rabbitSenderService.messageStartMessageSend(fum);
//			this.rabbitSenderService.smsStartMessageSend(fum, SendSmsNew.TREASUREMALL_SH_TEMP_WITHDRAW);
			this.sendMessage(fum, SendSmsNew.TREASUREMALL_SH_TEMP_WITHDRAW);
		} else if (FrontUserWithdrawOrderStatus.CANCEL.equals(fuwo.getStatus())) {//失败
			fum.setUuid(UUID.randomUUID().toString());
			fum.setFrontUser(fuwo.getFrontUser());
			fum.setFrontUserShowId(fuwo.getFrontUserShowId());
			fum.setTitle("Order Canceled");
//					fum.setContent("您在"+Utlity.timeSpanToChinaDateString(fuwo.getCreatetime())+"提现"+fuwo.getActualAmount()+"元，提现失败，订单关闭，请注意账户余额变动信息！");
			fum.setContent("Your withdrawal of " + symbol + fuwo.getCurrencyAmount() 
//			+ " at " + Utlity.timeSpanToUsString(fuwo.getCreatetime())
			+ " was failed, the order was closed, please pay attention to the account balance.");
			fum.setSourceId(fuwo.getUuid());
			fum.setStatus(FrontUserMessageStatus.NORMAL);
			fum.setType(FrontUserMessageType.USER_ORDER);
			fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_WITHDRAW);
			fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			this.rabbitSenderService.messageStartMessageSend(fum);
			this.sendMessage(fum, SendSmsNew.TREASUREMALL_SH_TEMP_WITHDRAW_FAIL);
//			this.rabbitSenderService.smsStartMessageSend(fum, SendSmsNew.TREASUREMALL_SH_TEMP_WITHDRAW_FAIL);
		}
	}
}
