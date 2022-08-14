package cn.product.treasuremall.dao.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
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

import cn.product.treasuremall.dao.AdminOffsetOrderDao;
import cn.product.treasuremall.dao.CapitalAccountDao;
import cn.product.treasuremall.dao.CapitalAccountHistoryDao;
import cn.product.treasuremall.dao.CapitalAccountStatisticsDao;
import cn.product.treasuremall.dao.FrontUserAccountDao;
import cn.product.treasuremall.dao.FrontUserHistoryDao;
import cn.product.treasuremall.dao.FrontUserRechargeOrderDao;
import cn.product.treasuremall.dao.FrontUserVoucherDao;
import cn.product.treasuremall.entity.AdminOffsetOrder;
import cn.product.treasuremall.entity.CapitalAccount;
import cn.product.treasuremall.entity.CapitalAccountHistory;
import cn.product.treasuremall.entity.CapitalAccountHistory.CapitalAccountHistoryType;
import cn.product.treasuremall.entity.FrontUserRechargeOrder.FrontUserRechargeOrderStatus;
import cn.product.treasuremall.entity.CapitalAccountStatistics;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUserAccount;
import cn.product.treasuremall.entity.FrontUserHistory;
import cn.product.treasuremall.entity.FrontUserRechargeOrder;
import cn.product.treasuremall.entity.FrontUserVoucher;
import cn.product.treasuremall.entity.ActivityInfo.ActivityInfoName;
import cn.product.treasuremall.mapper.FrontUserRechargeOrderMapper;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.util.pay.ActivityPayUtil;

@Component
public class FrontUserRechargeOrderDaoImpl implements FrontUserRechargeOrderDao{
	
	@Autowired
	private FrontUserRechargeOrderMapper frontUserRechargeOrderMapper;
	
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
	private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
	private CapitalAccountStatisticsDao capitalAccountStatisticsDao;
	
	@Autowired
	private CapitalAccountDao capitalAccountDao;
	
	@Autowired
	private CapitalAccountHistoryDao capitalAccountHistoryDao;
    
    @Autowired
    private ActivityPayUtil activityPayUtil;
    
    @Autowired
    private FrontUserVoucherDao frontUserVoucherDao;

