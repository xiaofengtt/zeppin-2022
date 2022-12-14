package cn.product.worldmall.dao.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.stripe.model.PaymentIntent;

import cn.product.worldmall.controller.base.TransactionException;
import cn.product.worldmall.dao.AdminOffsetOrderDao;
import cn.product.worldmall.dao.BankDao;
import cn.product.worldmall.dao.CapitalAccountDao;
import cn.product.worldmall.dao.CapitalAccountHistoryDao;
import cn.product.worldmall.dao.CapitalAccountStatisticsDao;
import cn.product.worldmall.dao.FrontUserAccountDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserHistoryDao;
import cn.product.worldmall.dao.FrontUserMessageDao;
import cn.product.worldmall.dao.FrontUserRechargeOrderDao;
import cn.product.worldmall.dao.FrontUserVoucherDao;
import cn.product.worldmall.dao.FrontUserWithdrawOrderDao;
import cn.product.worldmall.dao.PayNotifyInfoDao;
import cn.product.worldmall.dao.SystemParamDao;
import cn.product.worldmall.dao.WinningInfoDao;
import cn.product.worldmall.dao.WinningInfoReceiveDao;
import cn.product.worldmall.entity.AdminOffsetOrder;
import cn.product.worldmall.entity.CapitalAccount;
import cn.product.worldmall.entity.CapitalAccountHistory;
import cn.product.worldmall.entity.CapitalAccountHistory.CapitalAccountHistoryType;
import cn.product.worldmall.entity.CapitalAccountStatistics;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUser.FrontUserLevel;
import cn.product.worldmall.entity.FrontUser.FrontUserType;
import cn.product.worldmall.entity.FrontUserAccount;
import cn.product.worldmall.entity.FrontUserHistory;
import cn.product.worldmall.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.worldmall.entity.FrontUserMessage;
import cn.product.worldmall.entity.FrontUserRechargeOrder;
import cn.product.worldmall.entity.FrontUserRechargeOrder.FrontUserRechargeOrderStatus;
import cn.product.worldmall.entity.FrontUserVoucher;
import cn.product.worldmall.entity.FrontUserWithdrawOrder;
import cn.product.worldmall.entity.FrontUserWithdrawOrder.FrontUserWithdrawOrderStatus;
import cn.product.worldmall.entity.PayNotifyInfo;
import cn.product.worldmall.entity.PayNotifyInfo.PayNotifyInfoPayType;
import cn.product.worldmall.entity.PayNotifyInfo.PayNotifyInfoStatus;
import cn.product.worldmall.entity.SystemParam;
import cn.product.worldmall.entity.SystemParam.SystemParamKey;
import cn.product.worldmall.entity.WinningInfo;
import cn.product.worldmall.entity.WinningInfoReceive;
import cn.product.worldmall.entity.base.Constants;
import cn.product.worldmall.mapper.FrontUserHistoryMapper;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.SendSmsNew;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.util.pay.ActivityPayUtil;

/**
 */
@Component
public class FrontUserHistoryDaoImpl implements FrontUserHistoryDao{
	
	private final static Logger log = LoggerFactory.getLogger(FrontUserHistoryDaoImpl.class);
	
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

	@Autowired
	private AdminOffsetOrderDao adminOffsetOrderDao;

	@Autowired
	private FrontUserRechargeOrderDao frontUserRechargeOrderDao;

	@Autowired
	private FrontUserWithdrawOrderDao frontUserWithdrawOrderDao;
	
	@Autowired
	private CapitalAccountDao capitalAccountDao;
	
	@Autowired
	private CapitalAccountStatisticsDao capitalAccountStatisticsDao;
	
	@Autowired
	private CapitalAccountHistoryDao capitalAccountHistoryDao;
	
	@Autowired
	private PayNotifyInfoDao payNotifyInfoDao;
	
	@Autowired
	private WinningInfoDao WinningInfoDao;
	
	@Autowired
    private WinningInfoReceiveDao winningInfoReceiveDao;
	
    @Autowired
    private CuratorFramework curatorFramework;
    
    @Autowired
    private FrontUserMessageDao frontUserMessageDao;
    
    @Autowired
    private FrontUserVoucherDao frontUserVoucherDao;
    
