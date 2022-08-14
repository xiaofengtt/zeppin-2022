package cn.product.payment.service.impl;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.product.payment.entity.Callback;
import cn.product.payment.entity.ChannelAccount;
import cn.product.payment.entity.ChannelAccountHistory;
import cn.product.payment.entity.CompanyAccount;
import cn.product.payment.entity.CompanyAccountHistory;
import cn.product.payment.entity.UserWithdraw;
import cn.product.payment.entity.Callback.CallbackStatus;
import cn.product.payment.entity.Callback.CallbackType;
import cn.product.payment.entity.ChannelAccountHistory.ChannelAccountHistoryType;
import cn.product.payment.entity.CompanyAccountHistory.CompanyAccountHistoryType;
import cn.product.payment.entity.UserWithdraw.UserWithdrawStatus;
import cn.product.payment.mapper.UserWithdrawMapper;
import cn.product.payment.rabbetmq.RabbitService;
import cn.product.payment.service.CallbackService;
import cn.product.payment.service.ChannelAccountHistoryService;
import cn.product.payment.service.ChannelAccountService;
import cn.product.payment.service.CompanyAccountHistoryService;
import cn.product.payment.service.CompanyAccountService;
import cn.product.payment.service.CompanyChannelService;
import cn.product.payment.service.UserWithdrawService;
import cn.product.payment.util.JSONUtils;
import cn.product.payment.util.api.PaymentException;

@Service("userWithdrawService")
public class UserWithdrawServiceImpl implements UserWithdrawService{
	
	@Autowired
	private UserWithdrawMapper userWithdrawMapper;
	
	@Autowired
	private CompanyAccountService companyAccountService;
	
	@Autowired
	private ChannelAccountService channelAccountService;
	
	@Autowired
	private ChannelAccountHistoryService channelAccountHistoryService;
	
	@Autowired
	private CompanyChannelService companyChannelService;
	
	@Autowired
	private CompanyAccountHistoryService companyAccountHistoryService;
	
	@Autowired
	private CallbackService callbackService;
	
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
		
