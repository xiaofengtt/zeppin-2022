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

import cn.product.treasuremall.dao.ActivityInfoScorelotteryPrizeDao;
import cn.product.treasuremall.dao.AdminOffsetOrderDao;
import cn.product.treasuremall.dao.FrontUserAccountDao;
import cn.product.treasuremall.dao.FrontUserHistoryDao;
import cn.product.treasuremall.dao.FrontUserScoreHistoryDao;
import cn.product.treasuremall.dao.FrontUserScorelotteryHistoryDao;
import cn.product.treasuremall.dao.FrontUserVoucherDao;
import cn.product.treasuremall.entity.AdminOffsetOrder;
import cn.product.treasuremall.entity.FrontUserAccount;
import cn.product.treasuremall.entity.FrontUserHistory;
import cn.product.treasuremall.entity.FrontUserScoreHistory;
import cn.product.treasuremall.entity.FrontUserScorelotteryHistory;
import cn.product.treasuremall.entity.FrontUserVoucher;
import cn.product.treasuremall.mapper.FrontUserScorelotteryHistoryMapper;

@Component
public class FrontUserScorelotteryHistoryDaoImpl implements FrontUserScorelotteryHistoryDao{
	
	public static final Logger log= LoggerFactory.getLogger(FrontUserScorelotteryHistoryDaoImpl.class);
	
	@Autowired
	private FrontUserScorelotteryHistoryMapper frontUserScorelotteryHistoryMapper;
	
	@Autowired
	private ActivityInfoScorelotteryPrizeDao activityInfoScorelotteryPrizeDao;	
	
	@Autowired
	private AdminOffsetOrderDao adminOffsetOrderDao;	
	
	@Autowired
	private FrontUserHistoryDao frontUserHistoryDao;	
	
	@Autowired
	private FrontUserVoucherDao frontUserVoucherDao;	
	
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
	private FrontUserScoreHistoryDao frontUserScoreHistoryDao;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserScorelotteryHistoryMapper.getCountByParams(params);
	}
	
	@Override
	public List<FrontUserScorelotteryHistory> getListByParams(Map<String, Object> params) {
		return frontUserScorelotteryHistoryMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="frontUserScorelotteryHistory",key="'frontUserScorelotteryHistory:' + #key")
	public FrontUserScorelotteryHistory get(String key) {
		return frontUserScorelotteryHistoryMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(FrontUserScorelotteryHistory frontUserScorelotteryHistory) {
		return frontUserScorelotteryHistoryMapper.insert(frontUserScorelotteryHistory);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserScorelotteryHistory", key = "'frontUserScorelotteryHistory:' + #key")})
	public int delete(String key) {
		return frontUserScorelotteryHistoryMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserScorelotteryHistory", key = "'frontUserScorelotteryHistory:' + #frontUserScorelotteryHistory.uuid")})
	public int update(FrontUserScorelotteryHistory frontUserScorelotteryHistory) {
		return frontUserScorelotteryHistoryMapper.updateByPrimaryKey(frontUserScorelotteryHistory);
	}

	/**
	 * 用户参与
	 * 
	 */
	@Override
	@Transactional
	public void scorelottery(Map<String, Object> params) {
		//入库
		if(params.containsKey("scorelotteryHistory")) {
			if(params.get("scorelotteryHistory") != null) {
				FrontUserScorelotteryHistory fuch = (FrontUserScorelotteryHistory) params.get("scorelotteryHistory");
				this.frontUserScorelotteryHistoryMapper.insert(fuch);
			}
			if(params.containsKey("offsetOrder") && params.containsKey("userHistory")) {
				if(params.get("offsetOrder") != null) {
					AdminOffsetOrder aoo = (AdminOffsetOrder) params.get("offsetOrder");
					this.adminOffsetOrderDao.insert(aoo);
				}
				if(params.get("userHistory") != null) {
					FrontUserHistory fuh = (FrontUserHistory)params.get("userHistory");	
					this.frontUserHistoryDao.insert(fuh);
				}
			}

			if(params.containsKey("scoreHistory") && params.get("scoreHistory") != null) {
				FrontUserScoreHistory fush = (FrontUserScoreHistory)params.get("scoreHistory");
				this.frontUserScoreHistoryDao.insert(fush);
			}
			
			if(params.containsKey("userAccount") && params.get("userAccount") != null) {
				FrontUserAccount fua = (FrontUserAccount)params.get("userAccount");
				this.frontUserAccountDao.update(fua);
			}
			
			if(params.containsKey("userVoucher") && params.get("userVoucher") != null) {
				FrontUserVoucher fuv = (FrontUserVoucher)params.get("userVoucher");
				this.frontUserVoucherDao.insert(fuv);
			}
		}
	}

	@Override
	public Integer getGroupCountByParams(Map<String, Object> params) {
		return this.frontUserScorelotteryHistoryMapper.getGroupCountByParams(params);
	}

	@Override
	public List<FrontUserScorelotteryHistory> getGroupListByParams(Map<String, Object> params) {
		return this.frontUserScorelotteryHistoryMapper.getGroupListByParams(params);
	}

	@Override
	public Integer getLeftCountByParams(Map<String, Object> params) {
		return this.frontUserScorelotteryHistoryMapper.getLeftCountByParams(params);
	}

	@Override
	public List<FrontUserScorelotteryHistory> getLeftListByParams(Map<String, Object> params) {
		return this.frontUserScorelotteryHistoryMapper.getLeftListByParams(params);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "frontUserScorelotteryHistory", key = "'frontUserScorelotteryHistory:' + #frontUserScorelotteryHistory.uuid")})
	public void userReceive(FrontUserScorelotteryHistory frontUserScorelotteryHistory, Map<String, Object> params) {

		this.frontUserScorelotteryHistoryMapper.updateByPrimaryKey(frontUserScorelotteryHistory);
		
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