	@Autowired
	private AdminOffsetOrderDao adminOffsetOrderDao;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserRechargeOrderMapper.getCountByParams(params);
	}
	
	@Override
	public List<FrontUserRechargeOrder> getListByParams(Map<String, Object> params) {
		return frontUserRechargeOrderMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="frontUserRechargeOrder",key="'frontUserRechargeOrder:' + #key")
	public FrontUserRechargeOrder get(String key) {
		return frontUserRechargeOrderMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(FrontUserRechargeOrder frontUserRechargeOrder) {
		return frontUserRechargeOrderMapper.insert(frontUserRechargeOrder);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserRechargeOrder", key = "'frontUserRechargeOrder:' + #key"),
			@CacheEvict(value = "isPartakeFirstcharge", key = "'isPartakeFirstcharge:' + #frontUserRechargeOrder.frontUser")})
	public int delete(String key) {
		return frontUserRechargeOrderMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserRechargeOrder", key = "'frontUserRechargeOrder:' + #frontUserRechargeOrder.uuid"),
			@CacheEvict(value = "isPartakeFirstcharge", key = "'isPartakeFirstcharge:' + #frontUserRechargeOrder.frontUser"),
			@CacheEvict(value = "userDayPreRechargeByCapitalAccount", 
				key = "'userDayPreRechargeByCapitalAccount:' + #frontUserRechargeOrder.frontUser + '_' + #frontUserRechargeOrder.capitalAccount")})
	public int update(FrontUserRechargeOrder frontUserRechargeOrder) {
		return frontUserRechargeOrderMapper.updateByPrimaryKey(frontUserRechargeOrder);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "frontUserRechargeOrder", key = "'frontUserRechargeOrder:' + #frontUserRechargeOrder.uuid"),
			@CacheEvict(value = "isPartakeFirstcharge", key = "'isPartakeFirstcharge:' + #frontUserRechargeOrder.frontUser"),
			@CacheEvict(value = "userDayPreRechargeByCapitalAccount", 
				key = "'userDayPreRechargeByCapitalAccount:' + #frontUserRechargeOrder.frontUser + '_' + #frontUserRechargeOrder.capitalAccount")})
	public void check(FrontUserRechargeOrder frontUserRechargeOrder, FrontUserAccount frontUserAccount, FrontUserHistory fuh) throws ParseException {
		CapitalAccountStatistics cas = this.capitalAccountStatisticsDao.get(frontUserRechargeOrder.getCapitalAccount());
		CapitalAccount ca = this.capitalAccountDao.get(frontUserRechargeOrder.getCapitalAccount());
		//保存企业流水
		//渠道账户流水儿
		//渠道账户流水儿
		Long serialNum = Utlity.getOrderNum();
		CapitalAccountHistory cah = new CapitalAccountHistory();
		cah.setUuid(UUID.randomUUID().toString());
		cah.setCapitalAccount(cas.getCapitalAccount());
		cah.setCapitalPlatform(ca.getCapitalPlatform());
		cah.setSerialNum(serialNum);
		cah.setOrderId(frontUserRechargeOrder.getUuid());
		cah.setOrderNum(frontUserRechargeOrder.getOrderNum()+"");
		cah.setOrderType(frontUserRechargeOrder.getType());
		cah.setBalanceBefore(cas.getBalance());
		cah.setBalanceAfter(cas.getBalance().add(frontUserRechargeOrder.getAmount()));
		cah.setAmount(frontUserRechargeOrder.getAmount());
		cah.setPoundage(BigDecimal.ZERO);
		cah.setType(CapitalAccountHistoryType.USER_RECHARGE);
		cah.setCreatetime(fuh.getCreatetime());
		cah.setReason("用户充值");
		cah.setRemark(frontUserRechargeOrder.getRemark());
		
		//计算手续费
		BigDecimal amount = frontUserRechargeOrder.getAmount();
		BigDecimal poundage = amount.multiply(ca.getPoundageRate());//订单金额*通道手续费
		//渠道账户
		cas.setBalance(cas.getBalance().add(frontUserRechargeOrder.getAmount()).subtract(poundage));
		cas.setRechargeTimes(cas.getRechargeTimes() + 1);
		cas.setTotalRecharge(cas.getTotalRecharge().add(frontUserRechargeOrder.getAmount()));
		cas.setDailySum(cas.getDailySum().add(frontUserRechargeOrder.getAmount()).subtract(poundage));
		
		//20200604活动入账
		List<FrontUserVoucher> fuvList = new ArrayList<>();
		FrontUserHistory fuhAct = new FrontUserHistory();
		AdminOffsetOrder aoo = new AdminOffsetOrder();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		this.activityPayUtil.isActivity(frontUserRechargeOrder, frontUserAccount, fuvList, fuhAct, aoo, returnMap);
		fuhAct = (FrontUserHistory)returnMap.get("fuhAct");
		aoo = (AdminOffsetOrder)returnMap.get("aoo");
		frontUserAccount = (FrontUserAccount)returnMap.get("fua");
		frontUserRechargeOrder = (FrontUserRechargeOrder)returnMap.get("furo");


		this.frontUserRechargeOrderMapper.updateByPrimaryKey(frontUserRechargeOrder);
		this.frontUserAccountDao.update(frontUserAccount);
		if(fuh != null) {
			this.frontUserHistoryDao.insert(fuh);
		}
		
		this.capitalAccountHistoryDao.insert(cah);
		this.capitalAccountStatisticsDao.update(cas);
		
		//20200604活动入账
		if(fuvList.size() > 0) {
			this.frontUserVoucherDao.insert(fuvList);
		}
		if(!Utlity.checkStringNull(fuhAct.getUuid()) && !Utlity.checkStringNull(aoo.getUuid())) {
			this.adminOffsetOrderDao.insert(aoo);
			this.frontUserHistoryDao.insert(fuhAct);
		}
	}

	@Override
	public List<Map<String, Object>> getFirstListByParams(Map<String, Object> params) {
		return frontUserRechargeOrderMapper.getFirstListByParams(params);
	}

	/**
	 * 结果插入缓存
	 */
	@Override
	@Cacheable(cacheNames="isPartakeFirstcharge",key="'isPartakeFirstcharge:' + #frontUser")
	public Boolean isPartakeFirstcharge(String frontUser) {
		//查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", frontUser);
		searchMap.put("activityId", ActivityInfoName.FIRSTCHARGE);
		searchMap.put("status", FrontUserRechargeOrderStatus.CHECKED);
		Integer count = this.frontUserRechargeOrderMapper.getCountByParams(searchMap);
		if(count != null && count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public BigDecimal getAmountByParams(String dateStr) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("starttime", dateStr + " 00:00:00");
		searchMap.put("endtime", dateStr + " 23:59:59");
		searchMap.put("status", FrontUserRechargeOrderStatus.CHECKED);
		BigDecimal amount = this.frontUserRechargeOrderMapper.getAmountByParams(searchMap);
		if(amount == null){
			amount = BigDecimal.ZERO;
		}
		return amount;
	}

	@Override
	@Cacheable(cacheNames="isInSevenDayFirstcharge",key="'isInSevenDayFirstcharge:' + #frontUser.uuid")
	public Boolean isInSevenDayFirstcharge(FrontUser frontUser) {
		String starttime = Utlity.timeSpanToString(frontUser.getCreatetime());
		String endtime = Utlity.timeSpanToString(new Timestamp(frontUser.getCreatetime().getTime()+1000*60*60*24*7L));
		//查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", frontUser);
		searchMap.put("status", FrontUserRechargeOrderStatus.CHECKED);
		searchMap.put("starttime", starttime);
		searchMap.put("endtime", endtime);
		Integer count = this.frontUserRechargeOrderMapper.getCountByParams(searchMap);
		if(count != null && count > 0) {
			return true;
		}
		return false;
	}

	@Override
	@Cacheable(cacheNames="userDayPreRechargeByCapitalAccount",key="'userDayPreRechargeByCapitalAccount:' + #frontUser + '_' + #capitalAccount")
	public Integer getCountByParams4Days(String frontUser, String capitalAccount, Integer days) {
		//查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", frontUser);
		searchMap.put("days", days);
		searchMap.put("status", FrontUserRechargeOrderStatus.CHECKED);
		searchMap.put("capitalAccount", capitalAccount);
		return this.frontUserRechargeOrderMapper.getCountByParams(searchMap) == null ? 0 : this.frontUserRechargeOrderMapper.getCountByParams(searchMap);
	}
}
