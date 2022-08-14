package cn.zeppin.product.score.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.zeppin.product.score.entity.FrontUserAccount;
import cn.zeppin.product.score.entity.FrontUserBet;
import cn.zeppin.product.score.entity.FrontUserBet.FrontUserBetStatus;
import cn.zeppin.product.score.entity.FrontUserBetDetail;
import cn.zeppin.product.score.entity.FrontUserHistory;
import cn.zeppin.product.score.entity.FrontUserHistory.FrontUserHistoryStatus;
import cn.zeppin.product.score.entity.FrontUserHistory.FrontUserHistoryType;
import cn.zeppin.product.score.entity.Orderinfo;
import cn.zeppin.product.score.entity.Orderinfo.OrderinfoStatus;
import cn.zeppin.product.score.entity.Orderinfo.OrderinfoType;
import cn.zeppin.product.score.mapper.FrontUserBetMapper;
import cn.zeppin.product.score.service.FrontUserAccountService;
import cn.zeppin.product.score.service.FrontUserBetDetailService;
import cn.zeppin.product.score.service.FrontUserBetService;
import cn.zeppin.product.score.service.FrontUserHistoryService;
import cn.zeppin.product.score.service.FrontUserService;
import cn.zeppin.product.score.service.OrderinfoService;
import cn.zeppin.product.score.util.Utlity;
import cn.zeppin.product.score.vo.back.StatusCountVO;

@Service("frontUserBetService")
public class FrontUserBetServiceImpl implements FrontUserBetService{
	
	@Autowired
	private FrontUserBetMapper frontUserBetMapper;

	@Autowired
	private FrontUserBetDetailService frontUserBetDetailService;
	
	@Autowired
	private FrontUserService frontUserService;
	
	@Autowired
	private FrontUserAccountService frontUserAccountService;
	
	@Autowired
	private FrontUserHistoryService frontUserHistoryService;
	
	@Autowired
    private OrderinfoService orderinfoService;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserBetMapper.getCountByParams(params);
	}
	
	@Override
	public List<FrontUserBet> getListByParams(Map<String, Object> params) {
		return frontUserBetMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="frontUserBet",key="'frontUserBet:' + #key")
	public FrontUserBet get(String key) {
		return frontUserBetMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(FrontUserBet frontUserBet) {
		return frontUserBetMapper.insert(frontUserBet);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserBet", key = "'frontUserBet:' + #key")})
	public int delete(String key) {
		return frontUserBetMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserBet", key = "'frontUserBet:' + #frontUserBet.uuid")})
	public int update(FrontUserBet frontUserBet) {
		return frontUserBetMapper.updateByPrimaryKey(frontUserBet);
	}

	@Override
	public void bet(FrontUserAccount fua, FrontUserBet fub, List<FrontUserBetDetail> listFubd) {
		this.frontUserBetMapper.insert(fub);
		for(FrontUserBetDetail fubd : listFubd) {
			this.frontUserBetDetailService.insert(fubd);
		}
		fua.setBalanceFree(fua.getBalanceFree().subtract(fub.getPrice()));
		fua.setBalanceLock(fua.getBalanceLock().add(fub.getPrice()));
		this.frontUserAccountService.update(fua);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "frontUserBet", key = "'frontUserBet:' + #frontUserBet.uuid")})
	public void comfirmFrontUserBet(FrontUserBet frontUserBet) {
		FrontUserAccount fua = this.frontUserAccountService.getByFrontUser(frontUserBet.getFrontUser());
		if(fua != null && fua.getBalanceLock().compareTo(frontUserBet.getPrice()) >= 0){
			frontUserBet.setStatus(FrontUserBetStatus.CONFIRM);
			fua.setBalanceLock(fua.getBalanceLock().subtract(frontUserBet.getPrice()));
			
			Orderinfo order = new Orderinfo();
			order.setUuid(UUID.randomUUID().toString());
			order.setType(OrderinfoType.USER_BET);
			order.setMessage("");
			order.setFrontUser(fua.getFrontUser());
			order.setOrderNum(Utlity.getOrderNumStr(Utlity.BILL_DEVICE_APP,Utlity.BILL_PAYTYPE_SCORE,Utlity.BILL_PURPOSE_BET));
			order.setFee(frontUserBet.getPrice());
			order.setStatus(OrderinfoStatus.SUCCESS);
			order.setCreatetime(frontUserBet.getChecktime());
			
			FrontUserHistory fuh = new FrontUserHistory();
			fuh.setUuid(UUID.randomUUID().toString());
			fuh.setType(FrontUserHistoryType.USER_BET);
			fuh.setOrderId(order.getUuid());
			fuh.setOrderNum(order.getOrderNum());
			fuh.setOrderType(OrderinfoType.USER_BET);
			fuh.setFrontUser(frontUserBet.getFrontUser());
			fuh.setFrontUserAccount(fua.getUuid());
			fuh.setIncome(BigDecimal.ZERO);
			fuh.setPay(frontUserBet.getPrice());
			fuh.setBalance(fua.getBalanceFree());
			fuh.setPoundage(BigDecimal.ZERO);
			fuh.setFrontUserBet(frontUserBet.getUuid());
			fuh.setStatus(FrontUserHistoryStatus.SUCCESS);
			fuh.setOperator(frontUserBet.getChecker());
			fuh.setOperattime(frontUserBet.getChecktime());
			fuh.setCreatetime(frontUserBet.getChecktime());
			
			this.orderinfoService.insert(order);
			this.frontUserHistoryService.insert(fuh);
			this.frontUserBetMapper.updateByPrimaryKey(frontUserBet);
			this.frontUserAccountService.update(fua);
		}
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "frontUserBet", key = "'frontUserBet:' + #frontUserBet.uuid")})
	public void refundFrontUserBet(FrontUserBet frontUserBet) {
		FrontUserAccount fua = this.frontUserAccountService.getByFrontUser(frontUserBet.getFrontUser());
		if(fua != null && fua.getBalanceLock().compareTo(frontUserBet.getPrice()) >= 0){
			frontUserBet.setStatus(FrontUserBetStatus.FAILED);
			fua.setBalanceLock(fua.getBalanceLock().subtract(frontUserBet.getPrice()));
			fua.setBalanceFree(fua.getBalanceFree().add(frontUserBet.getPrice()));
			
			this.frontUserBetMapper.updateByPrimaryKey(frontUserBet);
			this.frontUserAccountService.update(fua);
		}
	}

	@Override
	public List<StatusCountVO> getStatusList() {
		return frontUserBetMapper.getStatusList();
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "frontUserBet", key = "'frontUserBet:' + #frontUserBet.uuid")})
	public void lottery(FrontUserAccount fua, FrontUserBet frontUserBet, FrontUserHistory fuh) {
		this.frontUserBetMapper.updateByPrimaryKey(frontUserBet);
		this.frontUserAccountService.update(fua);
		this.frontUserHistoryService.insert(fuh);
	}
}
