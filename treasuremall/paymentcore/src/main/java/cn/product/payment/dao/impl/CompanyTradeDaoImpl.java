package cn.product.payment.dao.impl;

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

import cn.product.payment.dao.CompanyAccountDao;
import cn.product.payment.dao.CompanyAccountHistoryDao;
import cn.product.payment.dao.CompanyTradeDao;
import cn.product.payment.entity.CompanyAccount;
import cn.product.payment.entity.CompanyAccountHistory;
import cn.product.payment.entity.CompanyTrade;
import cn.product.payment.entity.CompanyAccountHistory.CompanyAccountHistoryType;
import cn.product.payment.entity.CompanyTrade.CompanyTradeStatus;
import cn.product.payment.entity.CompanyTrade.CompanyTradeType;
import cn.product.payment.mapper.CompanyTradeMapper;
import cn.product.payment.util.api.PaymentException;

@Component
public class CompanyTradeDaoImpl implements CompanyTradeDao{
	
	@Autowired
	private CompanyTradeMapper companyTradeMapper;
	
	@Autowired
	private CompanyAccountDao companyAccountDao;
	
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
			//??????????????????
			CompanyAccount ca = this.companyAccountDao.get(companyTrade.getCompany());
			if(ca.getBalance().compareTo(companyTrade.getTotalAmount()) < 0 ){
				throw new PaymentException("??????????????????");
			}
			
