package cn.product.worldmall.aotutask;

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

import com.paypal.http.HttpResponse;
import com.paypal.orders.Order;

import cn.product.worldmall.controller.base.TransactionException;
import cn.product.worldmall.dao.ActivityInfoDao;
import cn.product.worldmall.dao.ActivityInfoRecommendRankingDao;
import cn.product.worldmall.dao.AdminOffsetOrderDao;
import cn.product.worldmall.dao.CapitalAccountDao;
import cn.product.worldmall.dao.CapitalAccountHistoryDao;
import cn.product.worldmall.dao.CapitalAccountStatisticsDao;
import cn.product.worldmall.dao.CapitalPlatformDao;
import cn.product.worldmall.dao.FrontUserAccountDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserHistoryDao;
import cn.product.worldmall.dao.FrontUserRechargeOrderDao;
import cn.product.worldmall.dao.FrontUserWithdrawOrderDao;
import cn.product.worldmall.dao.PayNotifyInfoDao;
import cn.product.worldmall.dao.SystemParamDao;
import cn.product.worldmall.entity.ActivityInfo;
import cn.product.worldmall.entity.ActivityInfo.ActivityInfoName;
import cn.product.worldmall.entity.ActivityInfo.ActivityInfoStatus;
import cn.product.worldmall.entity.ActivityInfoRecommendRanking;
import cn.product.worldmall.entity.ActivityInfoRecommendRanking.ActivityInfoRecommendRankingStatus;
import cn.product.worldmall.entity.ActivityInfoRecommendRanking.ActivityInfoRecommendRankingType;
import cn.product.worldmall.entity.AdminOffsetOrder;
import cn.product.worldmall.entity.AdminOffsetOrder.AdminOffsetOrderStatus;
import cn.product.worldmall.entity.AdminOffsetOrder.AdminOffsetOrderType;
import cn.product.worldmall.entity.CapitalAccount;
import cn.product.worldmall.entity.CapitalAccountHistory;
import cn.product.worldmall.entity.CapitalAccountHistory.CapitalAccountHistoryType;
import cn.product.worldmall.entity.CapitalAccountStatistics;
import cn.product.worldmall.entity.CapitalPlatform.CapitalPlatformType;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUser.FrontUserLevel;
import cn.product.worldmall.entity.FrontUserAccount;
import cn.product.worldmall.entity.FrontUserHistory;
import cn.product.worldmall.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.worldmall.entity.FrontUserMessage;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageSourceType;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageStatus;
import cn.product.worldmall.entity.FrontUserMessage.FrontUserMessageType;
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
import cn.product.worldmall.entity.base.Constants;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.util.jinzun.JinzunUtlity;
import cn.product.worldmall.util.pay.ActivityPayUtil;
import cn.product.worldmall.util.paypal.PaypalAccount;
import cn.product.worldmall.util.paypal.PaypalClient;
import cn.product.worldmall.util.unionpay.UnionPayUtlity;

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
	 * ????????????????????????????????????
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
					if(PayNotifyInfoPayType.RECHARGE_UNION_COM.equals(pni.getPayType())){//??????????????????
						result.put("data", data);
						resultMap = this.frontUserHistoryDao.rechargeByUnion(result);
					} else if(PayNotifyInfoPayType.WITHDRAW_UNION_COM.equals(pni.getPayType())){//??????????????????
						result.put("data", data);
						resultMap = this.frontUserHistoryDao.withdrawByUnion(result);
					} else if(PayNotifyInfoPayType.RECHARGE_ACICPAY_COM.equals(pni.getPayType())){//??????????????????
						result.put("data", data);
						resultMap = this.frontUserHistoryDao.rechargeByAcicpay(result);
					} else if(PayNotifyInfoPayType.RECHARGE_JINZUN_COM.equals(pni.getPayType())){//??????????????????
						result.put("data", data);
						resultMap = this.frontUserHistoryDao.rechargeByJinzun(result);
					} else if(PayNotifyInfoPayType.RECHARGE_STRIPE_COM.equals(pni.getPayType())){//Stripe????????????
						result.put("data", data);
						resultMap = this.frontUserHistoryDao.rechargeByStripe(result);
					} else if(PayNotifyInfoPayType.RECHARGE_ALIPAY_COM.equals(pni.getPayType())){//???????????????
					} else if(PayNotifyInfoPayType.RECHARGE_WECHAT_COM.equals(pni.getPayType())){//????????????
					} else {//????????????????????????
						continue;
					}
					
					if((Boolean)resultMap.get("result")){
						pni.setStatus(PayNotifyInfoStatus.SUCCESS);
	    			} else {
	    				pni.setStatus(PayNotifyInfoStatus.FAIL);
	    			}
					listUpdate.add(pni);
					Thread.sleep(100);//????????????
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
	 * ???????????????????????????
	 */
