package cn.product.worldmall.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.worldmall.dao.CapitalAccountDao;
import cn.product.worldmall.dao.CapitalAccountStatisticsDao;
import cn.product.worldmall.entity.CapitalAccount;
import cn.product.worldmall.entity.CapitalAccountStatistics;
import cn.product.worldmall.mapper.CapitalAccountMapper;

@Component
public class CapitalAccountDaoImpl implements CapitalAccountDao{
	
	@Autowired
	private CapitalAccountMapper capitalAccountMapper;
	
	@Autowired
	private CapitalAccountStatisticsDao capitalAccountStatisticsDao;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return capitalAccountMapper.getCountByParams(params);
	}
	
	@Override
	public List<CapitalAccount> getListByParams(Map<String, Object> params) {
		return capitalAccountMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="capitalAccount",key="'capitalAccount:' + #key")
	public CapitalAccount get(String key) {
		return capitalAccountMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(CapitalAccount capitalAccount) {
		return capitalAccountMapper.insert(capitalAccount);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "capitalAccount", key = "'capitalAccount:' + #key")})
	public int delete(String key) {
		return capitalAccountMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "capitalAccount", key = "'capitalAccount:' + #capitalAccount.uuid")})
	public int update(CapitalAccount capitalAccount) {
		return capitalAccountMapper.updateByPrimaryKey(capitalAccount);
	}

	@Override
	@Transactional
	public void insertWithStatistics(CapitalAccount capitalAccount) {
		this.capitalAccountMapper.insert(capitalAccount);
		
		CapitalAccountStatistics cas = new CapitalAccountStatistics();
		cas.setCapitalAccount(capitalAccount.getUuid());
		cas.setBalance(BigDecimal.ZERO);
		cas.setDailySum(BigDecimal.ZERO);
		cas.setTotalPayment(BigDecimal.ZERO);
		cas.setTotalRecharge(BigDecimal.ZERO);
		cas.setTotalWithdraw(BigDecimal.ZERO);
		cas.setPaymentTimes(0);
		cas.setRechargeTimes(0);
		cas.setWithdrawTimes(0);
		
		this.capitalAccountStatisticsDao.insert(cas);
	}

}
