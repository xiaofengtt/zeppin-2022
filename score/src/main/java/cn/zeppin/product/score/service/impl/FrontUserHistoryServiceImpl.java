package cn.zeppin.product.score.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.zeppin.product.score.entity.CapitalPlatform.CapitalPlatformType;
import cn.zeppin.product.score.entity.FrontUserAccount;
import cn.zeppin.product.score.entity.FrontUserHistory;
import cn.zeppin.product.score.entity.Orderinfo;
import cn.zeppin.product.score.entity.Orderinfo.OrderinfoStatus;
import cn.zeppin.product.score.entity.Orderinfo.OrderinfoType;
import cn.zeppin.product.score.entity.SystemParam;
import cn.zeppin.product.score.entity.SystemParam.SystemParamKey;
import cn.zeppin.product.score.mapper.FrontUserHistoryMapper;
import cn.zeppin.product.score.service.BankService;
import cn.zeppin.product.score.service.CapitalAccountHistoryService;
import cn.zeppin.product.score.service.CapitalAccountService;
import cn.zeppin.product.score.service.FrontUserAccountService;
import cn.zeppin.product.score.service.FrontUserBankcardService;
import cn.zeppin.product.score.service.FrontUserHistoryService;
import cn.zeppin.product.score.service.FrontUserService;
import cn.zeppin.product.score.service.OrderinfoService;
import cn.zeppin.product.score.service.SystemParamService;
import cn.zeppin.product.score.util.JSONUtils;
import cn.zeppin.product.score.util.SendSmsNew;
import cn.zeppin.product.score.util.Utlity;
import cn.zeppin.product.score.vo.back.StatusCountVO;

/**
 */
@Service("frontUserHistoryService")
public class FrontUserHistoryServiceImpl implements FrontUserHistoryService{
	
	@Autowired
	private OrderinfoService orderinfoService;
	
	@Autowired
	private FrontUserHistoryMapper frontUserHistoryMapper;
	
	@Autowired
	private FrontUserAccountService frontUserAccountService;
	
	@Autowired
	private FrontUserBankcardService frontUserBankcardService;
	
	@Autowired
	private BankService bankService;
	
	@Autowired
	private FrontUserService frontUserService;
	
	@Autowired
    private CapitalAccountService capitalAccountService;
	
	@Autowired
    private CapitalAccountHistoryService capitalAccountHistoryService;
	
	@Autowired
	private SystemParamService systemParamService;
	
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
			
			this.orderinfoService.insert(order);
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
			
			this.orderinfoService.insert(order);
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
		
		FrontUserAccount fua = this.frontUserAccountService.get(fuh.getFrontUserAccount());
		fua.setBalanceFree(fua.getBalanceFree().subtract(fuh.getPay()));
		fua.setBalanceLock(fua.getBalanceLock().add(fuh.getPay()));
		
		this.frontUserAccountService.update(fua);
		this.orderinfoService.insert(order);
		this.insert(fuh);
		
		SystemParam messageFlag = this.systemParamService.get(SystemParamKey.WITHDRAW_MESSAGE_FLAG);
		if(Boolean.valueOf(messageFlag.getParamValue())){
			SystemParam mobleParam = this.systemParamService.get(SystemParamKey.WITHDRAW_NOTICE_MOBILE);
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
