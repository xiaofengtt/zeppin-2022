package cn.zeppin.product.score.aotutask;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.zeppin.product.score.entity.CapitalAccount;
import cn.zeppin.product.score.entity.CapitalAccountHistory;
import cn.zeppin.product.score.entity.CapitalAccountHistory.CapitalAccountHistoryStatus;
import cn.zeppin.product.score.entity.CapitalAccountHistory.CapitalAccountHistoryType;
import cn.zeppin.product.score.entity.FrontUserAccount;
import cn.zeppin.product.score.entity.FrontUserHistory;
import cn.zeppin.product.score.entity.FrontUserHistory.FrontUserHistoryStatus;
import cn.zeppin.product.score.entity.FrontUserHistory.FrontUserHistoryType;
import cn.zeppin.product.score.entity.Orderinfo;
import cn.zeppin.product.score.entity.Orderinfo.OrderinfoStatus;
import cn.zeppin.product.score.entity.Orderinfo.OrderinfoType;
import cn.zeppin.product.score.entity.PayNotifyInfo;
import cn.zeppin.product.score.entity.PayNotifyInfo.PayNotifyInfoPayType;
import cn.zeppin.product.score.entity.PayNotifyInfo.PayNotifyInfoStatus;
import cn.zeppin.product.score.service.CapitalAccountHistoryService;
import cn.zeppin.product.score.service.CapitalAccountService;
import cn.zeppin.product.score.service.CapitalPlatformService;
import cn.zeppin.product.score.service.FrontUserAccountService;
import cn.zeppin.product.score.service.FrontUserHistoryCheckService;
import cn.zeppin.product.score.service.FrontUserHistoryService;
import cn.zeppin.product.score.service.OrderinfoService;
import cn.zeppin.product.score.service.PayNotifyInfoService;
import cn.zeppin.product.score.util.JSONUtils;
import cn.zeppin.product.score.util.Utlity;
import cn.zeppin.product.score.util.alipay.AliUtlity;

@Component
public class PayTask {
	
	@Autowired
	private FrontUserAccountService frontUserAccountService;
	
	@Autowired
    private FrontUserHistoryService frontUserHistoryService;
	
	@Autowired
    private OrderinfoService orderinfoService;
	
	@Autowired
    private CapitalAccountService capitalAccountService;
	
	@Autowired
    private CapitalPlatformService capitalPlatformService;
	
	@Autowired
    private CapitalAccountHistoryService capitalAccountHistoryService;
	
	@Autowired
	private FrontUserHistoryCheckService frontUserHistoryCheckService;
	
