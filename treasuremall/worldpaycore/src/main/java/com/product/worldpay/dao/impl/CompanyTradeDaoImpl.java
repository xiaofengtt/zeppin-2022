package com.product.worldpay.dao.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.product.worldpay.dao.CompanyAccountHistoryDao;
import com.product.worldpay.dao.CompanyChannelDao;
import com.product.worldpay.dao.CompanyTradeDao;
import com.product.worldpay.entity.CompanyAccountHistory;
import com.product.worldpay.entity.CompanyAccountHistory.CompanyAccountHistoryType;
import com.product.worldpay.entity.CompanyChannel;
import com.product.worldpay.entity.CompanyTrade;
import com.product.worldpay.entity.CompanyTrade.CompanyTradeStatus;
import com.product.worldpay.entity.CompanyTrade.CompanyTradeType;
import com.product.worldpay.mapper.CompanyTradeMapper;
import com.product.worldpay.util.api.PaymentException;

@Component
public class CompanyTradeDaoImpl implements CompanyTradeDao{
	
	@Autowired
	private CompanyTradeMapper companyTradeMapper;
	
	@Autowired
	private CompanyChannelDao companyChannelDao;
	
	@Autowired
	private CompanyAccountHistoryDao companyAccountHistoryDao;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return companyTradeMapper.getCountByParams(params);
	}
	
	@Override
	public List<CompanyTrade> getListByParams(Map<String, Object> params) {
		return companyTradeMapper.getListByParams(params);
	}
    
    @Override
	@Cacheable(cacheNames="companyTrade",key="'companyTrade:' + #key")
	public CompanyTrade get(String key) {
		return companyTradeMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(CompanyTrade companyTrade) {
		return companyTradeMapper.insert(companyTrade);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "companyTrade", key = "'companyTrade:' + #key")})
	public int delete(String key) {
		return companyTradeMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "companyTrade", key = "'companyTrade:' + #companyTrade.uuid")})
	public int update(CompanyTrade companyTrade) {
		return companyTradeMapper.updateByPrimaryKey(companyTrade);
	}

	@Override
	@Transactional
	public void insertTrade(CompanyTrade companyTrade) throws PaymentException {
		if(CompanyTradeType.WITHDRAW.equals(companyTrade.getType())){
			CompanyChannel cch = this.companyChannelDao.get(companyTrade.getCompanyChannel());
			if(cch.getBalance().compareTo(companyTrade.getTotalAmount()) < 0 ){
				throw new PaymentException("账户余额不足");
			}
			
			cch.setBalance(cch.getBalance().subtract(companyTrade.getTotalAmount()));
			cch.setBalanceLock(cch.getBalanceLock().add(companyTrade.getTotalAmount()));
			this.companyChannelDao.update(cch);
		}
		this.companyTradeMapper.insert(companyTrade);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "companyTrade", key = "'companyTrade:' + #companyTrade.uuid")})
	public void processOrder(CompanyTrade companyTrade, String status) throws PaymentException {
		CompanyTrade ct = this.companyTradeMapper.selectByPrimaryKey(companyTrade.getUuid());
		if(!companyTrade.getStatus().equals(ct.getStatus())){
			throw new PaymentException("this order was processed !");
		}
		
		CompanyChannel cch = this.companyChannelDao.get(companyTrade.getCompanyChannel());
		if(status.equals(CompanyTradeStatus.CLOSE)){
			if(CompanyTradeStatus.CLOSE.equals(ct.getStatus()) || CompanyTradeStatus.SUCCESS.equals(ct.getStatus())){
				throw new PaymentException("order status error !");
			}
			if(CompanyTradeType.WITHDRAW.equals(ct.getType())){
				if(cch.getBalanceLock().compareTo(ct.getTotalAmount()) < 0){
					throw new PaymentException("balance of account is insufficient !");
				}
				cch.setBalance(cch.getBalance().add(ct.getTotalAmount()));
				cch.setBalanceLock(cch.getBalanceLock().subtract(ct.getTotalAmount()));
				this.companyChannelDao.update(cch);
			}
			ct.setStatus(CompanyTradeStatus.CLOSE);
			ct.setOperator(companyTrade.getOperator());
			ct.setOperattime(new Timestamp(System.currentTimeMillis()));
			this.companyTradeMapper.updateByPrimaryKey(ct);
		}else if(status.equals(CompanyTradeStatus.FAIL)){
			if(CompanyTradeStatus.SUCCESS.equals(ct.getStatus()) || CompanyTradeStatus.CLOSE.equals(ct.getStatus())){
				throw new PaymentException("order status error !");
			}
			ct.setStatus(CompanyTradeStatus.FAIL);
			ct.setFailReason(companyTrade.getFailReason());
			ct.setOperator(companyTrade.getOperator());
			ct.setOperattime(new Timestamp(System.currentTimeMillis()));
			
			this.companyTradeMapper.updateByPrimaryKey(ct);
		}else if(status.equals(CompanyTradeStatus.CHECKING)){
			if(!CompanyTradeStatus.FAIL.equals(ct.getStatus())){
				throw new PaymentException("order status error !");
			}
			ct.setStatus(CompanyTradeStatus.CHECKING);
			ct.setOperattime(new Timestamp(System.currentTimeMillis()));
			
			this.companyTradeMapper.updateByPrimaryKey(ct);
		}else if(status.equals(CompanyTradeStatus.CHECKED)){
			if(!CompanyTradeStatus.CHECKING.equals(ct.getStatus()) && !CompanyTradeStatus.FAIL.equals(ct.getStatus())){
				throw new PaymentException("order status error !");
			}
			ct.setStatus(CompanyTradeStatus.CHECKED);
			ct.setFailReason(null);
			ct.setOperator(companyTrade.getOperator());
			ct.setOperattime(new Timestamp(System.currentTimeMillis()));
			
			this.companyTradeMapper.updateByPrimaryKey(ct);
		}else if(status.equals(CompanyTradeStatus.SUCCESS)){
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			
			if(!CompanyTradeStatus.CHECKED.equals(ct.getStatus())){
				throw new PaymentException("order status error !");
			}
			
			if(CompanyTradeType.WITHDRAW.equals(ct.getType())){
				//提现订单
				ct.setStatus(CompanyTradeStatus.SUCCESS);
				ct.setProof(companyTrade.getProof());
				ct.setOperator(companyTrade.getOperator());
				ct.setOperattime(timestamp);
				this.companyTradeMapper.updateByPrimaryKey(ct);
				
				//商户账户
				cch.setBalanceLock(cch.getBalanceLock().subtract(ct.getTotalAmount()));
				this.companyChannelDao.update(cch);
				
				//商户账户流水
				CompanyAccountHistory cah = new CompanyAccountHistory();
				cah.setUuid(UUID.randomUUID().toString());
				cah.setCompany(ct.getCompany());
				cah.setCompanyBankcard(ct.getCompanyBankcard());
				cah.setType(CompanyAccountHistoryType.COMPANY_WITHDRAW);
				cah.setCurrency(ct.getCurrency());
				cah.setOrderInfo(ct.getUuid());
				cah.setOrderNum(ct.getOrderNum());
				cah.setPoundage(ct.getPoundage());
				cah.setAmount(ct.getTotalAmount());
				cah.setCompanyData(ct.getData());
				cah.setBalance(cch.getBalance().add(cch.getBalanceLock()));
				cah.setSubmittime(ct.getCreatetime());
				cah.setCreatetime(timestamp);
				this.companyAccountHistoryDao.insert(cah);
			}else if(CompanyTradeType.RECHARGE.equals(ct.getType())){
				//充值订单
				ct.setStatus(CompanyTradeStatus.SUCCESS);
				ct.setOperator(companyTrade.getOperator());
				ct.setOperattime(timestamp);
				this.companyTradeMapper.updateByPrimaryKey(ct);
				
				//商户账户
				cch.setBalance(cch.getBalance().add(ct.getAmount()));
				this.companyChannelDao.update(cch);
				
				//商户账户流水
				CompanyAccountHistory cah = new CompanyAccountHistory();
				cah.setUuid(UUID.randomUUID().toString());
				cah.setCompany(ct.getCompany());
				cah.setType(CompanyAccountHistoryType.COMPANY_RECHARGE);
				cah.setCurrency(ct.getCurrency());
				cah.setOrderInfo(ct.getUuid());
				cah.setOrderNum(ct.getOrderNum());
				cah.setPoundage(ct.getPoundage());
				cah.setAmount(ct.getAmount());
				cah.setCompanyData(ct.getData());
				cah.setBalance(cch.getBalance().add(cch.getBalanceLock()));
				cah.setSubmittime(ct.getCreatetime());
				cah.setCreatetime(timestamp);
				this.companyAccountHistoryDao.insert(cah);
			}
		}
	}
}
