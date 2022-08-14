package cn.product.treasuremall.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.treasuremall.dao.FrontUserBankcardDao;
import cn.product.treasuremall.entity.FrontUserBankcard;
import cn.product.treasuremall.entity.MobileCode;
import cn.product.treasuremall.mapper.FrontUserBankcardMapper;
import cn.product.treasuremall.mapper.MobileCodeMapper;

@Component
public class FrontUserBankcardDaoImpl implements FrontUserBankcardDao{
	
	@Autowired
	private FrontUserBankcardMapper frontUserBankcardMapper;
	
	@Autowired
	private MobileCodeMapper mobileCodeMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserBankcardMapper.getCountByParams(params);
	}
	
	@Override
	public List<FrontUserBankcard> getListByParams(Map<String, Object> params) {
		return frontUserBankcardMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="frontUserBankcard",key="'frontUserBankcard:' + #key")
	public FrontUserBankcard get(String key) {
		return frontUserBankcardMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(FrontUserBankcard frontUserBankcard) {
		return frontUserBankcardMapper.insert(frontUserBankcard);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserBankcard", key = "'frontUserBankcard:' + #key")})
	public int delete(String key) {
		return frontUserBankcardMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserBankcard", key = "'frontUserBankcard:' + #frontUserBankcard.uuid")})
	public int update(FrontUserBankcard frontUserBankcard) {
		return frontUserBankcardMapper.updateByPrimaryKey(frontUserBankcard);
	}

	@Override
	@Transactional
	public void insertFrontUserBankcard(FrontUserBankcard fub, MobileCode mc) {
		this.frontUserBankcardMapper.insert(fub);
		this.mobileCodeMapper.updateByPrimaryKey(mc);
		
	}
}
