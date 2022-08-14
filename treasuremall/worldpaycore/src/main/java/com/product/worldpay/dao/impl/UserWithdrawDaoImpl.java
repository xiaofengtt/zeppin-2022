package com.product.worldpay.dao.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.product.worldpay.dao.CallbackDao;
import com.product.worldpay.dao.ChannelAccountDailyDao;
import com.product.worldpay.dao.ChannelAccountDao;
import com.product.worldpay.dao.ChannelAccountHistoryDao;
import com.product.worldpay.dao.CompanyAccountHistoryDao;
import com.product.worldpay.dao.CompanyChannelDao;
import com.product.worldpay.dao.UserWithdrawDao;
import com.product.worldpay.entity.Callback;
import com.product.worldpay.entity.Callback.CallbackStatus;
import com.product.worldpay.entity.Callback.CallbackType;
import com.product.worldpay.entity.ChannelAccount;
import com.product.worldpay.entity.ChannelAccountDaily;
import com.product.worldpay.entity.ChannelAccountHistory;
import com.product.worldpay.entity.ChannelAccountHistory.ChannelAccountHistoryType;
import com.product.worldpay.entity.CompanyAccountHistory;
import com.product.worldpay.entity.CompanyAccountHistory.CompanyAccountHistoryType;
import com.product.worldpay.entity.CompanyChannel;
import com.product.worldpay.entity.UserWithdraw;
import com.product.worldpay.entity.UserWithdraw.UserWithdrawStatus;
import com.product.worldpay.mapper.UserWithdrawMapper;
import com.product.worldpay.rabbetmq.RabbitService;
import com.product.worldpay.util.JSONUtils;
import com.product.worldpay.util.api.PaymentException;
import com.product.worldpay.vo.system.StatusCountVO;

@Component
public class UserWithdrawDaoImpl implements UserWithdrawDao{
	
	@Autowired
	private UserWithdrawMapper userWithdrawMapper;
	
	@Autowired
	private ChannelAccountDao channelAccountDao;
	
	@Autowired
	private ChannelAccountHistoryDao channelAccountHistoryDao;
	
	@Autowired
	private ChannelAccountDailyDao channelAccountDailyDao;
	
	@Autowired
	private CompanyChannelDao companyChannelDao;
	
	@Autowired
	private CompanyAccountHistoryDao companyAccountHistoryDao;
	
	@Autowired
	private CallbackDao callbackDao;
	