			//??????????????????
			ca.setBalance(ca.getBalance().subtract(companyTrade.getTotalAmount()));
			ca.setBalanceLock(ca.getBalanceLock().add(companyTrade.getTotalAmount()));
			this.companyAccountDao.update(ca);
		}
		this.companyTradeMapper.insert(companyTrade);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "companyTrade", key = "'companyTrade:' + #companyTrade.uuid")})
	public void processOrder(CompanyTrade companyTrade, String status) throws PaymentException {
		CompanyTrade ct = this.companyTradeMapper.selectByPrimaryKey(companyTrade.getUuid());
		if(!companyTrade.getStatus().equals(ct.getStatus())){
			throw new PaymentException("?????????????????????????????????");
		}
		
		CompanyAccount ca = this.companyAccountDao.get(ct.getCompany());
		if(status.equals(CompanyTradeStatus.CLOSE)){
			//????????????
			if(CompanyTradeStatus.CLOSE.equals(ct.getStatus()) || CompanyTradeStatus.SUCCESS.equals(ct.getStatus())){
				throw new PaymentException("?????????????????????");
			}
			if(CompanyTradeType.WITHDRAW.equals(ct.getType())){
				//??????????????????????????????
				if(ca.getBalanceLock().compareTo(ct.getTotalAmount()) < 0){
					throw new PaymentException("?????????????????????");
				}
				ca.setBalance(ca.getBalance().add(ct.getTotalAmount()));
				ca.setBalanceLock(ca.getBalanceLock().subtract(ct.getTotalAmount()));
				this.companyAccountDao.update(ca);
			}
			ct.setStatus(CompanyTradeStatus.CLOSE);
			ct.setOperator(companyTrade.getOperator());
			ct.setOperattime(new Timestamp(System.currentTimeMillis()));
			this.companyTradeMapper.updateByPrimaryKey(ct);
		}else if(status.equals(CompanyTradeStatus.FAIL)){
			//??????????????????
			if(CompanyTradeStatus.SUCCESS.equals(ct.getStatus()) || CompanyTradeStatus.CLOSE.equals(ct.getStatus())){
				throw new PaymentException("?????????????????????");
			}
			ct.setStatus(CompanyTradeStatus.FAIL);
			ct.setFailReason(companyTrade.getFailReason());
			ct.setOperator(companyTrade.getOperator());
			ct.setOperattime(new Timestamp(System.currentTimeMillis()));
			
			this.companyTradeMapper.updateByPrimaryKey(ct);
		}else if(status.equals(CompanyTradeStatus.CHECKING)){
			//???????????????
			if(!CompanyTradeStatus.FAIL.equals(ct.getStatus())){
				throw new PaymentException("?????????????????????");
			}
			ct.setStatus(CompanyTradeStatus.CHECKING);
			ct.setOperattime(new Timestamp(System.currentTimeMillis()));
			
			this.companyTradeMapper.updateByPrimaryKey(ct);
		}else if(status.equals(CompanyTradeStatus.CHECKED)){
			//???????????????
			if(!CompanyTradeStatus.CHECKING.equals(ct.getStatus()) && !CompanyTradeStatus.FAIL.equals(ct.getStatus())){
				throw new PaymentException("?????????????????????");
			}
			ct.setStatus(CompanyTradeStatus.CHECKED);
			ct.setFailReason(null);
			ct.setOperator(companyTrade.getOperator());
			ct.setOperattime(new Timestamp(System.currentTimeMillis()));
			
			this.companyTradeMapper.updateByPrimaryKey(ct);
		}else if(status.equals(CompanyTradeStatus.SUCCESS)){
			//??????????????????
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			
			if(!CompanyTradeStatus.CHECKED.equals(ct.getStatus())){
				throw new PaymentException("?????????????????????");
			}
			
			if(CompanyTradeType.WITHDRAW.equals(ct.getType())){
				//????????????
				ct.setStatus(CompanyTradeStatus.SUCCESS);
				ct.setProof(companyTrade.getProof());
				ct.setOperator(companyTrade.getOperator());
				ct.setOperattime(timestamp);
				this.companyTradeMapper.updateByPrimaryKey(ct);
				
				//????????????
				ca.setBalanceLock(ca.getBalanceLock().subtract(ct.getTotalAmount()));
				this.companyAccountDao.update(ca);
				
				//??????????????????
				CompanyAccountHistory cah = new CompanyAccountHistory();
				cah.setUuid(UUID.randomUUID().toString());
				cah.setCompany(ct.getCompany());
				cah.setCompanyBankcard(ct.getCompanyBankcard());
				cah.setType(CompanyAccountHistoryType.COMPANY_WITHDRAW);
				cah.setOrderInfo(ct.getUuid());
				cah.setOrderNum(ct.getOrderNum());
				cah.setPoundage(ct.getPoundage());
				cah.setAmount(ct.getTotalAmount());
				cah.setCompanyData(ct.getData());
				cah.setBalance(ca.getBalance().add(ca.getBalanceLock()));
				cah.setSubmittime(ct.getCreatetime());
				cah.setCreatetime(timestamp);
				this.companyAccountHistoryDao.insert(cah);
			}else if(CompanyTradeType.RECHARGE.equals(ct.getType())){
				//????????????
				ct.setStatus(CompanyTradeStatus.SUCCESS);
				ct.setOperator(companyTrade.getOperator());
				ct.setOperattime(timestamp);
				this.companyTradeMapper.updateByPrimaryKey(ct);
				
				//????????????
				ca.setBalance(ca.getBalance().add(ct.getAmount()));
				this.companyAccountDao.update(ca);
				
				//??????????????????
				CompanyAccountHistory cah = new CompanyAccountHistory();
				cah.setUuid(UUID.randomUUID().toString());
				cah.setCompany(ct.getCompany());
				cah.setType(CompanyAccountHistoryType.COMPANY_RECHARGE);
				cah.setOrderInfo(ct.getUuid());
				cah.setOrderNum(ct.getOrderNum());
				cah.setPoundage(ct.getPoundage());
				cah.setAmount(ct.getAmount());
				cah.setCompanyData(ct.getData());
				cah.setBalance(ca.getBalance().add(ca.getBalanceLock()));
				cah.setSubmittime(ct.getCreatetime());
				cah.setCreatetime(timestamp);
				this.companyAccountHistoryDao.insert(cah);
			}
		}
	}
}
