package cn.zeppin.product.score.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.zeppin.product.score.entity.FrontUserBankcard;
import cn.zeppin.product.score.entity.MobileCode;
import cn.zeppin.product.score.mapper.FrontUserBankcardMapper;
import cn.zeppin.product.score.mapper.MobileCodeMapper;
import cn.zeppin.product.score.service.FrontUserBankcardService;

@Service("frontUserBankcardService")
public class FrontUserBankcardServiceImpl implements FrontUserBankcardService{
	
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
