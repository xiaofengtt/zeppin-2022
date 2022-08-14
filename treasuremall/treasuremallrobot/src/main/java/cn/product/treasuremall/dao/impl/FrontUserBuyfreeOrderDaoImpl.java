package cn.product.treasuremall.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.treasuremall.dao.ActivityInfoBuyfreeDao;
import cn.product.treasuremall.dao.ActivityInfoBuyfreeSharesnumDao;
import cn.product.treasuremall.dao.FrontUserAccountDao;
import cn.product.treasuremall.dao.FrontUserBuyfreeOrderDao;
import cn.product.treasuremall.dao.FrontUserHistoryDao;
import cn.product.treasuremall.entity.ActivityInfoBuyfree;
import cn.product.treasuremall.entity.ActivityInfoBuyfreeSharesnum;
import cn.product.treasuremall.entity.FrontUserAccount;
import cn.product.treasuremall.entity.FrontUserBuyfreeOrder;
import cn.product.treasuremall.entity.FrontUserHistory;
import cn.product.treasuremall.mapper.FrontUserBuyfreeOrderMapper;

@Component
public class FrontUserBuyfreeOrderDaoImpl implements FrontUserBuyfreeOrderDao{
	
	public static final Logger log= LoggerFactory.getLogger(FrontUserBuyfreeOrderDaoImpl.class);
	
	@Autowired
	private FrontUserBuyfreeOrderMapper frontUserBuyfreeOrderMapper;
	
	@Autowired
	private ActivityInfoBuyfreeSharesnumDao activityInfoBuyfreeSharesnumDao;
	
	@Autowired
	private ActivityInfoBuyfreeDao activityInfoBuyfreeDao;	
	
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;	
	
	@Autowired
	private FrontUserHistoryDao frontUserHistoryDao;	
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserBuyfreeOrderMapper.getCountByParams(params);
	}
	
	@Override
	public List<FrontUserBuyfreeOrder> getListByParams(Map<String, Object> params) {
		return frontUserBuyfreeOrderMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="frontUserBuyfreeOrder",key="'frontUserBuyfreeOrder:' + #key")
	public FrontUserBuyfreeOrder get(String key) {
		return frontUserBuyfreeOrderMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(FrontUserBuyfreeOrder frontUserBuyfreeOrder) {
		return frontUserBuyfreeOrderMapper.insert(frontUserBuyfreeOrder);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserBuyfreeOrder", key = "'frontUserBuyfreeOrder:' + #key")})
	public int delete(String key) {
		return frontUserBuyfreeOrderMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserBuyfreeOrder", key = "'frontUserBuyfreeOrder:' + #frontUserBuyfreeOrder.uuid")})
	public int update(FrontUserBuyfreeOrder frontUserBuyfreeOrder) {
		return frontUserBuyfreeOrderMapper.updateByPrimaryKey(frontUserBuyfreeOrder);
	}

	/**
	 * 机器人抽奖投注入库处理
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void userBet(Map<String, Object> params) {
		
		//抽奖信息更新，抽奖信息需要同步到前端
		if(params.get("activityInfoBuyfree") != null) {
			List<ActivityInfoBuyfree> list = (List<ActivityInfoBuyfree>) params.get("activityInfoBuyfree");
			for(ActivityInfoBuyfree aib : list) {
				this.activityInfoBuyfreeDao.update(aib);
			}
		}
		if(params.get("issueNums") != null) {
			List<ActivityInfoBuyfreeSharesnum> list = (List<ActivityInfoBuyfreeSharesnum>) params.get("issueNums");
			for(ActivityInfoBuyfreeSharesnum aibs : list) {
				this.activityInfoBuyfreeSharesnumDao.update(aibs);
			}
		}
		
		//订单入库
		if(params.get("paymentOrder") != null) {
			List<FrontUserBuyfreeOrder> list = (List<FrontUserBuyfreeOrder>) params.get("paymentOrder");
			for(FrontUserBuyfreeOrder fupo : list) {
				this.frontUserBuyfreeOrderMapper.insert(fupo);
			}
		}
	}

	@Override
	public Integer getGroupCountByParams(Map<String, Object> params) {
		return this.frontUserBuyfreeOrderMapper.getGroupCountByParams(params);
	}

	@Override
	public List<FrontUserBuyfreeOrder> getGroupListByParams(Map<String, Object> params) {
		return this.frontUserBuyfreeOrderMapper.getGroupListByParams(params);
	}

	@Override
	public Integer getLeftCountByParams(Map<String, Object> params) {
		return this.frontUserBuyfreeOrderMapper.getLeftCountByParams(params);
	}

	@Override
	public List<FrontUserBuyfreeOrder> getLeftListByParams(Map<String, Object> params) {
		return this.frontUserBuyfreeOrderMapper.getLeftListByParams(params);
	}

	/**
	 * 用户兑奖信息入库
	 */
	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "frontUserBuyfreeOrder", key = "'frontUserBuyfreeOrder:' + #frontUserBuyfreeOrder.uuid")})
	public void userReceive(FrontUserBuyfreeOrder frontUserBuyfreeOrder, Map<String, Object> params) {
		
		this.frontUserBuyfreeOrderMapper.updateByPrimaryKey(frontUserBuyfreeOrder);
		
		if(params.containsKey("frontUserAccount") && params.get("frontUserAccount") != null) {
			FrontUserAccount fua = (FrontUserAccount)params.get("frontUserAccount");
			this.frontUserAccountDao.update(fua);
		}
		
		if(params.containsKey("frontUserHistory") && params.get("frontUserHistory") != null) {
			FrontUserHistory fuh = (FrontUserHistory)params.get("frontUserHistory");
			this.frontUserHistoryDao.insert(fuh);
		}
	}
}
