package cn.product.worldmall.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.worldmall.dao.AdminOffsetOrderDao;
import cn.product.worldmall.dao.BankDao;
import cn.product.worldmall.dao.CapitalAccountDao;
import cn.product.worldmall.dao.CapitalAccountHistoryDao;
import cn.product.worldmall.dao.CapitalAccountStatisticsDao;
import cn.product.worldmall.dao.FrontUserAccountDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserMessageDao;
import cn.product.worldmall.dao.FrontUserRechargeOrderDao;
import cn.product.worldmall.dao.FrontUserScoreHistoryDao;
import cn.product.worldmall.dao.FrontUserWithdrawOrderDao;
import cn.product.worldmall.dao.PayNotifyInfoDao;
import cn.product.worldmall.dao.SystemParamDao;
import cn.product.worldmall.dao.WinningInfoDao;
import cn.product.worldmall.dao.WinningInfoReceiveDao;
import cn.product.worldmall.entity.FrontUserScoreHistory;
import cn.product.worldmall.mapper.FrontUserScoreHistoryMapper;

/**
 */
@Component
public class FrontUserScoreHistoryDaoImpl implements FrontUserScoreHistoryDao{
	
	private final static Logger log = LoggerFactory.getLogger(FrontUserScoreHistoryDaoImpl.class);
	
	@Autowired
	private FrontUserScoreHistoryMapper frontUserScoreHistoryMapper;
	
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
	private BankDao bankDao;
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private SystemParamDao systemParamDao;

	@Autowired
	private AdminOffsetOrderDao adminOffsetOrderDao;

	@Autowired
	private FrontUserRechargeOrderDao frontUserRechargeOrderDao;

	@Autowired
	private FrontUserWithdrawOrderDao frontUserWithdrawOrderDao;
	
	@Autowired
	private CapitalAccountDao capitalAccountDao;
	
	@Autowired
	private CapitalAccountStatisticsDao capitalAccountStatisticsDao;
	
	@Autowired
	private CapitalAccountHistoryDao capitalAccountHistoryDao;
	
	@Autowired
	private PayNotifyInfoDao payNotifyInfoDao;
	
	@Autowired
	private WinningInfoDao WinningInfoDao;
	
	@Autowired
    private WinningInfoReceiveDao winningInfoReceiveDao;
	
    @Autowired
    private CuratorFramework curatorFramework;
    
    @Autowired
    private FrontUserMessageDao frontUserMessageDao;
	
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserScoreHistoryMapper.getCountByParams(params);
	}
	
	@Override
	public List<FrontUserScoreHistory> getListByParams(Map<String, Object> params) {
		return frontUserScoreHistoryMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="frontUserScoreHistory",key="'frontUserScoreHistory:' + #key")
	public FrontUserScoreHistory get(String key) {
		return frontUserScoreHistoryMapper.selectByPrimaryKey(key);
	}

	@Override
	@Cacheable(cacheNames="frontUserScoreHistory",key="'frontUserScoreHistory:' + #orderId")
	public FrontUserScoreHistory getByOrderId(String orderId) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("orderId", orderId);
		List<FrontUserScoreHistory> list = this.frontUserScoreHistoryMapper.getListByParams(searchMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	@Override
	@Caching(evict={@CacheEvict(value = "frontUserScoreHistory", key = "'frontUserScoreHistory:' + #frontUserScoreHistory.orderId")})
	public int insert(FrontUserScoreHistory frontUserScoreHistory) {
		return frontUserScoreHistoryMapper.insert(frontUserScoreHistory);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserScoreHistory", key = "'frontUserScoreHistory:' + #key")
	,@CacheEvict(value = "frontUserScoreHistory", key = "'frontUserScoreHistory:' + #frontUserScoreHistory.orderId")})
	public int delete(String key) {
		return frontUserScoreHistoryMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserScoreHistory", key = "'frontUserScoreHistory:' + #frontUserScoreHistory.uuid"),
			@CacheEvict(value = "frontUserScoreHistory", key = "'frontUserScoreHistory:' + #frontUserScoreHistory.orderId")})
	public int update(FrontUserScoreHistory frontUserScoreHistory) {
		return frontUserScoreHistoryMapper.updateByPrimaryKey(frontUserScoreHistory);
	}

}
