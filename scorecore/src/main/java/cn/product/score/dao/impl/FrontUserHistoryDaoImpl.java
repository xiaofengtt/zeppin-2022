package cn.product.score.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.score.dao.BankDao;
import cn.product.score.dao.FrontUserAccountDao;
import cn.product.score.dao.FrontUserDao;
import cn.product.score.dao.FrontUserHistoryDao;
import cn.product.score.dao.OrderinfoDao;
import cn.product.score.dao.SystemParamDao;
import cn.product.score.entity.CapitalPlatform.CapitalPlatformType;
import cn.product.score.entity.FrontUserAccount;
import cn.product.score.entity.FrontUserHistory;
import cn.product.score.entity.Orderinfo;
import cn.product.score.entity.Orderinfo.OrderinfoStatus;
import cn.product.score.entity.Orderinfo.OrderinfoType;
import cn.product.score.entity.SystemParam;
import cn.product.score.entity.SystemParam.SystemParamKey;
import cn.product.score.mapper.FrontUserHistoryMapper;
import cn.product.score.util.JSONUtils;
import cn.product.score.util.SendSmsNew;
import cn.product.score.util.Utlity;
import cn.product.score.vo.back.StatusCountVO;

/**
 */
@Component
public class FrontUserHistoryDaoImpl implements FrontUserHistoryDao{
	
	@Autowired
	private OrderinfoDao orderinfoDao;
	
	@Autowired
	private FrontUserHistoryMapper frontUserHistoryMapper;
	
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
	private BankDao bankDao;
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private SystemParamDao systemParamDao;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserHistoryMapper.getCountByParams(params);
	}
	
	@Override
	public List<FrontUserHistory> getListByParams(Map<String, Object> params) {
		return frontUserHistoryMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="frontUserHistory",key="'frontUserHistory:' + #key")
	public FrontUserHistory get(String key) {
		return frontUserHistoryMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(FrontUserHistory frontUserHistory) {
		return frontUserHistoryMapper.insert(frontUserHistory);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserHistory", key = "'frontUserHistory:' + #key")})
	public int delete(String key) {
		return frontUserHistoryMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserHistory", key = "'frontUserHistory:' + #frontUserHistory.uuid")})
	public int update(FrontUserHistory frontUserHistory) {
		return frontUserHistoryMapper.updateByPrimaryKey(frontUserHistory);
	}

	@Override
	@Transactional
	public void recharge(FrontUserHistory fuh, String type) {
		if(CapitalPlatformType.PERSONAL_BANKCARD.equals(type) || CapitalPlatformType.COMPANY_BANKCARD.equals(type)){
			Orderinfo order = new Orderinfo();
			order.setUuid(UUID.randomUUID().toString());
			order.setType(type);
			order.setMessage("");
			order.setFrontUser(fuh.getFrontUser());
			order.setOrderNum(Utlity.getOrderNumStr(Utlity.BILL_DEVICE_APP,Utlity.getOrderTypeByPlatformType(type),Utlity.BILL_PURPOSE_RECHARGE));
			order.setFee(fuh.getIncome());
			order.setStatus(OrderinfoStatus.NORMAL);
			order.setCreatetime(fuh.getCreatetime());
			
			fuh.setOrderId(order.getUuid());
			fuh.setOrderNum(order.getOrderNum());
			fuh.setOrderType(order.getType());
			
			this.orderinfoDao.insert(order);
			this.insert(fuh);
		}else if(CapitalPlatformType.COMPANY_ALIPAY.equals(type)){
			Orderinfo order = new Orderinfo();
			order.setUuid(UUID.randomUUID().toString());
			order.setType(type);
			order.setMessage("");
			order.setFrontUser(fuh.getFrontUser());
			order.setOrderNum(fuh.getOrderNum());
			order.setFee(fuh.getIncome());
			order.setStatus(OrderinfoStatus.NORMAL);
			order.setCreatetime(fuh.getCreatetime());
			
			fuh.setOrderId(order.getUuid());
			fuh.setOrderType(order.getType());
			
			this.orderinfoDao.insert(order);
			this.insert(fuh);
		}
	}

	@Override
	@Transactional
	public void withdraw(FrontUserHistory fuh) {
		Orderinfo order = new Orderinfo();
		order.setUuid(UUID.randomUUID().toString());
		order.setType(OrderinfoType.ARTIFICIAL);
		order.setMessage("");
		order.setFrontUser(fuh.getFrontUser());
		order.setOrderNum(Utlity.getOrderNumStr(Utlity.BILL_DEVICE_APP,Utlity.getOrderTypeByPlatformType(OrderinfoType.ARTIFICIAL),Utlity.BILL_PURPOSE_WITHDRAW));
		order.setFee(fuh.getIncome());
		order.setStatus(OrderinfoStatus.NORMAL);
		order.setCreatetime(fuh.getCreatetime());
		
		fuh.setOrderId(order.getUuid());
		fuh.setOrderNum(order.getOrderNum());
		fuh.setOrderType(order.getType());
		
		FrontUserAccount fua = this.frontUserAccountDao.get(fuh.getFrontUserAccount());
		fua.setBalanceFree(fua.getBalanceFree().subtract(fuh.getPay()));
		fua.setBalanceLock(fua.getBalanceLock().add(fuh.getPay()));
		
		this.frontUserAccountDao.update(fua);
		this.orderinfoDao.insert(order);
		this.insert(fuh);
		
		SystemParam messageFlag = this.systemParamDao.get(SystemParamKey.WITHDRAW_MESSAGE_FLAG);
		if(Boolean.valueOf(messageFlag.getParamValue())){
			SystemParam mobleParam = this.systemParamDao.get(SystemParamKey.WITHDRAW_NOTICE_MOBILE);
			Map<String, Object> mobileMap = JSONUtils.json2map(mobleParam.getParamValue());
			if(mobileMap.keySet().size() > 0){
				String mobiles = "";
				for(String mobile : mobileMap.keySet()){
					mobiles = mobiles + mobile + ",";
				}
				mobiles.substring(mobiles.length() - 1);
				SendSmsNew.sendSms("有用户发起提现了，请注意及时处理！", mobiles);
			}
		}
	}
	
	@Override
	public List<StatusCountVO> getStatusList(Map<String, Object> params) {
		return frontUserHistoryMapper.getStatusList(params);
	}
}
