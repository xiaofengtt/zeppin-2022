package cn.product.treasuremall.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.treasuremall.dao.WinningInfoDao;
import cn.product.treasuremall.entity.WinningInfo;
import cn.product.treasuremall.entity.FrontUser.FrontUserType;
import cn.product.treasuremall.entity.WinningInfo.WinningInfoType;
import cn.product.treasuremall.mapper.WinningInfoMapper;

@Component
public class WinningInfoDaoImpl implements WinningInfoDao{
	
	@Autowired
	private WinningInfoMapper winningInfoMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return winningInfoMapper.getCountByParams(params);
	}
	
	@Override
	public List<WinningInfo> getListByParams(Map<String, Object> params) {
		return winningInfoMapper.getListByParams(params);
	}
	
	@Override
	@Cacheable(cacheNames="winningInfo",key="'winningInfo:' + #key")
	public WinningInfo get(String key) {
		return winningInfoMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(WinningInfo winningInfo) {
		return winningInfoMapper.insert(winningInfo);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "winningInfo", key = "'winningInfo:' + #key")})
	public int delete(String key) {
		return winningInfoMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "winningInfo", key = "'winningInfo:' + #winningInfo.uuid")})
	public int update(WinningInfo winningInfo) {
		return winningInfoMapper.updateByPrimaryKey(winningInfo);
	}

	@Override
	public Integer getCountByRobotParams(Map<String, Object> params) {
		return winningInfoMapper.getCountByRobotParams(params);
	}

	@Override
	public List<WinningInfo> getListByRobotParams(Map<String, Object> params) {
		return winningInfoMapper.getListByRobotParams(params);
	}

	@Override
	public BigDecimal getWinningByDate(String dateStr) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("starttime", dateStr + " 00:00:00");
		searchMap.put("endtime", dateStr + " 23:59:59");
		searchMap.put("userType", FrontUserType.NORMAL);
		BigDecimal amount = this.winningInfoMapper.getAmountByParams(searchMap);
		if(amount == null){
			amount = BigDecimal.ZERO;
		}
		return amount;
	}

	@Override
	public BigDecimal getDeliveryByDate(String dateStr) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("starttime", dateStr + " 00:00:00");
		searchMap.put("endtime", dateStr + " 23:59:59");
		searchMap.put("type", WinningInfoType.ENTITY);
		searchMap.put("userType", FrontUserType.NORMAL);
		BigDecimal amount = this.winningInfoMapper.getAmountByParams(searchMap);
		if(amount == null){
			amount = BigDecimal.ZERO;
		}
		return amount;
	}
}
