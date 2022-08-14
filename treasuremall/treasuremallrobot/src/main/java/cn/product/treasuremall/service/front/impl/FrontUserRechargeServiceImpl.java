package cn.product.treasuremall.service.front.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.CapitalAccountDao;
import cn.product.treasuremall.dao.CapitalPlatformDao;
import cn.product.treasuremall.dao.FrontUserAccountDao;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.FrontUserHistoryDao;
import cn.product.treasuremall.dao.FrontUserRechargeAmountSetDao;
import cn.product.treasuremall.dao.FrontUserRechargeOrderDao;
import cn.product.treasuremall.dao.SystemParamDao;
import cn.product.treasuremall.entity.CapitalAccount;
import cn.product.treasuremall.entity.CapitalPlatform;
import cn.product.treasuremall.entity.CapitalPlatform.CapitalPlatformType;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUserAccount;
import cn.product.treasuremall.entity.FrontUserRechargeAmountSet;
import cn.product.treasuremall.entity.FrontUserAccount.FrontUserAccountStatus;
import cn.product.treasuremall.entity.FrontUserRechargeAmountSet.FrontUserRechargeAmountSetStatus;
import cn.product.treasuremall.entity.FrontUserRechargeOrder;
import cn.product.treasuremall.entity.FrontUserRechargeOrder.FrontUserRechargeOrderStatus;
import cn.product.treasuremall.entity.FrontUserRechargeOrder.FrontUserRechargeOrderType;
import cn.product.treasuremall.entity.SystemParam;
import cn.product.treasuremall.entity.SystemParam.SystemParamKey;
import cn.product.treasuremall.service.front.FrontUserRechargeService;
import cn.product.treasuremall.util.Base64Util;
import cn.product.treasuremall.util.JSONUtils;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.util.unionpay.UnionPayUtlity;

@Service("frontUserRechargeService")
public class FrontUserRechargeServiceImpl implements FrontUserRechargeService{
	
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
	private FrontUserRechargeOrderDao frontUserRechargeOrderDao;
	
	@Autowired
	private CapitalPlatformDao capitalPlatformDao;
	
	@Autowired
	private CapitalAccountDao capitalAccountDao;
	
	@Autowired
    private FrontUserRechargeAmountSetDao frontUserRechargeAmountSetDao;
	
	@Autowired
    private SystemParamDao systemParamDao;

	@Override
	public void getAmountSet(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		
		searchMap.put("status", FrontUserRechargeAmountSetStatus.NORMAL);
		
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		List<FrontUserRechargeAmountSet> list = this.frontUserRechargeAmountSetDao.getListByParams(searchMap);
		Integer totalCount = this.frontUserRechargeAmountSetDao.getCountByParams(searchMap);
		
		
		result.setData(list);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
		result.setMessage("查询成功！");
		return;
	}

