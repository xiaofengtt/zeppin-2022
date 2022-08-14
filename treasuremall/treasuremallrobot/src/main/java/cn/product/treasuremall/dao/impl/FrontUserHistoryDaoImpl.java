package cn.product.treasuremall.dao.impl;

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

import cn.product.treasuremall.controller.base.TransactionException;
import cn.product.treasuremall.dao.AdminOffsetOrderDao;
import cn.product.treasuremall.dao.BankDao;
import cn.product.treasuremall.dao.CapitalAccountDao;
import cn.product.treasuremall.dao.CapitalAccountHistoryDao;
import cn.product.treasuremall.dao.CapitalAccountStatisticsDao;
import cn.product.treasuremall.dao.FrontUserAccountDao;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.FrontUserHistoryDao;
import cn.product.treasuremall.dao.FrontUserMessageDao;
import cn.product.treasuremall.dao.FrontUserRechargeOrderDao;
import cn.product.treasuremall.dao.FrontUserVoucherDao;
import cn.product.treasuremall.dao.FrontUserWithdrawOrderDao;
import cn.product.treasuremall.dao.PayNotifyInfoDao;
import cn.product.treasuremall.dao.SystemParamDao;
import cn.product.treasuremall.dao.WinningInfoDao;
import cn.product.treasuremall.dao.WinningInfoReceiveDao;
import cn.product.treasuremall.entity.AdminOffsetOrder;
import cn.product.treasuremall.entity.CapitalAccount;
import cn.product.treasuremall.entity.CapitalAccountHistory;
import cn.product.treasuremall.entity.CapitalAccountHistory.CapitalAccountHistoryType;
import cn.product.treasuremall.entity.CapitalAccountStatistics;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUser.FrontUserLevel;
import cn.product.treasuremall.entity.FrontUser.FrontUserType;
import cn.product.treasuremall.entity.FrontUserAccount;
import cn.product.treasuremall.entity.FrontUserHistory;
import cn.product.treasuremall.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.treasuremall.entity.FrontUserMessage;
import cn.product.treasuremall.entity.FrontUserMessage.FrontUserMessageSourceType;
import cn.product.treasuremall.entity.FrontUserMessage.FrontUserMessageStatus;
import cn.product.treasuremall.entity.FrontUserMessage.FrontUserMessageType;
import cn.product.treasuremall.entity.FrontUserRechargeOrder;
import cn.product.treasuremall.entity.FrontUserRechargeOrder.FrontUserRechargeOrderStatus;
import cn.product.treasuremall.entity.FrontUserVoucher;
import cn.product.treasuremall.entity.FrontUserWithdrawOrder;
import cn.product.treasuremall.entity.FrontUserWithdrawOrder.FrontUserWithdrawOrderStatus;
import cn.product.treasuremall.entity.PayNotifyInfo;
import cn.product.treasuremall.entity.SystemParam;
import cn.product.treasuremall.entity.PayNotifyInfo.PayNotifyInfoPayType;
import cn.product.treasuremall.entity.PayNotifyInfo.PayNotifyInfoStatus;
import cn.product.treasuremall.entity.SystemParam.SystemParamKey;
import cn.product.treasuremall.entity.WinningInfo;
import cn.product.treasuremall.entity.WinningInfoReceive;
import cn.product.treasuremall.entity.base.Constants;
import cn.product.treasuremall.mapper.FrontUserHistoryMapper;
import cn.product.treasuremall.util.JSONUtils;
import cn.product.treasuremall.util.SendSmsNew;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.util.pay.ActivityPayUtil;

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
		//解析参数
		/*
		 * 1、封装异步通知信息对象并存储
		 * 2、等待计时任务处理异步通知信息
		 */
		PayNotifyInfo pni = new PayNotifyInfo();
		pni.setUuid(UUID.randomUUID().toString());
		pni.setCreatetime(new Timestamp(System.currentTimeMillis()));
		pni.setPayType(PayNotifyInfoPayType.RECHARGE_UNION_COM);
		pni.setSource(JSONUtils.obj2json(paramsls));
		pni.setStatus(PayNotifyInfoStatus.UNPRO);
		
		//
		//商户交易流水号
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
		//解析参数
		/*
		 * 1、封装异步通知信息对象并存储
		 * 2、等待计时任务处理异步通知信息
		 */
		PayNotifyInfo pni = new PayNotifyInfo();
		pni.setUuid(UUID.randomUUID().toString());
		pni.setCreatetime(new Timestamp(System.currentTimeMillis()));
		pni.setPayType(PayNotifyInfoPayType.RECHARGE_ACICPAY_COM);
		pni.setSource(JSONUtils.obj2json(paramsls));
		pni.setStatus(PayNotifyInfoStatus.UNPRO);
		
		//
		//商户交易流水号
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
		//解析参数
		/*
		 * 1、封装异步通知信息对象并存储
		 * 2、等待计时任务处理异步通知信息
		 */
		PayNotifyInfo pni = new PayNotifyInfo();
		pni.setUuid(UUID.randomUUID().toString());
		pni.setCreatetime(new Timestamp(System.currentTimeMillis()));
		pni.setPayType(PayNotifyInfoPayType.RECHARGE_JINZUN_COM);
		pni.setSource(JSONUtils.obj2json(paramsls));
		pni.setStatus(PayNotifyInfoStatus.UNPRO);
		
		//
		//商户交易流水号
		Long orderNum = Long.valueOf(paramsls.get("outTradeNo"));
		
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
		//解析参数
		/* 1、获取异步回调信息，转成map<string,string>
		 * 2、获取回调信息中的处理状态status，判断是否继续处理
		 * 2、处理对应逻辑：success--更新交易记录状态，更新订单状态，增加渠道账户流水，增加取到账户余额 
		 * 其他状态对应处理 交由订单状态查询任务处理
		 */
		Map<String, Object> paramsls = JSONUtils.json2map(params.get("data").toString()) ;
		//聚合支付交易状态
		String status = paramsls.get("status") == null ? "" : paramsls.get("status").toString();
		if("success".equals(status)) {
			//公共参数
			String passbackParams = paramsls.get("passbackParams").toString();
			
			BigDecimal poundage = BigDecimal.valueOf(Double.valueOf(paramsls.get("poundage").toString())).divide(BigDecimal.valueOf(100));
			
			FrontUserRechargeOrder furo = null;
			if(!Utlity.checkStringNull(passbackParams)) {
				furo = this.frontUserRechargeOrderDao.get(passbackParams);
			} else {
				message = "账单错误A";
				throw new TransactionException(message);
			}
			if(furo == null) {
				message = "账单错误B";
				throw new TransactionException(message);
			}
			if(FrontUserRechargeOrderStatus.CHECKED.equals(furo.getStatus())) {
				message = "账单已处理完毕！不要重复处理";
//				throw new TransactionException(message);
				resultMap.put("message", message);
				resultMap.put("result", resultFlag);
				return resultMap;
			}
			FrontUser fu = this.frontUserDao.get(furo.getFrontUser());
			if(fu == null) {
				message = "账单错误C";
				throw new TransactionException(message);
			}
			
			FrontUserAccount fua = this.frontUserAccountDao.get(furo.getFrontUser());
			if(fua == null) {
				message = "账单错误C";
				throw new TransactionException(message);
			}
			CapitalAccountStatistics cas = this.capitalAccountStatisticsDao.get(furo.getCapitalAccount());
			if(cas == null) {
				message = "账单错误D";
				throw new TransactionException(message);
			}
			CapitalAccount ca = this.capitalAccountDao.get(furo.getCapitalAccount());
			if(ca == null) {
				message = "账单错误E";
				throw new TransactionException(message);
			}
			furo.setStatus(FrontUserRechargeOrderStatus.CHECKED);
			furo.setOperattime(new Timestamp(System.currentTimeMillis()));
			//是否是首次充值
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
//			fua.setTotalRecharge(fua.getTotalRecharge().add(furo.getAmount()));//总充值（法币）
//			fuh.setBalanceAfter(fua.getBalance().add(fua.getBalanceLock()));
			
			//用户账户
			fua.setBalance(fua.getBalance().add(furo.getIncreaseDAmount()));
			fua.setTotalRecharge(fua.getTotalRecharge().add(furo.getAmount()));//总充值（法币）|
			fua.setRechargeTimes(fua.getRechargeTimes() + 1);
			
			//20200805修改，改为根据系统设置的参数，判断是否满足用户级别变更的条件
			//获取用户级别变更参数
			SystemParam changeLinesp = this.systemParamDao.get(SystemParamKey.GROUP_CHANGE_LINE_RECHARGED);//充值订单超时时间--单位分钟
			BigDecimal changeLine = Utlity.GROUP_CHANGE_LINE_RECHARGED;
			if(changeLinesp != null) {
				changeLine = BigDecimal.valueOf(Double.valueOf(changeLinesp.getParamValue()));
			}
			if(fua.getTotalRecharge().compareTo(changeLine) >= 0) {//大于设置参数，则变更用户级别为充值用户
				fu.setLevel(FrontUserLevel.RECHARGED);
			}
//			if(furo.getIsFirsttime()) {//首次充值-变更用户级别
//				fu.setLevel(FrontUserLevel.RECHARGED);
//			}
			
//			if(cas.getDailyMax().abs().compareTo(cas.getDailySum().abs()) <= 0){
//				cas.setStatus(CapitalAccountStatus.DISABLE);
//			}
			
			//渠道账户流水儿
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
			cah.setReason("用户充值");
			cah.setRemark(furo.getRemark());
			

			//渠道账户
			cas.setBalance(cas.getBalance().add(furo.getAmount()).subtract(poundage));
			cas.setRechargeTimes(cas.getRechargeTimes() + 1);
			cas.setTotalRecharge(cas.getTotalRecharge().add(furo.getAmount()));
			cas.setDailySum(cas.getDailySum().add(furo.getAmount()).subtract(poundage));
			
			
			//20200604活动入账
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
			
			//充值通知消息
			FrontUserMessage fum = new FrontUserMessage();
			fum.setUuid(UUID.randomUUID().toString());
			fum.setFrontUser(fu.getUuid());
			fum.setFrontUserShowId(fu.getShowId());
			fum.setTitle("充值到账提醒");
			fum.setContent("您在"+Utlity.timeSpanToChinaDateString(furo.getCreatetime())+"充值"+furo.getAmount()+"元已到账，请注意账户余额变动信息！");
			fum.setSourceId(fuh.getUuid());
			fum.setStatus(FrontUserMessageStatus.NORMAL);
			fum.setType(FrontUserMessageType.USER_ORDER);
			fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_RECHARGE);
			fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			this.frontUserMessageDao.sendMessage(fum);
			this.frontUserMessageDao.sendMessage(fum, SendSmsNew.TREASUREMALL_SH_TEMP_RECHARGE);
		} else if("close".equals(status)) {//订单关闭
			//公共参数
			String passbackParams = paramsls.get("passbackParams").toString();
			
			FrontUserRechargeOrder furo = null;
			if(!Utlity.checkStringNull(passbackParams)) {
				furo = this.frontUserRechargeOrderDao.get(passbackParams);
			} else {
				message = "账单错误A";
				throw new TransactionException(message);
			}
			if(furo == null) {
				message = "账单错误B";
				throw new TransactionException(message);
			}
			if(!FrontUserRechargeOrderStatus.NORMAL.equals(furo.getStatus())) {
				message = "账单已处理完毕！不要重复处理";
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
		//解析参数
		/* 1、获取异步回调信息，转成map<string,string>
		 * 2、获取回调信息中的处理状态status，判断是否继续处理
		 * 2、处理对应逻辑：success--更新交易记录状态，更新订单状态，增加渠道账户流水，增加取到账户余额 
		 * 其他状态对应处理 交由订单状态查询任务处理
		 */
		Map<String, Object> paramsls = JSONUtils.json2map(params.get("data").toString()) ;
		//聚合支付交易状态
		String status = paramsls.get("returncode") == null ? "" : paramsls.get("returncode").toString();
		if("00".equals(status)) {
			//公共参数
			String passbackParams = paramsls.get("attach").toString();
			
//			BigDecimal poundage = BigDecimal.valueOf(Double.valueOf(paramsls.get("poundage").toString())).divide(BigDecimal.valueOf(100));
			
			FrontUserRechargeOrder furo = null;
			if(!Utlity.checkStringNull(passbackParams)) {
				furo = this.frontUserRechargeOrderDao.get(passbackParams);
			} else {
				message = "账单错误A";
				throw new TransactionException(message);
			}
			if(furo == null) {
				message = "账单错误B";
				throw new TransactionException(message);
			}
			if(!FrontUserRechargeOrderStatus.NORMAL.equals(furo.getStatus())) {
				message = "账单已处理完毕！不要重复处理";
//				throw new TransactionException(message);
				resultMap.put("message", message);
				resultMap.put("result", resultFlag);
				return resultMap;
			}
			FrontUser fu = this.frontUserDao.get(furo.getFrontUser());
			if(fu == null) {
				message = "账单错误C";
				throw new TransactionException(message);
			}
			
			FrontUserAccount fua = this.frontUserAccountDao.get(furo.getFrontUser());
			if(fua == null) {
				message = "账单错误C";
				throw new TransactionException(message);
			}
			CapitalAccountStatistics cas = this.capitalAccountStatisticsDao.get(furo.getCapitalAccount());
			if(cas == null) {
				message = "账单错误D";
				throw new TransactionException(message);
			}
			CapitalAccount ca = this.capitalAccountDao.get(furo.getCapitalAccount());
			if(ca == null) {
				message = "账单错误E";
				throw new TransactionException(message);
			}
			
			//计算手续费
			BigDecimal amount = BigDecimal.valueOf(Double.valueOf(paramsls.get("amount").toString()));
			BigDecimal poundage = amount.multiply(ca.getPoundageRate());//订单金额*通道手续费
				
			
			furo.setStatus(FrontUserRechargeOrderStatus.CHECKED);
			furo.setOperattime(new Timestamp(System.currentTimeMillis()));
			//是否是首次充值
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
//			fua.setTotalRecharge(fua.getTotalRecharge().add(furo.getAmount()));//总充值（法币）
//			fuh.setBalanceAfter(fua.getBalance().add(fua.getBalanceLock()));
			
			//用户账户
			fua.setBalance(fua.getBalance().add(furo.getIncreaseDAmount()));
			fua.setTotalRecharge(fua.getTotalRecharge().add(furo.getAmount()));//总充值（法币）|
			fua.setRechargeTimes(fua.getRechargeTimes() + 1);
			
			//20200805修改，改为根据系统设置的参数，判断是否满足用户级别变更的条件
			//获取用户级别变更参数
			SystemParam changeLinesp = this.systemParamDao.get(SystemParamKey.GROUP_CHANGE_LINE_RECHARGED);//充值订单超时时间--单位分钟
			BigDecimal changeLine = Utlity.GROUP_CHANGE_LINE_RECHARGED;
			if(changeLinesp != null) {
				changeLine = BigDecimal.valueOf(Double.valueOf(changeLinesp.getParamValue()));
			}
			if(fua.getTotalRecharge().compareTo(changeLine) >= 0) {//大于设置参数，则变更用户级别为充值用户
				fu.setLevel(FrontUserLevel.RECHARGED);
			}
//			if(furo.getIsFirsttime()) {//首次充值-变更用户级别
//				fu.setLevel(FrontUserLevel.RECHARGED);
//			}
			
//			if(cas.getDailyMax().abs().compareTo(cas.getDailySum().abs()) <= 0){
//				cas.setStatus(CapitalAccountStatus.DISABLE);
//			}
			
			//渠道账户流水儿
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
			cah.setReason("用户充值");
			cah.setRemark(furo.getRemark());
			

			//渠道账户
			cas.setBalance(cas.getBalance().add(furo.getAmount()).subtract(poundage));
			cas.setRechargeTimes(cas.getRechargeTimes() + 1);
			cas.setTotalRecharge(cas.getTotalRecharge().add(furo.getAmount()));
			cas.setDailySum(cas.getDailySum().add(furo.getAmount()).subtract(poundage));
			
			//20200604活动入账
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
			
			//充值通知消息
			FrontUserMessage fum = new FrontUserMessage();
			fum.setUuid(UUID.randomUUID().toString());
			fum.setFrontUser(fu.getUuid());
			fum.setFrontUserShowId(fu.getShowId());
			fum.setTitle("充值到账提醒");
			fum.setContent("您在"+Utlity.timeSpanToChinaDateString(furo.getCreatetime())+"充值"+furo.getAmount()+"元已到账，请注意账户余额变动信息！");
			fum.setSourceId(fuh.getUuid());
			fum.setStatus(FrontUserMessageStatus.NORMAL);
			fum.setType(FrontUserMessageType.USER_ORDER);
			fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_RECHARGE);
			fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			this.frontUserMessageDao.sendMessage(fum);
			this.frontUserMessageDao.sendMessage(fum, SendSmsNew.TREASUREMALL_SH_TEMP_RECHARGE);
		} else {//订单关闭
			//公共参数
			String passbackParams = paramsls.get("passbackParams").toString();
			
			FrontUserRechargeOrder furo = null;
			if(!Utlity.checkStringNull(passbackParams)) {
				furo = this.frontUserRechargeOrderDao.get(passbackParams);
			} else {
				message = "账单错误A";
				throw new TransactionException(message);
			}
			if(furo == null) {
				message = "账单错误B";
				throw new TransactionException(message);
			}
			if(!FrontUserRechargeOrderStatus.NORMAL.equals(furo.getStatus())) {
				message = "账单已处理完毕！不要重复处理";
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
	@Transactional
	public Map<String, Object> withdrawNoticeByUnion(Map<String, String> paramsls) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String message = "ok";
		Boolean resultFlag = true;
		//解析参数
		/*
		 * 1、封装异步通知信息对象并存储
		 * 2、等待计时任务处理异步通知信息
		 */
		PayNotifyInfo pni = new PayNotifyInfo();
		pni.setUuid(UUID.randomUUID().toString());
		pni.setCreatetime(new Timestamp(System.currentTimeMillis()));
		pni.setPayType(PayNotifyInfoPayType.WITHDRAW_UNION_COM);
		pni.setSource(JSONUtils.obj2json(paramsls));
		pni.setStatus(PayNotifyInfoStatus.UNPRO);
		
		//
		//商户交易流水号
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
		//解析参数
		/* 1、获取异步回调信息，转成map<string,string>
		 * 2、获取回调信息中的处理状态status，判断是否继续处理
		 * 2、处理对应逻辑：success--更新交易记录状态，更新订单状态，增加渠道账户流水，增加取到账户余额 
		 * 其他状态对应处理 交由订单状态查询任务处理
		 */
		Map<String, Object> paramsls = JSONUtils.json2map(params.get("data").toString()) ;
		//聚合支付交易状态
		String status = paramsls.get("status") == null ? "" : paramsls.get("status").toString();
		if("success".equals(status)) {
			//公共参数
			String passbackParams = paramsls.get("passbackParams").toString();
			
			BigDecimal poundage = BigDecimal.valueOf(Double.valueOf(paramsls.get("poundage").toString())).divide(BigDecimal.valueOf(100));
			
			FrontUserWithdrawOrder fuwo = null;
			if(!Utlity.checkStringNull(passbackParams)) {
				fuwo = this.frontUserWithdrawOrderDao.get(passbackParams);
			} else {
				message = "账单错误A";
				throw new TransactionException(message);
			}
			if(fuwo == null) {
				message = "账单错误B";
				throw new TransactionException(message);
			}
//			if(FrontUserWithdrawOrderStatus.CLOSE.equals(fuwo.getStatus()) || FrontUserWithdrawOrderStatus.CANCEL.equals(fuwo.getStatus()) 
//					|| FrontUserWithdrawOrderStatus.FAIL.equals(fuwo.getStatus()) || FrontUserWithdrawOrderStatus.FAIL.equals(fuwo.getStatus())) {
//				message = "订单已处理完成！不要重复处理";
//				resultMap.put("message", message);
//				resultMap.put("result", resultFlag);
//				return resultMap;
//			}
			if(!FrontUserWithdrawOrderStatus.CHECKING.equals(fuwo.getStatus())) {
//				message = "账单已处理完毕！不要重复处理";
//				throw new TransactionException(message);
				message = "订单已处理完成！不要重复处理";
				resultMap.put("message", message);
				resultMap.put("result", resultFlag);
				return resultMap;
			}
			FrontUser fu = this.frontUserDao.get(fuwo.getFrontUser());
			if(fu == null) {
				message = "账单错误C";
				throw new TransactionException(message);
			}
			
			FrontUserAccount fua = this.frontUserAccountDao.get(fuwo.getFrontUser());
			if(fua == null) {
				message = "账单错误C";
				throw new TransactionException(message);
			}
			CapitalAccountStatistics cas = this.capitalAccountStatisticsDao.get(fuwo.getCapitalAccount());
			if(cas == null) {
				message = "账单错误D";
				throw new TransactionException(message);
			}
			CapitalAccount ca = this.capitalAccountDao.get(fuwo.getCapitalAccount());
			if(ca == null) {
				message = "账单错误E";
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
			
			//用户账户
			fua.setBalanceLock(fua.getBalanceLock().subtract(fuwo.getReduceDAmount()));
			fua.setTotalWithdraw(fua.getTotalWithdraw().add(fuwo.getActualAmount()));//总提现（法币）|
			fua.setWithdrawTimes(fua.getWithdrawTimes() + 1);
			
			
			//渠道账户流水儿
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
			cah.setReason("用户提现");
			cah.setRemark(fuwo.getRemark());
			

			//渠道账户
			cas.setBalance(cas.getBalance().subtract(fuwo.getAmount()).subtract(poundage));
			cas.setWithdrawTimes(cas.getWithdrawTimes() + 1);
			cas.setTotalWithdraw(cas.getTotalWithdraw().add(fuwo.getAmount()));
			
			this.capitalAccountHistoryDao.insert(cah);
			this.capitalAccountStatisticsDao.update(cas);
			this.frontUserHistoryMapper.insert(fuh);
			this.frontUserDao.update(fu);
			this.frontUserAccountDao.update(fua);
			this.frontUserWithdrawOrderDao.update(fuwo);
			
			//提现通知消息
			FrontUserMessage fum = new FrontUserMessage();
			fum.setUuid(UUID.randomUUID().toString());
			fum.setFrontUser(fu.getUuid());
			fum.setFrontUserShowId(fu.getShowId());
			fum.setTitle("提现到账提醒");
			fum.setContent("您在"+Utlity.timeSpanToChinaDateString(fuwo.getCreatetime())+"提现"+fuwo.getActualAmount()+"元已到账，请注意账户余额变动信息！");
			fum.setSourceId(fuh.getUuid());
			fum.setStatus(FrontUserMessageStatus.NORMAL);
			fum.setType(FrontUserMessageType.USER_ORDER);
			fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_WITHDRAW);
			fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			this.frontUserMessageDao.sendMessage(fum);
			this.frontUserMessageDao.sendMessage(fum, SendSmsNew.TREASUREMALL_SH_TEMP_WITHDRAW);
		} else if("fail".equals(status)) {//订单关闭/失败
			//公共参数
			String passbackParams = paramsls.get("passbackParams").toString();
			
			FrontUserWithdrawOrder fuwo = null;
			if(!Utlity.checkStringNull(passbackParams)) {
				fuwo = this.frontUserWithdrawOrderDao.get(passbackParams);
			} else {
				message = "账单错误A";
				throw new TransactionException(message);
			}
			if(fuwo == null) {
				message = "账单错误B";
				throw new TransactionException(message);
			}
			if(FrontUserWithdrawOrderStatus.CLOSE.equals(fuwo.getStatus())) {
				message = "订单已关闭！不要重复处理";
				resultMap.put("message", message);
				resultMap.put("result", resultFlag);
				return resultMap;
			}
			if(!FrontUserWithdrawOrderStatus.CHECKING.equals(fuwo.getStatus()) && !FrontUserWithdrawOrderStatus.FAIL.equals(fuwo.getStatus())) {
				message = "账单已处理完毕！不要重复处理";
				throw new TransactionException(message);
			}
			FrontUser fu = this.frontUserDao.get(fuwo.getFrontUser());
			if(fu == null) {
				message = "账单错误C";
				throw new TransactionException(message);
			}
			FrontUserAccount fua = this.frontUserAccountDao.get(fuwo.getFrontUser());
			if(fua == null) {
				message = "账单错误C";
				throw new TransactionException(message);
			}
			
			fuwo.setStatus(FrontUserWithdrawOrderStatus.FAIL);
			this.frontUserWithdrawOrderDao.update(fuwo);
		} else if("close".equals(status)) {//订单关闭/失败
			//公共参数
			String passbackParams = paramsls.get("passbackParams").toString();
			
			FrontUserWithdrawOrder fuwo = null;
			if(!Utlity.checkStringNull(passbackParams)) {
				fuwo = this.frontUserWithdrawOrderDao.get(passbackParams);
			} else {
				message = "账单错误A";
				throw new TransactionException(message);
			}
			if(fuwo == null) {
				message = "账单错误B";
				throw new TransactionException(message);
			}
			if(FrontUserWithdrawOrderStatus.CLOSE.equals(fuwo.getStatus())) {
				message = "订单已关闭！不要重复处理";
				resultMap.put("message", message);
				resultMap.put("result", resultFlag);
				return resultMap;
			}
			if(FrontUserWithdrawOrderStatus.CANCEL.equals(fuwo.getStatus())) {
				message = "管理员已取消订单！不要重复处理";
				resultMap.put("message", message);
				resultMap.put("result", resultFlag);
				return resultMap;
			}
			if(!FrontUserWithdrawOrderStatus.CHECKING.equals(fuwo.getStatus()) && !FrontUserWithdrawOrderStatus.FAIL.equals(fuwo.getStatus())) {
				message = "账单已处理完毕！不要重复处理";
				throw new TransactionException(message);
			}
			FrontUser fu = this.frontUserDao.get(fuwo.getFrontUser());
			if(fu == null) {
				message = "账单错误C";
				throw new TransactionException(message);
			}
			FrontUserAccount fua = this.frontUserAccountDao.get(fuwo.getFrontUser());
			if(fua == null) {
				message = "账单错误C";
				throw new TransactionException(message);
			}
			
			fuwo.setStatus(FrontUserWithdrawOrderStatus.CLOSE);
			fua.setBalanceLock(fua.getBalanceLock().subtract(fuwo.getReduceDAmount()));
			fua.setBalance(fua.getBalance().add(fuwo.getReduceDAmount()));
			this.frontUserWithdrawOrderDao.update(fuwo);
			this.frontUserAccountDao.update(fua);
			
			//提现通知消息
			FrontUserMessage fum = new FrontUserMessage();
			fum.setUuid(UUID.randomUUID().toString());
			fum.setFrontUser(fu.getUuid());
			fum.setFrontUserShowId(fu.getShowId());
			fum.setTitle("订单关闭提醒");
			fum.setContent("您在"+Utlity.timeSpanToChinaDateString(fuwo.getCreatetime())+"提现"+fuwo.getActualAmount()+"元，提现失败，订单关闭，请注意账户余额变动信息！");
			fum.setSourceId(fuwo.getUuid());
			fum.setStatus(FrontUserMessageStatus.NORMAL);
			fum.setType(FrontUserMessageType.USER_ORDER);
			fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_WITHDRAW);
			fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			this.frontUserMessageDao.sendMessage(fum);
			this.frontUserMessageDao.sendMessage(fum, SendSmsNew.TREASUREMALL_SH_TEMP_WITHDRAW_FAIL);
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
				log.info("获取zookeeper锁" + Constants.ZK_LOCK_PATHPREFIX+fua.getFrontUser());
				try {
					if(mutex.acquire(Constants.ZK_LOCK_TIMEOUT, TimeUnit.SECONDS)) {
						this.frontUserAccountDao.update(fua);
					}
				} catch (Exception e) {
					log.info("获取zookeeper锁" + Constants.ZK_LOCK_PATHPREFIX+fua.getFrontUser()+"-----异常:"+e.fillInStackTrace());
				} finally {
	    			if (mutex!=null){
	                    try {
							mutex.release();//释放锁
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
		//聚合支付交易状态
		String status = paramsls.get("orderState") == null ? "" : paramsls.get("orderState").toString();
		if("2".equals(status)) {
			//公共参数
			String passbackParams = paramsls.get("remark").toString();
			
			FrontUserRechargeOrder furo = null;
			if(!Utlity.checkStringNull(passbackParams)) {
				furo = this.frontUserRechargeOrderDao.get(passbackParams);
			} else {
				message = "账单错误A";
				throw new TransactionException(message);
			}
			if(furo == null) {
				message = "账单错误B";
				throw new TransactionException(message);
			}
			if(!FrontUserRechargeOrderStatus.NORMAL.equals(furo.getStatus())) {
				message = "账单已处理完毕！不要重复处理";
//				throw new TransactionException(message);
				resultMap.put("message", message);
				resultMap.put("result", resultFlag);
				return resultMap;
			}
			FrontUser fu = this.frontUserDao.get(furo.getFrontUser());
			if(fu == null) {
				message = "账单错误C";
				throw new TransactionException(message);
			}
			
			FrontUserAccount fua = this.frontUserAccountDao.get(furo.getFrontUser());
			if(fua == null) {
				message = "账单错误C";
				throw new TransactionException(message);
			}
			CapitalAccountStatistics cas = this.capitalAccountStatisticsDao.get(furo.getCapitalAccount());
			if(cas == null) {
				message = "账单错误D";
				throw new TransactionException(message);
			}
			CapitalAccount ca = this.capitalAccountDao.get(furo.getCapitalAccount());
			if(ca == null) {
				message = "账单错误E";
				throw new TransactionException(message);
			}
			
			//计算手续费
			BigDecimal amount = BigDecimal.valueOf(Double.valueOf(paramsls.get("amount").toString()));
			BigDecimal poundage = amount.multiply(ca.getPoundageRate());//订单金额*通道手续费
			
			furo.setStatus(FrontUserRechargeOrderStatus.CHECKED);
			furo.setOperattime(new Timestamp(System.currentTimeMillis()));
			//是否是首次充值
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
			
			//用户账户
			fua.setBalance(fua.getBalance().add(furo.getIncreaseDAmount()));
			fua.setTotalRecharge(fua.getTotalRecharge().add(furo.getAmount()));//总充值（法币）
			fua.setRechargeTimes(fua.getRechargeTimes() + 1);
			
			//20200805修改，改为根据系统设置的参数，判断是否满足用户级别变更的条件
			//获取用户级别变更参数
			SystemParam changeLinesp = this.systemParamDao.get(SystemParamKey.GROUP_CHANGE_LINE_RECHARGED);//充值订单超时时间--单位分钟
			BigDecimal changeLine = Utlity.GROUP_CHANGE_LINE_RECHARGED;
			if(changeLinesp != null) {
				changeLine = BigDecimal.valueOf(Double.valueOf(changeLinesp.getParamValue()));
			}
			if(fua.getTotalRecharge().compareTo(changeLine) >= 0) {//大于设置参数，则变更用户级别为充值用户
				fu.setLevel(FrontUserLevel.RECHARGED);
			}
			
//			if(furo.getIsFirsttime()) {//首次充值-变更用户级别
//				fu.setLevel(FrontUserLevel.RECHARGED);
//			}
			
			//渠道账户流水儿
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
			cah.setReason("用户充值");
			cah.setRemark(furo.getRemark());
			

			//渠道账户
			cas.setBalance(cas.getBalance().add(furo.getAmount()).subtract(poundage));
			cas.setRechargeTimes(cas.getRechargeTimes() + 1);
			cas.setTotalRecharge(cas.getTotalRecharge().add(furo.getAmount()));
			cas.setDailySum(cas.getDailySum().add(furo.getAmount()).subtract(poundage));
			
			//20200604活动入账
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
			
			//充值通知消息
			FrontUserMessage fum = new FrontUserMessage();
			fum.setUuid(UUID.randomUUID().toString());
			fum.setFrontUser(fu.getUuid());
			fum.setFrontUserShowId(fu.getShowId());
			fum.setTitle("充值到账提醒");
			fum.setContent("您在"+Utlity.timeSpanToChinaDateString(furo.getCreatetime())+"充值"+furo.getAmount()+"元已到账，请注意账户余额变动信息！");
			fum.setSourceId(fuh.getUuid());
			fum.setStatus(FrontUserMessageStatus.NORMAL);
			fum.setType(FrontUserMessageType.USER_ORDER);
			fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_RECHARGE);
			fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			this.frontUserMessageDao.sendMessage(fum);
			this.frontUserMessageDao.sendMessage(fum, SendSmsNew.TREASUREMALL_SH_TEMP_RECHARGE);
		} else if("1".equals(status)){//订单关闭
			//公共参数
			String passbackParams = paramsls.get("remark").toString();
			
			FrontUserRechargeOrder furo = null;
			if(!Utlity.checkStringNull(passbackParams)) {
				furo = this.frontUserRechargeOrderDao.get(passbackParams);
			} else {
				message = "账单错误A";
				throw new TransactionException(message);
			}
			if(furo == null) {
				message = "账单错误B";
				throw new TransactionException(message);
			}
			if(!FrontUserRechargeOrderStatus.NORMAL.equals(furo.getStatus())) {
				message = "账单已处理完毕！不要重复处理";
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
}
