package cn.product.score.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.score.dao.FrontUserAccountDao;
import cn.product.score.dao.FrontUserBetDao;
import cn.product.score.dao.FrontUserBetDetailDao;
import cn.product.score.dao.FrontUserHistoryDao;
import cn.product.score.dao.OrderinfoDao;
import cn.product.score.entity.FrontUserAccount;
import cn.product.score.entity.FrontUserBet;
import cn.product.score.entity.FrontUserBet.FrontUserBetStatus;
import cn.product.score.entity.FrontUserBetDetail;
import cn.product.score.entity.FrontUserHistory;
import cn.product.score.entity.FrontUserHistory.FrontUserHistoryStatus;
import cn.product.score.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.score.entity.Orderinfo;
import cn.product.score.entity.Orderinfo.OrderinfoStatus;
import cn.product.score.entity.Orderinfo.OrderinfoType;
import cn.product.score.mapper.FrontUserBetMapper;
import cn.product.score.util.Utlity;
import cn.product.score.vo.back.StatusCountVO;

@Component
public class FrontUserBetDaoImpl implements FrontUserBetDao{
	
	@Autowired
	private FrontUserBetMapper frontUserBetMapper;

	@Autowired
	private FrontUserBetDetailDao frontUserBetDetailDao;
	
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
	private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
    private OrderinfoDao orderinfoDao;
	
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
			this.frontUserBetDetailDao.insert(fubd);
		}
		fua.setBalanceFree(fua.getBalanceFree().subtract(fub.getPrice()));
		fua.setBalanceLock(fua.getBalanceLock().add(fub.getPrice()));
		this.frontUserAccountDao.update(fua);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "frontUserBet", key = "'frontUserBet:' + #frontUserBet.uuid")})
	public void comfirmFrontUserBet(FrontUserBet frontUserBet) {
		FrontUserAccount fua = this.frontUserAccountDao.getByFrontUser(frontUserBet.getFrontUser());
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
			
			this.orderinfoDao.insert(order);
			this.frontUserHistoryDao.insert(fuh);
			this.frontUserBetMapper.updateByPrimaryKey(frontUserBet);
			this.frontUserAccountDao.update(fua);
		}
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "frontUserBet", key = "'frontUserBet:' + #frontUserBet.uuid")})
	public void refundFrontUserBet(FrontUserBet frontUserBet) {
		FrontUserAccount fua = this.frontUserAccountDao.getByFrontUser(frontUserBet.getFrontUser());
		if(fua != null && fua.getBalanceLock().compareTo(frontUserBet.getPrice()) >= 0){
			frontUserBet.setStatus(FrontUserBetStatus.FAILED);
			fua.setBalanceLock(fua.getBalanceLock().subtract(frontUserBet.getPrice()));
			fua.setBalanceFree(fua.getBalanceFree().add(frontUserBet.getPrice()));
			
			this.frontUserBetMapper.updateByPrimaryKey(frontUserBet);
			this.frontUserAccountDao.update(fua);
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
		this.frontUserAccountDao.update(fua);
		this.frontUserHistoryDao.insert(fuh);
	}
}
