package cn.product.worldmall.dao.impl;

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

import cn.product.worldmall.dao.ActivityInfoCheckinPrizeDao;
import cn.product.worldmall.dao.AdminOffsetOrderDao;
import cn.product.worldmall.dao.FrontUserAccountDao;
import cn.product.worldmall.dao.FrontUserCheckinHistoryDao;
import cn.product.worldmall.dao.FrontUserHistoryDao;
import cn.product.worldmall.dao.FrontUserVoucherDao;
import cn.product.worldmall.entity.AdminOffsetOrder;
import cn.product.worldmall.entity.FrontUserAccount;
import cn.product.worldmall.entity.FrontUserCheckinHistory;
import cn.product.worldmall.entity.FrontUserHistory;
import cn.product.worldmall.entity.FrontUserVoucher;
import cn.product.worldmall.mapper.FrontUserCheckinHistoryMapper;

@Component
public class FrontUserCheckinHistoryDaoImpl implements FrontUserCheckinHistoryDao{
	
	public static final Logger log= LoggerFactory.getLogger(FrontUserCheckinHistoryDaoImpl.class);
	
	@Autowired
	private FrontUserCheckinHistoryMapper frontUserCheckinHistoryMapper;
	
	@Autowired
	private ActivityInfoCheckinPrizeDao activityInfoCheckinPrizeDao;	
	
	@Autowired
	private AdminOffsetOrderDao adminOffsetOrderDao;	
	
	@Autowired
	private FrontUserHistoryDao frontUserHistoryDao;	
	
	@Autowired
	private FrontUserVoucherDao frontUserVoucherDao;	
	
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;	
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserCheckinHistoryMapper.getCountByParams(params);
	}
	
	@Override
	public List<FrontUserCheckinHistory> getListByParams(Map<String, Object> params) {
		return frontUserCheckinHistoryMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="frontUserCheckinHistory",key="'frontUserCheckinHistory:' + #key")
	public FrontUserCheckinHistory get(String key) {
		return frontUserCheckinHistoryMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(FrontUserCheckinHistory frontUserCheckinHistory) {
		return frontUserCheckinHistoryMapper.insert(frontUserCheckinHistory);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserCheckinHistory", key = "'frontUserCheckinHistory:' + #key")})
	public int delete(String key) {
		return frontUserCheckinHistoryMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserCheckinHistory", key = "'frontUserCheckinHistory:' + #frontUserCheckinHistory.uuid")})
	public int update(FrontUserCheckinHistory frontUserCheckinHistory) {
		return frontUserCheckinHistoryMapper.updateByPrimaryKey(frontUserCheckinHistory);
	}

	/**
	 * 用户签到
	 * 
	 */
	@Override
	@Transactional
	public void checkin(Map<String, Object> params) {
		//入库
		if(params.containsKey("checkinHistory")) {
			if(params.get("checkinHistory") != null) {
				FrontUserCheckinHistory fuch = (FrontUserCheckinHistory) params.get("checkinHistory");
				this.frontUserCheckinHistoryMapper.insert(fuch);
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
			
			if(params.containsKey("userVoucher") && params.get("userVoucher") != null) {
				FrontUserVoucher fuv = (FrontUserVoucher)params.get("userVoucher");
				this.frontUserVoucherDao.insert(fuv);
			}
			
			if(params.containsKey("userAccount") && params.get("userAccount") != null) {
				FrontUserAccount fua = (FrontUserAccount)params.get("userAccount");
				this.frontUserAccountDao.update(fua);
			}
		}
	}

	@Override
	public Integer getGroupCountByParams(Map<String, Object> params) {
		return this.frontUserCheckinHistoryMapper.getGroupCountByParams(params);
	}

	@Override
	public List<FrontUserCheckinHistory> getGroupListByParams(Map<String, Object> params) {
		return this.frontUserCheckinHistoryMapper.getGroupListByParams(params);
	}

	@Override
	public Integer getLeftCountByParams(Map<String, Object> params) {
		return this.frontUserCheckinHistoryMapper.getLeftCountByParams(params);
	}

	@Override
	public List<FrontUserCheckinHistory> getLeftListByParams(Map<String, Object> params) {
		return this.frontUserCheckinHistoryMapper.getLeftListByParams(params);
	}
	
	/**
	 * 用户兑奖信息入库
	 */
	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "frontUserCheckinHistory", key = "'frontUserCheckinHistory:' + #frontUserCheckinHistory.uuid")})
	public void userReceive(FrontUserCheckinHistory frontUserCheckinHistory, Map<String, Object> params) {

		this.frontUserCheckinHistoryMapper.updateByPrimaryKey(frontUserCheckinHistory);
		
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
