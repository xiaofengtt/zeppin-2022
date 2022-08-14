package cn.product.worldmall.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.worldmall.dao.FrontUserBankcardDao;
import cn.product.worldmall.entity.FrontUserBankcard;
import cn.product.worldmall.entity.MobileCode;
import cn.product.worldmall.entity.FrontUserBankcard.FrontUserBankcardStatus;
import cn.product.worldmall.entity.FrontUserBankcard.FrontUserBankcardType;
import cn.product.worldmall.mapper.FrontUserBankcardMapper;
import cn.product.worldmall.mapper.MobileCodeMapper;

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
	@Caching(evict={@CacheEvict(value = "frontUserPaypal", key = "'frontUserPaypal:' + #frontUserBankcard.frontUser", beforeInvocation = true)})
	public int insert(FrontUserBankcard frontUserBankcard) {
		return frontUserBankcardMapper.insert(frontUserBankcard);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserBankcard", key = "'frontUserBankcard:' + #key")})
	public int delete(String key) {
		return frontUserBankcardMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserBankcard", key = "'frontUserBankcard:' + #frontUserBankcard.uuid")
	, @CacheEvict(value = "frontUserPaypal", key = "'frontUserPaypal:' + #frontUserBankcard.frontUser", beforeInvocation = true)})
	public int update(FrontUserBankcard frontUserBankcard) {
		return frontUserBankcardMapper.updateByPrimaryKey(frontUserBankcard);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "frontUserPaypal", key = "'frontUserPaypal:' + #fub.frontUser")})
	public void insertFrontUserBankcard(FrontUserBankcard fub, MobileCode mc) {
		this.frontUserBankcardMapper.insert(fub);
		this.mobileCodeMapper.updateByPrimaryKey(mc);
		
	}

	@Override
	@Cacheable(cacheNames="frontUserPaypal",key="'frontUserPaypal:' + #frontUser")
	public FrontUserBankcard getPaypal(String frontUser) {
		Map<String, Object> paramsls = new HashMap<String, Object>();
		paramsls.put("frontUser", frontUser);
		paramsls.put("status", FrontUserBankcardStatus.NORMAL);
		paramsls.put("type", FrontUserBankcardType.ACCOUNT);
		List<FrontUserBankcard> list = this.frontUserBankcardMapper.getListByParams(paramsls);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