	@Autowired
	private RabbitService rabbitService;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return userWithdrawMapper.getCountByParams(params);
	}
	
	@Override
	public List<UserWithdraw> getListByParams(Map<String, Object> params) {
		return userWithdrawMapper.getListByParams(params);
	}
    
    @Override
	@Cacheable(cacheNames="userWithdraw",key="'userWithdraw:' + #key")
	public UserWithdraw get(String key) {
		return userWithdrawMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(UserWithdraw userWithdraw) {
		return userWithdrawMapper.insert(userWithdraw);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "userWithdraw", key = "'userWithdraw:' + #key")})
	public int delete(String key) {
		return userWithdrawMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "userWithdraw", key = "'userWithdraw:' + #userWithdraw.uuid")})
	public int update(UserWithdraw userWithdraw) {
		return userWithdrawMapper.updateByPrimaryKey(userWithdraw);
	}

	@Override
	@Transactional
	public void insertWithdraw(UserWithdraw userWithdraw) {
		this.userWithdrawMapper.insert(userWithdraw);
		
		CompanyChannel cch = this.companyChannelDao.get(userWithdraw.getCompanyChannel());
		cch.setBalance(cch.getBalance().subtract(userWithdraw.getTotalAmount()));
		cch.setBalanceLock(cch.getBalanceLock().add(userWithdraw.getTotalAmount()));
		this.companyChannelDao.update(cch);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "userWithdraw", key = "'userWithdraw:' + #userWithdraw.uuid")})
	public void processOrder(UserWithdraw userWithdraw, String status) throws PaymentException{
		UserWithdraw uw = this.userWithdrawMapper.selectByPrimaryKey(userWithdraw.getUuid());
		if(!userWithdraw.getStatus().equals(uw.getStatus())){
			 throw new PaymentException("this order was processed !");
		}
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		CompanyChannel cch = this.companyChannelDao.get(userWithdraw.getCompanyChannel());
		
		if(status.equals(UserWithdrawStatus.CLOSE)){
			if(UserWithdrawStatus.CLOSE.equals(uw.getStatus()) || UserWithdrawStatus.SUCCESS.equals(uw.getStatus())){
				throw new PaymentException("order status error !");
			}
			if(cch.getBalanceLock().compareTo(uw.getTotalAmount()) < 0){
				throw new PaymentException("The balance of account is insufficient !");
			}
			
			uw.setStatus(UserWithdrawStatus.CLOSE);
			uw.setOperator(userWithdraw.getOperator());
			uw.setOperattime(timestamp);
			this.userWithdrawMapper.updateByPrimaryKey(uw);
			
			cch.setBalance(cch.getBalance().add(uw.getTotalAmount()));
			cch.setBalanceLock(cch.getBalanceLock().subtract(uw.getTotalAmount()));
			this.companyChannelDao.update(cch);
			
			//回调
			Map<String, String> bodyMap = new HashMap<String, String>();
			bodyMap.put("company", uw.getCompany());
			bodyMap.put("channel", uw.getCompanyChannel());
			bodyMap.put("orderNum", uw.getCompanyOrderNum());
			bodyMap.put("paymentOrderNum", uw.getOrderNum());
			bodyMap.put("currency", uw.getCurrency());
			bodyMap.put("amount", uw.getTotalAmount().toString());
			bodyMap.put("poundage", uw.getPoundage().toString());
			bodyMap.put("passbackParams", uw.getCompanyData());
			bodyMap.put("status", uw.getStatus());
			
			Callback callback = new Callback();
			callback.setUuid(UUID.randomUUID().toString());
			callback.setType(CallbackType.USER_WITHDRAW);
			callback.setChannel(uw.getChannel());
			callback.setOrderInfo(uw.getUuid());
			callback.setBody(JSONUtils.obj2json(bodyMap));
			callback.setUrl(uw.getCompanyNotifyUrl());
			callback.setTimes(0);
			callback.setStatus(CallbackStatus.NORMAL);
			callback.setCreatetime(timestamp);
			this.callbackDao.insert(callback);
			this.rabbitService.sendCallback(callback);
		}else if(status.equals(UserWithdrawStatus.FAIL)){
			if(UserWithdrawStatus.SUCCESS.equals(uw.getStatus()) || UserWithdrawStatus.CLOSE.equals(uw.getStatus())){
				throw new PaymentException("order status error !");
			}
			uw.setStatus(UserWithdrawStatus.FAIL);
			uw.setFailReason(userWithdraw.getFailReason());
			uw.setOperator(userWithdraw.getOperator());
			uw.setOperattime(timestamp);
			
			this.userWithdrawMapper.updateByPrimaryKey(uw);
			
			//回调
			Map<String, String> bodyMap = new HashMap<String, String>();
			bodyMap.put("company", uw.getCompany());
			bodyMap.put("channel", uw.getCompanyChannel());
			bodyMap.put("orderNum", uw.getCompanyOrderNum());
			bodyMap.put("paymentOrderNum", uw.getOrderNum());
			bodyMap.put("currency", uw.getCurrency());
			bodyMap.put("amount", uw.getTotalAmount().toString());
			bodyMap.put("poundage", uw.getPoundage().toString());
			bodyMap.put("passbackParams", uw.getCompanyData());
			bodyMap.put("status", uw.getStatus());
			
			Callback callback = new Callback();
			callback.setUuid(UUID.randomUUID().toString());
			callback.setType(CallbackType.USER_WITHDRAW);
			callback.setChannel(uw.getChannel());
			callback.setOrderInfo(uw.getUuid());
			callback.setBody(JSONUtils.obj2json(bodyMap));
			callback.setUrl(uw.getCompanyNotifyUrl());
			callback.setTimes(0);
			callback.setStatus(CallbackStatus.NORMAL);
			callback.setCreatetime(uw.getOperattime());
			this.callbackDao.insert(callback);
			this.rabbitService.sendCallback(callback);
		}else if(status.equals(UserWithdrawStatus.CHECKING)){
			if(!UserWithdrawStatus.FAIL.equals(uw.getStatus())){
				throw new PaymentException("order status error !");
			}
			uw.setStatus(UserWithdrawStatus.CHECKING);
			uw.setOperattime(timestamp);
			
			this.userWithdrawMapper.updateByPrimaryKey(uw);
		}else if(status.equals(UserWithdrawStatus.CHECKED)){
			if(!UserWithdrawStatus.CHECKING.equals(uw.getStatus()) && !UserWithdrawStatus.FAIL.equals(uw.getStatus())){
				throw new PaymentException("order status error !");
			}
			uw.setStatus(UserWithdrawStatus.CHECKED);
			uw.setFailReason(null);
			uw.setChannelAccount(userWithdraw.getChannelAccount());
			uw.setOperator(userWithdraw.getOperator());
			uw.setOperattime(timestamp);
			
			this.userWithdrawMapper.updateByPrimaryKey(uw);
		}else if(status.equals(UserWithdrawStatus.SUCCESS)){
			if(!UserWithdrawStatus.CHECKED.equals(uw.getStatus())){
				throw new PaymentException("order status error !");
			}
			if(cch.getBalanceLock().compareTo(uw.getTotalAmount()) < 0){
				throw new PaymentException("The balance of account is insufficient !");
			}
			
			//提现订单
			uw.setStatus(UserWithdrawStatus.SUCCESS);
			uw.setProof(userWithdraw.getProof());
			uw.setOperator(userWithdraw.getOperator());
			uw.setOperattime(timestamp);
			this.userWithdrawMapper.updateByPrimaryKey(uw);
			
			//商户账户
			cch.setBalanceLock(cch.getBalanceLock().subtract(uw.getTotalAmount()));
			this.companyChannelDao.update(cch);
			
			//渠道账户
			ChannelAccount cha = this.channelAccountDao.get(uw.getChannelAccount());
			BigDecimal chaPoundage = BigDecimal.ZERO;
			if(cha.getPoundage() != null){
				chaPoundage = cha.getPoundage();
			}
			if(cha.getPoundageRate() != null){
				chaPoundage = uw.getAmount().multiply(cha.getPoundageRate()).setScale(0, BigDecimal.ROUND_HALF_UP);
			}
			cha.setBalance(cha.getBalance().subtract(uw.getAmount().add(chaPoundage)));
			this.channelAccountDao.update(cha);
			
			//商户账户流水
			CompanyAccountHistory cah = new CompanyAccountHistory();
			cah.setUuid(UUID.randomUUID().toString());
			cah.setChannel(uw.getChannel());
			cah.setChannelAccount(uw.getChannelAccount());
			cah.setCompany(uw.getCompany());
			cah.setCompanyChannel(uw.getCompanyChannel());
			cah.setType(CompanyAccountHistoryType.USER_WITHDRAW);
			cah.setCurrency(uw.getCurrency());
			cah.setOrderInfo(uw.getUuid());
			cah.setOrderNum(uw.getOrderNum());
			cah.setPoundage(uw.getPoundage());
			cah.setAmount(uw.getTotalAmount());
			cah.setCompanyOrderNum(uw.getCompanyOrderNum());
			cah.setCompanyData(uw.getCompanyData());
			cah.setBalance(cch.getBalance().add(cch.getBalanceLock()));
			cah.setSubmittime(uw.getCreatetime());
			cah.setCreatetime(timestamp);
			this.companyAccountHistoryDao.insert(cah);

			//渠道账户流水
			ChannelAccountHistory chah = new ChannelAccountHistory();
			chah.setUuid(UUID.randomUUID().toString());
			chah.setChannel(uw.getChannel());
			chah.setChannelAccount(uw.getChannelAccount());
			chah.setCompany(uw.getCompany());
			chah.setType(ChannelAccountHistoryType.USER_WITHDRAW);
			chah.setCurrency(uw.getCurrency());
			chah.setOrderInfo(uw.getUuid());
			chah.setOrderNum(uw.getOrderNum());
			chah.setPoundage(chaPoundage);
			chah.setAmount(uw.getAmount().add(chaPoundage));
			chah.setBalance(cha.getBalance());
			chah.setCreatetime(timestamp);
			this.channelAccountHistoryDao.insert(chah);
			
			//日统计
			ChannelAccountDaily cad = this.channelAccountDailyDao.get(userWithdraw.getChannelAccount());
			if(cad == null){
				cad = new ChannelAccountDaily();
				cad.setUuid(userWithdraw.getChannelAccount());
				cad.setAmount(uw.getAmount().add(chaPoundage));
				this.channelAccountDailyDao.insert(cad);
			}else{
				cad.setAmount(cad.getAmount().add(uw.getAmount().add(chaPoundage)));
				this.channelAccountDailyDao.update(cad);
			}
			
			//回调
			Map<String, String> bodyMap = new HashMap<String, String>();
			bodyMap.put("company", uw.getCompany());
			bodyMap.put("channel", uw.getCompanyChannel());
			bodyMap.put("orderNum", uw.getCompanyOrderNum());
			bodyMap.put("paymentOrderNum", uw.getOrderNum());
			bodyMap.put("currency", uw.getCurrency());
			bodyMap.put("amount", uw.getTotalAmount().toString());
			bodyMap.put("poundage", uw.getPoundage().toString());
			bodyMap.put("passbackParams", uw.getCompanyData());
			bodyMap.put("status", uw.getStatus());
			
			Callback callback = new Callback();
			callback.setUuid(UUID.randomUUID().toString());
			callback.setType(CallbackType.USER_WITHDRAW);
			callback.setChannel(uw.getChannel());
			callback.setOrderInfo(uw.getUuid());
			callback.setBody(JSONUtils.obj2json(bodyMap));
			callback.setUrl(uw.getCompanyNotifyUrl());
			callback.setTimes(0);
			callback.setStatus(CallbackStatus.NORMAL);
			callback.setCreatetime(timestamp);
			this.callbackDao.insert(callback);
			this.rabbitService.sendCallback(callback);
		}
	}
	
	@Override
	public List<StatusCountVO> getCheckingChannelList() {
		return userWithdrawMapper.getCheckingChannelList();
	}
}
