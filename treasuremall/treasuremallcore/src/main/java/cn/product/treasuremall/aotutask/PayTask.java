package cn.product.treasuremall.aotutask;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.product.treasuremall.controller.base.TransactionException;
import cn.product.treasuremall.dao.ActivityInfoDao;
import cn.product.treasuremall.dao.ActivityInfoRecommendRankingDao;
import cn.product.treasuremall.dao.AdminOffsetOrderDao;
import cn.product.treasuremall.dao.CapitalAccountDao;
import cn.product.treasuremall.dao.CapitalAccountHistoryDao;
import cn.product.treasuremall.dao.CapitalAccountStatisticsDao;
import cn.product.treasuremall.dao.CapitalPlatformDao;
import cn.product.treasuremall.dao.FrontUserAccountDao;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.FrontUserHistoryDao;
import cn.product.treasuremall.dao.FrontUserRechargeOrderDao;
import cn.product.treasuremall.dao.FrontUserWithdrawOrderDao;
import cn.product.treasuremall.dao.PayNotifyInfoDao;
import cn.product.treasuremall.dao.SystemParamDao;
import cn.product.treasuremall.entity.ActivityInfo;
import cn.product.treasuremall.entity.ActivityInfo.ActivityInfoName;
import cn.product.treasuremall.entity.ActivityInfo.ActivityInfoStatus;
import cn.product.treasuremall.entity.ActivityInfoRecommendRanking;
import cn.product.treasuremall.entity.ActivityInfoRecommendRanking.ActivityInfoRecommendRankingStatus;
import cn.product.treasuremall.entity.ActivityInfoRecommendRanking.ActivityInfoRecommendRankingType;
import cn.product.treasuremall.entity.AdminOffsetOrder;
import cn.product.treasuremall.entity.AdminOffsetOrder.AdminOffsetOrderStatus;
import cn.product.treasuremall.entity.AdminOffsetOrder.AdminOffsetOrderType;
import cn.product.treasuremall.entity.CapitalAccount;
import cn.product.treasuremall.entity.CapitalAccountHistory;
import cn.product.treasuremall.entity.CapitalAccountHistory.CapitalAccountHistoryType;
import cn.product.treasuremall.entity.CapitalAccountStatistics;
import cn.product.treasuremall.entity.CapitalPlatform.CapitalPlatformType;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUser.FrontUserLevel;
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
import cn.product.treasuremall.entity.PayNotifyInfo.PayNotifyInfoPayType;
import cn.product.treasuremall.entity.PayNotifyInfo.PayNotifyInfoStatus;
import cn.product.treasuremall.entity.SystemParam;
import cn.product.treasuremall.entity.SystemParam.SystemParamKey;
import cn.product.treasuremall.entity.base.Constants;
import cn.product.treasuremall.util.JSONUtils;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.util.jinzun.JinzunUtlity;
import cn.product.treasuremall.util.pay.ActivityPayUtil;
import cn.product.treasuremall.util.unionpay.UnionPayUtlity;

@Component
public class PayTask {
	private final static Logger log = LoggerFactory.getLogger(PayTask.class);
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
    private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
    private CapitalAccountDao capitalAccountDao;
	
	@Autowired
    private CapitalPlatformDao capitalPlatformDao;
	
	@Autowired
    private CapitalAccountHistoryDao capitalAccountHistoryDao;
	
	@Autowired
	private PayNotifyInfoDao payNotifyInfoDao;
	
	@Autowired
	private FrontUserRechargeOrderDao frontUserRechargeOrderDao;
	
	@Autowired
	private FrontUserWithdrawOrderDao frontUserWithdrawOrderDao;
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private CapitalAccountStatisticsDao capitalAccountStatisticsDao;
    
    @Autowired
    private ActivityPayUtil activityPayUtil;
    
    @Autowired
    private SystemParamDao systemParamDao;
    
    @Autowired
	private ActivityInfoDao activityInfoDao;
    
    @Autowired
	private AdminOffsetOrderDao adminOffsetOrderDao;
    
    @Autowired
	private ActivityInfoRecommendRankingDao activityInfoRecommendRankingDao;
    