//	@Scheduled(cron="0 0/1 *  * * * ")
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
			//?????????????????????????????????
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", FrontUserRechargeOrderStatus.NORMAL);
			List<FrontUserRechargeOrder> list = this.frontUserRechargeOrderDao.getListByParams(params);
			if(list != null) {
				//??????????????????????????????
				SystemParam sp = this.systemParamDao.get(SystemParamKey.RECHARGE_TIMEOUT);//????????????????????????--????????????
				long timeline = Utlity.TIMELINE_ORDER;
				if(sp != null) {
					timeline = (long)(Double.valueOf(sp.getParamValue()) * 1000L * 60L);
				}
				
				//?????????????????????
				SystemParam spcurrency = this.systemParamDao.get(SystemParamKey.INTERNATIONAL_CURRENCIES_SYMBOL);
				String symbol = "";
				
				for(FrontUserRechargeOrder furo : list) {
					CapitalAccount ca = this.capitalAccountDao.get(furo.getCapitalAccount());
					if(ca != null && !ca.getType().equals(CapitalPlatformType.UNION)) {
						continue;
					}
					String message = "";
					//?????????????????????????????????????????????????????????
					//???????????????????????????????????????????????????????????????
					Timestamp time = furo.getCreatetime();
					boolean flagTimeCheck = Utlity.checkOrderTime(time.getTime(), System.currentTimeMillis(), timeline);
					if(flagTimeCheck) {
						params.clear();
						params.put("orderNum", furo.getOrderNum() + "");
						Map<String, Object> resultMap = UnionPayUtlity.createOrder4APIwapCheck(params);
						if((Boolean)resultMap.get("result")){
							Map<String, Object> paramsls = JSONUtils.json2map(resultMap.get("response").toString()) ;
							//????????????????????????
							String status = paramsls.get("status") == null ? "" : paramsls.get("status").toString();
							//????????????
							String passbackParams = paramsls.get("passbackParams").toString();
							
							if(!Utlity.checkStringNull(passbackParams)) {
								furo = this.frontUserRechargeOrderDao.get(passbackParams);
							} else {
								message = "????????????A";
//								throw new TransactionException(message);
								log.info("--------????????????????????????---------------???" + message);
								continue;
							}
							if(furo == null) {
								message = "????????????B";
//								throw new TransactionException(message);
								log.info("--------????????????????????????---------------???" + message);
								continue;
							}
							if(!FrontUserRechargeOrderStatus.NORMAL.equals(furo.getStatus())) {
								message = "??????????????????????????????????????????";
//								throw new TransactionException(message);
								log.info("--------????????????????????????---------------???" + message);
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
									message = "????????????C";
//									throw new TransactionException(message);
									log.info("--------????????????????????????---------------???" + message);
									continue;
								}
								
								FrontUserAccount fua = null;
								if(fuaMap.containsKey(furo.getFrontUser())) {
									fua = fuaMap.get(furo.getFrontUser());
								} else {
									fua = this.frontUserAccountDao.get(furo.getFrontUser());
								}
								if(fua == null) {
									message = "????????????C";
//									throw new TransactionException(message);
									log.info("--------????????????????????????---------------???" + message);
									continue;
								}
								CapitalAccountStatistics cas = null;
								if(caMap.containsKey(furo.getCapitalAccount())) {
									cas = caMap.get(furo.getCapitalAccount());
								} else {
									cas = this.capitalAccountStatisticsDao.get(furo.getCapitalAccount());
								}
								if(cas == null) {
									message = "????????????D";
//									throw new TransactionException(message);
									log.info("--------????????????????????????---------------???" + message);
									continue;
								}
//								CapitalAccount ca = this.capitalAccountDao.get(furo.getCapitalAccount());
								if(ca == null) {
									message = "????????????E";
//									throw new TransactionException(message);
									log.info("--------????????????????????????---------------???" + message);
									continue;
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
								
//								if(furo.getIsFirsttime()) {//????????????-??????????????????
//									fu.setLevel(FrontUserLevel.RECHARGED);
//								}
								
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
								
								//??????????????????
								if(spcurrency != null) {
									Map<String, Object> currencies = JSONUtils.json2map(spcurrency.getParamValue());
									if(currencies != null && !currencies.isEmpty()) {
										symbol = currencies.get(furo.getCurrency()) == null ? "" : currencies.get(furo.getCurrency()).toString();
									}
								}
								if(Utlity.checkStringNull(symbol)) {
									symbol = furo.getCurrency();
								}
								FrontUserMessage fum = new FrontUserMessage();
								fum.setUuid(UUID.randomUUID().toString());
								fum.setFrontUser(fu.getUuid());
								fum.setFrontUserShowId(fu.getShowId());
								fum.setTitle("Top-up Successful");
//								fum.setContent("??????"+Utlity.timeSpanToChinaDateString(furo.getCreatetime())+"??????"+furo.getAmount()+"???????????????????????????????????????????????????");
								fum.setContent("Your recharge of " + symbol + furo.getCurrencyAmount() 
//								+ " at " + Utlity.timeSpanToUsString(furo.getCreatetime()) 
													+ " was successful,please pay attention to the account balance.");
								fum.setSourceId(fuh.getUuid());
								fum.setStatus(FrontUserMessageStatus.NORMAL);
								fum.setType(FrontUserMessageType.USER_ORDER);
								fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_RECHARGE);
								fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
								messageList.add(fum);
							} else if("close".equals(status)) {//????????????
								furo.setStatus(FrontUserRechargeOrderStatus.CLOSE);
								furo.setOperattime(new Timestamp(System.currentTimeMillis()));
								orderList.add(furo);
							}
						} else {//????????????????????????????????????
							continue;
						}
					}
				}
			}
			
			//????????????????????????????????????
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
	 * ???????????????????????????
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
			//?????????????????????????????????
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", FrontUserRechargeOrderStatus.NORMAL);
			List<FrontUserRechargeOrder> list = this.frontUserRechargeOrderDao.getListByParams(params);
			if(list != null) {
				//??????????????????????????????
				SystemParam sp = this.systemParamDao.get(SystemParamKey.RECHARGE_TIMEOUT);//????????????????????????--????????????
				long timeline = Utlity.TIMELINE_ORDER;
				if(sp != null) {
					timeline = (long)(Double.valueOf(sp.getParamValue()) * 1000L * 60L);
				}
				
				//?????????????????????
				SystemParam spcurrency = this.systemParamDao.get(SystemParamKey.INTERNATIONAL_CURRENCIES_SYMBOL);
				String symbol = "";
				
				for(FrontUserRechargeOrder furo : list) {
					CapitalAccount ca = this.capitalAccountDao.get(furo.getCapitalAccount());
					if(ca != null && !ca.getType().equals(CapitalPlatformType.ACICPAY)) {
						continue;
					}
					String message = "";
					//?????????????????????????????????????????????????????????
					//???????????????????????????????????????????????????????????????
					Timestamp time = furo.getCreatetime();
					boolean flagTimeCheck = Utlity.checkOrderTime(time.getTime(), System.currentTimeMillis(), timeline);
					if(flagTimeCheck) {
						if(!FrontUserRechargeOrderStatus.NORMAL.equals(furo.getStatus())) {
							message = "??????????????????????????????????????????";
//								throw new TransactionException(message);
							log.info("--------????????????????????????---------------???" + message);
							continue;
						}
						String status = "close";
						if("success".equals(status)) {
							//???????????????
							BigDecimal amount = furo.getAmount();
							BigDecimal poundage = amount.multiply(ca.getPoundageRate());//????????????*???????????????
							
							FrontUser fu = null;
							if(fuMap.containsKey(furo.getFrontUser())) {
								fu = fuMap.get(furo.getFrontUser());
							} else {
								fu = this.frontUserDao.get(furo.getFrontUser());
							}
									
							if(fu == null) {
								message = "????????????C";
//									throw new TransactionException(message);
								log.info("--------????????????????????????---------------???" + message);
								continue;
							}
							
							FrontUserAccount fua = null;
							if(fuaMap.containsKey(furo.getFrontUser())) {
								fua = fuaMap.get(furo.getFrontUser());
							} else {
								fua = this.frontUserAccountDao.get(furo.getFrontUser());
							}
							if(fua == null) {
								message = "????????????C";
//									throw new TransactionException(message);
								log.info("--------????????????????????????---------------???" + message);
								continue;
							}
							CapitalAccountStatistics cas = null;
							if(caMap.containsKey(furo.getCapitalAccount())) {
								cas = caMap.get(furo.getCapitalAccount());
							} else {
								cas = this.capitalAccountStatisticsDao.get(furo.getCapitalAccount());
							}
							if(cas == null) {
								message = "????????????D";
//									throw new TransactionException(message);
								log.info("--------????????????????????????---------------???" + message);
								continue;
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
							
							//??????????????????
							if(spcurrency != null) {
								Map<String, Object> currencies = JSONUtils.json2map(spcurrency.getParamValue());
								if(currencies != null && !currencies.isEmpty()) {
									symbol = currencies.get(furo.getCurrency()) == null ? "" : currencies.get(furo.getCurrency()).toString();
								}
							}
							if(Utlity.checkStringNull(symbol)) {
								symbol = furo.getCurrency();
							}
							FrontUserMessage fum = new FrontUserMessage();
							fum.setUuid(UUID.randomUUID().toString());
							fum.setFrontUser(fu.getUuid());
							fum.setFrontUserShowId(fu.getShowId());
							fum.setTitle("Top-up Successful");
//							fum.setContent("??????"+Utlity.timeSpanToChinaDateString(furo.getCreatetime())+"??????"+furo.getAmount()+"???????????????????????????????????????????????????");
							fum.setContent("Your recharge of " + symbol + furo.getCurrencyAmount() 
//							+ " at " + Utlity.timeSpanToUsString(furo.getCreatetime()) 
							+ " was successful,please pay attention to the account balance.");
							fum.setSourceId(fuh.getUuid());
							fum.setStatus(FrontUserMessageStatus.NORMAL);
							fum.setType(FrontUserMessageType.USER_ORDER);
							fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_RECHARGE);
							fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
							messageList.add(fum);
						} else if("close".equals(status)) {//????????????
							furo.setStatus(FrontUserRechargeOrderStatus.CLOSE);
							furo.setOperattime(new Timestamp(System.currentTimeMillis()));
							orderList.add(furo);
						}
//						} else {//????????????????????????????????????
//							continue;
//						}
					}
				}
			}
			
			//????????????????????????????????????
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
	 * ???????????????????????????
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
			//?????????????????????????????????
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", FrontUserWithdrawOrderStatus.NORMAL);
			List<FrontUserWithdrawOrder> list = this.frontUserWithdrawOrderDao.getListByParams(params);
			if(list != null) {
				//?????????????????????
				SystemParam spcurrency = this.systemParamDao.get(SystemParamKey.INTERNATIONAL_CURRENCIES_SYMBOL);
				String symbol = "";
				for(FrontUserWithdrawOrder fuwo : list) {
					String message = "";
					//?????????????????????????????????????????????????????????
					//???????????????????????????????????????????????????????????????
					Timestamp time = fuwo.getCreatetime();
					boolean flagTimeCheck = Utlity.checkOrderTime(time.getTime(), System.currentTimeMillis());//10????????????
					if(flagTimeCheck) {
						if(spcurrency != null) {
							Map<String, Object> currencies = JSONUtils.json2map(spcurrency.getParamValue());
							if(currencies != null && !currencies.isEmpty()) {
								symbol = currencies.get(fuwo.getCurrency()) == null ? "" : currencies.get(fuwo.getCurrency()).toString();
							}
						}
						if(Utlity.checkStringNull(symbol)) {
							symbol = fuwo.getCurrency();
						}
						params.clear();
						params.put("orderNum", fuwo.getOrderNum() + "");
						Map<String, Object> resultMap = UnionPayUtlity.createOrder4APIwapCheck(params);
						if((Boolean)resultMap.get("result")){
							Map<String, Object> paramsls = JSONUtils.json2map(resultMap.get("response").toString()) ;
							
							//????????????
							String passbackParams = paramsls.get("passbackParams").toString();
							
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
							if(!FrontUserWithdrawOrderStatus.NORMAL.equals(fuwo.getStatus())) {
								message = "??????????????????????????????????????????";
//								throw new TransactionException(message);
								log.info("--------????????????????????????---------------???" + message);
								continue;
							}
							//????????????????????????
							String status = paramsls.get("status") == null ? "" : paramsls.get("status").toString();
							if("success".equals(status)) {
								BigDecimal poundage = BigDecimal.valueOf(Double.valueOf(paramsls.get("poundage").toString())).divide(BigDecimal.valueOf(100));
								
								FrontUser fu = this.frontUserDao.get(fuwo.getFrontUser());
								if(fu == null) {
									message = "????????????C";
//									throw new TransactionException(message);
									log.info("--------????????????????????????---------------???" + message);
									continue;
								}
								
								FrontUserAccount fua = null;
								if(fuaMap.containsKey(fuwo.getFrontUser())) {
									fua = fuaMap.get(fuwo.getFrontUser());
								} else {
									fua = this.frontUserAccountDao.get(fuwo.getFrontUser());
								}
									
								if(fua == null) {
									message = "????????????C";
//									throw new TransactionException(message);
									log.info("--------????????????????????????---------------???" + message);
									continue;
								}
								CapitalAccountStatistics cas = null;
								if(caMap.containsKey(fuwo.getCapitalAccount())) {
									cas = caMap.get(fuwo.getCapitalAccount());
								} else {
									cas = this.capitalAccountStatisticsDao.get(fuwo.getCapitalAccount());
								}
										
								if(cas == null) {
									message = "????????????D";
//									throw new TransactionException(message);
									log.info("--------????????????????????????---------------???" + message);
									continue;
								}
								CapitalAccount ca = this.capitalAccountDao.get(fuwo.getCapitalAccount());
								if(ca == null) {
									message = "????????????E";
//									throw new TransactionException(message);
									log.info("--------????????????????????????---------------???" + message);
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
								
								cahList.add(cah);
								caMap.put(cas.getCapitalAccount(), cas);
								fuhList.add(fuh);
								fuaMap.put(fua.getFrontUser(), fua);
								orderList.add(fuwo);
								
								//??????????????????
								FrontUserMessage fum = new FrontUserMessage();
								fum.setUuid(UUID.randomUUID().toString());
								fum.setFrontUser(fu.getUuid());
								fum.setFrontUserShowId(fu.getShowId());
								fum.setTitle("Withdrawl Request Successful");
//								fum.setContent("??????"+Utlity.timeSpanToChinaDateString(fuwo.getCreatetime())+"??????"+fuwo.getActualAmount()+"???????????????????????????????????????????????????");
								fum.setContent("Your withdrawal of " + symbol + fuwo.getCurrencyAmount() 
//								+ " at " + Utlity.timeSpanToUsString(fuwo.getCreatetime()) 
								+ " was successful,please pay attention to the account balance.");
								fum.setSourceId(fuh.getUuid());
								fum.setStatus(FrontUserMessageStatus.NORMAL);
								fum.setType(FrontUserMessageType.USER_ORDER);
								fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_WITHDRAW);
								fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
								messageList.add(fum);
							} else if("close".equals(status)) {//????????????
								FrontUser fu = this.frontUserDao.get(fuwo.getFrontUser());
								if(fu == null) {
									message = "????????????C";
//									throw new TransactionException(message);
									log.info("--------????????????????????????---------------???" + message);
									continue;
								}
								FrontUserAccount fua = this.frontUserAccountDao.get(fuwo.getFrontUser());
								if(fua == null) {
									message = "????????????C";
									throw new TransactionException(message);
								}
								
								fuwo.setStatus(FrontUserWithdrawOrderStatus.CLOSE);
								fua.setBalanceLock(fua.getBalanceLock().subtract(fuwo.getReduceDAmount()));
								fua.setBalance(fua.getBalance().add(fuwo.getReduceDAmount()));
								
								fuaMap.put(fua.getFrontUser(), fua);
								orderList.add(fuwo);
								
								//??????????????????
								FrontUserMessage fum = new FrontUserMessage();
								fum.setUuid(UUID.randomUUID().toString());
								fum.setFrontUser(fu.getUuid());
								fum.setFrontUserShowId(fu.getShowId());
								fum.setTitle("Order Canceled");
//								fum.setContent("??????"+Utlity.timeSpanToChinaDateString(fuwo.getCreatetime())+"??????"+fuwo.getActualAmount()+"????????????????????????????????????????????????????????????????????????");
								fum.setContent("Your withdrawal of " + symbol + fuwo.getCurrencyAmount() 
//								+ " at " + Utlity.timeSpanToUsString(fuwo.getCreatetime())
								+ " was failed, the order was closed, please pay attention to the account balance.");
								fum.setSourceId(fuwo.getUuid());
								fum.setStatus(FrontUserMessageStatus.NORMAL);
								fum.setType(FrontUserMessageType.USER_ORDER);
								fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_WITHDRAW);
								fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
								messageList.add(fum);
							} else if("fail".equals(status)) {//????????????
								FrontUser fu = this.frontUserDao.get(fuwo.getFrontUser());
								if(fu == null) {
									message = "????????????C";
//									throw new TransactionException(message);
									log.info("--------????????????????????????---------------???" + message);
									continue;
								}
								FrontUserAccount fua = this.frontUserAccountDao.get(fuwo.getFrontUser());
								if(fua == null) {
									message = "????????????C";
									throw new TransactionException(message);
								}
								
								fuwo.setStatus(FrontUserWithdrawOrderStatus.FAIL);
//								fua.setBalanceLock(fua.getBalanceLock().subtract(fuwo.getReduceDAmount()));
//								fua.setBalance(fua.getBalance().add(fuwo.getReduceDAmount()));
//								
//								fuaMap.put(fua.getFrontUser(), fua);
								orderList.add(fuwo);
								
//								//??????????????????
//								FrontUserMessage fum = new FrontUserMessage();
//								fum.setUuid(UUID.randomUUID().toString());
//								fum.setFrontUser(fu.getUuid());
//								fum.setFrontUserShowId(fu.getShowId());
//								fum.setTitle("??????????????????");
//								fum.setContent("??????"+Utlity.timeSpanToChinaDateString(fuwo.getCreatetime())+"??????"+fuwo.getActualAmount()+"?????????????????????????????????????????????????????????");
//								fum.setSourceId(fuwo.getUuid());
//								fum.setStatus(FrontUserMessageStatus.NORMAL);
//								fum.setType(FrontUserMessageType.USER_ORDER);
//								fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_WITHDRAW);
//								fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
//								messageList.add(fum);
							}
						} else {//????????????????????????????????????
							continue;
						}
					}
				}
			}
			
			//????????????????????????????????????
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
	 * ???????????????????????????
	 */
