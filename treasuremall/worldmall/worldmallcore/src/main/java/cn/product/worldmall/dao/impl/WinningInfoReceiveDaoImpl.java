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
import cn.product.worldmall.dao.FrontUserScoreHistoryDao;
import cn.product.worldmall.dao.GoodsDao;
import cn.product.worldmall.dao.LuckygameGoodsIssueDao;
import cn.product.worldmall.dao.SystemParamDao;
import cn.product.worldmall.dao.WinningInfoDao;
import cn.product.worldmall.dao.WinningInfoReceiveDao;
import cn.product.worldmall.entity.FrontUserAccount;
import cn.product.worldmall.entity.FrontUserHistory;
import cn.product.worldmall.entity.FrontUserScoreHistory;
import cn.product.worldmall.entity.FrontUserScoreHistory.FrontUserScoreHistoryType;
import cn.product.worldmall.entity.Goods;
import cn.product.worldmall.entity.SystemParam;
import cn.product.worldmall.entity.SystemParam.SystemParamKey;
import cn.product.worldmall.entity.WinningInfo;
import cn.product.worldmall.entity.WinningInfoReceive;
import cn.product.worldmall.entity.WinningInfoReceive.WinningInfoReceiveType;
import cn.product.worldmall.entity.base.Constants;
import cn.product.worldmall.mapper.WinningInfoReceiveMapper;
import cn.product.worldmall.util.Utlity;

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
		
		//20200623???????????????????????????????????????
		if(WinningInfoReceiveType.ENTITY.equals(winningInfoReceive.getType())) {
			//20200624?????????????????????????????????????????????
			/*
			 * 1???????????????????????????????????????????????????????????? M
				2???????????????????????????????????? J = ?????????????????? G*M
				3???????????????????????????????????? J = ??????????????????????????????????????????=G-J/M
				4????????????0??????????????????????????? = G
				5?????????????????????????????????????????????????????????????????????????????????
			 */
			SystemParam scorerate = this.systemParamDao.get(SystemParamKey.DELIVERY_SCOREAMOUNT);//??????????????????
			BigDecimal scoreRate = BigDecimal.valueOf(2);
			if(scorerate != null) {
				scoreRate = BigDecimal.valueOf(Double.valueOf(scorerate.getParamValue()));
			}
			
			//??????????????????
			BigDecimal scoreAmount = BigDecimal.ZERO;
			BigDecimal scoreBalance = fua.getScoreBalance();
			BigDecimal dAmount = wi.getDealPrice();//?????????????????????
			scoreAmount = dAmount.multiply(scoreRate).setScale(0, BigDecimal.ROUND_UP);//??????????????????1???1?????? ??????????????? ??????????????????;
			if(scoreBalance.compareTo(BigDecimal.ZERO) > 0) {
				if(scoreBalance.compareTo(scoreAmount) >= 0) {//????????????????????????
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
				fush.setRemark("The user receives the physical object to deduct the handling fee, and the reset is not refunded!");
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
		
		//????????????????????????
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