	/**
	 * 聚合支付异步通知消息处理
	 */
	@Scheduled(cron="*/15 * *  * * * ")
	public void notifyInfoByUnionTask() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", PayNotifyInfoStatus.UNPRO);
			List<PayNotifyInfo> list = this.payNotifyInfoDao.getListByParams(params);
			List<PayNotifyInfo> listUpdate = new ArrayList<PayNotifyInfo>();
			if(list != null && !list.isEmpty()){
				for(PayNotifyInfo pni : list){
					String data = pni.getSource();
					Map<String, Object> result = new HashMap<String, Object>();
					Map<String, Object> resultMap = null;
					if(PayNotifyInfoPayType.RECHARGE_UNION_COM.equals(pni.getPayType())){//聚合支付充值
						result.put("data", data);
						resultMap = this.frontUserHistoryDao.rechargeByUnion(result);
					} else if(PayNotifyInfoPayType.WITHDRAW_UNION_COM.equals(pni.getPayType())){//聚合支付提现
						result.put("data", data);
						resultMap = this.frontUserHistoryDao.withdrawByUnion(result);
					} else if(PayNotifyInfoPayType.RECHARGE_ACICPAY_COM.equals(pni.getPayType())){//兴达支付提现
						result.put("data", data);
						resultMap = this.frontUserHistoryDao.rechargeByAcicpay(result);
					} else if(PayNotifyInfoPayType.RECHARGE_JINZUN_COM.equals(pni.getPayType())){//金樽支付提现
						result.put("data", data);
						resultMap = this.frontUserHistoryDao.rechargeByJinzun(result);
					} else if(PayNotifyInfoPayType.RECHARGE_ALIPAY_COM.equals(pni.getPayType())){//支付宝充值
					} else if(PayNotifyInfoPayType.RECHARGE_WECHAT_COM.equals(pni.getPayType())){//微信充值
					} else {//其他类型不予处理
						continue;
					}
					
					if((Boolean)resultMap.get("result")){
						pni.setStatus(PayNotifyInfoStatus.SUCCESS);
	    			} else {
	    				pni.setStatus(PayNotifyInfoStatus.FAIL);
	    			}
					listUpdate.add(pni);
					Thread.sleep(100);//手动延迟
				}
			}
			if(listUpdate != null && listUpdate.size() > 0){
				this.payNotifyInfoDao.updateAll(listUpdate);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 充值未完成订单处理
	 */
	@Scheduled(cron="0 0/1 *  * * * ")
	public void rechargeByUnionTask() {
		try {
			Map<String, CapitalAccountStatistics> caMap = new HashMap<String, CapitalAccountStatistics>();
			Map<String, FrontUserAccount> fuaMap = new HashMap<String, FrontUserAccount>();
			Map<String, FrontUser> fuMap = new HashMap<String, FrontUser>();
			List<FrontUserRechargeOrder> orderList = new ArrayList<FrontUserRechargeOrder>();
			List<FrontUserHistory> fuhList = new ArrayList<FrontUserHistory>();
			List<CapitalAccountHistory> cahList = new ArrayList<CapitalAccountHistory>();
			List<FrontUserMessage> messageList = new ArrayList<FrontUserMessage>();
			List<FrontUserVoucher> voucherList = new ArrayList<FrontUserVoucher>();
			List<AdminOffsetOrder> aooList = new ArrayList<AdminOffsetOrder>();
			//查询未完成的支付宝订单
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", FrontUserRechargeOrderStatus.NORMAL);
			List<FrontUserRechargeOrder> list = this.frontUserRechargeOrderDao.getListByParams(params);
			if(list != null) {
				//获取订单超时时间配置
				SystemParam sp = this.systemParamDao.get(SystemParamKey.RECHARGE_TIMEOUT);//充值订单超时时间--单位分钟
				long timeline = Utlity.TIMELINE_ORDER;
				if(sp != null) {
					timeline = (long)(Double.valueOf(sp.getParamValue()) * 1000L * 60L);
				}
				for(FrontUserRechargeOrder furo : list) {
					CapitalAccount ca = this.capitalAccountDao.get(furo.getCapitalAccount());
					if(ca != null && !ca.getType().equals(CapitalPlatformType.UNION)) {
						continue;
					}
					String message = "";
					//判断交易时间是否超时，未超时的不处理，
					//超时的，调用支付宝交易查询接口确认订单信息
					Timestamp time = furo.getCreatetime();
					boolean flagTimeCheck = Utlity.checkOrderTime(time.getTime(), System.currentTimeMillis(), timeline);
					if(flagTimeCheck) {
						params.clear();
						params.put("orderNum", furo.getOrderNum() + "");
						Map<String, Object> resultMap = UnionPayUtlity.createOrder4APIwapCheck(params);
						if((Boolean)resultMap.get("result")){
							Map<String, Object> paramsls = JSONUtils.json2map(resultMap.get("response").toString()) ;
							//聚合支付交易状态
							String status = paramsls.get("status") == null ? "" : paramsls.get("status").toString();
							//公共参数
							String passbackParams = paramsls.get("passbackParams").toString();
							
							if(!Utlity.checkStringNull(passbackParams)) {
								furo = this.frontUserRechargeOrderDao.get(passbackParams);
							} else {
								message = "账单错误A";
//								throw new TransactionException(message);
								log.info("--------充值订单超时处理---------------：" + message);
								continue;
							}
							if(furo == null) {
								message = "账单错误B";
//								throw new TransactionException(message);
								log.info("--------充值订单超时处理---------------：" + message);
								continue;
							}
							if(!FrontUserRechargeOrderStatus.NORMAL.equals(furo.getStatus())) {
								message = "账单已处理完毕！不要重复处理";
//								throw new TransactionException(message);
								log.info("--------充值订单超时处理---------------：" + message);
								continue;
							}
							
							if("success".equals(status)) {
								BigDecimal poundage = BigDecimal.valueOf(Double.valueOf(paramsls.get("poundage").toString())).divide(BigDecimal.valueOf(100));
								
								FrontUser fu = null;
								if(fuMap.containsKey(furo.getFrontUser())) {
									fu = fuMap.get(furo.getFrontUser());
								} else {
									fu = this.frontUserDao.get(furo.getFrontUser());
								}
										
								if(fu == null) {
									message = "账单错误C";
//									throw new TransactionException(message);
									log.info("--------充值订单超时处理---------------：" + message);
									continue;
								}
								
								FrontUserAccount fua = null;
								if(fuaMap.containsKey(furo.getFrontUser())) {
									fua = fuaMap.get(furo.getFrontUser());
								} else {
									fua = this.frontUserAccountDao.get(furo.getFrontUser());
								}
								if(fua == null) {
									message = "账单错误C";
//									throw new TransactionException(message);
									log.info("--------充值订单超时处理---------------：" + message);
									continue;
								}
								CapitalAccountStatistics cas = null;
								if(caMap.containsKey(furo.getCapitalAccount())) {
									cas = caMap.get(furo.getCapitalAccount());
								} else {
									cas = this.capitalAccountStatisticsDao.get(furo.getCapitalAccount());
								}
								if(cas == null) {
									message = "账单错误D";
//									throw new TransactionException(message);
									log.info("--------充值订单超时处理---------------：" + message);
									continue;
								}
//								CapitalAccount ca = this.capitalAccountDao.get(furo.getCapitalAccount());
								if(ca == null) {
									message = "账单错误E";
//									throw new TransactionException(message);
									log.info("--------充值订单超时处理---------------：" + message);
									continue;
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
								
//								if(furo.getIsFirsttime()) {//首次充值-变更用户级别
//									fu.setLevel(FrontUserLevel.RECHARGED);
//								}
								
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
								
								if(fuvList.size() > 0) {
									voucherList.addAll(fuvList);
								}
								if(!Utlity.checkStringNull(fuhAct.getUuid()) && !Utlity.checkStringNull(aoo.getUuid())) {
									fuhList.add(fuhAct);
									aooList.add(aoo);
								}
								
								cahList.add(cah);
								caMap.put(cas.getCapitalAccount(), cas);
								fuhList.add(fuh);
								fuaMap.put(fua.getFrontUser(), fua);
								fuMap.put(fu.getUuid(), fu);
								orderList.add(furo);
								
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
								messageList.add(fum);
							} else if("close".equals(status)) {//订单关闭
								furo.setStatus(FrontUserRechargeOrderStatus.CLOSE);
								furo.setOperattime(new Timestamp(System.currentTimeMillis()));
								orderList.add(furo);
							}
						} else {//失败即订单不存在，不处理
							continue;
						}
					}
				}
			}
			
			//根据返回结果处理交易信息
			params.clear();
			if(!fuaMap.isEmpty()) {
				params.put("fuaMap", fuaMap);
			}
			if(!fuMap.isEmpty()) {
				params.put("fuMap", fuMap);
			}
			if(!caMap.isEmpty()) {
				params.put("caMap", caMap);
			}
			if(!orderList.isEmpty()) {
				params.put("orderList", orderList);
			}
			if(!fuhList.isEmpty()) {
				params.put("fuhList", fuhList);
			}
			if(!cahList.isEmpty()) {
				params.put("cahList", cahList);
			}
			if(!messageList.isEmpty()) {
				params.put("messageList", messageList);
			}
			if(!voucherList.isEmpty()) {
				params.put("voucherList", voucherList);
			}
			if(!aooList.isEmpty()) {
				params.put("aooList", aooList);
			}
			this.frontUserHistoryDao.rechargeTaskByUnion(params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 充值未完成订单处理
	 */
//	@Scheduled(cron="0 0/1 *  * * * ")
	public void rechargeByAcicpayTask() {
		try {
			Map<String, CapitalAccountStatistics> caMap = new HashMap<String, CapitalAccountStatistics>();
			Map<String, FrontUserAccount> fuaMap = new HashMap<String, FrontUserAccount>();
			Map<String, FrontUser> fuMap = new HashMap<String, FrontUser>();
			List<FrontUserRechargeOrder> orderList = new ArrayList<FrontUserRechargeOrder>();
			List<FrontUserHistory> fuhList = new ArrayList<FrontUserHistory>();
			List<CapitalAccountHistory> cahList = new ArrayList<CapitalAccountHistory>();
			List<FrontUserMessage> messageList = new ArrayList<FrontUserMessage>();
			List<FrontUserVoucher> voucherList = new ArrayList<FrontUserVoucher>();
			List<AdminOffsetOrder> aooList = new ArrayList<AdminOffsetOrder>();
			//查询未完成的支付宝订单
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", FrontUserRechargeOrderStatus.NORMAL);
			List<FrontUserRechargeOrder> list = this.frontUserRechargeOrderDao.getListByParams(params);
			if(list != null) {
				//获取订单超时时间配置
				SystemParam sp = this.systemParamDao.get(SystemParamKey.RECHARGE_TIMEOUT);//充值订单超时时间--单位分钟
				long timeline = Utlity.TIMELINE_ORDER;
				if(sp != null) {
					timeline = (long)(Double.valueOf(sp.getParamValue()) * 1000L * 60L);
				}
				for(FrontUserRechargeOrder furo : list) {
					CapitalAccount ca = this.capitalAccountDao.get(furo.getCapitalAccount());
					if(ca != null && !ca.getType().equals(CapitalPlatformType.ACICPAY)) {
						continue;
					}
					String message = "";
					//判断交易时间是否超时，未超时的不处理，
					//超时的，调用支付宝交易查询接口确认订单信息
					Timestamp time = furo.getCreatetime();
					boolean flagTimeCheck = Utlity.checkOrderTime(time.getTime(), System.currentTimeMillis(), timeline);
					if(flagTimeCheck) {
						if(!FrontUserRechargeOrderStatus.NORMAL.equals(furo.getStatus())) {
							message = "账单已处理完毕！不要重复处理";
//								throw new TransactionException(message);
							log.info("--------充值订单超时处理---------------：" + message);
							continue;
						}
						String status = "close";
						if("success".equals(status)) {
							//计算手续费
							BigDecimal amount = furo.getAmount();
							BigDecimal poundage = amount.multiply(ca.getPoundageRate());//订单金额*通道手续费
							
							FrontUser fu = null;
							if(fuMap.containsKey(furo.getFrontUser())) {
								fu = fuMap.get(furo.getFrontUser());
							} else {
								fu = this.frontUserDao.get(furo.getFrontUser());
							}
									
							if(fu == null) {
								message = "账单错误C";
//									throw new TransactionException(message);
								log.info("--------充值订单超时处理---------------：" + message);
								continue;
							}
							
							FrontUserAccount fua = null;
							if(fuaMap.containsKey(furo.getFrontUser())) {
								fua = fuaMap.get(furo.getFrontUser());
							} else {
								fua = this.frontUserAccountDao.get(furo.getFrontUser());
							}
							if(fua == null) {
								message = "账单错误C";
//									throw new TransactionException(message);
								log.info("--------充值订单超时处理---------------：" + message);
								continue;
							}
							CapitalAccountStatistics cas = null;
							if(caMap.containsKey(furo.getCapitalAccount())) {
								cas = caMap.get(furo.getCapitalAccount());
							} else {
								cas = this.capitalAccountStatisticsDao.get(furo.getCapitalAccount());
							}
							if(cas == null) {
								message = "账单错误D";
//									throw new TransactionException(message);
								log.info("--------充值订单超时处理---------------：" + message);
								continue;
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
							
							if(fuvList.size() > 0) {
								voucherList.addAll(fuvList);
							}
							if(!Utlity.checkStringNull(fuhAct.getUuid()) && !Utlity.checkStringNull(aoo.getUuid())) {
								fuhList.add(fuhAct);
								aooList.add(aoo);
							}
							
							cahList.add(cah);
							caMap.put(cas.getCapitalAccount(), cas);
							fuhList.add(fuh);
							fuaMap.put(fua.getFrontUser(), fua);
							fuMap.put(fu.getUuid(), fu);
							orderList.add(furo);
							
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
							messageList.add(fum);
						} else if("close".equals(status)) {//订单关闭
							furo.setStatus(FrontUserRechargeOrderStatus.CLOSE);
							furo.setOperattime(new Timestamp(System.currentTimeMillis()));
							orderList.add(furo);
						}
//						} else {//失败即订单不存在，不处理
//							continue;
//						}
					}
				}
			}
			
			//根据返回结果处理交易信息
			params.clear();
			if(!fuaMap.isEmpty()) {
				params.put("fuaMap", fuaMap);
			}
			if(!fuMap.isEmpty()) {
				params.put("fuMap", fuMap);
			}
			if(!caMap.isEmpty()) {
				params.put("caMap", caMap);
			}
			if(!orderList.isEmpty()) {
				params.put("orderList", orderList);
			}
			if(!fuhList.isEmpty()) {
				params.put("fuhList", fuhList);
			}
			if(!cahList.isEmpty()) {
				params.put("cahList", cahList);
			}
			if(!messageList.isEmpty()) {
				params.put("messageList", messageList);
			}
			if(!voucherList.isEmpty()) {
				params.put("voucherList", voucherList);
			}
			if(!aooList.isEmpty()) {
				params.put("aooList", aooList);
			}
			this.frontUserHistoryDao.rechargeTaskByUnion(params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 提现未完成订单处理
	 */
//	@Scheduled(cron="0 0/3 *  * * * ")
	public void withdrawByUnionTask() {
		try {
			Map<String, CapitalAccountStatistics> caMap = new HashMap<String, CapitalAccountStatistics>();
			Map<String, FrontUserAccount> fuaMap = new HashMap<String, FrontUserAccount>();
			List<FrontUserWithdrawOrder> orderList = new ArrayList<FrontUserWithdrawOrder>();
			List<FrontUserHistory> fuhList = new ArrayList<FrontUserHistory>();
			List<CapitalAccountHistory> cahList = new ArrayList<CapitalAccountHistory>();
			List<FrontUserMessage> messageList = new ArrayList<FrontUserMessage>();
			//查询未完成的支付宝订单
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", FrontUserWithdrawOrderStatus.NORMAL);
			List<FrontUserWithdrawOrder> list = this.frontUserWithdrawOrderDao.getListByParams(params);
			if(list != null) {
				for(FrontUserWithdrawOrder fuwo : list) {
					String message = "";
					//判断交易时间是否超时，未超时的不处理，
					//超时的，调用支付宝交易查询接口确认订单信息
					Timestamp time = fuwo.getCreatetime();
					boolean flagTimeCheck = Utlity.checkOrderTime(time.getTime(), System.currentTimeMillis());//10分钟超时
					if(flagTimeCheck) {
						params.clear();
						params.put("orderNum", fuwo.getOrderNum() + "");
						Map<String, Object> resultMap = UnionPayUtlity.createOrder4APIwapCheck(params);
						if((Boolean)resultMap.get("result")){
							Map<String, Object> paramsls = JSONUtils.json2map(resultMap.get("response").toString()) ;
							
							//公共参数
							String passbackParams = paramsls.get("passbackParams").toString();
							
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
							if(!FrontUserWithdrawOrderStatus.NORMAL.equals(fuwo.getStatus())) {
								message = "账单已处理完毕！不要重复处理";
//								throw new TransactionException(message);
								log.info("--------提现订单超时处理---------------：" + message);
								continue;
							}
							//聚合支付交易状态
							String status = paramsls.get("status") == null ? "" : paramsls.get("status").toString();
							if("success".equals(status)) {
								BigDecimal poundage = BigDecimal.valueOf(Double.valueOf(paramsls.get("poundage").toString())).divide(BigDecimal.valueOf(100));
								
								FrontUser fu = this.frontUserDao.get(fuwo.getFrontUser());
								if(fu == null) {
									message = "账单错误C";
//									throw new TransactionException(message);
									log.info("--------提现订单超时处理---------------：" + message);
									continue;
								}
								
								FrontUserAccount fua = null;
								if(fuaMap.containsKey(fuwo.getFrontUser())) {
									fua = fuaMap.get(fuwo.getFrontUser());
								} else {
									fua = this.frontUserAccountDao.get(fuwo.getFrontUser());
								}
									
								if(fua == null) {
									message = "账单错误C";
//									throw new TransactionException(message);
									log.info("--------提现订单超时处理---------------：" + message);
									continue;
								}
								CapitalAccountStatistics cas = null;
								if(caMap.containsKey(fuwo.getCapitalAccount())) {
									cas = caMap.get(fuwo.getCapitalAccount());
								} else {
									cas = this.capitalAccountStatisticsDao.get(fuwo.getCapitalAccount());
								}
										
								if(cas == null) {
									message = "账单错误D";
//									throw new TransactionException(message);
									log.info("--------提现订单超时处理---------------：" + message);
									continue;
								}
								CapitalAccount ca = this.capitalAccountDao.get(fuwo.getCapitalAccount());
								if(ca == null) {
									message = "账单错误E";
//									throw new TransactionException(message);
									log.info("--------提现订单超时处理---------------：" + message);
									continue;
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
								
								cahList.add(cah);
								caMap.put(cas.getCapitalAccount(), cas);
								fuhList.add(fuh);
								fuaMap.put(fua.getFrontUser(), fua);
								orderList.add(fuwo);
								
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
								messageList.add(fum);
							} else if("close".equals(status)) {//订单关闭
								FrontUser fu = this.frontUserDao.get(fuwo.getFrontUser());
								if(fu == null) {
									message = "账单错误C";
//									throw new TransactionException(message);
									log.info("--------提现订单超时处理---------------：" + message);
									continue;
								}
								FrontUserAccount fua = this.frontUserAccountDao.get(fuwo.getFrontUser());
								if(fua == null) {
									message = "账单错误C";
									throw new TransactionException(message);
								}
								
								fuwo.setStatus(FrontUserWithdrawOrderStatus.CLOSE);
								fua.setBalanceLock(fua.getBalanceLock().subtract(fuwo.getReduceDAmount()));
								fua.setBalance(fua.getBalance().add(fuwo.getReduceDAmount()));
								
								fuaMap.put(fua.getFrontUser(), fua);
								orderList.add(fuwo);
								
								//提现通知消息
								FrontUserMessage fum = new FrontUserMessage();
								fum.setUuid(UUID.randomUUID().toString());
								fum.setFrontUser(fu.getUuid());
								fum.setFrontUserShowId(fu.getShowId());
								fum.setTitle("提现失败提醒");
								fum.setContent("您在"+Utlity.timeSpanToChinaDateString(fuwo.getCreatetime())+"提现"+fuwo.getActualAmount()+"元，提现失败，请注意账户余额变动信息！");
								fum.setSourceId(fuwo.getUuid());
								fum.setStatus(FrontUserMessageStatus.NORMAL);
								fum.setType(FrontUserMessageType.USER_ORDER);
								fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_WITHDRAW);
								fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
								messageList.add(fum);
							} else if("fail".equals(status)) {//订单关闭
								FrontUser fu = this.frontUserDao.get(fuwo.getFrontUser());
								if(fu == null) {
									message = "账单错误C";
//									throw new TransactionException(message);
									log.info("--------提现订单超时处理---------------：" + message);
									continue;
								}
								FrontUserAccount fua = this.frontUserAccountDao.get(fuwo.getFrontUser());
								if(fua == null) {
									message = "账单错误C";
									throw new TransactionException(message);
								}
								
								fuwo.setStatus(FrontUserWithdrawOrderStatus.FAIL);
//								fua.setBalanceLock(fua.getBalanceLock().subtract(fuwo.getReduceDAmount()));
//								fua.setBalance(fua.getBalance().add(fuwo.getReduceDAmount()));
//								
//								fuaMap.put(fua.getFrontUser(), fua);
								orderList.add(fuwo);
								
//								//提现通知消息
//								FrontUserMessage fum = new FrontUserMessage();
//								fum.setUuid(UUID.randomUUID().toString());
//								fum.setFrontUser(fu.getUuid());
//								fum.setFrontUserShowId(fu.getShowId());
//								fum.setTitle("提现失败提醒");
//								fum.setContent("您在"+Utlity.timeSpanToChinaDateString(fuwo.getCreatetime())+"提现"+fuwo.getActualAmount()+"元，提现失败，请注意账户余额变动信息！");
//								fum.setSourceId(fuwo.getUuid());
//								fum.setStatus(FrontUserMessageStatus.NORMAL);
//								fum.setType(FrontUserMessageType.USER_ORDER);
//								fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_WITHDRAW);
//								fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
//								messageList.add(fum);
							}
						} else {//失败即订单不存在，不处理
							continue;
						}
					}
				}
			}
			
			//根据返回结果处理交易信息
			params.clear();
			if(!fuaMap.isEmpty()) {
				params.put("fuaMap", fuaMap);
			}
			if(!caMap.isEmpty()) {
				params.put("caMap", caMap);
			}
			if(!orderList.isEmpty()) {
				params.put("orderList", orderList);
			}
			if(!fuhList.isEmpty()) {
				params.put("fuhList", fuhList);
			}
			if(!cahList.isEmpty()) {
				params.put("cahList", cahList);
			}
			if(!messageList.isEmpty()) {
				params.put("messageList", messageList);
			}
			this.frontUserHistoryDao.withdrawTaskByUnion(params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 充值未完成订单处理
	 */
	@Scheduled(cron="0 0/1 *  * * * ")
	public void rechargeByJinzunTask() {
		try {
			Map<String, CapitalAccountStatistics> caMap = new HashMap<String, CapitalAccountStatistics>();
			Map<String, FrontUserAccount> fuaMap = new HashMap<String, FrontUserAccount>();
			Map<String, FrontUser> fuMap = new HashMap<String, FrontUser>();
			List<FrontUserRechargeOrder> orderList = new ArrayList<FrontUserRechargeOrder>();
			List<FrontUserHistory> fuhList = new ArrayList<FrontUserHistory>();
			List<CapitalAccountHistory> cahList = new ArrayList<CapitalAccountHistory>();
			List<FrontUserMessage> messageList = new ArrayList<FrontUserMessage>();
			List<FrontUserVoucher> voucherList = new ArrayList<FrontUserVoucher>();
			List<AdminOffsetOrder> aooList = new ArrayList<AdminOffsetOrder>();
			//查询未完成的支付宝订单
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", FrontUserRechargeOrderStatus.NORMAL);
			List<FrontUserRechargeOrder> list = this.frontUserRechargeOrderDao.getListByParams(params);
			if(list != null) {
				//获取订单超时时间配置
				SystemParam sp = this.systemParamDao.get(SystemParamKey.RECHARGE_TIMEOUT);//充值订单超时时间--单位分钟
				long timeline = Utlity.TIMELINE_ORDER;
				if(sp != null) {
					timeline = (long)(Double.valueOf(sp.getParamValue()) * 1000L * 60L);
				}
				
				for(FrontUserRechargeOrder furo : list) {
					CapitalAccount ca = this.capitalAccountDao.get(furo.getCapitalAccount());
					if(ca != null && !ca.getType().equals(CapitalPlatformType.JINZUN)) {
						continue;
					}
					String message = "";
					//判断交易时间是否超时，未超时的不处理，
					//超时的，调用支付宝交易查询接口确认订单信息
					Timestamp time = furo.getCreatetime();
					boolean flagTimeCheck = Utlity.checkOrderTime(time.getTime(), System.currentTimeMillis(), timeline);
					if(flagTimeCheck) {
						params.clear();
						params.put("orderNum", furo.getOrderNum() + "");
						params.put("createtime", System.currentTimeMillis() + "");
						Map<String, Object> resultMap = JinzunUtlity.queryOrder(params);
						if((Boolean)resultMap.get("result")){
							Map<String, Object> resultParamsls = JSONUtils.json2map(resultMap.get("response").toString()) ;
							//取交易状态
							Boolean result = resultParamsls.get("successed") == null ? false : (Boolean)resultParamsls.get("successed");
							
							if(result) {
								Map<String, Object> paramsls = JSONUtils.json2map(resultParamsls.get("returnValue").toString());
								if("2".equals(paramsls.get("orderState").toString())){
									String orderNum = paramsls.get("outTradeNo").toString();
									//公共参数
									if(!orderNum.equals(String.valueOf(furo.getOrderNum()))) {
										message = "账单错误A";
										log.info("--------充值订单超时处理---------------：" + message);
										continue;
									}
									if(!FrontUserRechargeOrderStatus.NORMAL.equals(furo.getStatus())) {
										message = "账单已处理完毕！不要重复处理";
										log.info("--------充值订单超时处理---------------：" + message);
										continue;
									}
									BigDecimal poundage = BigDecimal.valueOf(Double.valueOf(paramsls.get("serviceCharge") == null ? "0" : paramsls.get("serviceCharge").toString()));
									
									FrontUser fu = null;
									if(fuMap.containsKey(furo.getFrontUser())) {
										fu = fuMap.get(furo.getFrontUser());
									} else {
										fu = this.frontUserDao.get(furo.getFrontUser());
									}
											
									if(fu == null) {
										message = "账单错误C";
										log.info("--------充值订单超时处理---------------：" + message);
										continue;
									}
									
									FrontUserAccount fua = null;
									if(fuaMap.containsKey(furo.getFrontUser())) {
										fua = fuaMap.get(furo.getFrontUser());
									} else {
										fua = this.frontUserAccountDao.get(furo.getFrontUser());
									}
									if(fua == null) {
										message = "账单错误C";
										log.info("--------充值订单超时处理---------------：" + message);
										continue;
									}
									CapitalAccountStatistics cas = null;
									if(caMap.containsKey(furo.getCapitalAccount())) {
										cas = caMap.get(furo.getCapitalAccount());
									} else {
										cas = this.capitalAccountStatisticsDao.get(furo.getCapitalAccount());
									}
									if(cas == null) {
										message = "账单错误D";
										log.info("--------充值订单超时处理---------------：" + message);
										continue;
									}
									if(ca == null) {
										message = "账单错误E";
										log.info("--------充值订单超时处理---------------：" + message);
										continue;
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
									
//									if(furo.getIsFirsttime()) {//首次充值-变更用户级别
//										fu.setLevel(FrontUserLevel.RECHARGED);
//									}
									
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
									
									if(fuvList.size() > 0) {
										voucherList.addAll(fuvList);
									}
									if(!Utlity.checkStringNull(fuhAct.getUuid()) && !Utlity.checkStringNull(aoo.getUuid())) {
										fuhList.add(fuhAct);
										aooList.add(aoo);
									}
									
	
									//渠道账户
									cas.setBalance(cas.getBalance().add(furo.getAmount()).subtract(poundage));
									cas.setRechargeTimes(cas.getRechargeTimes() + 1);
									cas.setTotalRecharge(cas.getTotalRecharge().add(furo.getAmount()));
									cas.setDailySum(cas.getDailySum().add(furo.getAmount()).subtract(poundage));
									
									cahList.add(cah);
									caMap.put(cas.getCapitalAccount(), cas);
									fuhList.add(fuh);
									fuaMap.put(fua.getFrontUser(), fua);
									fuMap.put(fu.getUuid(), fu);
									orderList.add(furo);
									
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
									messageList.add(fum);
								}else{
								//订单关闭
									furo.setStatus(FrontUserRechargeOrderStatus.CLOSE);
									furo.setOperattime(new Timestamp(System.currentTimeMillis()));
									orderList.add(furo);
								}
							} else{ //失败即订单不存在，不处理
								continue;
							}
						} else {//失败即订单不存在，不处理
							continue;
						}
					}
				}
			}
			
			//根据返回结果处理交易信息
			params.clear();
			if(!fuaMap.isEmpty()) {
				params.put("fuaMap", fuaMap);
			}
			if(!fuMap.isEmpty()) {
				params.put("fuMap", fuMap);
			}
			if(!caMap.isEmpty()) {
				params.put("caMap", caMap);
			}
			if(!orderList.isEmpty()) {
				params.put("orderList", orderList);
			}
			if(!fuhList.isEmpty()) {
				params.put("fuhList", fuhList);
			}
			if(!cahList.isEmpty()) {
				params.put("cahList", cahList);
			}
			if(!messageList.isEmpty()) {
				params.put("messageList", messageList);
			}
			if(!voucherList.isEmpty()) {
				params.put("voucherList", voucherList);
			}
			if(!aooList.isEmpty()) {
				params.put("aooList", aooList);
			}
			this.frontUserHistoryDao.rechargeTaskByUnion(params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	/**
	 * 邀新活动，满足条件的充值订单，进行相关奖励信息的上账操作
	 * 目前规则为延迟一天上账
	 * 获取活动信息，以获得获利上账比率，
	 * 是否需要判断活动是否进行？
	 * 查询订单表里，一天前，状态为checked activityInfo 字段包含recommend，且offsetOrder为空的记录。
	 * 判断时间是否够一天
	 * 按照充值金额*获利比率 计算获利金额
	 * 通过frontUser查询到邀请人，并上账到邀请人账户
	 */
	@Scheduled(cron="*/10 * *  * * * ")
	public void recommendTash() {
		try {
			//获取活动信息
			ActivityInfo ai = this.activityInfoDao.get(ActivityInfoName.RECOMMEND);
			if(ai != null && ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {//状态为开启
				
				//获取返利利率
				BigDecimal rate = BigDecimal.ZERO;
				String awardRate = "";
				Map<String, Object> detailMap = JSONUtils.json2map(ai.getConfiguration());
				if(detailMap.get("awardRate") != null){
					awardRate = detailMap.get("awardRate").toString();
					rate = BigDecimal.valueOf(Double.valueOf(awardRate)).divide(BigDecimal.valueOf(100));
				}
				
				if(rate.compareTo(BigDecimal.ZERO) > 0) {
					//查询充值订单
					Map<String, Object> searchMap = new HashMap<String, Object>();
					searchMap.put("status", FrontUserRechargeOrderStatus.CHECKED);
					searchMap.put("activityId", ActivityInfoName.RECOMMEND);
					searchMap.put("isOffsetOrder", "0");
					String endtime = Utlity.timeSpanToString(new Timestamp(System.currentTimeMillis() - 1000*60*60*24L));
					searchMap.put("endtime", endtime);
					List<FrontUserRechargeOrder> listOrder = this.frontUserRechargeOrderDao.getListByParams(searchMap);
					if(listOrder != null && listOrder.size() > 0) {
						List<FrontUserHistory> listHistory = new ArrayList<FrontUserHistory>();
						List<AdminOffsetOrder> listOffset = new ArrayList<AdminOffsetOrder>();
						Map<String, FrontUserAccount> fuaMap = new HashMap<String, FrontUserAccount>();
						List<FrontUserRechargeOrder> listOrderNew = new ArrayList<FrontUserRechargeOrder>();
						Map<String, ActivityInfoRecommendRanking> airrMap = new HashMap<String, ActivityInfoRecommendRanking>();
						for(FrontUserRechargeOrder furo : listOrder) {
							//计算获利金额，两位小数，向下取整
							BigDecimal awardPrice = furo.getIncreaseDAmount().multiply(rate).setScale(2, BigDecimal.ROUND_DOWN);
							//获取邀请人
							FrontUser fu = this.frontUserDao.get(furo.getFrontUser());
							if(fu != null && !Utlity.checkStringNull(fu.getAgent())) {
								FrontUserAccount fua = null;
								if(fuaMap.containsKey(fu.getAgent())) {
									fua = fuaMap.get(fu.getAgent());
								} else {
									fua = this.frontUserAccountDao.get(fu.getAgent());//邀请人账户
								}
									
								//生成系统订单和账户流水

								String content = "用户邀新返利活动赠送" + awardRate + "%金币";
			    				
								AdminOffsetOrder aoo = new AdminOffsetOrder();
			    				aoo.setUuid(UUID.randomUUID().toString());
			    				aoo.setdAmount(awardPrice);
			    				aoo.setFrontUser(fua.getFrontUser());
								aoo.setOrderType(Constants.ORDER_TYPE_SYSTEM_ADD);
			    				aoo.setOrderNum(Utlity.getOrderNum());
								aoo.setReason(content);
								aoo.setRemark(content);
								aoo.setStatus(AdminOffsetOrderStatus.CHECKED);
								aoo.setType(AdminOffsetOrderType.ADMIN_ADD);
			    				aoo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			    				aoo.setRechargeOrder(furo.getUuid());
			    				listOffset.add(aoo);
			    				
			    				FrontUserHistory fuhAct = new FrontUserHistory();
			    				fuhAct.setUuid(UUID.randomUUID().toString());
			    				fuhAct.setFrontUser(aoo.getFrontUser());
			    				fuhAct.setOrderNum(aoo.getOrderNum());
			    				fuhAct.setOrderId(aoo.getUuid());
			    				fuhAct.setdAmount(aoo.getdAmount());
			    				fuhAct.setBalanceBefore(fua.getBalance().add(fua.getBalanceLock()));
			    				fuhAct.setCreatetime(aoo.getCreatetime());
			    				fuhAct.setRemark(content);
			    				
			    				fuhAct.setOrderType(Constants.ORDER_TYPE_USER_RECOMMEND);
								fuhAct.setType(FrontUserHistoryType.USER_ADD);
								fuhAct.setReason(content);
								
								fua.setBalance(fua.getBalance().add(aoo.getdAmount()));
								fuhAct.setBalanceAfter(fua.getBalance().add(fua.getBalanceLock()));
								fuaMap.put(fua.getFrontUser(), fua);
								listHistory.add(fuhAct);
								
								furo.setOffsetOrder(aoo.getUuid());
								listOrderNew.add(furo);
								
								//更新排行榜数据
								//获取订单超时时间配置
								SystemParam sp = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);//充值订单超时时间--单位分钟
								BigDecimal goldRate = BigDecimal.ONE;
								if(sp != null) {
									goldRate = BigDecimal.valueOf(Double.valueOf(sp.getParamValue()));
								}
								
								//邀请排行榜更新
								ActivityInfoRecommendRanking airr = null;
								if(airrMap.containsKey(fua.getFrontUser())) {
									airr = airrMap.get(fua.getFrontUser());
								} else {
									airr = this.activityInfoRecommendRankingDao.getByFrontUser(fua.getFrontUser());
								}
								
								if(airr != null) {
									airr.setAward(airr.getAward().add(awardPrice.divide(goldRate,2,BigDecimal.ROUND_HALF_UP)));
								} else {
									airr = new ActivityInfoRecommendRanking();
									airr.setUuid(UUID.randomUUID().toString());
									airr.setFrontUser(fua.getFrontUser());
									airr.setType(ActivityInfoRecommendRankingType.NORMAL);
									airr.setStatus(ActivityInfoRecommendRankingStatus.NORMAL);
									airr.setCreatetime(new Timestamp(System.currentTimeMillis()));
									airr.setAward(awardPrice.divide(goldRate,2,BigDecimal.ROUND_HALF_UP));
									airr.setRecommend(0);
								}
								if(furo.getIsFirsttime()) {//首充，记录有效邀新
									airr.setRecommend(airr.getRecommend() + 1);
								}
								airrMap.put(fua.getFrontUser(), airr);
							}
						}
						Map<String, Object> paramsMap = new HashMap<String, Object>();
						paramsMap.put("frontUserAccount", fuaMap);
						paramsMap.put("frontUserHistory", listHistory);
						paramsMap.put("frontUserRechargeOrder", listOrderNew);
						paramsMap.put("adminOffsetOrder", listOffset);
						paramsMap.put("activityInfoRecommendRanking", airrMap);
						this.adminOffsetOrderDao.recommendTask(paramsMap);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}