		CompanyAccount ca = this.companyAccountService.get(userWithdraw.getCompany());
		ca.setBalance(ca.getBalance().subtract(userWithdraw.getTotalAmount()));
		ca.setBalanceLock(ca.getBalanceLock().add(userWithdraw.getTotalAmount()));
		this.companyAccountService.update(ca);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "userWithdraw", key = "'userWithdraw:' + #userWithdraw.uuid")})
	public void processOrder(UserWithdraw userWithdraw, String status) throws PaymentException{
		UserWithdraw uw = this.get(userWithdraw.getUuid());
		if(!userWithdraw.getStatus().equals(uw.getStatus())){
			 throw new PaymentException("该记录已完成其他操作！");
		}
		
		CompanyAccount ca = this.companyAccountService.get(uw.getCompany());
		
		if(status.equals(UserWithdrawStatus.CLOSE)){
			if(UserWithdrawStatus.CLOSE.equals(uw.getStatus()) || UserWithdrawStatus.SUCCESS.equals(uw.getStatus())){
				throw new PaymentException("订单状态错误！");
			}
			if(ca.getBalanceLock().compareTo(uw.getTotalAmount()) < 0){
				throw new PaymentException("账户余额不足！");
			}
			uw.setStatus(UserWithdrawStatus.CLOSE);
			uw.setOperator(userWithdraw.getOperator());
			uw.setOperattime(new Timestamp(System.currentTimeMillis()));
			this.userWithdrawMapper.updateByPrimaryKey(uw);
			
			ca.setBalance(ca.getBalance().add(uw.getTotalAmount()));
			ca.setBalanceLock(ca.getBalanceLock().subtract(uw.getTotalAmount()));
			this.companyAccountService.update(ca);
		}else if(status.equals(UserWithdrawStatus.FAIL)){
			if(UserWithdrawStatus.SUCCESS.equals(uw.getStatus()) || UserWithdrawStatus.CLOSE.equals(uw.getStatus())){
				throw new PaymentException("订单状态错误！");
			}
			uw.setStatus(UserWithdrawStatus.FAIL);
			uw.setFailReason(userWithdraw.getFailReason());
			uw.setOperator(userWithdraw.getOperator());
			uw.setOperattime(new Timestamp(System.currentTimeMillis()));
			
			this.userWithdrawMapper.updateByPrimaryKey(uw);
			
			//回调
			Map<String, String> bodyMap = new HashMap<String, String>();
			bodyMap.put("company", uw.getCompany());
			bodyMap.put("channel", uw.getCompanyChannel());
			bodyMap.put("orderNum", uw.getCompanyOrderNum());
			bodyMap.put("paymentOrderNum", uw.getOrderNum());
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
			this.callbackService.insert(callback);
			this.rabbitService.sendCallback(callback);
		}else if(status.equals(UserWithdrawStatus.CHECKED)){
			if(!UserWithdrawStatus.CHECKING.equals(uw.getStatus()) && !UserWithdrawStatus.FAIL.equals(uw.getStatus())){
				throw new PaymentException("订单状态错误！");
			}
			uw.setStatus(UserWithdrawStatus.CHECKED);
			uw.setChannelAccount(userWithdraw.getChannelAccount());
			uw.setOperator(userWithdraw.getOperator());
			uw.setOperattime(new Timestamp(System.currentTimeMillis()));
			
			this.userWithdrawMapper.updateByPrimaryKey(uw);
		}else if(status.equals(UserWithdrawStatus.SUCCESS)){
			if(!UserWithdrawStatus.CHECKED.equals(uw.getStatus())){
				throw new PaymentException("订单状态错误！");
			}
			if(ca.getBalanceLock().compareTo(uw.getTotalAmount()) < 0){
				throw new PaymentException("账户余额不足！");
			}
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			
			//提现订单
			uw.setStatus(UserWithdrawStatus.SUCCESS);
			uw.setOperator(userWithdraw.getOperator());
			uw.setOperattime(timestamp);
			this.userWithdrawMapper.updateByPrimaryKey(uw);
			
			//企业账户
			ca.setBalanceLock(ca.getBalanceLock().subtract(uw.getTotalAmount()));
			this.companyAccountService.update(ca);
			
			//渠道账户
			ChannelAccount cha = this.channelAccountService.get(uw.getChannelAccount());
			BigDecimal chaPoundage = BigDecimal.ZERO;
			if(cha.getPoundage() != null){
				chaPoundage = cha.getPoundage();
			}
			if(cha.getPoundageRate() != null){
				chaPoundage = uw.getAmount().multiply(cha.getPoundageRate()).setScale(0, BigDecimal.ROUND_DOWN);
			}
			cha.setBalance(cha.getBalance().subtract(uw.getAmount().add(chaPoundage)));
			this.channelAccountService.update(cha);
			
			//企业账户流水
			CompanyAccountHistory cah = new CompanyAccountHistory();
			cah.setUuid(UUID.randomUUID().toString());
			cah.setChannel(uw.getChannel());
			cah.setChannelAccount(uw.getChannelAccount());
			cah.setCompany(uw.getCompany());
			cah.setCompanyChannel(uw.getCompanyChannel());
			cah.setType(CompanyAccountHistoryType.USER_WITHDRAW);
			cah.setOrderInfo(uw.getUuid());
			cah.setOrderNum(uw.getOrderNum());
			cah.setPoundage(uw.getPoundage());
			cah.setAmount(uw.getTotalAmount());
			cah.setCompanyOrderNum(uw.getCompanyOrderNum());
			cah.setCompanyData(uw.getCompanyData());
			cah.setBalance(ca.getBalance());
			cah.setSubmittime(uw.getCreatetime());
			cah.setCreatetime(timestamp);
			this.companyAccountHistoryService.insert(cah);

			//渠道账户流水
			ChannelAccountHistory chah = new ChannelAccountHistory();
			chah.setUuid(UUID.randomUUID().toString());
			chah.setChannel(uw.getChannel());
			chah.setChannelAccount(uw.getChannelAccount());
			chah.setCompany(uw.getCompany());
			chah.setType(ChannelAccountHistoryType.USER_WITHDRAW);
			chah.setOrderInfo(uw.getUuid());
			chah.setOrderNum(uw.getOrderNum());
			chah.setPoundage(chaPoundage);
			chah.setAmount(uw.getAmount().add(chaPoundage));
			chah.setBalance(cha.getBalance());
			chah.setCreatetime(timestamp);
			this.channelAccountHistoryService.insert(chah);
			
			//回调
			Map<String, String> bodyMap = new HashMap<String, String>();
			bodyMap.put("company", uw.getCompany());
			bodyMap.put("channel", uw.getCompanyChannel());
			bodyMap.put("orderNum", uw.getCompanyOrderNum());
			bodyMap.put("paymentOrderNum", uw.getOrderNum());
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
			this.callbackService.insert(callback);
			this.rabbitService.sendCallback(callback);
		}
	}
}
