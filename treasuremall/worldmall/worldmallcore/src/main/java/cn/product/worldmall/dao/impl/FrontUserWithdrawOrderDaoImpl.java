package cn.product.worldmall.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.worldmall.dao.FrontUserAccountDao;
import cn.product.worldmall.dao.FrontUserHistoryDao;
import cn.product.worldmall.dao.FrontUserMessageDao;
import cn.product.worldmall.dao.FrontUserScoreHistoryDao;
import cn.product.worldmall.dao.FrontUserWithdrawOrderDao;
import cn.product.worldmall.dao.SystemParamDao;
import cn.product.worldmall.entity.FrontUserAccount;
import cn.product.worldmall.entity.FrontUserHistory;
import cn.product.worldmall.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.worldmall.entity.FrontUserScoreHistory;
import cn.product.worldmall.entity.FrontUserWithdrawOrder;
import cn.product.worldmall.entity.FrontUserWithdrawOrder.FrontUserWithdrawOrderStatus;
import cn.product.worldmall.entity.SystemParam;
import cn.product.worldmall.entity.SystemParam.SystemParamKey;
import cn.product.worldmall.entity.base.Constants;
import cn.product.worldmall.mapper.FrontUserWithdrawOrderMapper;

@Component
public class FrontUserWithdrawOrderDaoImpl implements FrontUserWithdrawOrderDao{
	
	@Autowired
	private FrontUserWithdrawOrderMapper frontUserWithdrawOrderMapper;
	
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
	private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
	private FrontUserScoreHistoryDao frontUserScoreHistoryDao;
	
	@Autowired
	private FrontUserMessageDao frontUserMessageDao;
	
