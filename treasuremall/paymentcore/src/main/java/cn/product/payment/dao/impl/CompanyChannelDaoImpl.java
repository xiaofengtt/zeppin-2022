package cn.product.payment.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.payment.dao.CompanyChannelAccountDao;
import cn.product.payment.dao.CompanyChannelDao;
import cn.product.payment.entity.CompanyChannel;
import cn.product.payment.entity.CompanyChannelAccount;
import cn.product.payment.mapper.CompanyChannelMapper;

@Component
public class CompanyChannelDaoImpl implements CompanyChannelDao{
	
	@Autowired
	private CompanyChannelMapper companyChannelMapper;
	
	@Autowired
	private CompanyChannelAccountDao companyChannelAccountDao;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return companyChannelMapper.getCountByParams(params);
	}
	
	@Override
	public List<CompanyChannel> getListByParams(Map<String, Object> params) {
		return companyChannelMapper.getListByParams(params);
	}
    
    @Override
	@Cacheable(cacheNames="companyChannel",key="'companyChannel:' + #key")
	public CompanyChannel get(String key) {
		return companyChannelMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(CompanyChannel companyChannel) {
		return companyChannelMapper.insert(companyChannel);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "companyChannel", key = "'companyChannel:' + #key")})
	public int delete(String key) {
		return companyChannelMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "companyChannel", key = "'companyChannel:' + #companyChannel.uuid")})
	public int update(CompanyChannel companyChannel) {
		return companyChannelMapper.updateByPrimaryKey(companyChannel);
	}

	/**
	 * 新增商户渠道
	 */
	@Override
	@Transactional
	public void insertCompanyChannel(CompanyChannel companyChannel, List<CompanyChannelAccount> companyChannelAccountList) {
		//插入商户渠道
		companyChannelMapper.insert(companyChannel);
		//插入商户绑定的渠道账户
		for(CompanyChannelAccount cca : companyChannelAccountList){
			this.companyChannelAccountDao.insert(cca);
		}
	}
	
	/**
	 * 更新商户渠道
	 */
	@Override
	@Caching(evict={@CacheEvict(value = "companyChannel", key = "'companyChannel:' + #companyChannel.uuid")})
	@Transactional
	public void updateCompanyChannel(CompanyChannel companyChannel, List<CompanyChannelAccount> companyChannelAccountList) {
		//插入商户渠道
		companyChannelMapper.updateByPrimaryKey(companyChannel);
		//清空商户之前绑定的渠道账户
		this.companyChannelAccountDao.deleteByCompanyChannel(companyChannel.getUuid());
		//插入商户绑定的渠道账户
		for(CompanyChannelAccount cca : companyChannelAccountList){
			this.companyChannelAccountDao.insert(cca);
		}
	}
}