	@Autowired
	private PayNotifyInfoService payNotifyInfoService;
	/**
	 * ??????????????????????????????
	 */
//	@Scheduled(cron="0 0/2 *  * * * ")
	public void rechargeByAlipayTask() {
		try {
			Map<String, CapitalAccount> caMap = new HashMap<String, CapitalAccount>();
			Map<String, FrontUserAccount> fuaMap = new HashMap<String, FrontUserAccount>();
			List<Orderinfo> orderList = new ArrayList<Orderinfo>();
			List<FrontUserHistory> fuhList = new ArrayList<FrontUserHistory>();
			List<CapitalAccountHistory> cahList = new ArrayList<CapitalAccountHistory>();
			//?????????????????????????????????
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", FrontUserHistoryStatus.NORMAL);
			params.put("orderType", OrderinfoType.COMPANY_ALIPAY);
			params.put("type", FrontUserHistoryType.USER_RECHARGE);
			List<FrontUserHistory> list = this.frontUserHistoryService.getListByParams(params);
			if(list != null) {
				for(FrontUserHistory fuh : list) {
					//?????????????????????????????????????????????????????????
					//???????????????????????????????????????????????????????????????
					Timestamp time = fuh.getCreatetime();
					boolean flagTimeCheck = Utlity.checkLessTime(time.getTime(), System.currentTimeMillis());
					if(flagTimeCheck) {
						params.clear();
						params.put("orderNum", fuh.getOrderNum());
						Map<String, Object> resultMap = AliUtlity.getOrderInfo(params);
						Orderinfo order = this.orderinfoService.get(fuh.getOrderId());
						if((Boolean)resultMap.get("result")){
							Map<String, Object> responseMap = JSONUtils.json2map(resultMap.get("response").toString());
							String code = responseMap.get("code") == null ? "" : responseMap.get("code").toString();
							String trade_status = responseMap.get("trade_status") == null ? "" : responseMap.get("trade_status").toString();
							if(AliUtlity.CODE_10000.equals(code) && AliUtlity.TRADE_SUCCESS.equals(trade_status)) {//????????????
								
								FrontUserAccount fua = null;
								if(fuaMap.containsKey(fuh.getFrontUserAccount())) {
									fua = fuaMap.get(fuh.getFrontUserAccount());
								} else {
									fua = this.frontUserAccountService.get(fuh.getFrontUserAccount());
								}
										
								CapitalAccount ca = null;
								if(caMap.containsKey(fuh.getCapitalAccount())) {
									ca = caMap.get(fuh.getCapitalAccount());
								} else {
									ca = this.capitalAccountService.get(fuh.getCapitalAccount());
								}
										
								
								//????????????
								fua.setBalanceFree(fua.getBalanceFree().add(fuh.getIncome()).subtract(fuh.getPoundage()));
								
								//????????????
								ca.setBalance(ca.getBalance().add(fuh.getIncome()).subtract(fuh.getPoundage()));
								ca.setDailySum(ca.getDailySum().add(fuh.getIncome()).subtract(fuh.getPoundage()));
								
								//?????????????????????
								CapitalAccountHistory cah = new CapitalAccountHistory();
								cah.setUuid(UUID.randomUUID().toString());
								cah.setCapitalAccount(ca.getUuid());
								cah.setCapitalPlatform(ca.getCapitalPlatform());
								cah.setOrderId(order.getUuid());
								cah.setOrderNum(order.getOrderNum());
								cah.setOrderType(order.getType());
								cah.setBalance(ca.getBalance());
								cah.setIncome(fuh.getIncome());
								cah.setPay(BigDecimal.ZERO);
								cah.setPoundage(fuh.getPoundage());
								cah.setType(CapitalAccountHistoryType.USER_RECHARGE);
								cah.setTransData(fuh.getTransData());
								cah.setFrontUser(fua.getFrontUser());
								cah.setFrontUserHistory(fuh.getUuid());
								cah.setStatus(CapitalAccountHistoryStatus.SUCCESS);
								cah.setCreatetime(fuh.getCreatetime());
								
								fuh.setStatus(FrontUserHistoryStatus.SUCCESS);
								order.setStatus(OrderinfoStatus.SUCCESS);
								
								caMap.put(ca.getUuid(), ca);
								fuaMap.put(fua.getUuid(), fua);
								orderList.add(order);
								fuhList.add(fuh);
								cahList.add(cah);
							} else if (AliUtlity.CODE_40004.equals(code)) {//??????????????? ????????????
								fuh.setStatus(FrontUserHistoryStatus.CLOSE);
								order.setStatus(OrderinfoStatus.CLOSE);
								
								orderList.add(order);
								fuhList.add(fuh);
							}
							
						} else {
							Map<String, Object> responseMap = JSONUtils.json2map(resultMap.get("response").toString());
							String code = responseMap.get("code") == null ? "" : responseMap.get("code").toString();
							
							if (AliUtlity.CODE_40004.equals(code)) {//??????????????? ????????????
								fuh.setStatus(FrontUserHistoryStatus.CLOSE);
								order.setStatus(OrderinfoStatus.CLOSE);
								
								orderList.add(order);
								fuhList.add(fuh);
							}
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
			this.frontUserHistoryCheckService.rechargeTask4Alipay(params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * ??????????????????????????????
	 */
//	@Scheduled(cron="0 0/2 *  * * * ")
	public void withdrawNotifyInfoByReapal() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", PayNotifyInfoStatus.UNPRO);
			List<PayNotifyInfo> list = this.payNotifyInfoService.getListByParams(params);
			List<PayNotifyInfo> listUpdate = new ArrayList<PayNotifyInfo>();
			if(list != null && !list.isEmpty()){
				for(PayNotifyInfo pni : list){
					String data = pni.getSource();
					Map<String, Object> result = new HashMap<String, Object>();
					HashMap<String, Object> resultMap = null;
					if(PayNotifyInfoPayType.WITHDRAW_REAPAL_COM.equals(pni.getPayType())){//????????????
						result.put("data", data);
						resultMap = this.frontUserHistoryCheckService.insertReapalNotice4Pay(result);
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
				}
			}
			if(listUpdate != null && listUpdate.size() > 0){
				this.payNotifyInfoService.updateAll(listUpdate);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}