    @Autowired
    private ActivityPayUtil activityPayUtil;
	
	
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
	@Cacheable(cacheNames="frontUserHistory",key="'frontUserHistory:' + #orderId")
	public FrontUserHistory getByOrderId(String orderId) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("orderId", orderId);
		List<FrontUserHistory> list = this.frontUserHistoryMapper.getListByParams(searchMap);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	@Override
	@Caching(evict={@CacheEvict(value = "frontUserHistory", key = "'frontUserHistory:' + #frontUserHistory.orderId")})
	public int insert(FrontUserHistory frontUserHistory) {
		return frontUserHistoryMapper.insert(frontUserHistory);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserHistory", key = "'frontUserHistory:' + #key")
	,@CacheEvict(value = "frontUserHistory", key = "'frontUserHistory:' + #frontUserHistory.orderId")})
	public int delete(String key) {
		return frontUserHistoryMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserHistory", key = "'frontUserHistory:' + #frontUserHistory.uuid"),
			@CacheEvict(value = "frontUserHistory", key = "'frontUserHistory:' + #frontUserHistory.orderId")})
	public int update(FrontUserHistory frontUserHistory) {
		return frontUserHistoryMapper.updateByPrimaryKey(frontUserHistory);
	}

	@Override
	@Transactional
	public void recharge(FrontUserHistory fuh, String type) {
	}

	@Override
	@Transactional
	public void withdraw(FrontUserHistory fuh) {
	}
	
	@Override
	@Transactional
	public void recharge(List<AdminOffsetOrder> aooList, List<FrontUserHistory> fuhList,
			List<FrontUserAccount> fuaList) {
		if(aooList != null) {
			for(AdminOffsetOrder aoo : aooList) {
				this.adminOffsetOrderDao.insert(aoo);
			}
		}
		if(fuhList != null) {
			for(FrontUserHistory fuh : fuhList) {
				this.frontUserHistoryMapper.insert(fuh);
			}
		}
		if(fuaList != null) {
			for(FrontUserAccount fua : fuaList) {
				this.frontUserAccountDao.update(fua);
			}
		}
	}

	@Override
	@Transactional
	public Map<String, Object> rechargeNoticeByUnion(Map<String, String> paramsls) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String message = "ok";
		Boolean resultFlag = true;
		//????????????
		/*
		 * 1??????????????????????????????????????????
		 * 2?????????????????????????????????????????????
		 */
		PayNotifyInfo pni = new PayNotifyInfo();
		pni.setUuid(UUID.randomUUID().toString());
		pni.setCreatetime(new Timestamp(System.currentTimeMillis()));
		pni.setPayType(PayNotifyInfoPayType.RECHARGE_UNION_COM);
		pni.setSource(JSONUtils.obj2json(paramsls));
		pni.setStatus(PayNotifyInfoStatus.UNPRO);
		
		//
		//?????????????????????
		Long orderNum = Long.valueOf(paramsls.get("orderNum"));
		
		pni.setOrderNum(String.valueOf(orderNum));
		this.payNotifyInfoDao.insert(pni);
		resultMap.put("message", message);
		resultMap.put("result", resultFlag);
		return resultMap;
	}
	

	@Override
	public Map<String, Object> rechargeNoticeByAcicpay(Map<String, String> paramsls) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String message = "ok";
		Boolean resultFlag = true;
		//????????????
		/*
		 * 1??????????????????????????????????????????
		 * 2?????????????????????????????????????????????
		 */
		PayNotifyInfo pni = new PayNotifyInfo();
		pni.setUuid(UUID.randomUUID().toString());
		pni.setCreatetime(new Timestamp(System.currentTimeMillis()));
		pni.setPayType(PayNotifyInfoPayType.RECHARGE_ACICPAY_COM);
		pni.setSource(JSONUtils.obj2json(paramsls));
		pni.setStatus(PayNotifyInfoStatus.UNPRO);
		
		//
		//?????????????????????
		Long orderNum = Long.valueOf(paramsls.get("orderid"));
		
		pni.setOrderNum(String.valueOf(orderNum));
		this.payNotifyInfoDao.insert(pni);
		resultMap.put("message", message);
		resultMap.put("result", resultFlag);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> rechargeNoticeByJinzun(Map<String, String> paramsls) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String message = "SUCCESS";
		Boolean resultFlag = true;
		//????????????
		/*
		 * 1??????????????????????????????????????????
		 * 2?????????????????????????????????????????????
		 */
		PayNotifyInfo pni = new PayNotifyInfo();
		pni.setUuid(UUID.randomUUID().toString());
		pni.setCreatetime(new Timestamp(System.currentTimeMillis()));
		pni.setPayType(PayNotifyInfoPayType.RECHARGE_JINZUN_COM);
		pni.setSource(JSONUtils.obj2json(paramsls));
		pni.setStatus(PayNotifyInfoStatus.UNPRO);
		
		//
		//?????????????????????
		Long orderNum = Long.valueOf(paramsls.get("outTradeNo"));
		
		pni.setOrderNum(String.valueOf(orderNum));
		this.payNotifyInfoDao.insert(pni);
		resultMap.put("message", message);
		resultMap.put("result", resultFlag);
		return resultMap;
	}
	

	@Override
	public Map<String, Object> rechargeNoticeByStripe(Map<String, String> paramsls) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String message = "SUCCESS";
		Boolean resultFlag = true;
		//????????????
		/*
		 * 1??????????????????????????????????????????
		 * 2?????????????????????????????????????????????
		 */
		PayNotifyInfo pni = new PayNotifyInfo();
		pni.setUuid(UUID.randomUUID().toString());
		pni.setCreatetime(new Timestamp(System.currentTimeMillis()));
		pni.setPayType(PayNotifyInfoPayType.RECHARGE_STRIPE_COM);
		pni.setSource(paramsls.get("sourceStr"));
		pni.setStatus(PayNotifyInfoStatus.UNPRO);
		
		//
		//?????????????????????
		Long orderNum = Long.valueOf(paramsls.get("ordernum"));
		
		pni.setOrderNum(String.valueOf(orderNum));
		this.payNotifyInfoDao.insert(pni);
		resultMap.put("message", message);
		resultMap.put("result", resultFlag);
		return resultMap;
	}
	
	@Override
	@Transactional
	public Map<String, Object> rechargeByUnion(Map<String, Object> params) throws TransactionException, ParseException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String message = "ok";
		Boolean resultFlag = true;
		//????????????
		/* 1????????????????????????????????????map<string,string>
		 * 2???????????????????????????????????????status???????????????????????????
		 * 2????????????????????????success--??????????????????????????????????????????????????????????????????????????????????????????????????? 
		 * ???????????????????????? ????????????????????????????????????
		 */
		Map<String, Object> paramsls = JSONUtils.json2map(params.get("data").toString()) ;
		//????????????????????????
		String status = paramsls.get("status") == null ? "" : paramsls.get("status").toString();
		if("success".equals(status)) {
			//????????????
			String passbackParams = paramsls.get("passbackParams").toString();
			
			BigDecimal poundage = BigDecimal.valueOf(Double.valueOf(paramsls.get("poundage").toString())).divide(BigDecimal.valueOf(100));
			
			FrontUserRechargeOrder furo = null;
			if(!Utlity.checkStringNull(passbackParams)) {
				furo = this.frontUserRechargeOrderDao.get(passbackParams);
			} else {
				message = "????????????A";
				throw new TransactionException(message);
			}
			if(furo == null) {
				message = "????????????B";
				throw new TransactionException(message);
			}
			if(FrontUserRechargeOrderStatus.CHECKED.equals(furo.getStatus())) {
				message = "??????????????????????????????????????????";
//				throw new TransactionException(message);
				resultMap.put("message", message);
				resultMap.put("result", resultFlag);
				return resultMap;
			}
			FrontUser fu = this.frontUserDao.get(furo.getFrontUser());
			if(fu == null) {
				message = "????????????C";
				throw new TransactionException(message);
			}
			
			FrontUserAccount fua = this.frontUserAccountDao.get(furo.getFrontUser());
			if(fua == null) {
				message = "????????????C";
				throw new TransactionException(message);
			}
			CapitalAccountStatistics cas = this.capitalAccountStatisticsDao.get(furo.getCapitalAccount());
			if(cas == null) {
				message = "????????????D";
				throw new TransactionException(message);
			}
			CapitalAccount ca = this.capitalAccountDao.get(furo.getCapitalAccount());
			if(ca == null) {
				message = "????????????E";
				throw new TransactionException(message);
			}
			furo.setStatus(FrontUserRechargeOrderStatus.CHECKED);
			furo.setOperattime(new Timestamp(System.currentTimeMillis()));
			//?????????????????????
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("frontUser", furo.getFrontUser());
			searchMap.put("status", FrontUserRechargeOrderStatus.CHECKED);
			Integer totalCount = this.frontUserRechargeOrderDao.getCountByParams(searchMap);
			if(totalCount != null && totalCount > 0) {
				furo.setIsFirsttime(false);
			} else {
				furo.setIsFirsttime(true);
			}
			
			FrontUserHistory fuh = new FrontUserHistory();
			fuh.setUuid(UUID.randomUUID().toString());
			fuh.setFrontUser(furo.getFrontUser());
			fuh.setOrderNum(furo.getOrderNum());
			fuh.setType(FrontUserHistoryType.USER_ADD);
			fuh.setOrderId(furo.getUuid());
			fuh.setOrderType(Constants.ORDER_TYPE_USER_RECHARGE);
			fuh.setdAmount(furo.getIncreaseDAmount());
			fuh.setBalanceBefore(fua.getBalance().add(fua.getBalanceLock()));
			fuh.setBalanceAfter(fua.getBalance().add(fua.getBalanceLock()).add(furo.getIncreaseDAmount()));
			fuh.setReason(Constants.orderTypeTemplateInfoMap.get(Constants.ORDER_TYPE_USER_RECHARGE));
			fuh.setCreatetime(furo.getOperattime());
			fuh.setRemark(furo.getRemark());
			
//			fua.setBalance(fua.getBalanceLock().add(furo.getIncreaseDAmount()));
//			fua.setTotalRecharge(fua.getTotalRecharge().add(furo.getAmount()));//?????????????????????
//			fuh.setBalanceAfter(fua.getBalance().add(fua.getBalanceLock()));
			
			//????????????
			fua.setBalance(fua.getBalance().add(furo.getIncreaseDAmount()));
			fua.setTotalRecharge(fua.getTotalRecharge().add(furo.getAmount()));//?????????????????????|
			fua.setRechargeTimes(fua.getRechargeTimes() + 1);
			
			//20200805??????????????????????????????????????????????????????????????????????????????????????????
			//??????????????????????????????
			SystemParam changeLinesp = this.systemParamDao.get(SystemParamKey.GROUP_CHANGE_LINE_RECHARGED);//????????????????????????--????????????
			BigDecimal changeLine = Utlity.GROUP_CHANGE_LINE_RECHARGED;
			if(changeLinesp != null) {
				changeLine = BigDecimal.valueOf(Double.valueOf(changeLinesp.getParamValue()));
			}
			if(fua.getTotalRecharge().compareTo(changeLine) >= 0) {//?????????????????????????????????????????????????????????
				fu.setLevel(FrontUserLevel.RECHARGED);
			}
//			if(furo.getIsFirsttime()) {//????????????-??????????????????
//				fu.setLevel(FrontUserLevel.RECHARGED);
//			}
			
//			if(cas.getDailyMax().abs().compareTo(cas.getDailySum().abs()) <= 0){
//				cas.setStatus(CapitalAccountStatus.DISABLE);
//			}
			
			//?????????????????????
			Long serialNum = Utlity.getOrderNum();
			CapitalAccountHistory cah = new CapitalAccountHistory();
			cah.setUuid(UUID.randomUUID().toString());
			cah.setSerialNum(serialNum);
			cah.setCapitalAccount(cas.getCapitalAccount());
			cah.setCapitalPlatform(ca.getCapitalPlatform());
			cah.setOrderId(furo.getUuid());
			cah.setOrderNum(furo.getOrderNum()+"");
			cah.setOrderType(fuh.getOrderType());
			cah.setBalanceBefore(cas.getBalance());
			cah.setBalanceAfter(cas.getBalance().add(furo.getAmount()).subtract(poundage));
			cah.setAmount(furo.getAmount());
			cah.setPoundage(poundage);
			cah.setType(CapitalAccountHistoryType.USER_RECHARGE);
			cah.setCreatetime(fuh.getCreatetime());
			cah.setReason("????????????");
			cah.setRemark(furo.getRemark());
			

			//????????????
			cas.setBalance(cas.getBalance().add(furo.getAmount()).subtract(poundage));
			cas.setRechargeTimes(cas.getRechargeTimes() + 1);
			cas.setTotalRecharge(cas.getTotalRecharge().add(furo.getAmount()));
			cas.setDailySum(cas.getDailySum().add(furo.getAmount()).subtract(poundage));
			
			
			//20200604????????????
			List<FrontUserVoucher> fuvList = new ArrayList<>();
			FrontUserHistory fuhAct = new FrontUserHistory();
			AdminOffsetOrder aoo = new AdminOffsetOrder();
			Map<String, Object> returnMap = new HashMap<String, Object>();
			this.activityPayUtil.isActivity(furo, fua, fuvList, fuhAct, aoo, returnMap);
			fuhAct = (FrontUserHistory)returnMap.get("fuhAct");
			aoo = (AdminOffsetOrder)returnMap.get("aoo");
			fua = (FrontUserAccount)returnMap.get("fua");
			furo = (FrontUserRechargeOrder)returnMap.get("furo");
			
			this.capitalAccountHistoryDao.insert(cah);
			this.capitalAccountStatisticsDao.update(cas);
			this.frontUserHistoryMapper.insert(fuh);
			this.frontUserDao.update(fu);
			this.frontUserAccountDao.update(fua);
			this.frontUserRechargeOrderDao.update(furo);
			
			if(fuvList.size() > 0) {
				this.frontUserVoucherDao.insert(fuvList);
			}
			if(!Utlity.checkStringNull(fuhAct.getUuid()) && !Utlity.checkStringNull(aoo.getUuid())) {
				this.adminOffsetOrderDao.insert(aoo);
				this.frontUserHistoryMapper.insert(fuhAct);
			}
			
//			//??????????????????
//			FrontUserMessage fum = new FrontUserMessage();
//			fum.setUuid(UUID.randomUUID().toString());
//			fum.setFrontUser(fu.getUuid());
//			fum.setFrontUserShowId(fu.getShowId());
//			fum.setTitle("Top-up Successful");
////			fum.setContent("??????"+Utlity.timeSpanToChinaDateString(furo.getCreatetime())+"??????"+furo.getAmount()+"???????????????????????????????????????????????????");
//			fum.setContent("Your recharge of $" + furo.getAmount() + " at " + Utlity.timeSpanToUsString(furo.getCreatetime()) 
//			+ " was successful,please pay attention to the account balance.");
//			fum.setSourceId(fuh.getUuid());
//			fum.setStatus(FrontUserMessageStatus.NORMAL);
//			fum.setType(FrontUserMessageType.USER_ORDER);
//			fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_RECHARGE);
//			fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
//			
//			this.frontUserMessageDao.sendMessage(fum);
//			this.frontUserMessageDao.sendMessage(fum, SendSmsNew.TREASUREMALL_SH_TEMP_RECHARGE);
			//??????????????????
			this.frontUserMessageDao.sendMessage(furo, fuh.getUuid());
		} else if("close".equals(status)) {//????????????
			//????????????
			String passbackParams = paramsls.get("passbackParams").toString();
			
			FrontUserRechargeOrder furo = null;
			if(!Utlity.checkStringNull(passbackParams)) {
				furo = this.frontUserRechargeOrderDao.get(passbackParams);
			} else {
				message = "????????????A";
				throw new TransactionException(message);
			}
			if(furo == null) {
				message = "????????????B";
				throw new TransactionException(message);
			}
			if(!FrontUserRechargeOrderStatus.NORMAL.equals(furo.getStatus())) {
				message = "??????????????????????????????????????????";
				throw new TransactionException(message);
			}
			furo.setStatus(FrontUserRechargeOrderStatus.CLOSE);
			furo.setOperattime(new Timestamp(System.currentTimeMillis()));
			this.frontUserRechargeOrderDao.update(furo);
		}
		resultMap.put("message", message);
		resultMap.put("result", resultFlag);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> rechargeByAcicpay(Map<String, Object> params) throws TransactionException, ParseException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String message = "ok";
		Boolean resultFlag = true;
		//????????????
		/* 1????????????????????????????????????map<string,string>
		 * 2???????????????????????????????????????status???????????????????????????
		 * 2????????????????????????success--??????????????????????????????????????????????????????????????????????????????????????????????????? 
		 * ???????????????????????? ????????????????????????????????????
		 */
		Map<String, Object> paramsls = JSONUtils.json2map(params.get("data").toString()) ;
		//????????????????????????
		String status = paramsls.get("returncode") == null ? "" : paramsls.get("returncode").toString();
		if("00".equals(status)) {
			//????????????
			String passbackParams = paramsls.get("attach").toString();
			
//			BigDecimal poundage = BigDecimal.valueOf(Double.valueOf(paramsls.get("poundage").toString())).divide(BigDecimal.valueOf(100));
			
			FrontUserRechargeOrder furo = null;
			if(!Utlity.checkStringNull(passbackParams)) {
				furo = this.frontUserRechargeOrderDao.get(passbackParams);
			} else {
				message = "????????????A";
				throw new TransactionException(message);
			}
			if(furo == null) {
				message = "????????????B";
				throw new TransactionException(message);
			}
			if(!FrontUserRechargeOrderStatus.NORMAL.equals(furo.getStatus())) {
				message = "??????????????????????????????????????????";
//				throw new TransactionException(message);
				resultMap.put("message", message);
				resultMap.put("result", resultFlag);
				return resultMap;
			}
			FrontUser fu = this.frontUserDao.get(furo.getFrontUser());
			if(fu == null) {
				message = "????????????C";
				throw new TransactionException(message);
			}
			
			FrontUserAccount fua = this.frontUserAccountDao.get(furo.getFrontUser());
			if(fua == null) {
				message = "????????????C";
				throw new TransactionException(message);
			}
			CapitalAccountStatistics cas = this.capitalAccountStatisticsDao.get(furo.getCapitalAccount());
			if(cas == null) {
				message = "????????????D";
				throw new TransactionException(message);
			}
			CapitalAccount ca = this.capitalAccountDao.get(furo.getCapitalAccount());
			if(ca == null) {
				message = "????????????E";
				throw new TransactionException(message);
			}
			
			//???????????????
			BigDecimal amount = BigDecimal.valueOf(Double.valueOf(paramsls.get("amount").toString()));
			BigDecimal poundage = amount.multiply(ca.getPoundageRate());//????????????*???????????????
				
			
			furo.setStatus(FrontUserRechargeOrderStatus.CHECKED);
			furo.setOperattime(new Timestamp(System.currentTimeMillis()));
			//?????????????????????
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("frontUser", furo.getFrontUser());
			searchMap.put("status", FrontUserRechargeOrderStatus.CHECKED);
			Integer totalCount = this.frontUserRechargeOrderDao.getCountByParams(searchMap);
			if(totalCount != null && totalCount > 0) {
				furo.setIsFirsttime(false);
			} else {
				furo.setIsFirsttime(true);
			}
			
			FrontUserHistory fuh = new FrontUserHistory();
			fuh.setUuid(UUID.randomUUID().toString());
			fuh.setFrontUser(furo.getFrontUser());
			fuh.setOrderNum(furo.getOrderNum());
			fuh.setType(FrontUserHistoryType.USER_ADD);
			fuh.setOrderId(furo.getUuid());
			fuh.setOrderType(Constants.ORDER_TYPE_USER_RECHARGE);
			fuh.setdAmount(furo.getIncreaseDAmount());
			fuh.setBalanceBefore(fua.getBalance().add(fua.getBalanceLock()));
			fuh.setBalanceAfter(fua.getBalance().add(fua.getBalanceLock()).add(furo.getIncreaseDAmount()));
			fuh.setReason(Constants.orderTypeTemplateInfoMap.get(Constants.ORDER_TYPE_USER_RECHARGE));
			fuh.setCreatetime(furo.getOperattime());
			fuh.setRemark(furo.getRemark());
			
//			fua.setBalance(fua.getBalanceLock().add(furo.getIncreaseDAmount()));
//			fua.setTotalRecharge(fua.getTotalRecharge().add(furo.getAmount()));//?????????????????????
//			fuh.setBalanceAfter(fua.getBalance().add(fua.getBalanceLock()));
			
			//????????????
			fua.setBalance(fua.getBalance().add(furo.getIncreaseDAmount()));
			fua.setTotalRecharge(fua.getTotalRecharge().add(furo.getAmount()));//?????????????????????|
			fua.setRechargeTimes(fua.getRechargeTimes() + 1);
			
			//20200805??????????????????????????????????????????????????????????????????????????????????????????
			//??????????????????????????????
			SystemParam changeLinesp = this.systemParamDao.get(SystemParamKey.GROUP_CHANGE_LINE_RECHARGED);//????????????????????????--????????????
			BigDecimal changeLine = Utlity.GROUP_CHANGE_LINE_RECHARGED;
			if(changeLinesp != null) {
				changeLine = BigDecimal.valueOf(Double.valueOf(changeLinesp.getParamValue()));
			}
			if(fua.getTotalRecharge().compareTo(changeLine) >= 0) {//?????????????????????????????????????????????????????????
				fu.setLevel(FrontUserLevel.RECHARGED);
			}
//			if(furo.getIsFirsttime()) {//????????????-??????????????????
//				fu.setLevel(FrontUserLevel.RECHARGED);
//			}
			
//			if(cas.getDailyMax().abs().compareTo(cas.getDailySum().abs()) <= 0){
//				cas.setStatus(CapitalAccountStatus.DISABLE);
//			}
			
			//?????????????????????
			Long serialNum = Utlity.getOrderNum();
			CapitalAccountHistory cah = new CapitalAccountHistory();
			cah.setUuid(UUID.randomUUID().toString());
			cah.setSerialNum(serialNum);
			cah.setCapitalAccount(cas.getCapitalAccount());
			cah.setCapitalPlatform(ca.getCapitalPlatform());
			cah.setOrderId(furo.getUuid());
			cah.setOrderNum(furo.getOrderNum()+"");
			cah.setOrderType(fuh.getOrderType());
			cah.setBalanceBefore(cas.getBalance());
			cah.setBalanceAfter(cas.getBalance().add(furo.getAmount()).subtract(poundage));
			cah.setAmount(furo.getAmount());
			cah.setPoundage(poundage);
			cah.setType(CapitalAccountHistoryType.USER_RECHARGE);
			cah.setCreatetime(fuh.getCreatetime());
			cah.setReason("????????????");
			cah.setRemark(furo.getRemark());
			

			//????????????
			cas.setBalance(cas.getBalance().add(furo.getAmount()).subtract(poundage));
			cas.setRechargeTimes(cas.getRechargeTimes() + 1);
			cas.setTotalRecharge(cas.getTotalRecharge().add(furo.getAmount()));
			cas.setDailySum(cas.getDailySum().add(furo.getAmount()).subtract(poundage));
			
			//20200604????????????
			List<FrontUserVoucher> fuvList = new ArrayList<>();
			FrontUserHistory fuhAct = new FrontUserHistory();
			AdminOffsetOrder aoo = new AdminOffsetOrder();
			Map<String, Object> returnMap = new HashMap<String, Object>();
			this.activityPayUtil.isActivity(furo, fua, fuvList, fuhAct, aoo, returnMap);
			fuhAct = (FrontUserHistory)returnMap.get("fuhAct");
			aoo = (AdminOffsetOrder)returnMap.get("aoo");
			fua = (FrontUserAccount)returnMap.get("fua");
			furo = (FrontUserRechargeOrder)returnMap.get("furo");
			
			this.capitalAccountHistoryDao.insert(cah);
			this.capitalAccountStatisticsDao.update(cas);
			this.frontUserHistoryMapper.insert(fuh);
			this.frontUserDao.update(fu);
			this.frontUserAccountDao.update(fua);
			this.frontUserRechargeOrderDao.update(furo);
			
			if(fuvList.size() > 0) {
				this.frontUserVoucherDao.insert(fuvList);
			}
			if(!Utlity.checkStringNull(fuhAct.getUuid()) && !Utlity.checkStringNull(aoo.getUuid())) {
				this.adminOffsetOrderDao.insert(aoo);
				this.frontUserHistoryMapper.insert(fuhAct);
			}
			
//			//??????????????????
//			FrontUserMessage fum = new FrontUserMessage();
//			fum.setUuid(UUID.randomUUID().toString());
//			fum.setFrontUser(fu.getUuid());
//			fum.setFrontUserShowId(fu.getShowId());
//			fum.setTitle("Top-up Successful");
////			fum.setContent("??????"+Utlity.timeSpanToChinaDateString(furo.getCreatetime())+"??????"+furo.getAmount()+"???????????????????????????????????????????????????");
//			fum.setContent("Your recharge of $" + furo.getAmount() + " at " + Utlity.timeSpanToUsString(furo.getCreatetime()) 
//			+ " was successful,please pay attention to the account balance.");
//			fum.setSourceId(fuh.getUuid());
//			fum.setStatus(FrontUserMessageStatus.NORMAL);
//			fum.setType(FrontUserMessageType.USER_ORDER);
//			fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_RECHARGE);
//			fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
//			
//			this.frontUserMessageDao.sendMessage(fum);
//			this.frontUserMessageDao.sendMessage(fum, SendSmsNew.TREASUREMALL_SH_TEMP_RECHARGE);
			//??????????????????
			this.frontUserMessageDao.sendMessage(furo, fuh.getUuid());
		} else {//????????????
			//????????????
			String passbackParams = paramsls.get("passbackParams").toString();
			
			FrontUserRechargeOrder furo = null;
			if(!Utlity.checkStringNull(passbackParams)) {
				furo = this.frontUserRechargeOrderDao.get(passbackParams);
			} else {
				message = "????????????A";
				throw new TransactionException(message);
			}
			if(furo == null) {
				message = "????????????B";
				throw new TransactionException(message);
			}
			if(!FrontUserRechargeOrderStatus.NORMAL.equals(furo.getStatus())) {
				message = "??????????????????????????????????????????";
				throw new TransactionException(message);
			}
			furo.setStatus(FrontUserRechargeOrderStatus.CLOSE);
			furo.setOperattime(new Timestamp(System.currentTimeMillis()));
			this.frontUserRechargeOrderDao.update(furo);
		}
		resultMap.put("message", message);
		resultMap.put("result", resultFlag);
		return resultMap;
	}
	

	@Override
	public Map<String, Object> rechargeByStripe(Map<String, Object> params)
			throws TransactionException, ParseException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String message = "ok";
		Boolean resultFlag = true;
		//????????????
		/* 1????????????????????????????????????map<string,string>
		 * 2???????????????????????????????????????status???????????????????????????
		 * 2????????????????????????success--??????????????????????????????????????????????????????????????????????????????????????????????????? 
		 * ???????????????????????? ????????????????????????????????????
		 */
		PaymentIntent intent = JSONUtils.json2obj(params.get("data").toString(), PaymentIntent.class);
		
		if(intent.getStatus().equals("succeeded")) {
			FrontUserRechargeOrder furo = null;
			Map<String,String> metaData = intent.getMetadata();//????????????????????????
			String orderid = metaData == null ? "" : metaData.isEmpty() ? "" : metaData.get("orderid");
			if(!Utlity.checkStringNull(orderid)){
				furo = this.frontUserRechargeOrderDao.get(orderid);
			} else {
				message = "????????????A";
				throw new TransactionException(message);
			}
			if(furo == null) {
				message = "????????????B";
				throw new TransactionException(message);
			}
			if(!FrontUserRechargeOrderStatus.NORMAL.equals(furo.getStatus()) && !FrontUserRechargeOrderStatus.CLOSE.equals(furo.getStatus())) {
				message = "??????????????????????????????????????????";
//				throw new TransactionException(message);
				resultMap.put("message", message);
				resultMap.put("result", resultFlag);
				return resultMap;
			}
			FrontUser fu = this.frontUserDao.get(furo.getFrontUser());
			if(fu == null) {
				message = "????????????C";
				throw new TransactionException(message);
			}
			
			FrontUserAccount fua = this.frontUserAccountDao.get(furo.getFrontUser());
			if(fua == null) {
				message = "????????????C";
				throw new TransactionException(message);
			}
			CapitalAccountStatistics cas = this.capitalAccountStatisticsDao.get(furo.getCapitalAccount());
			if(cas == null) {
				message = "????????????D";
				throw new TransactionException(message);
			}
			CapitalAccount ca = this.capitalAccountDao.get(furo.getCapitalAccount());
			if(ca == null) {
				message = "????????????E";
				throw new TransactionException(message);
			}
			
			//???????????????
//			BigDecimal amount = BigDecimal.valueOf(Double.valueOf(paramsls.get("amount").toString()));
			BigDecimal amount = BigDecimal.ZERO;
			BigDecimal poundage = amount.multiply(ca.getPoundageRate());//????????????*???????????????
				
			
			furo.setStatus(FrontUserRechargeOrderStatus.CHECKED);
			furo.setOperattime(new Timestamp(System.currentTimeMillis()));
			//?????????????????????
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("frontUser", furo.getFrontUser());
			searchMap.put("status", FrontUserRechargeOrderStatus.CHECKED);
			Integer totalCount = this.frontUserRechargeOrderDao.getCountByParams(searchMap);
			if(totalCount != null && totalCount > 0) {
				furo.setIsFirsttime(false);
			} else {
				furo.setIsFirsttime(true);
			}
			
			FrontUserHistory fuh = new FrontUserHistory();
			fuh.setUuid(UUID.randomUUID().toString());
			fuh.setFrontUser(furo.getFrontUser());
			fuh.setOrderNum(furo.getOrderNum());
			fuh.setType(FrontUserHistoryType.USER_ADD);
			fuh.setOrderId(furo.getUuid());
			fuh.setOrderType(Constants.ORDER_TYPE_USER_RECHARGE);
			fuh.setdAmount(furo.getIncreaseDAmount());
			fuh.setBalanceBefore(fua.getBalance().add(fua.getBalanceLock()));
			fuh.setBalanceAfter(fua.getBalance().add(fua.getBalanceLock()).add(furo.getIncreaseDAmount()));
			fuh.setReason(Constants.orderTypeTemplateInfoMap.get(Constants.ORDER_TYPE_USER_RECHARGE));
			fuh.setCreatetime(furo.getOperattime());
			fuh.setRemark(furo.getRemark());
			
//			fua.setBalance(fua.getBalanceLock().add(furo.getIncreaseDAmount()));
//			fua.setTotalRecharge(fua.getTotalRecharge().add(furo.getAmount()));//?????????????????????
//			fuh.setBalanceAfter(fua.getBalance().add(fua.getBalanceLock()));
			
			//????????????
			fua.setBalance(fua.getBalance().add(furo.getIncreaseDAmount()));
			fua.setTotalRecharge(fua.getTotalRecharge().add(furo.getAmount()));//?????????????????????|
			fua.setRechargeTimes(fua.getRechargeTimes() + 1);
			
			//20200805??????????????????????????????????????????????????????????????????????????????????????????
			//??????????????????????????????
			SystemParam changeLinesp = this.systemParamDao.get(SystemParamKey.GROUP_CHANGE_LINE_RECHARGED);//????????????????????????--????????????
			BigDecimal changeLine = Utlity.GROUP_CHANGE_LINE_RECHARGED;
			if(changeLinesp != null) {
				changeLine = BigDecimal.valueOf(Double.valueOf(changeLinesp.getParamValue()));
			}
			if(fua.getTotalRecharge().compareTo(changeLine) >= 0) {//?????????????????????????????????????????????????????????
				fu.setLevel(FrontUserLevel.RECHARGED);
			}
//			if(furo.getIsFirsttime()) {//????????????-??????????????????
//				fu.setLevel(FrontUserLevel.RECHARGED);
//			}
			
//			if(cas.getDailyMax().abs().compareTo(cas.getDailySum().abs()) <= 0){
//				cas.setStatus(CapitalAccountStatus.DISABLE);
//			}
			
			//?????????????????????
			Long serialNum = Utlity.getOrderNum();
			CapitalAccountHistory cah = new CapitalAccountHistory();
			cah.setUuid(UUID.randomUUID().toString());
			cah.setSerialNum(serialNum);
			cah.setCapitalAccount(cas.getCapitalAccount());
			cah.setCapitalPlatform(ca.getCapitalPlatform());
			cah.setOrderId(furo.getUuid());
			cah.setOrderNum(furo.getOrderNum()+"");
			cah.setOrderType(fuh.getOrderType());
			cah.setBalanceBefore(cas.getBalance());
			cah.setBalanceAfter(cas.getBalance().add(furo.getAmount()).subtract(poundage));
			cah.setAmount(furo.getAmount());
			cah.setPoundage(poundage);
			cah.setType(CapitalAccountHistoryType.USER_RECHARGE);
			cah.setCreatetime(fuh.getCreatetime());
			cah.setReason("????????????");
			cah.setRemark(furo.getRemark());
			

			//????????????
			cas.setBalance(cas.getBalance().add(furo.getAmount()).subtract(poundage));
			cas.setRechargeTimes(cas.getRechargeTimes() + 1);
			cas.setTotalRecharge(cas.getTotalRecharge().add(furo.getAmount()));
			cas.setDailySum(cas.getDailySum().add(furo.getAmount()).subtract(poundage));
			
			//20200604????????????
			List<FrontUserVoucher> fuvList = new ArrayList<>();
			FrontUserHistory fuhAct = new FrontUserHistory();
			AdminOffsetOrder aoo = new AdminOffsetOrder();
			Map<String, Object> returnMap = new HashMap<String, Object>();
			this.activityPayUtil.isActivity(furo, fua, fuvList, fuhAct, aoo, returnMap);
			fuhAct = (FrontUserHistory)returnMap.get("fuhAct");
			aoo = (AdminOffsetOrder)returnMap.get("aoo");
			fua = (FrontUserAccount)returnMap.get("fua");
			furo = (FrontUserRechargeOrder)returnMap.get("furo");
			
			this.capitalAccountHistoryDao.insert(cah);
			this.capitalAccountStatisticsDao.update(cas);
			this.frontUserHistoryMapper.insert(fuh);
			this.frontUserDao.update(fu);
			this.frontUserAccountDao.update(fua);
			this.frontUserRechargeOrderDao.update(furo);
			
			if(fuvList.size() > 0) {
				this.frontUserVoucherDao.insert(fuvList);
			}
			if(!Utlity.checkStringNull(fuhAct.getUuid()) && !Utlity.checkStringNull(aoo.getUuid())) {
				this.adminOffsetOrderDao.insert(aoo);
				this.frontUserHistoryMapper.insert(fuhAct);
			}
			
//			//??????????????????
//			FrontUserMessage fum = new FrontUserMessage();
//			fum.setUuid(UUID.randomUUID().toString());
//			fum.setFrontUser(fu.getUuid());
//			fum.setFrontUserShowId(fu.getShowId());
//			fum.setTitle("Top-up Successful");
////			fum.setContent("??????"+Utlity.timeSpanToChinaDateString(furo.getCreatetime())+"??????"+furo.getAmount()+"???????????????????????????????????????????????????");
//			fum.setContent("Your recharge of $" + furo.getAmount() + " at " + Utlity.timeSpanToUsString(furo.getCreatetime()) 
//			+ " was successful,please pay attention to the account balance.");
//			fum.setSourceId(fuh.getUuid());
//			fum.setStatus(FrontUserMessageStatus.NORMAL);
//			fum.setType(FrontUserMessageType.USER_ORDER);
//			fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_RECHARGE);
//			fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
//			
//			this.frontUserMessageDao.sendMessage(fum);
//			this.frontUserMessageDao.sendMessage(fum, SendSmsNew.TREASUREMALL_SH_TEMP_RECHARGE);
			//??????????????????
			this.frontUserMessageDao.sendMessage(furo, fuh.getUuid());
		} else {//????????????
			FrontUserRechargeOrder furo = null;
			Map<String,String> metaData = intent.getMetadata();//????????????????????????
			String orderid = metaData == null ? "" : metaData.isEmpty() ? "" : metaData.get("orderid");
			if(!Utlity.checkStringNull(orderid)){
				furo = this.frontUserRechargeOrderDao.get(orderid);
			} else {
				message = "????????????A";
				throw new TransactionException(message);
			}
			if(furo == null) {
				message = "????????????B";
				throw new TransactionException(message);
			}
			if(!FrontUserRechargeOrderStatus.NORMAL.equals(furo.getStatus()) && !FrontUserRechargeOrderStatus.CLOSE.equals(furo.getStatus())) {
				message = "??????????????????????????????????????????";
//				throw new TransactionException(message);
				resultMap.put("message", message);
				resultMap.put("result", resultFlag);
				return resultMap;
			}
			furo.setStatus(FrontUserRechargeOrderStatus.CLOSE);
			furo.setOperattime(new Timestamp(System.currentTimeMillis()));
			this.frontUserRechargeOrderDao.update(furo);
		}
		
		resultMap.put("message", message);
		resultMap.put("result", resultFlag);
		return resultMap;
	}

	@Override
	@Transactional
	public Map<String, Object> withdrawNoticeByUnion(Map<String, String> paramsls) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String message = "ok";
		Boolean resultFlag = true;
		//????????????
		/*
		 * 1??????????????????????????????????????????
		 * 2?????????????????????????????????????????????
		 */
		PayNotifyInfo pni = new PayNotifyInfo();
		pni.setUuid(UUID.randomUUID().toString());
		pni.setCreatetime(new Timestamp(System.currentTimeMillis()));
		pni.setPayType(PayNotifyInfoPayType.WITHDRAW_UNION_COM);
		pni.setSource(JSONUtils.obj2json(paramsls));
		pni.setStatus(PayNotifyInfoStatus.UNPRO);
		
		//
		//?????????????????????
		Long orderNum = Long.valueOf(paramsls.get("orderNum"));
		
		pni.setOrderNum(String.valueOf(orderNum));
		this.payNotifyInfoDao.insert(pni);
		resultMap.put("message", message);
		resultMap.put("result", resultFlag);
		return resultMap;
	}

	@Override
	@Transactional
	public Map<String, Object> withdrawByUnion(Map<String, Object> params) throws TransactionException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String message = "ok";
		Boolean resultFlag = true;
		//????????????
		/* 1????????????????????????????????????map<string,string>
		 * 2???????????????????????????????????????status???????????????????????????
		 * 2????????????????????????success--??????????????????????????????????????????????????????????????????????????????????????????????????? 
		 * ???????????????????????? ????????????????????????????????????
		 */
		Map<String, Object> paramsls = JSONUtils.json2map(params.get("data").toString()) ;
		//????????????????????????
		String status = paramsls.get("status") == null ? "" : paramsls.get("status").toString();
		if("success".equals(status)) {
			//????????????
			String passbackParams = paramsls.get("passbackParams").toString();
			
			BigDecimal poundage = BigDecimal.valueOf(Double.valueOf(paramsls.get("poundage").toString())).divide(BigDecimal.valueOf(100));
			
			FrontUserWithdrawOrder fuwo = null;
			if(!Utlity.checkStringNull(passbackParams)) {
				fuwo = this.frontUserWithdrawOrderDao.get(passbackParams);
			} else {
				message = "????????????A";
				throw new TransactionException(message);
			}
			if(fuwo == null) {
				message = "????????????B";
				throw new TransactionException(message);
			}
//			if(FrontUserWithdrawOrderStatus.CLOSE.equals(fuwo.getStatus()) || FrontUserWithdrawOrderStatus.CANCEL.equals(fuwo.getStatus()) 
//					|| FrontUserWithdrawOrderStatus.FAIL.equals(fuwo.getStatus()) || FrontUserWithdrawOrderStatus.FAIL.equals(fuwo.getStatus())) {
//				message = "??????????????????????????????????????????";
//				resultMap.put("message", message);
//				resultMap.put("result", resultFlag);
//				return resultMap;
//			}
			if(!FrontUserWithdrawOrderStatus.CHECKING.equals(fuwo.getStatus())) {
//				message = "??????????????????????????????????????????";
//				throw new TransactionException(message);
				message = "??????????????????????????????????????????";
				resultMap.put("message", message);
				resultMap.put("result", resultFlag);
				return resultMap;
			}
			FrontUser fu = this.frontUserDao.get(fuwo.getFrontUser());
			if(fu == null) {
				message = "????????????C";
				throw new TransactionException(message);
			}
			
			FrontUserAccount fua = this.frontUserAccountDao.get(fuwo.getFrontUser());
			if(fua == null) {
				message = "????????????C";
				throw new TransactionException(message);
			}
			CapitalAccountStatistics cas = this.capitalAccountStatisticsDao.get(fuwo.getCapitalAccount());
			if(cas == null) {
				message = "????????????D";
				throw new TransactionException(message);
			}
			CapitalAccount ca = this.capitalAccountDao.get(fuwo.getCapitalAccount());
			if(ca == null) {
				message = "????????????E";
				throw new TransactionException(message);
			}
			fuwo.setStatus(FrontUserWithdrawOrderStatus.CHECKED);
			
			FrontUserHistory fuh = new FrontUserHistory();
			fuh.setUuid(UUID.randomUUID().toString());
			fuh.setFrontUser(fuwo.getFrontUser());
			fuh.setOrderNum(fuwo.getOrderNum());
			fuh.setType(FrontUserHistoryType.USER_SUB);
			fuh.setOrderId(fuwo.getUuid());
			fuh.setOrderType(Constants.ORDER_TYPE_USER_WITHDRAW);
			fuh.setdAmount(fuwo.getReduceDAmount());
			fuh.setBalanceBefore(fua.getBalance().add(fua.getBalanceLock()));
			fuh.setBalanceAfter(fua.getBalance().add(fua.getBalanceLock()).subtract(fuwo.getReduceDAmount()));
			fuh.setReason(Constants.orderTypeTemplateInfoMap.get(Constants.ORDER_TYPE_USER_WITHDRAW));
			fuh.setCreatetime(fuwo.getOperattime());
			fuh.setRemark(fuwo.getRemark());
			
			//????????????
			fua.setBalanceLock(fua.getBalanceLock().subtract(fuwo.getReduceDAmount()));
			fua.setTotalWithdraw(fua.getTotalWithdraw().add(fuwo.getActualAmount()));//?????????????????????|
			fua.setWithdrawTimes(fua.getWithdrawTimes() + 1);
			
			
			//?????????????????????
			Long serialNum = Utlity.getOrderNum();
			CapitalAccountHistory cah = new CapitalAccountHistory();
			cah.setUuid(UUID.randomUUID().toString());
			cah.setSerialNum(serialNum);
			cah.setCapitalAccount(cas.getCapitalAccount());
			cah.setCapitalPlatform(ca.getCapitalPlatform());
			cah.setOrderId(fuwo.getUuid());
			cah.setOrderNum(fuwo.getOrderNum()+"");
			cah.setOrderType(fuh.getOrderType());
			cah.setBalanceBefore(cas.getBalance());
			cah.setBalanceAfter(cas.getBalance().add(fuwo.getAmount()).subtract(poundage));
			cah.setAmount(fuwo.getAmount());
			cah.setPoundage(poundage);
			cah.setType(CapitalAccountHistoryType.USER_WITHDRAW);
			cah.setCreatetime(fuh.getCreatetime());
			cah.setReason("????????????");
			cah.setRemark(fuwo.getRemark());
			

			//????????????
			cas.setBalance(cas.getBalance().subtract(fuwo.getAmount()).subtract(poundage));
			cas.setWithdrawTimes(cas.getWithdrawTimes() + 1);
			cas.setTotalWithdraw(cas.getTotalWithdraw().add(fuwo.getAmount()));
			
			this.capitalAccountHistoryDao.insert(cah);
			this.capitalAccountStatisticsDao.update(cas);
			this.frontUserHistoryMapper.insert(fuh);
			this.frontUserDao.update(fu);
			this.frontUserAccountDao.update(fua);
			this.frontUserWithdrawOrderDao.update(fuwo);
			
//			//??????????????????
//			FrontUserMessage fum = new FrontUserMessage();
//			fum.setUuid(UUID.randomUUID().toString());
//			fum.setFrontUser(fu.getUuid());
//			fum.setFrontUserShowId(fu.getShowId());
//			fum.setTitle("Withdrawl Request Successful");
////			fum.setContent("??????"+Utlity.timeSpanToChinaDateString(fuwo.getCreatetime())+"??????"+fuwo.getActualAmount()+"???????????????????????????????????????????????????");
//			fum.setContent("Your withdrawal of $" + fuwo.getActualAmount() + " at " + Utlity.timeSpanToUsString(fuwo.getCreatetime()) 
//			+ " was successful,please pay attention to the account balance.");
//			fum.setSourceId(fuh.getUuid());
//			fum.setStatus(FrontUserMessageStatus.NORMAL);
//			fum.setType(FrontUserMessageType.USER_ORDER);
//			fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_WITHDRAW);
//			fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
//			
//			this.frontUserMessageDao.sendMessage(fum);
//			this.frontUserMessageDao.sendMessage(fum, SendSmsNew.TREASUREMALL_SH_TEMP_WITHDRAW);
			//??????????????????
			this.frontUserMessageDao.sendMessage(fuwo, fuh.getUuid());
		} else if("fail".equals(status)) {//????????????/??????
			//????????????
			String passbackParams = paramsls.get("passbackParams").toString();
			
			FrontUserWithdrawOrder fuwo = null;
			if(!Utlity.checkStringNull(passbackParams)) {
				fuwo = this.frontUserWithdrawOrderDao.get(passbackParams);
			} else {
				message = "????????????A";
				throw new TransactionException(message);
			}
			if(fuwo == null) {
				message = "????????????B";
				throw new TransactionException(message);
			}
			if(FrontUserWithdrawOrderStatus.CLOSE.equals(fuwo.getStatus())) {
				message = "????????????????????????????????????";
				resultMap.put("message", message);
				resultMap.put("result", resultFlag);
				return resultMap;
			}
			if(!FrontUserWithdrawOrderStatus.CHECKING.equals(fuwo.getStatus()) && !FrontUserWithdrawOrderStatus.FAIL.equals(fuwo.getStatus())) {
				message = "??????????????????????????????????????????";
				throw new TransactionException(message);
			}
			FrontUser fu = this.frontUserDao.get(fuwo.getFrontUser());
			if(fu == null) {
				message = "????????????C";
				throw new TransactionException(message);
			}
			FrontUserAccount fua = this.frontUserAccountDao.get(fuwo.getFrontUser());
			if(fua == null) {
				message = "????????????C";
				throw new TransactionException(message);
			}
			
			fuwo.setStatus(FrontUserWithdrawOrderStatus.FAIL);
			this.frontUserWithdrawOrderDao.update(fuwo);
		} else if("close".equals(status)) {//????????????/??????
			//????????????
			String passbackParams = paramsls.get("passbackParams").toString();
			
			FrontUserWithdrawOrder fuwo = null;
			if(!Utlity.checkStringNull(passbackParams)) {
				fuwo = this.frontUserWithdrawOrderDao.get(passbackParams);
			} else {
				message = "????????????A";
				throw new TransactionException(message);
			}
			if(fuwo == null) {
				message = "????????????B";
				throw new TransactionException(message);
			}
			if(FrontUserWithdrawOrderStatus.CLOSE.equals(fuwo.getStatus())) {
				message = "????????????????????????????????????";
				resultMap.put("message", message);
				resultMap.put("result", resultFlag);
				return resultMap;
			}
			if(FrontUserWithdrawOrderStatus.CANCEL.equals(fuwo.getStatus())) {
				message = "?????????????????????????????????????????????";
				resultMap.put("message", message);
				resultMap.put("result", resultFlag);
				return resultMap;
			}
			if(!FrontUserWithdrawOrderStatus.CHECKING.equals(fuwo.getStatus()) && !FrontUserWithdrawOrderStatus.FAIL.equals(fuwo.getStatus())) {
				message = "??????????????????????????????????????????";
				throw new TransactionException(message);
			}
			FrontUser fu = this.frontUserDao.get(fuwo.getFrontUser());
			if(fu == null) {
				message = "????????????C";
				throw new TransactionException(message);
			}
			FrontUserAccount fua = this.frontUserAccountDao.get(fuwo.getFrontUser());
			if(fua == null) {
				message = "????????????C";
				throw new TransactionException(message);
			}
			
			fuwo.setStatus(FrontUserWithdrawOrderStatus.CLOSE);
			fua.setBalanceLock(fua.getBalanceLock().subtract(fuwo.getReduceDAmount()));
			fua.setBalance(fua.getBalance().add(fuwo.getReduceDAmount()));
			this.frontUserWithdrawOrderDao.update(fuwo);
			this.frontUserAccountDao.update(fua);
			
//			//??????????????????
//			FrontUserMessage fum = new FrontUserMessage();
//			fum.setUuid(UUID.randomUUID().toString());
//			fum.setFrontUser(fu.getUuid());
//			fum.setFrontUserShowId(fu.getShowId());
//			fum.setTitle("Order Canceled");
////			fum.setContent("??????"+Utlity.timeSpanToChinaDateString(fuwo.getCreatetime())+"??????"+fuwo.getActualAmount()+"????????????????????????????????????????????????????????????????????????");
//			fum.setContent("Your withdrawal of $" + fuwo.getActualAmount() + " at " + Utlity.timeSpanToUsString(fuwo.getCreatetime())
//			+ " was failed, the order was closed, please pay attention to the account balance.");
//			fum.setSourceId(fuwo.getUuid());
//			fum.setStatus(FrontUserMessageStatus.NORMAL);
//			fum.setType(FrontUserMessageType.USER_ORDER);
//			fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_WITHDRAW);
//			fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
//			
//			this.frontUserMessageDao.sendMessage(fum);
//			this.frontUserMessageDao.sendMessage(fum, SendSmsNew.TREASUREMALL_SH_TEMP_WITHDRAW_FAIL);
			//??????????????????
			this.frontUserMessageDao.sendMessage(fuwo, "");
		}
		resultMap.put("message", message);
		resultMap.put("result", resultFlag);
		return resultMap;
	}

	@Override
	@Transactional
	public void receiveByRobot(Map<String, FrontUserAccount> accountMap, List<WinningInfo> updateWin,
			List<WinningInfoReceive> insertReceive, List<FrontUserHistory> insertHistory) {
		if(!accountMap.isEmpty()) {
			for(FrontUserAccount fua : accountMap.values()) {
				InterProcessMutex mutex=new InterProcessMutex(curatorFramework,Constants.ZK_LOCK_PATHPREFIX+fua.getFrontUser()+"-lock");
				log.info("??????zookeeper???" + Constants.ZK_LOCK_PATHPREFIX+fua.getFrontUser());
				try {
					if(mutex.acquire(Constants.ZK_LOCK_TIMEOUT, TimeUnit.SECONDS)) {
						this.frontUserAccountDao.update(fua);
					}
				} catch (Exception e) {
					log.info("??????zookeeper???" + Constants.ZK_LOCK_PATHPREFIX+fua.getFrontUser()+"-----??????:"+e.fillInStackTrace());
				} finally {
	    			if (mutex!=null){
	                    try {
							mutex.release();//?????????
						} catch (Exception e) {
							e.printStackTrace();
						}
	                }
		        }
			}
		}
		
		if(updateWin != null && updateWin.size() > 0) {
			for(WinningInfo wi : updateWin) {
				this.WinningInfoDao.update(wi);
			}
		}
		
		if(insertReceive != null && insertReceive.size() > 0) {
			for(WinningInfoReceive wir : insertReceive) {
				System.out.println("-----------"+wir.getWinningInfo());
				this.winningInfoReceiveDao.insert(wir);
			}
		}
		
		if(insertHistory != null && insertHistory.size() > 0) {
			for(FrontUserHistory fuh : insertHistory) {
				this.frontUserHistoryMapper.insert(fuh);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void rechargeTaskByUnion(Map<String, Object> params) {
		if(params != null) {
			if(params.containsKey("fuaMap")) {
				Map<String, FrontUserAccount> fuaMap = (Map<String, FrontUserAccount>) params.get("fuaMap");
				for(FrontUserAccount fua : fuaMap.values()) {
					this.frontUserAccountDao.update(fua);
				}
			}
			if(params.containsKey("fuMap")) {
				Map<String, FrontUser> fuMap = (Map<String, FrontUser>) params.get("fuMap");
				for(FrontUser fu : fuMap.values()) {
					this.frontUserDao.update(fu);
				}
			}
			if (params.containsKey("caMap")) {
				Map<String, CapitalAccount> caMap = (Map<String, CapitalAccount>) params.get("caMap");
				for(CapitalAccount ca : caMap.values()) {
					this.capitalAccountDao.update(ca);
				}
			}
			if (params.containsKey("cahList")) {
				List<CapitalAccountHistory> cahList = (List<CapitalAccountHistory>) params.get("cahList");
				for(CapitalAccountHistory cah : cahList) {
					this.capitalAccountHistoryDao.insert(cah);
				}
			}
			if (params.containsKey("orderList")) {
				List<FrontUserRechargeOrder> orderList = (List<FrontUserRechargeOrder>) params.get("orderList");
				for(FrontUserRechargeOrder order : orderList) {
					this.frontUserRechargeOrderDao.update(order);
				}
			}
			if (params.containsKey("fuhList")) {
				List<FrontUserHistory> fuhList = (List<FrontUserHistory>) params.get("fuhList");
				for(FrontUserHistory fuh : fuhList) {
					this.frontUserHistoryMapper.insert(fuh);
				}
			}

			if (params.containsKey("voucherList")) {
				List<FrontUserVoucher> voucherList = (List<FrontUserVoucher>) params.get("voucherList");
				this.frontUserVoucherDao.insert(voucherList);
			}

			if (params.containsKey("aooList")) {
				List<AdminOffsetOrder> aooList = (List<AdminOffsetOrder>) params.get("aooList");
				for(AdminOffsetOrder aoo : aooList) {
					this.adminOffsetOrderDao.insert(aoo);
				}
			}
			
			if (params.containsKey("messageList")) {
				List<FrontUserMessage> messageList = (List<FrontUserMessage>) params.get("messageList");
				for(FrontUserMessage fum : messageList) {
					this.frontUserMessageDao.sendMessage(fum);
					this.frontUserMessageDao.sendMessage(fum, SendSmsNew.TREASUREMALL_SH_TEMP_RECHARGE);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void withdrawTaskByUnion(Map<String, Object> params) {
		if(params != null) {
			if(params.containsKey("fuaMap")) {
				Map<String, FrontUserAccount> fuaMap = (Map<String, FrontUserAccount>) params.get("fuaMap");
				for(FrontUserAccount fua : fuaMap.values()) {
					this.frontUserAccountDao.update(fua);
				}
			}
			if(params.containsKey("fuMap")) {
				Map<String, FrontUser> fuMap = (Map<String, FrontUser>) params.get("fuMap");
				for(FrontUser fu : fuMap.values()) {
					this.frontUserDao.update(fu);
				}
			}
			if (params.containsKey("caMap")) {
				Map<String, CapitalAccount> caMap = (Map<String, CapitalAccount>) params.get("caMap");
				for(CapitalAccount ca : caMap.values()) {
					this.capitalAccountDao.update(ca);
				}
			}
			if (params.containsKey("cahList")) {
				List<CapitalAccountHistory> cahList = (List<CapitalAccountHistory>) params.get("cahList");
				for(CapitalAccountHistory cah : cahList) {
					this.capitalAccountHistoryDao.insert(cah);
				}
			}
			if (params.containsKey("orderList")) {
				List<FrontUserWithdrawOrder> orderList = (List<FrontUserWithdrawOrder>) params.get("orderList");
				for(FrontUserWithdrawOrder order : orderList) {
					this.frontUserWithdrawOrderDao.update(order);
				}
			}
			if (params.containsKey("fuhList")) {
				List<FrontUserHistory> fuhList = (List<FrontUserHistory>) params.get("fuhList");
				for(FrontUserHistory fuh : fuhList) {
					this.frontUserHistoryMapper.insert(fuh);
				}
			}
			if (params.containsKey("messageList")) {
				List<FrontUserMessage> messageList = (List<FrontUserMessage>) params.get("messageList");
				for(FrontUserMessage fum : messageList) {
					this.frontUserMessageDao.sendMessage(fum);
					this.frontUserMessageDao.sendMessage(fum, SendSmsNew.TREASUREMALL_SH_TEMP_WITHDRAW);
				}
			}
		}
	}

	@Override
	public BigDecimal getAmountByParams(String dateStr, String orderType) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("starttime", dateStr + " 00:00:00");
		searchMap.put("endtime", dateStr + " 23:59:59");
		searchMap.put("orderType", orderType);
		searchMap.put("userType", FrontUserType.NORMAL);
		BigDecimal amount = this.frontUserHistoryMapper.getAmountByParams(searchMap);
		if(amount == null){
			amount = BigDecimal.ZERO;
		}
		return amount;
	}

	@Override
	public Map<String, Object> rechargeByJinzun(Map<String, Object> params) throws TransactionException, ParseException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String message = "ok";
		Boolean resultFlag = true;
		
		Map<String, Object> paramsls = JSONUtils.json2map(params.get("data").toString()) ;
		//????????????????????????
		String status = paramsls.get("orderState") == null ? "" : paramsls.get("orderState").toString();
		if("2".equals(status)) {
			//????????????
			String passbackParams = paramsls.get("remark").toString();
			
			FrontUserRechargeOrder furo = null;
			if(!Utlity.checkStringNull(passbackParams)) {
				furo = this.frontUserRechargeOrderDao.get(passbackParams);
			} else {
				message = "????????????A";
				throw new TransactionException(message);
			}
			if(furo == null) {
				message = "????????????B";
				throw new TransactionException(message);
			}
			if(!FrontUserRechargeOrderStatus.NORMAL.equals(furo.getStatus())) {
				message = "??????????????????????????????????????????";
//				throw new TransactionException(message);
				resultMap.put("message", message);
				resultMap.put("result", resultFlag);
				return resultMap;
			}
			FrontUser fu = this.frontUserDao.get(furo.getFrontUser());
			if(fu == null) {
				message = "????????????C";
				throw new TransactionException(message);
			}
			
			FrontUserAccount fua = this.frontUserAccountDao.get(furo.getFrontUser());
			if(fua == null) {
				message = "????????????C";
				throw new TransactionException(message);
			}
			CapitalAccountStatistics cas = this.capitalAccountStatisticsDao.get(furo.getCapitalAccount());
			if(cas == null) {
				message = "????????????D";
				throw new TransactionException(message);
			}
			CapitalAccount ca = this.capitalAccountDao.get(furo.getCapitalAccount());
			if(ca == null) {
				message = "????????????E";
				throw new TransactionException(message);
			}
			
			//???????????????
			BigDecimal amount = BigDecimal.valueOf(Double.valueOf(paramsls.get("amount").toString()));
			BigDecimal poundage = amount.multiply(ca.getPoundageRate());//????????????*???????????????
			
			furo.setStatus(FrontUserRechargeOrderStatus.CHECKED);
			furo.setOperattime(new Timestamp(System.currentTimeMillis()));
			//?????????????????????
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("frontUser", furo.getFrontUser());
			searchMap.put("status", FrontUserRechargeOrderStatus.CHECKED);
			Integer totalCount = this.frontUserRechargeOrderDao.getCountByParams(searchMap);
			if(totalCount != null && totalCount > 0) {
				furo.setIsFirsttime(false);
			} else {
				furo.setIsFirsttime(true);
			}
			
			FrontUserHistory fuh = new FrontUserHistory();
			fuh.setUuid(UUID.randomUUID().toString());
			fuh.setFrontUser(furo.getFrontUser());
			fuh.setOrderNum(furo.getOrderNum());
			fuh.setType(FrontUserHistoryType.USER_ADD);
			fuh.setOrderId(furo.getUuid());
			fuh.setOrderType(Constants.ORDER_TYPE_USER_RECHARGE);
			fuh.setdAmount(furo.getIncreaseDAmount());
			fuh.setBalanceBefore(fua.getBalance().add(fua.getBalanceLock()));
			fuh.setBalanceAfter(fua.getBalance().add(fua.getBalanceLock()).add(furo.getIncreaseDAmount()));
			fuh.setReason(Constants.orderTypeTemplateInfoMap.get(Constants.ORDER_TYPE_USER_RECHARGE));
			fuh.setCreatetime(furo.getOperattime());
			fuh.setRemark(furo.getRemark());
			
			//????????????
			fua.setBalance(fua.getBalance().add(furo.getIncreaseDAmount()));
			fua.setTotalRecharge(fua.getTotalRecharge().add(furo.getAmount()));//?????????????????????
			fua.setRechargeTimes(fua.getRechargeTimes() + 1);
			
			//20200805??????????????????????????????????????????????????????????????????????????????????????????
			//??????????????????????????????
			SystemParam changeLinesp = this.systemParamDao.get(SystemParamKey.GROUP_CHANGE_LINE_RECHARGED);//????????????????????????--????????????
			BigDecimal changeLine = Utlity.GROUP_CHANGE_LINE_RECHARGED;
			if(changeLinesp != null) {
				changeLine = BigDecimal.valueOf(Double.valueOf(changeLinesp.getParamValue()));
			}
			if(fua.getTotalRecharge().compareTo(changeLine) >= 0) {//?????????????????????????????????????????????????????????
				fu.setLevel(FrontUserLevel.RECHARGED);
			}
			
//			if(furo.getIsFirsttime()) {//????????????-??????????????????
//				fu.setLevel(FrontUserLevel.RECHARGED);
//			}
			
			//?????????????????????
			Long serialNum = Utlity.getOrderNum();
			CapitalAccountHistory cah = new CapitalAccountHistory();
			cah.setUuid(UUID.randomUUID().toString());
			cah.setSerialNum(serialNum);
			cah.setCapitalAccount(cas.getCapitalAccount());
			cah.setCapitalPlatform(ca.getCapitalPlatform());
			cah.setOrderId(furo.getUuid());
			cah.setOrderNum(furo.getOrderNum()+"");
			cah.setOrderType(fuh.getOrderType());
			cah.setBalanceBefore(cas.getBalance());
			cah.setBalanceAfter(cas.getBalance().add(furo.getAmount()).subtract(poundage));
			cah.setAmount(furo.getAmount());
			cah.setPoundage(poundage);
			cah.setType(CapitalAccountHistoryType.USER_RECHARGE);
			cah.setCreatetime(fuh.getCreatetime());
			cah.setReason("????????????");
			cah.setRemark(furo.getRemark());
			

			//????????????
			cas.setBalance(cas.getBalance().add(furo.getAmount()).subtract(poundage));
			cas.setRechargeTimes(cas.getRechargeTimes() + 1);
			cas.setTotalRecharge(cas.getTotalRecharge().add(furo.getAmount()));
			cas.setDailySum(cas.getDailySum().add(furo.getAmount()).subtract(poundage));
			
			//20200604????????????
			List<FrontUserVoucher> fuvList = new ArrayList<>();
			FrontUserHistory fuhAct = new FrontUserHistory();
			AdminOffsetOrder aoo = new AdminOffsetOrder();
			Map<String, Object> returnMap = new HashMap<String, Object>();
			this.activityPayUtil.isActivity(furo, fua, fuvList, fuhAct, aoo, returnMap);
			fuhAct = (FrontUserHistory)returnMap.get("fuhAct");
			aoo = (AdminOffsetOrder)returnMap.get("aoo");
			fua = (FrontUserAccount)returnMap.get("fua");
			furo = (FrontUserRechargeOrder)returnMap.get("furo");
			
			this.capitalAccountHistoryDao.insert(cah);
			this.capitalAccountStatisticsDao.update(cas);
			this.frontUserHistoryMapper.insert(fuh);
			this.frontUserDao.update(fu);
			this.frontUserAccountDao.update(fua);
			this.frontUserRechargeOrderDao.update(furo);
			
			if(fuvList.size() > 0) {
				this.frontUserVoucherDao.insert(fuvList);
			}
			if(!Utlity.checkStringNull(fuhAct.getUuid()) && !Utlity.checkStringNull(aoo.getUuid())) {
				this.adminOffsetOrderDao.insert(aoo);
				this.frontUserHistoryMapper.insert(fuhAct);
			}
			
//			//??????????????????
//			FrontUserMessage fum = new FrontUserMessage();
//			fum.setUuid(UUID.randomUUID().toString());
//			fum.setFrontUser(fu.getUuid());
//			fum.setFrontUserShowId(fu.getShowId());
//			fum.setTitle("Top-up Successful");
////			fum.setContent("??????"+Utlity.timeSpanToChinaDateString(furo.getCreatetime())+"??????"+furo.getAmount()+"???????????????????????????????????????????????????");
//			fum.setContent("Your recharge of $" + furo.getAmount() + " at " + Utlity.timeSpanToUsString(furo.getCreatetime()) 
//			+ " was successful,please pay attention to the account balance.");
//			fum.setSourceId(fuh.getUuid());
//			fum.setStatus(FrontUserMessageStatus.NORMAL);
//			fum.setType(FrontUserMessageType.USER_ORDER);
//			fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_RECHARGE);
//			fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
//			
//			this.frontUserMessageDao.sendMessage(fum);
//			this.frontUserMessageDao.sendMessage(fum, SendSmsNew.TREASUREMALL_SH_TEMP_RECHARGE);
			//??????????????????
			this.frontUserMessageDao.sendMessage(furo, fuh.getUuid());
		} else if("1".equals(status)){//????????????
			//????????????
			String passbackParams = paramsls.get("remark").toString();
			
			FrontUserRechargeOrder furo = null;
			if(!Utlity.checkStringNull(passbackParams)) {
				furo = this.frontUserRechargeOrderDao.get(passbackParams);
			} else {
				message = "????????????A";
				throw new TransactionException(message);
			}
			if(furo == null) {
				message = "????????????B";
				throw new TransactionException(message);
			}
			if(!FrontUserRechargeOrderStatus.NORMAL.equals(furo.getStatus())) {
				message = "??????????????????????????????????????????";
				throw new TransactionException(message);
			}
			furo.setStatus(FrontUserRechargeOrderStatus.CLOSE);
			furo.setOperattime(new Timestamp(System.currentTimeMillis()));
			this.frontUserRechargeOrderDao.update(furo);
		}
		resultMap.put("message", message);
		resultMap.put("result", resultFlag);
		return resultMap;
	}

	@Override
	public Integer getLeftCountByParams(Map<String, Object> params) {
		return this.frontUserHistoryMapper.getLeftCountByParams(params);
	}

	@Override
	public List<FrontUserHistory> getLeftListByParams(Map<String, Object> params) {
		return this.frontUserHistoryMapper.getLeftListByParams(params);
	}

	@Override
	public List<Map<String, Object>> getMonthListByParams(Map<String, Object> params) {
		return this.frontUserHistoryMapper.getMonthListByParams(params);
	}
	
	@Override
	@Transactional
	public void rechargeByWorldpay(FrontUserRechargeOrder userRecharge, String status) throws TransactionException, ParseException {
		FrontUserRechargeOrder ur = this.frontUserRechargeOrderDao.get(userRecharge.getUuid());
		if(!userRecharge.getStatus().equals(ur.getStatus())){
			return;
		}
		
		if(status.equals(FrontUserRechargeOrderStatus.CLOSE)){
			if(!FrontUserRechargeOrderStatus.CLOSE.equals(ur.getStatus()) && !FrontUserRechargeOrderStatus.CHECKED.equals(ur.getStatus())){
				ur.setStatus(FrontUserRechargeOrderStatus.CLOSE);
				ur.setOperator(userRecharge.getOperator());
				ur.setOperattime(new Timestamp(System.currentTimeMillis()));
				this.frontUserRechargeOrderDao.update(ur);
			}
		}else if(status.equals("fail")){
			if(!FrontUserRechargeOrderStatus.CHECKED.equals(ur.getStatus())){
				ur.setStatus(FrontUserRechargeOrderStatus.CLOSE);
				ur.setOperator(userRecharge.getOperator());
				ur.setOperattime(new Timestamp(System.currentTimeMillis()));
				this.frontUserRechargeOrderDao.update(ur);
			}
		}else if(status.equals(FrontUserRechargeOrderStatus.CHECKED)){
			if(!FrontUserRechargeOrderStatus.CHECKED.equals(ur.getStatus())){
//				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				
				//??????????????????
//				if(FrontUserRechargeOrderStatus.CHECKED.equals(furo.getStatus())) {
//					message = "??????????????????????????????????????????";
////					throw new TransactionException(message);
//					resultMap.put("message", message);
//					resultMap.put("result", resultFlag);
//					return resultMap;
//				}
				FrontUser fu = this.frontUserDao.get(ur.getFrontUser());
				if(fu == null) {
//					message = "????????????C";
//					throw new TransactionException(message);
				}
				
				FrontUserAccount fua = this.frontUserAccountDao.get(ur.getFrontUser());
				if(fua == null) {
//					message = "????????????C";
//					throw new TransactionException(message);
				}
				CapitalAccountStatistics cas = this.capitalAccountStatisticsDao.get(ur.getCapitalAccount());
				if(cas == null) {
//					message = "????????????D";
//					throw new TransactionException(message);
				}
				CapitalAccount ca = this.capitalAccountDao.get(ur.getCapitalAccount());
				if(ca == null) {
//					message = "????????????E";
//					throw new TransactionException(message);
				}
				ur.setStatus(FrontUserRechargeOrderStatus.CHECKED);
				ur.setOperattime(new Timestamp(System.currentTimeMillis()));
				//?????????????????????
				Map<String, Object> searchMap = new HashMap<String, Object>();
				searchMap.put("frontUser", ur.getFrontUser());
				searchMap.put("status", FrontUserRechargeOrderStatus.CHECKED);
				Integer totalCount = this.frontUserRechargeOrderDao.getCountByParams(searchMap);
				if(totalCount != null && totalCount > 0) {
					ur.setIsFirsttime(false);
				} else {
					ur.setIsFirsttime(true);
				}
				
				FrontUserHistory fuh = new FrontUserHistory();
				fuh.setUuid(UUID.randomUUID().toString());
				fuh.setFrontUser(ur.getFrontUser());
				fuh.setOrderNum(ur.getOrderNum());
				fuh.setType(FrontUserHistoryType.USER_ADD);
				fuh.setOrderId(ur.getUuid());
				fuh.setOrderType(Constants.ORDER_TYPE_USER_RECHARGE);
				fuh.setdAmount(ur.getIncreaseDAmount());
				fuh.setBalanceBefore(fua.getBalance().add(fua.getBalanceLock()));
				fuh.setBalanceAfter(fua.getBalance().add(fua.getBalanceLock()).add(ur.getIncreaseDAmount()));
				fuh.setReason(Constants.orderTypeTemplateInfoMap.get(Constants.ORDER_TYPE_USER_RECHARGE));
				fuh.setCreatetime(ur.getOperattime());
				fuh.setRemark(ur.getRemark());
				
//				fua.setBalance(fua.getBalanceLock().add(ur.getIncreaseDAmount()));
//				fua.setTotalRecharge(fua.getTotalRecharge().add(ur.getAmount()));//?????????????????????
//				fuh.setBalanceAfter(fua.getBalance().add(fua.getBalanceLock()));
				
				//????????????
				fua.setBalance(fua.getBalance().add(ur.getIncreaseDAmount()));
				fua.setTotalRecharge(fua.getTotalRecharge().add(ur.getAmount()));//?????????????????????|
				fua.setRechargeTimes(fua.getRechargeTimes() + 1);
				
				//20200805??????????????????????????????????????????????????????????????????????????????????????????
				//??????????????????????????????
				SystemParam changeLinesp = this.systemParamDao.get(SystemParamKey.GROUP_CHANGE_LINE_RECHARGED);//????????????????????????--????????????
				BigDecimal changeLine = Utlity.GROUP_CHANGE_LINE_RECHARGED;
				if(changeLinesp != null) {
					changeLine = BigDecimal.valueOf(Double.valueOf(changeLinesp.getParamValue()));
				}
				if(fua.getTotalRecharge().compareTo(changeLine) >= 0) {//?????????????????????????????????????????????????????????
					fu.setLevel(FrontUserLevel.RECHARGED);
				}
//				if(ur.getIsFirsttime()) {//????????????-??????????????????
//					fu.setLevel(FrontUserLevel.RECHARGED);
//				}
				
//				if(cas.getDailyMax().abs().compareTo(cas.getDailySum().abs()) <= 0){
//					cas.setStatus(CapitalAccountStatus.DISABLE);
//				}
				//?????????
				BigDecimal poundage = BigDecimal.ZERO;
				if(ca.getPoundageRate() != null){
					poundage = ur.getAmount().multiply(ca.getPoundageRate()).setScale(0, BigDecimal.ROUND_HALF_UP);
				}
				
				//?????????????????????
				Long serialNum = Utlity.getOrderNum();
				CapitalAccountHistory cah = new CapitalAccountHistory();
				cah.setUuid(UUID.randomUUID().toString());
				cah.setSerialNum(serialNum);
				cah.setCapitalAccount(cas.getCapitalAccount());
				cah.setCapitalPlatform(ca.getCapitalPlatform());
				cah.setOrderId(ur.getUuid());
				cah.setOrderNum(ur.getOrderNum()+"");
				cah.setOrderType(fuh.getOrderType());
				cah.setBalanceBefore(cas.getBalance());
				cah.setBalanceAfter(cas.getBalance().add(ur.getAmount()).subtract(poundage));
				cah.setAmount(ur.getAmount());
				cah.setPoundage(poundage);
				cah.setType(CapitalAccountHistoryType.USER_RECHARGE);
				cah.setCreatetime(fuh.getCreatetime());
				cah.setReason("????????????");
				cah.setRemark(ur.getRemark());
				

				//????????????
				cas.setBalance(cas.getBalance().add(ur.getAmount()).subtract(poundage));
				cas.setRechargeTimes(cas.getRechargeTimes() + 1);
				cas.setTotalRecharge(cas.getTotalRecharge().add(ur.getAmount()));
				cas.setDailySum(cas.getDailySum().add(ur.getAmount()).subtract(poundage));
				
				
				//20200604????????????
				List<FrontUserVoucher> fuvList = new ArrayList<>();
				FrontUserHistory fuhAct = new FrontUserHistory();
				AdminOffsetOrder aoo = new AdminOffsetOrder();
				Map<String, Object> returnMap = new HashMap<String, Object>();
				this.activityPayUtil.isActivity(ur, fua, fuvList, fuhAct, aoo, returnMap);
				fuhAct = (FrontUserHistory)returnMap.get("fuhAct");
				aoo = (AdminOffsetOrder)returnMap.get("aoo");
				fua = (FrontUserAccount)returnMap.get("fua");
				ur = (FrontUserRechargeOrder)returnMap.get("furo");
				
				this.capitalAccountHistoryDao.insert(cah);
				this.capitalAccountStatisticsDao.update(cas);
				this.frontUserHistoryMapper.insert(fuh);
				this.frontUserDao.update(fu);
				this.frontUserAccountDao.update(fua);
				this.frontUserRechargeOrderDao.update(ur);
				
				if(fuvList.size() > 0) {
					this.frontUserVoucherDao.insert(fuvList);
				}
				if(!Utlity.checkStringNull(fuhAct.getUuid()) && !Utlity.checkStringNull(aoo.getUuid())) {
					this.adminOffsetOrderDao.insert(aoo);
					this.frontUserHistoryMapper.insert(fuhAct);
				}
				
//				//??????????????????
//				FrontUserMessage fum = new FrontUserMessage();
//				fum.setUuid(UUID.randomUUID().toString());
//				fum.setFrontUser(fu.getUuid());
//				fum.setFrontUserShowId(fu.getShowId());
//				fum.setTitle("Top-up Successful");
////				fum.setContent("??????"+Utlity.timeSpanToChinaDateString(furo.getCreatetime())+"??????"+furo.getAmount()+"???????????????????????????????????????????????????");
//				fum.setContent("Your recharge of $" + ur.getAmount() + " at " + Utlity.timeSpanToUsString(ur.getCreatetime()) 
//				+ " was successful,please pay attention to the account balance.");
//				fum.setSourceId(fuh.getUuid());
//				fum.setStatus(FrontUserMessageStatus.NORMAL);
//				fum.setType(FrontUserMessageType.USER_ORDER);
//				fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_RECHARGE);
//				fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
//				
//				this.frontUserMessageDao.sendMessage(fum);
//				this.frontUserMessageDao.sendMessage(fum, SendSmsNew.TREASUREMALL_SH_TEMP_RECHARGE);
				//??????????????????
				this.frontUserMessageDao.sendMessage(ur, fuh.getUuid());
			}
		}
	}
}
