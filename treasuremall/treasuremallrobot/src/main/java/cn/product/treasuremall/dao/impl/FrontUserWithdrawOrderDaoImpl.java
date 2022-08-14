package cn.product.treasuremall.dao.impl;

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

import cn.product.treasuremall.dao.FrontUserAccountDao;
import cn.product.treasuremall.dao.FrontUserHistoryDao;
import cn.product.treasuremall.dao.FrontUserScoreHistoryDao;
import cn.product.treasuremall.dao.FrontUserWithdrawOrderDao;
import cn.product.treasuremall.dao.SystemParamDao;
import cn.product.treasuremall.entity.FrontUserAccount;
import cn.product.treasuremall.entity.FrontUserHistory;
import cn.product.treasuremall.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.treasuremall.entity.FrontUserScoreHistory;
import cn.product.treasuremall.entity.FrontUserWithdrawOrder;
import cn.product.treasuremall.entity.FrontUserWithdrawOrder.FrontUserWithdrawOrderStatus;
import cn.product.treasuremall.entity.SystemParam;
import cn.product.treasuremall.entity.SystemParam.SystemParamKey;
import cn.product.treasuremall.entity.base.Constants;
import cn.product.treasuremall.mapper.FrontUserWithdrawOrderMapper;

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
				fush.setRemark("用户提现扣抵手续费，提现失败不返还！");
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
