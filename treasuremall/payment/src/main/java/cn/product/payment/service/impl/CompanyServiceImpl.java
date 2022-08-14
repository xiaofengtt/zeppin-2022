package cn.product.payment.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.product.payment.entity.Company;
import cn.product.payment.entity.CompanyAccount;
import cn.product.payment.mapper.CompanyMapper;
import cn.product.payment.service.CompanyAccountService;
import cn.product.payment.service.CompanyService;
import cn.product.payment.util.PaymentUtil;
import cn.product.payment.util.Utlity;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService{
	
	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private CompanyAccountService companyAccountService;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return companyMapper.getCountByParams(params);
	}
	
	@Override
	public List<Company> getListByParams(Map<String, Object> params) {
		return companyMapper.getListByParams(params);
	}
    
    @Override
	@Cacheable(cacheNames="company",key="'company:' + #key")
	public Company get(String key) {
		return companyMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(Company company) {
		return companyMapper.insert(company);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "company", key = "'company:' + #key"),@CacheEvict(value = "companyCode", allEntries = true)})
	public int delete(String key) {
		return companyMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "company", key = "'company:' + #company.uuid"), @CacheEvict(value = "companyCode", key = "'companyCode:' + #company.code")})
	public int update(Company company) {
		return companyMapper.updateByPrimaryKey(company);
	}

	@Override
	@Transactional
	public void insertCompany(Company company) {
		Integer count = this.getCountByParams(new HashMap<String, Object>());
		company.setCode(Utlity.timestampFormat(company.getCreatetime(), "yyyyMMdd") + Utlity.numberFormat(count, "0000"));
		CompanyAccount ca = new CompanyAccount();
		ca.setUuid(company.getUuid());
		ca.setBalance(BigDecimal.ZERO);
		ca.setBalanceLock(BigDecimal.ZERO);
		
		this.insert(company);
		this.companyAccountService.insert(ca);
	}

	@Override
	public Map<String, String> getCompanyKeys(Company company) {
		return PaymentUtil.createKeys(company.getUuid());
	}

	@Override
	@Cacheable(cacheNames="companyCode",key="'companyCode:' + #code")
	public Company getByCode(String code) {
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("code",code);
        List<Company> list =  this.getListByParams(params);
        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
	}
	
}
