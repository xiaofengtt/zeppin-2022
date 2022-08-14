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
import cn.product.treasuremall.dao.GoodsDao;
import cn.product.treasuremall.dao.LuckygameGoodsIssueDao;
import cn.product.treasuremall.dao.SystemParamDao;
import cn.product.treasuremall.dao.WinningInfoDao;
import cn.product.treasuremall.dao.WinningInfoReceiveDao;
import cn.product.treasuremall.entity.FrontUserAccount;
import cn.product.treasuremall.entity.FrontUserHistory;
import cn.product.treasuremall.entity.FrontUserScoreHistory;
import cn.product.treasuremall.entity.FrontUserScoreHistory.FrontUserScoreHistoryType;
import cn.product.treasuremall.entity.Goods;
import cn.product.treasuremall.entity.SystemParam;
import cn.product.treasuremall.entity.SystemParam.SystemParamKey;
import cn.product.treasuremall.entity.WinningInfo;
import cn.product.treasuremall.entity.WinningInfoReceive;
import cn.product.treasuremall.entity.WinningInfoReceive.WinningInfoReceiveType;
import cn.product.treasuremall.entity.base.Constants;
import cn.product.treasuremall.mapper.WinningInfoReceiveMapper;
import cn.product.treasuremall.util.Utlity;

@Component
public class WinningInfoReceiveDaoImpl implements WinningInfoReceiveDao{
	
	@Autowired
	private WinningInfoReceiveMapper winningInfoReceiveMapper;
	
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
	private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
	private WinningInfoDao winningInfoDao;
	
	@Autowired
	private FrontUserScoreHistoryDao frontUserScoreHistoryDao;
	
	@Autowired
	private SystemParamDao systemParamDao;
	
	@Autowired
	private LuckygameGoodsIssueDao luckygameGoodsIssueDao;
	
	@Autowired
	private GoodsDao goodsDao;
	
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return winningInfoReceiveMapper.getCountByParams(params);
	}
	
	@Override
	public List<WinningInfoReceive> getListByParams(Map<String, Object> params) {
		return winningInfoReceiveMapper.getListByParams(params);
	}
	
	@Override
	@Cacheable(cacheNames="winningInfoReceive",key="'winningInfoReceive:' + #key")
	public WinningInfoReceive get(String key) {
		return winningInfoReceiveMapper.selectByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "winningInfoReceive", key = "'winningInfoReceive:' + #winningInfoReceive.winningInfo")})
	public int insert(WinningInfoReceive winningInfoReceive) {
		return winningInfoReceiveMapper.insert(winningInfoReceive);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "winningInfoReceive", key = "'winningInfoReceive:' + #winningInfo")})
	public int delete(String key) {
		return winningInfoReceiveMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "winningInfoReceive", key = "'winningInfoReceive:' + #winningInfoReceive.winningInfo")})
	public int update(WinningInfoReceive winningInfoReceive) {
		return winningInfoReceiveMapper.updateByPrimaryKey(winningInfoReceive);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "winningInfoReceive", key = "'winningInfoReceive:' + #winningInfoReceive.winningInfo")})
	public void insertReceive(FrontUserAccount fua, FrontUserHistory fuh, WinningInfoReceive winningInfoReceive, WinningInfo wi) {
		this.winningInfoDao.update(wi);
		this.winningInfoReceiveMapper.insert(winningInfoReceive);
		if(fuh != null) {
			this.frontUserHistoryDao.insert(fuh);			
		}
		
		//20200623增加兑换实物扣除积分的规则
		if(WinningInfoReceiveType.ENTITY.equals(winningInfoReceive.getType())) {
			//20200624增加积分消耗按系统设置参数生效
			/*
			 * 1，增加一个实物兑换积分扣除比率的系统参数 M
				2，积分充足时，积分扣除额 J = 商品法币价格 G*M
				3，积分不足时，积分扣除额 J = 用户积分余额，补充扣除金币数=G-J/M
				4，积分为0时，补充扣除金币数 = G
				5，金币余额不足时，提示不满足兑换实物条件，终止本次兑换
			 */
			SystemParam scorerate = this.systemParamDao.get(SystemParamKey.DELIVERY_SCOREAMOUNT);//提现积分消耗
			BigDecimal scoreRate = BigDecimal.valueOf(2);
			if(scorerate != null) {
				scoreRate = BigDecimal.valueOf(Double.valueOf(scorerate.getParamValue()));
			}
			
			//计算所需积分
			BigDecimal scoreAmount = BigDecimal.ZERO;
			BigDecimal scoreBalance = fua.getScoreBalance();
			BigDecimal dAmount = wi.getDealPrice();//奖品价值金币数
			scoreAmount = dAmount.multiply(scoreRate).setScale(0, BigDecimal.ROUND_UP);//计算提现额度1元1积分 保留整数位 小数向上取整;
			if(scoreBalance.compareTo(BigDecimal.ZERO) > 0) {
				if(scoreBalance.compareTo(scoreAmount) >= 0) {//积分充足扣除积分
					scoreBalance = scoreBalance.subtract(scoreAmount);
					dAmount = BigDecimal.ZERO;
				} else {
					scoreAmount = scoreBalance;
					scoreBalance = BigDecimal.ZERO;
					dAmount = dAmount.subtract(scoreAmount.divide(scoreRate).setScale(0, BigDecimal.ROUND_UP));
				}
			} else {
				scoreAmount = scoreBalance;
			}
			
			if(scoreAmount.compareTo(BigDecimal.ZERO) > 0) {
				FrontUserScoreHistory fush = new FrontUserScoreHistory();
				fush.setUuid(UUID.randomUUID().toString());
				fush.setFrontUser(fua.getFrontUser());
				fush.setFrontUserShowId(wi.getShowId());
				fush.setOrderNum(Utlity.getOrderNum());
				fush.setOrderId(wi.getOrderId());
				fush.setType(FrontUserScoreHistoryType.USER_SUB);
				fush.setOrderType(Constants.ORDER_TYPE_USER_DELIVERY);
				fush.setsAmount(scoreAmount);
				fush.setScoreBalanceBefore(fua.getScoreBalance());
				fush.setScoreBalanceAfter(fua.getScoreBalance().subtract(scoreAmount));
				fush.setReason(Constants.orderTypeTemplateInfoMap.get(Constants.ORDER_TYPE_USER_DELIVERY));
				fush.setCreatetime(winningInfoReceive.getCreatetime());
				fush.setRemark("用户领取实物扣抵手续费，重置领奖不返还！");
				this.frontUserScoreHistoryDao.insert(fush);
			}
			
			fua.setScoreBalance(scoreBalance);
		}
		this.frontUserAccountDao.update(fua);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "winningInfoReceive", key = "'winningInfoReceive:' + #winningInfoReceive.winningInfo")})
	public void deleteReceive(WinningInfoReceive winningInfoReceive, WinningInfo wi) {
		this.winningInfoDao.update(wi);
		this.winningInfoReceiveMapper.delete(winningInfoReceive);
		
		//扣减兑换实物金额
		FrontUserAccount fua = this.frontUserAccountDao.get(wi.getFrontUser());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uuid", fua.getFrontUser());
		Goods goods = this.goodsDao.get(wi.getGoodsId());
		if(goods != null) {
			params.put("totalDelivery", fua.getTotalDelivery().subtract(goods.getPrice()));
			params.put("deliveryTimes", fua.getDeliveryTimes() - 1);
		}
		this.frontUserAccountDao.updateInfo(fua, params);
	}
}