	@Override
	public void byBankcard(InputParams params, DataResult<Object> result) {
//		Map<String, Object> paramsMap = params.getParams();
//    	String fu = paramsMap.get("fu") == null ? "" : paramsMap.get("fu").toString();
//    	String capitalAccount = paramsMap.get("capitalAccount") == null ? "" : paramsMap.get("capitalAccount").toString();
//    	BigDecimal fee = paramsMap.get("fee") == null ? null : BigDecimal.valueOf(Double.valueOf(paramsMap.get("fee").toString()));
//    	String transData = paramsMap.get("transData") == null ? "" : paramsMap.get("transData").toString();
//    	
//		CapitalAccount ca = this.capitalAccountDao.get(capitalAccount);
//		if(ca == null){
//			result.setStatus(ResultStatusType.FAILED);
//			result.setMessage("渠道账户不存在！");
//			return;
//		}
//		
//		CapitalPlatform cp = this.capitalPlatformDao.get(ca.getCapitalPlatform());
//		if(cp == null){
//			result.setStatus(ResultStatusType.FAILED);
//			result.setMessage("渠道账户有误，请选择其他充值方式！");
//			return;
//		}
//		
//		if(!CapitalPlatformType.COMPANY_BANKCARD.equals(cp.getType()) && !CapitalPlatformType.PERSONAL_BANKCARD.equals(cp.getType())){
//			result.setStatus(ResultStatusType.FAILED);
//			result.setMessage("充值渠道有误，请选择其他充值方式！");
//			return;
//		}
//		
//		FrontUserAccount fua = this.frontUserAccountDao.getByFrontUser(fu);
//		if(fua == null){
//			result.setStatus(ResultStatusType.FAILED);
//			result.setMessage("用户账户状态异常， 请联系管理员！");
//			return;
//		}
//		
//		FrontUserHistory fuh = new FrontUserHistory();
//		fuh.setUuid(UUID.randomUUID().toString());
//		fuh.setFrontUser(fu);
////		fuh.setFrontUserAccount(fua.getUuid());
////		fuh.setIncome(fee);
////		fuh.setPay(BigDecimal.ZERO);
////		fuh.setPoundage(fee.multiply(ca.getPoundageRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
////		fuh.setType(FrontUserHistoryType.USER_RECHARGE);
////		fuh.setTransData(transData);
////		fuh.setCapitalAccount(capitalAccount);
////		fuh.setStatus(FrontUserHistoryStatus.NORMAL);
////		fuh.setCreatetime(new Timestamp(System.currentTimeMillis()));
//		
//		this.frontUserHistoryDao.recharge(fuh, cp.getType());
//		result.setStatus(ResultStatusType.SUCCESS);
//		result.setMessage("操作成功！");
	}

	@Override
	public void byAlipay(InputParams params, DataResult<Object> result) {
//		Map<String, Object> paramsMap = params.getParams();
//    	String fu = paramsMap.get("fu") == null ? "" : paramsMap.get("fu").toString();
//    	String capitalAccount = paramsMap.get("capitalAccount") == null ? "" : paramsMap.get("capitalAccount").toString();
//    	BigDecimal fee = paramsMap.get("fee") == null ? null : BigDecimal.valueOf(Double.valueOf(paramsMap.get("fee").toString()));
//    	String transData = paramsMap.get("transData") == null ? "" : paramsMap.get("transData").toString();
//    	
//		CapitalAccount ca = this.capitalAccountDao.get(capitalAccount);
//		if(ca == null){
//			result.setStatus(ResultStatusType.FAILED);
//			result.setMessage("渠道账户不存在！");
//			return;
//		}
//		
//		CapitalPlatform cp = this.capitalPlatformDao.get(ca.getCapitalPlatform());
//		if(cp == null){
//			result.setStatus(ResultStatusType.FAILED);
//			result.setMessage("渠道账户有误，请选择其他充值方式！");
//			return;
//		}
//		
//		if(!CapitalPlatformType.COMPANY_ALIPAY.equals(cp.getType())){
//			result.setStatus(ResultStatusType.FAILED);
//			result.setMessage("充值渠道有误，请选择其他充值方式！");
//			return;
//		}
//		
//		FrontUserAccount fua = this.frontUserAccountDao.getByFrontUser(fu);
//		if(fua == null){
//			result.setStatus(ResultStatusType.FAILED);
//			result.setMessage("用户账户状态异常， 请联系管理员！");
//			return;
//		}
//		
//		//生成订单号
//		String orderNum = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_APP,Utlity.getOrderTypeByPlatformType(cp.getType()),Utlity.BILL_PURPOSE_RECHARGE);
//		
//		FrontUserHistory fuh = new FrontUserHistory();
//		fuh.setUuid(UUID.randomUUID().toString());
//		fuh.setFrontUser(fu);
////		fuh.setFrontUserAccount(fua.getUuid());
////		fuh.setIncome(fee);
////		fuh.setPay(BigDecimal.ZERO);
////		fuh.setPoundage(fee.multiply(ca.getPoundageRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
////		fuh.setType(FrontUserHistoryType.USER_RECHARGE);
////		fuh.setTransData(transData);
////		fuh.setCapitalAccount(capitalAccount);
////		fuh.setStatus(FrontUserHistoryStatus.NORMAL);
//		fuh.setCreatetime(new Timestamp(System.currentTimeMillis()));
//		fuh.setOrderNum(orderNum);
//		
//		//调用支付宝wap支付接口 返回form表单数据
//		Map<String, Object> paramsls = new HashMap<String, Object>();//封装参数
//		try {
//			paramsls.put("passback_params", URLEncoder.encode(fuh.getUuid(), "UTF-8"));
//			paramsls.put("time_expire", Utlity.timeSpanToStringLess(new Timestamp(fuh.getCreatetime().getTime()+5*60*1000)));//订单超时时间5分钟
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		paramsls.put("out_trade_no", fuh.getOrderNum());
//		paramsls.put("total_amount", fee);
//		
//		Map<String, Object> resultMap = AliUtlity.createOrder4wap(paramsls);
//		if ((boolean)resultMap.get("request")) {
//			if((boolean)resultMap.get("result")) {
//				this.frontUserHistoryDao.recharge(fuh, cp.getType());
//				result.setData(resultMap.get("response"));
//				result.setStatus(ResultStatusType.SUCCESS);
//				result.setMessage("操作成功！");
//			} else {
//				result.setStatus(ResultStatusType.FAILED);
//				result.setMessage(resultMap.get("message").toString());
//				return;
//			}
//		} else {
//			result.setStatus(ResultStatusType.FAILED);
//			result.setMessage("操作失败！");
//			return;
//		}
	}