	@Autowired
	private SystemParamDao systemParamDao;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserWithdrawOrderMapper.getCountByParams(params);
	}
	
	@Override
	public List<FrontUserWithdrawOrder> getListByParams(Map<String, Object> params) {
		return frontUserWithdrawOrderMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="frontUserWithdrawOrder",key="'frontUserWithdrawOrder:' + #key")
	public FrontUserWithdrawOrder get(String key) {
		return frontUserWithdrawOrderMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(FrontUserWithdrawOrder frontUserWithdrawOrder) {
		return frontUserWithdrawOrderMapper.insert(frontUserWithdrawOrder);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserWithdrawOrder", key = "'frontUserWithdrawOrder:' + #key")})
	public int delete(String key) {
		return frontUserWithdrawOrderMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserWithdrawOrder", key = "'frontUserWithdrawOrder:' + #frontUserWithdrawOrder.uuid")})
	public int update(FrontUserWithdrawOrder frontUserWithdrawOrder) {
		return frontUserWithdrawOrderMapper.updateByPrimaryKey(frontUserWithdrawOrder);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "frontUserWithdrawOrder", key = "'frontUserWithdrawOrder:' + #fuwo.uuid")})
	public void check(FrontUserWithdrawOrder fuwo, FrontUserAccount fua, FrontUserHistory fuh) {
		frontUserWithdrawOrderMapper.updateByPrimaryKey(fuwo);
		frontUserAccountDao.update(fua);
		if(fuh != null){
			frontUserHistoryDao.insert(fuh);
		}
		
		//提现通知消息
		this.frontUserMessageDao.sendMessage(fuwo, fuh == null ? "" : fuh.getUuid());
		
//		if(FrontUserWithdrawOrderStatus.CHECKED.equals(fuwo.getStatus())) {//成功
//			FrontUserMessage fum = new FrontUserMessage();
//			fum.setUuid(UUID.randomUUID().toString());
//			fum.setFrontUser(fuwo.getFrontUser());
//			fum.setFrontUserShowId(fuwo.getFrontUserShowId());
//			fum.setTitle("Withdrawl Request Successful");
////			fum.setContent("您在"+Utlity.timeSpanToChinaDateString(fuwo.getCreatetime())+"提现"+fuwo.getActualAmount()+"元已到账，请注意账户余额变动信息！");
//			fum.setContent("Your withdrawal of $" + fuwo.getActualAmount() + " at " + Utlity.timeSpanToUsString(fuwo.getCreatetime()) 
//			+ " was successful,please pay attention to the account balance.");
//			fum.setSourceId(fuh.getUuid());
//			fum.setStatus(FrontUserMessageStatus.NORMAL);
//			fum.setType(FrontUserMessageType.USER_ORDER);
//			fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_WITHDRAW);
//			fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
//			
//			this.frontUserMessageDao.sendMessage(fum);
//			this.frontUserMessageDao.sendMessage(fum, SendSmsNew.TREASUREMALL_SH_TEMP_WITHDRAW);
//		} else if (FrontUserWithdrawOrderStatus.CANCEL.equals(fuwo.getStatus())) {//失败
//			FrontUserMessage fum = new FrontUserMessage();
//			fum.setUuid(UUID.randomUUID().toString());
//			fum.setFrontUser(fuwo.getFrontUser());
//			fum.setFrontUserShowId(fuwo.getFrontUserShowId());
//			fum.setTitle("Order Canceled");
////			fum.setContent("您在"+Utlity.timeSpanToChinaDateString(fuwo.getCreatetime())+"提现"+fuwo.getActualAmount()+"元，提现失败，订单关闭，请注意账户余额变动信息！");
//			fum.setContent("Your withdrawal of $" + fuwo.getActualAmount() + " at " + Utlity.timeSpanToUsString(fuwo.getCreatetime())
//			+ " was failed, the order was closed, please pay attention to the account balance.");
//			fum.setSourceId(fuwo.getUuid());
//			fum.setStatus(FrontUserMessageStatus.NORMAL);
//			fum.setType(FrontUserMessageType.USER_ORDER);
//			fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_WITHDRAW);
//			fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
//			
//			this.frontUserMessageDao.sendMessage(fum);
//			this.frontUserMessageDao.sendMessage(fum, SendSmsNew.TREASUREMALL_SH_TEMP_WITHDRAW_FAIL);
//		}
	}

	/**
	 * 提现下单
	 * 将体现金币转入账户锁定余额
	 */
	@Override
	@Transactional
	public void withdraw(FrontUserWithdrawOrder fuwo) {
		this.frontUserWithdrawOrderMapper.insert(fuwo);
		FrontUserAccount fua = this.frontUserAccountDao.get(fuwo.getFrontUser());
		if(fua != null) {
			//20200612增加积分消耗按系统设置参数生效
			SystemParam scorerate = this.systemParamDao.get(SystemParamKey.WITHDRAW_SCOREAMOUNT);//提现积分消耗
			BigDecimal scoreRate = BigDecimal.valueOf(2);
			if(scorerate != null) {
				scoreRate = BigDecimal.valueOf(Double.valueOf(scorerate.getParamValue()));
			}
			
			fua.setBalance(fua.getBalance().subtract(fuwo.getReduceDAmount()));
			fua.setBalanceLock(fua.getBalanceLock().add(fuwo.getReduceDAmount()));
			BigDecimal scoreBalance = fua.getScoreBalance();
			BigDecimal scoreAmount = fuwo.getAmount().multiply(scoreRate).setScale(0, BigDecimal.ROUND_UP);//计算提现额度1元1积分 保留整数位 小数向上取整
			boolean flagScore = false;
			if(scoreBalance.compareTo(BigDecimal.ZERO) > 0) {//有积分，免费提现1元1积分
				flagScore = true;
				if(scoreAmount.compareTo(scoreBalance) > 0) {
					scoreAmount = scoreBalance;
					scoreBalance = BigDecimal.ZERO;
				} else {
					scoreBalance = scoreBalance.subtract(scoreAmount);//免费提现额度（积分）充足，则扣减积分
				}
			}
			//20200518增加积分消费记录
			if(flagScore) {
				FrontUserScoreHistory fush = new FrontUserScoreHistory();
				fush.setUuid(UUID.randomUUID().toString());
				fush.setFrontUser(fua.getFrontUser());
				fush.setFrontUserShowId(fuwo.getFrontUserShowId());
				fush.setOrderNum(fuwo.getOrderNum());
				fush.setOrderId(fuwo.getUuid());
				fush.setType(FrontUserHistoryType.USER_SUB);
				fush.setOrderType(Constants.ORDER_TYPE_USER_WITHDRAW);
				fush.setsAmount(scoreAmount);
				fush.setScoreBalanceBefore(fua.getScoreBalance());
				fush.setScoreBalanceAfter(fua.getScoreBalance().subtract(scoreAmount));
				fush.setReason(Constants.orderTypeTemplateInfoMap.get(Constants.ORDER_TYPE_USER_WITHDRAW));
				fush.setCreatetime(fuwo.getCreatetime());
				fush.setRemark("User withdrawals will deduct the handling fee, and withdrawals failure will not be returned!");
				this.frontUserScoreHistoryDao.insert(fush);
			}
			
			fua.setScoreBalance(scoreBalance);
			this.frontUserAccountDao.update(fua);
		}
	}

	@Override
	public BigDecimal getAmountByParams(String dateStr) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("starttime", dateStr + " 00:00:00");
		searchMap.put("endtime", dateStr + " 23:59:59");
		searchMap.put("status", FrontUserWithdrawOrderStatus.CHECKED);
		BigDecimal amount = this.frontUserWithdrawOrderMapper.getAmountByParams(searchMap);
		if(amount == null){
			amount = BigDecimal.ZERO;
		}
		return amount;
	}
}
