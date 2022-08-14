package cn.product.payment.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.payment.dao.CompanyAccountDao;
import cn.product.payment.entity.CompanyAccount;
import cn.product.payment.mapper.CompanyAccountMapper;

@Component
public class CompanyAccountDaoImpl implements CompanyAccountDao{
	
	@Autowired
	private CompanyAccountMapper companyAccountMapper;
	
    @Override
	@Cacheable(cacheNames="companyAccount",key="'companyAccount:' + #key")
	public CompanyAccount get(String key) {
		return companyAccountMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(CompanyAccount companyAccount) {
		return companyAccountMapper.insert(companyAccount);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "companyAccount", key = "'companyAccount:' + #key")})
	public int delete(String key) {
		return companyAccountMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "companyAccount", key = "'companyAccount:' + #companyAccount.uuid")})
	public int update(CompanyAccount companyAccount) {
		return companyAccountMapper.updateByPrimaryKey(companyAccount);
	}
}
