package cn.product.worldmall.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.worldmall.dao.BankDao;
import cn.product.worldmall.entity.Bank;
import cn.product.worldmall.mapper.BankMapper;
import cn.product.worldmall.util.Utlity;

@Component
public class BankDaoImpl implements BankDao{
	
	@Autowired
	private BankMapper bankMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return bankMapper.getCountByParams(params);
	}
	
	@Override
	public List<Bank> getListByParams(Map<String, Object> params) {
		return bankMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="bank",key="'bank:' + #key")
	public Bank get(String key) {
		return bankMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(Bank bank) {
		return bankMapper.insert(bank);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "bank", key = "'bank:' + #key")})
	public int delete(String key) {
		return bankMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "bank", key = "'bank:' + #bank.uuid")})
	public int update(Bank bank) {
		return bankMapper.updateByPrimaryKey(bank);
	}

	@Override
	public boolean isExistBankByName(String name, String uuid) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(!Utlity.checkStringNull(uuid)) {
			params.put("uuid", uuid);
		}
		if(!Utlity.checkStringNull(name)) {
			params.put("bankName", name);
		}
		int count = bankMapper.getCountByParams(params);
		if(count != 0) {
			return true;
		} else {
			return false;
		}
	}
}
