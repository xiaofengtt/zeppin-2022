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
import com.product.worldpay.dao.NoticeInfoDao;
import com.product.worldpay.dao.UserRechargeDao;
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
import com.product.worldpay.entity.NoticeInfo;
import com.product.worldpay.entity.NoticeInfo.NoticeInfoStatus;
import com.product.worldpay.entity.UserRecharge;
import com.product.worldpay.entity.UserRecharge.UserRechargeStatus;
import com.product.worldpay.mapper.UserRechargeMapper;
import com.product.worldpay.rabbetmq.RabbitService;
import com.product.worldpay.util.JSONUtils;
import com.product.worldpay.vo.system.StatusCountVO;

@Component
public class UserRechargeDaoImpl implements UserRechargeDao{
	
	@Autowired
	private UserRechargeMapper userRechargeMapper;
	
	@Autowired
	private NoticeInfoDao noticeInfoDao;
	
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
		return userRechargeMapper.getCountByParams(params);
	}
	
	@Override
	public List<UserRecharge> getListByParams(Map<String, Object> params) {
		return userRechargeMapper.getListByParams(params);
	}
    
    @Override
	@Cacheable(cacheNames="userRecharge",key="'userRecharge:' + #key")
	public UserRecharge get(String key) {
		return userRechargeMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(UserRecharge userRecharge) {
		return userRechargeMapper.insert(userRecharge);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "userRecharge", key = "'userRecharge:' + #key")})
	public int delete(String key) {
		return userRechargeMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "userRecharge", key = "'userRecharge:' + #userRecharge.uuid")})
	public int update(UserRecharge userRecharge) {
		return userRechargeMapper.updateByPrimaryKey(userRecharge);
	}

	@Override
	public UserRecharge getByOrderNum(String orderNum) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("orderNum", orderNum);
		List<UserRecharge> urList = this.getListByParams(searchMap);
		if(urList != null && urList.size() > 0){
			return urList.get(0);
		}else{
			return null;
		}
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "userRecharge", key = "'userRecharge:' + #userRecharge.uuid")})
	public void processOrder(UserRecharge userRecharge, NoticeInfo noticeInfo, String status) {
		UserRecharge ur = this.userRechargeMapper.selectByPrimaryKey(userRecharge.getUuid());
		if(!userRecharge.getStatus().equals(ur.getStatus())){
			return;
		}
		
		if(status.equals(UserRechargeStatus.CLOSE)){
			if(!UserRechargeStatus.CLOSE.equals(ur.getStatus()) && !UserRechargeStatus.SUCCESS.equals(ur.getStatus())){
				ur.setStatus(UserRechargeStatus.CLOSE);
				ur.setOperator(userRecharge.getOperator());
				ur.setOperattime(new Timestamp(System.currentTimeMillis()));
				if(noticeInfo != null){
					ur.setNoticeInfo(noticeInfo.getUuid());
				}
				this.userRechargeMapper.updateByPrimaryKey(ur);
			}
		}else if(status.equals(UserRechargeStatus.FAIL)){
			if(!UserRechargeStatus.SUCCESS.equals(ur.getStatus())){
				ur.setStatus(UserRechargeStatus.FAIL);
				ur.setFailReason(userRecharge.getFailReason());
				ur.setOperator(userRecharge.getOperator());
				ur.setOperattime(new Timestamp(System.currentTimeMillis()));
				if(noticeInfo != null){
					ur.setNoticeInfo(noticeInfo.getUuid());
				}
				this.userRechargeMapper.updateByPrimaryKey(ur);
			}
		}else if(status.equals(UserRechargeStatus.SUCCESS)){
			if(!UserRechargeStatus.SUCCESS.equals(ur.getStatus())){
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				
				//用户充值订单
				ur.setStatus(UserRechargeStatus.SUCCESS);
				ur.setTransData(userRecharge.getTransData());
				ur.setFailReason(null);
				ur.setProof(userRecharge.getProof());
				ur.setOperator(userRecharge.getOperator());
				ur.setOperattime(new Timestamp(System.currentTimeMillis()));
				if(noticeInfo != null){
					ur.setNoticeInfo(noticeInfo.getUuid());
				}
				this.userRechargeMapper.updateByPrimaryKey(ur);
				
				//商户账户
				CompanyChannel cch = this.companyChannelDao.get(ur.getCompanyChannel());
				cch.setBalance(cch.getBalance().add(ur.getAmount()));
				this.companyChannelDao.update(cch);
				
				//渠道账户
				ChannelAccount cha = this.channelAccountDao.get(ur.getChannelAccount());
				BigDecimal chaPoundage = BigDecimal.ZERO;
				if(cha.getPoundage() != null){
					chaPoundage = cha.getPoundage();
				}
				if(cha.getPoundageRate() != null){
					chaPoundage = ur.getTotalAmount().multiply(cha.getPoundageRate()).setScale(0, BigDecimal.ROUND_HALF_UP);
				}
				cha.setBalance(cha.getBalance().add(ur.getTotalAmount().subtract(chaPoundage)));
				this.channelAccountDao.update(cha);
				
				//商户账户流水
				CompanyAccountHistory cah = new CompanyAccountHistory();
				cah.setUuid(UUID.randomUUID().toString());
				cah.setChannel(ur.getChannel());
				cah.setChannelAccount(ur.getChannelAccount());
				cah.setCompany(ur.getCompany());
				cah.setCompanyChannel(ur.getCompanyChannel());
				cah.setType(CompanyAccountHistoryType.USER_RECHARGE);
				cah.setCurrency(ur.getCurrency());
				cah.setOrderInfo(ur.getUuid());
				cah.setOrderNum(ur.getOrderNum());
				cah.setPoundage(ur.getPoundage());
				cah.setAmount(ur.getAmount());
				cah.setCompanyOrderNum(ur.getCompanyOrderNum());
				cah.setCompanyData(ur.getCompanyData());
				cah.setBalance(cch.getBalance().add(cch.getBalanceLock()));
				cah.setSubmittime(ur.getCreatetime());
				cah.setCreatetime(timestamp);
				this.companyAccountHistoryDao.insert(cah);
				
				//渠道账户流水
				ChannelAccountHistory chah = new ChannelAccountHistory();
				chah.setUuid(UUID.randomUUID().toString());
				chah.setChannel(ur.getChannel());
				chah.setChannelAccount(ur.getChannelAccount());
				chah.setCompany(ur.getCompany());
				chah.setType(ChannelAccountHistoryType.USER_RECHARGE);
				chah.setCurrency(ur.getCurrency());
				chah.setOrderInfo(ur.getUuid());
				chah.setOrderNum(ur.getOrderNum());
				chah.setPoundage(chaPoundage);
				chah.setAmount(ur.getTotalAmount().subtract(chaPoundage));
				chah.setBalance(cha.getBalance());
				chah.setCreatetime(timestamp);
				this.channelAccountHistoryDao.insert(chah);
				
				//日统计
				ChannelAccountDaily cad = this.channelAccountDailyDao.get(cha.getUuid());
				if(cad == null){
					cad = new ChannelAccountDaily();
					cad.setUuid(cha.getUuid());
					cad.setAmount(ur.getTotalAmount());
					this.channelAccountDailyDao.insert(cad);
				}else{
					cad.setAmount(cad.getAmount().add(ur.getTotalAmount()));
					this.channelAccountDailyDao.update(cad);
				}
				
				//回调
				Map<String, String> bodyMap = new HashMap<String, String>();
				bodyMap.put("company", ur.getCompany());
				bodyMap.put("channel", ur.getCompanyChannel());
				bodyMap.put("orderNum", ur.getCompanyOrderNum());
				bodyMap.put("paymentOrderNum", ur.getOrderNum());
				bodyMap.put("currency", ur.getCurrency());
				bodyMap.put("amount", ur.getTotalAmount().toString());
				bodyMap.put("poundage", ur.getPoundage().toString());
				bodyMap.put("passbackParams", ur.getCompanyData());
				bodyMap.put("status", ur.getStatus());
				
				Callback callback = new Callback();
				callback.setUuid(UUID.randomUUID().toString());
				callback.setType(CallbackType.USER_RECHARGE);
				callback.setChannel(ur.getChannel());
				callback.setOrderInfo(ur.getUuid());
				callback.setBody(JSONUtils.obj2json(bodyMap));
				callback.setUrl(ur.getCompanyNotifyUrl());
				callback.setTimes(0);
				callback.setStatus(CallbackStatus.NORMAL);
				callback.setCreatetime(timestamp);
				this.callbackDao.insert(callback);
				this.rabbitService.sendCallback(callback);
			}
		}
		
		if(noticeInfo != null){
			noticeInfo.setStatus(NoticeInfoStatus.SUCCESS);
			this.noticeInfoDao.update(noticeInfo);
		}
	}

	@Override
	public List<StatusCountVO> getCheckingChannelList() {
		return userRechargeMapper.getCheckingChannelList();
	}
}