	@Override
	public void byUnion(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	String capitalAccount = paramsMap.get("capitalAccount") == null ? "" : paramsMap.get("capitalAccount").toString();
    	String amountStr = paramsMap.get("amount") == null ? "" : paramsMap.get("amount").toString();
    	String dAmountStr = paramsMap.get("dAmount") == null ? "" : paramsMap.get("dAmount").toString();
    	Boolean isFree = paramsMap.get("isFree") == null ? false : Boolean.valueOf(paramsMap.get("isFree").toString());
    	
    	amountStr = Base64Util.getFromBase64(amountStr);
    	dAmountStr = Base64Util.getFromBase64(dAmountStr);
    	BigDecimal amount = BigDecimal.valueOf(Double.valueOf(amountStr));
    	BigDecimal dAmount = BigDecimal.valueOf(Double.valueOf(dAmountStr));
    	if(amount.compareTo(BigDecimal.ZERO) <= 0) {
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("充值金额不正确！");
			return;
    	}
    	String transData = paramsMap.get("transData") == null ? "" : paramsMap.get("transData").toString();
    	
		CapitalAccount ca = this.capitalAccountDao.get(capitalAccount);
		if(ca == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("渠道账户不存在！");
			return;
		}
		
		CapitalPlatform cp = this.capitalPlatformDao.get(ca.getCapitalPlatform());
		if(cp == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("渠道账户有误，请选择其他充值方式！");
			return;
		}
		
		if(!CapitalPlatformType.UNION.equals(cp.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("充值渠道有误，请选择其他充值方式！");
			return;
		}
		
		FrontUserAccount fua = this.frontUserAccountDao.get(frontUser);
		if(fua == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户账户状态异常， 请联系管理员！");
			return;
		}
		if(!FrontUserAccountStatus.NORMAL.equals(fua.getAccountStatus())) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户账户状态异常， 请联系管理员！");
			return;
		}
		
		FrontUser fu = this.frontUserDao.get(frontUser);
		if(fu == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户状态异常， 请联系管理员！");
			return;
		}
		//是否为自定义金额
		if(isFree) {//是自定义金额
			//获取金币汇率
			SystemParam sp = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
			BigDecimal rate = BigDecimal.ONE;
			if(sp != null) {
				rate = BigDecimal.valueOf(Double.valueOf(sp.getParamValue()));
			}
			//获取充值金额设置列表，计算到账金币数值
			Map<String, Object> searchMap = new HashMap<String, Object>();
			
			searchMap.put("status", FrontUserRechargeAmountSetStatus.NORMAL);
			searchMap.put("sort", "amount asc");
			List<FrontUserRechargeAmountSet> list = this.frontUserRechargeAmountSetDao.getListByParams(searchMap);
			if(list != null && list.size() > 0) {
				//获取最大值和最小值
				if(amount.compareTo(list.get(0).getAmount()) < 0) {//是否小于最小值
					dAmount = amount.multiply(rate);
				} else if (amount.compareTo(list.get(list.size() - 1).getAmount()) > 0) {//大于最大值
					//
					dAmount = amount.multiply(rate).add(list.get(list.size() - 1).getGiveDAmount());
					
				} else {//判断先大于哪个
					
					for(int i = 0; i < list.size(); i++) {
						if(amount.compareTo(list.get(i).getAmount()) > 0 && amount.compareTo(list.get(i + 1).getAmount()) < 0) {
							dAmount = amount.multiply(rate).add(list.get(i).getGiveDAmount());
						}
					}
				}
			}
			
		}
		
		//生成订单号
		Long orderNum = Utlity.getOrderNum();
		
		//封装充值订单
		FrontUserRechargeOrder furo = new FrontUserRechargeOrder();
		furo.setUuid(UUID.randomUUID().toString());
		furo.setFrontUser(frontUser);
		furo.setFrontUserShowId(fu.getShowId());
		furo.setOrderNum(orderNum);
		furo.setOrderType(FrontUserRechargeOrderType.USER_RECHARGE);
		furo.setOrderChannel(ca.getUuid());
		furo.setAmount(amount);
		furo.setIncreaseDAmount(dAmount);
		furo.setType(FrontUserRechargeOrderType.USER_RECHARGE);
		furo.setCapitalAccount(ca.getUuid());
		furo.setCreatetime(new Timestamp(System.currentTimeMillis()));
		furo.setStatus(FrontUserRechargeOrderStatus.NORMAL);
		furo.setTransData(transData);
		furo.setRemark("用户充值备注");
		furo.setIsFirsttime(true);
		//是否是首次充值
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", frontUser);
		searchMap.put("status", FrontUserRechargeOrderStatus.CHECKED);
		Integer totalCount = this.frontUserRechargeOrderDao.getCountByParams(searchMap);
		if(totalCount != null && totalCount > 0) {
			furo.setIsFirsttime(false);
		}
		
		//调用union聚合支付-支付宝支付接口 返回支付宝form表单数据
		Map<String, Object> paramsls = new HashMap<String, Object>();//封装参数
		//获取渠道ID
		String channel = UnionPayUtlity.CHANNEL_RECHARGE_ALI;
		if(!Utlity.checkStringNull(ca.getData())) {
			Map<String, Object> data = JSONUtils.json2map(ca.getData());
			channel = data.get("rechargeChannel").toString();
		}
		paramsls.put("channel", channel);
		paramsls.put("title", "用户充值");
		paramsls.put("orderNum", furo.getOrderNum());
		paramsls.put("totalAmount", amount.multiply(BigDecimal.valueOf(Double.valueOf(100))));
		paramsls.put("passbackParams", furo.getUuid());
		paramsls.put("information", "");
		try {
			Map<String, Object> resultMap = UnionPayUtlity.createOrder4APIwap(paramsls);
			if((Boolean)resultMap.get("result")) {
				this.frontUserRechargeOrderDao.insert(furo);
				result.setData(resultMap.get("response"));
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("操作成功！");
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage(resultMap.get("message").toString());
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("网络繁忙，请稍后重试！");
			return;
		}
	}
}