//	@Scheduled(cron="0 0/1 *  * * * ")
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
			//?????????????????????????????????
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", FrontUserRechargeOrderStatus.NORMAL);
			List<FrontUserRechargeOrder> list = this.frontUserRechargeOrderDao.getListByParams(params);
			if(list != null) {
				//??????????????????????????????
				SystemParam sp = this.systemParamDao.get(SystemParamKey.RECHARGE_TIMEOUT);//????????????????????????--????????????
				long timeline = Utlity.TIMELINE_ORDER;
				if(sp != null) {
					timeline = (long)(Double.valueOf(sp.getParamValue()) * 1000L * 60L);
				}
				
				//?????????????????????
				SystemParam spcurrency = this.systemParamDao.get(SystemParamKey.INTERNATIONAL_CURRENCIES_SYMBOL);
				String symbol = "";
				
				for(FrontUserRechargeOrder furo : list) {
					CapitalAccount ca = this.capitalAccountDao.get(furo.getCapitalAccount());
					if(ca != null && !ca.getType().equals(CapitalPlatformType.JINZUN)) {
						continue;
					}
					String message = "";
					//?????????????????????????????????????????????????????????
					//???????????????????????????????????????????????????????????????
					Timestamp time = furo.getCreatetime();
					boolean flagTimeCheck = Utlity.checkOrderTime(time.getTime(), System.currentTimeMillis(), timeline);
					if(flagTimeCheck) {
						params.clear();
						params.put("orderNum", furo.getOrderNum() + "");
						params.put("createtime", System.currentTimeMillis() + "");
						Map<String, Object> resultMap = JinzunUtlity.queryOrder(params);
						if((Boolean)resultMap.get("result")){
							Map<String, Object> resultParamsls = JSONUtils.json2map(resultMap.get("response").toString()) ;
							//???????????????
							Boolean result = resultParamsls.get("successed") == null ? false : (Boolean)resultParamsls.get("successed");
							
							if(result) {
								Map<String, Object> paramsls = JSONUtils.json2map(resultParamsls.get("returnValue").toString());
								if("2".equals(paramsls.get("orderState").toString())){
									String orderNum = paramsls.get("outTradeNo").toString();
									//????????????
									if(!orderNum.equals(String.valueOf(furo.getOrderNum()))) {
										message = "????????????A";
										log.info("--------????????????????????????---------------???" + message);
										continue;
									}
									if(!FrontUserRechargeOrderStatus.NORMAL.equals(furo.getStatus())) {
										message = "??????????????????????????????????????????";
										log.info("--------????????????????????????---------------???" + message);
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
										message = "????????????C";
										log.info("--------????????????????????????---------------???" + message);
										continue;
									}
									
									FrontUserAccount fua = null;
									if(fuaMap.containsKey(furo.getFrontUser())) {
										fua = fuaMap.get(furo.getFrontUser());
									} else {
										fua = this.frontUserAccountDao.get(furo.getFrontUser());
									}
									if(fua == null) {
										message = "????????????C";
										log.info("--------????????????????????????---------------???" + message);
										continue;
									}
									CapitalAccountStatistics cas = null;
									if(caMap.containsKey(furo.getCapitalAccount())) {
										cas = caMap.get(furo.getCapitalAccount());
									} else {
										cas = this.capitalAccountStatisticsDao.get(furo.getCapitalAccount());
									}
									if(cas == null) {
										message = "????????????D";
										log.info("--------????????????????????????---------------???" + message);
										continue;
									}
									if(ca == null) {
										message = "????????????E";
										log.info("--------????????????????????????---------------???" + message);
										continue;
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
									
//									if(furo.getIsFirsttime()) {//????????????-??????????????????
//										fu.setLevel(FrontUserLevel.RECHARGED);
//									}
									
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
									
									if(fuvList.size() > 0) {
										voucherList.addAll(fuvList);
									}
									if(!Utlity.checkStringNull(fuhAct.getUuid()) && !Utlity.checkStringNull(aoo.getUuid())) {
										fuhList.add(fuhAct);
										aooList.add(aoo);
									}
									
	
									//????????????
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
									
									//??????????????????
									if(spcurrency != null) {
										Map<String, Object> currencies = JSONUtils.json2map(spcurrency.getParamValue());
										if(currencies != null && !currencies.isEmpty()) {
											symbol = currencies.get(furo.getCurrency()) == null ? "" : currencies.get(furo.getCurrency()).toString();
										}
									}
									if(Utlity.checkStringNull(symbol)) {
										symbol = furo.getCurrency();
									}
									FrontUserMessage fum = new FrontUserMessage();
									fum.setUuid(UUID.randomUUID().toString());
									fum.setFrontUser(fu.getUuid());
									fum.setFrontUserShowId(fu.getShowId());
									fum.setTitle("Top-up Successful");
//									fum.setContent("??????"+Utlity.timeSpanToChinaDateString(furo.getCreatetime())+"??????"+furo.getAmount()+"???????????????????????????????????????????????????");
									fum.setContent("Your recharge of " + symbol + furo.getCurrencyAmount() 
//									+ " at " + Utlity.timeSpanToUsString(furo.getCreatetime()) 
									+ " was successful,please pay attention to the account balance.");
									fum.setSourceId(fuh.getUuid());
									fum.setStatus(FrontUserMessageStatus.NORMAL);
									fum.setType(FrontUserMessageType.USER_ORDER);
									fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_RECHARGE);
									fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
									messageList.add(fum);
								}else{
								//????????????
									furo.setStatus(FrontUserRechargeOrderStatus.CLOSE);
									furo.setOperattime(new Timestamp(System.currentTimeMillis()));
									orderList.add(furo);
								}
							} else{ //????????????????????????????????????
								continue;
							}
						} else {//????????????????????????????????????
							continue;
						}
					}
				}
			}
			
			//????????????????????????????????????
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
	 * ????????????????????????????????????????????????????????????????????????????????????
	 * ?????????????????????????????????
	 * ???????????????????????????????????????????????????
	 * ???????????????????????????????????????
	 * ??????????????????????????????????????????checked activityInfo ????????????recommend??????offsetOrder??????????????????
	 * ???????????????????????????
	 * ??????????????????*???????????? ??????????????????
	 * ??????frontUser????????????????????????????????????????????????
	 */
	@Scheduled(cron="*/10 * *  * * * ")
	public void recommendTash() {
		try {
			//??????????????????
			ActivityInfo ai = this.activityInfoDao.get(ActivityInfoName.RECOMMEND);
			if(ai != null && ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {//???????????????
				
				//??????????????????
				BigDecimal rate = BigDecimal.ZERO;
				String awardRate = "";
				Map<String, Object> detailMap = JSONUtils.json2map(ai.getConfiguration());
				if(detailMap.get("awardRate") != null){
					awardRate = detailMap.get("awardRate").toString();
					rate = BigDecimal.valueOf(Double.valueOf(awardRate)).divide(BigDecimal.valueOf(100));
				}
				
				if(rate.compareTo(BigDecimal.ZERO) > 0) {
					//??????????????????
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
							//????????????????????????????????????????????????
							BigDecimal awardPrice = furo.getIncreaseDAmount().multiply(rate).setScale(2, BigDecimal.ROUND_DOWN);
							//???????????????
							FrontUser fu = this.frontUserDao.get(furo.getFrontUser());
							if(fu != null && !Utlity.checkStringNull(fu.getAgent())) {
								FrontUserAccount fua = null;
								if(fuaMap.containsKey(fu.getAgent())) {
									fua = fuaMap.get(fu.getAgent());
								} else {
									fua = this.frontUserAccountDao.get(fu.getAgent());//???????????????
								}
									
								//?????????????????????????????????

								String content = "New user invited rebate activities " + awardRate + "%coins";
			    				
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
								
								//?????????????????????
								//??????????????????????????????
								SystemParam sp = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);//????????????????????????--????????????
								BigDecimal goldRate = BigDecimal.ONE;
								if(sp != null) {
									goldRate = BigDecimal.valueOf(Double.valueOf(sp.getParamValue()));
								}
								
								//?????????????????????
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
								if(furo.getIsFirsttime()) {//???????????????????????????
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
	
	
	@Scheduled(cron="1 * *  * * * ")
	public void rechargeByWorldPayTask() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", FrontUserRechargeOrderStatus.NORMAL);
			List<FrontUserRechargeOrder> urList = this.frontUserRechargeOrderDao.getListByParams(params);
			
//			List<UserRecharge> urList = this.userRechargeDao.getListByParams(searchMap);
			if(urList != null && urList.size() > 0) {
				//??????????????????????????????
				SystemParam sp = this.systemParamDao.get(SystemParamKey.RECHARGE_TIMEOUT);//????????????????????????--????????????
				long timeline = Utlity.TIMELINE_ORDER;
				if(sp != null) {
					timeline = (long)(Double.valueOf(sp.getParamValue()) * 1000L * 60L);
				}
				for(FrontUserRechargeOrder ur : urList){
					CapitalAccount ca = this.capitalAccountDao.get(ur.getCapitalAccount());
					if(ca != null && !ca.getType().equals(CapitalPlatformType.PAYPAL) && !ca.getType().equals(CapitalPlatformType.STRIPE)) {
						continue;
					}
//					String message = "";
					//?????????????????????????????????????????????????????????
					//???????????????????????????????????????????????????????????????
					Timestamp time = ur.getCreatetime();
//					System.out.println("----createtime-----"+Utlity.timeSpanToString(time)+"-----------");
					boolean flagTimeCheck = Utlity.checkOrderTime(time.getTime(), System.currentTimeMillis(), timeline);
//					System.out.println("----now-----"+Utlity.timeSpanToString(new Timestamp(System.currentTimeMillis()))+"-----------");
//					System.out.println("----flagTimeCheck-----"+flagTimeCheck+"-----------");
					if(flagTimeCheck) {
						if(ca.getType().equals(CapitalPlatformType.PAYPAL)) {
							//paypal
							Map<String, Object> acParams = JSONUtils.json2map(ca.getData());
							PaypalAccount paypalAccount = new PaypalAccount(acParams.get("clientId").toString(), acParams.get("secret").toString(), Utlity.TANSFER_URL);
							
							Map<String, Object> transDataMap = JSONUtils.json2map(ur.getTransData());
							try {
								HttpResponse<Order> response = PaypalClient.queryOrder(paypalAccount, transDataMap.get("payperId").toString());
								if("COMPLETED".equals(response.result().status())){
									this.frontUserHistoryDao.rechargeByWorldpay(ur, FrontUserRechargeOrderStatus.CHECKED);
								}else{
									this.frontUserHistoryDao.rechargeByWorldpay(ur, FrontUserRechargeOrderStatus.CLOSE);
									continue;
								}
							} catch (Exception e) {
								this.frontUserHistoryDao.rechargeByWorldpay(ur, FrontUserRechargeOrderStatus.CLOSE);
								continue;
							}
						} else if(ca.getType().equals(CapitalPlatformType.STRIPE)) {
							this.frontUserHistoryDao.rechargeByWorldpay(ur, FrontUserRechargeOrderStatus.CLOSE);
							continue;
						}
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}