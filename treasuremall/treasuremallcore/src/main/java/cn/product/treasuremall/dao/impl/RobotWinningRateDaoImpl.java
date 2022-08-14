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
import org.springframework.transaction.annotation.Transactional;

import cn.product.treasuremall.dao.RobotWinningRateDao;
import cn.product.treasuremall.entity.RobotWinningRate;
import cn.product.treasuremall.entity.RobotWinningRate.RobotWinningRateStatus;
import cn.product.treasuremall.mapper.RobotWinningRateMapper;

@Component
public class RobotWinningRateDaoImpl implements RobotWinningRateDao{

    @Autowired
    private RobotWinningRateMapper robotWinningRateMapper;
    
    @Override
	public Integer getCountByParams(Map<String, Object> params) {
		return robotWinningRateMapper.getCountByParams(params);
	}
	
	@Override
	public List<RobotWinningRate> getListByParams(Map<String, Object> params) {
		return robotWinningRateMapper.getListByParams(params);
	}

	@Override
	@Cacheable(cacheNames="robotWinningRate",key="'robotWinningRate:' + #key")
	public RobotWinningRate get(String key) {
		return robotWinningRateMapper.selectByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "robotWinningRatePrice", allEntries = true)})
	public int insert(RobotWinningRate robotWinningRate) {
		return robotWinningRateMapper.insert(robotWinningRate);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "robotWinningRate", key = "'robotWinningRate:' + #key")
		,@CacheEvict(value = "robotWinningRatePrice", allEntries = true)})
	public int delete(String key) {
		return robotWinningRateMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "robotWinningRate", key = "'robotWinningRate:' + #robotWinningRate.uuid")
		,@CacheEvict(value = "robotWinningRatePrice", allEntries = true)})
	public int update(RobotWinningRate robotWinningRate) {
		return robotWinningRateMapper.updateByPrimaryKey(robotWinningRate);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "robotWinningRate", allEntries = true),@CacheEvict(value = "robotWinningRatePrice", allEntries = true)})
	public void batchWinningRate(List<RobotWinningRate> insert, List<RobotWinningRate> update) {
		if(insert != null) {
			for(RobotWinningRate rs : insert) {
				this.robotWinningRateMapper.insert(rs);
			}
		}
		
		if(update != null) {
			for(RobotWinningRate rs : update) {
				this.robotWinningRateMapper.updateByPrimaryKey(rs);
			}
		}
	}
	
	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "robotWinningRate", allEntries = true),@CacheEvict(value = "robotWinningRatePrice", allEntries = true)})
	public void updateStatus(Map<String, Object> params) {
		this.robotWinningRateMapper.updateStatus(params);
	}

	@Override
	@Cacheable(cacheNames="robotWinningRatePrice",key="'robotWinningRatePrice:' + #price")
	public RobotWinningRate getByPirice(BigDecimal price) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("status", RobotWinningRateStatus.NORMAL);
		List<RobotWinningRate> list = this.robotWinningRateMapper.getListByParams(searchMap);
		if(list != null && list.size() > 0) {
			for(RobotWinningRate rwr : list) {
				if(rwr.getGoodsPriceMax() != null && rwr.getGoodsPriceMax().compareTo(BigDecimal.ZERO) > 0) {
					if(rwr.getGoodsPriceMin().compareTo(price) <= 0 && rwr.getGoodsPriceMax().compareTo(price) > 0) {
						return rwr;
					}
				} else {
					if(rwr.getGoodsPriceMin().compareTo(price) <= 0) {
						return rwr;
					}
				}
			}
		}
		return null;
	}
}
