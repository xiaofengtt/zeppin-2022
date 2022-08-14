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
import cn.product.treasuremall.dao.ActivityInfoDao;
import cn.product.treasuremall.dao.ActivityInfoFirstchargePrizeDao;
import cn.product.treasuremall.dao.ActivityInfoRechargePrizeDao;
import cn.product.treasuremall.dao.CapitalAccountDao;
import cn.product.treasuremall.dao.CapitalPlatformDao;
import cn.product.treasuremall.dao.FrontUserAccountDao;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.FrontUserHistoryDao;
import cn.product.treasuremall.dao.FrontUserRechargeAmountSetDao;
import cn.product.treasuremall.dao.FrontUserRechargeOrderDao;
import cn.product.treasuremall.dao.SystemParamDao;
import cn.product.treasuremall.entity.ActivityInfo;
import cn.product.treasuremall.entity.ActivityInfo.ActivityInfoName;
import cn.product.treasuremall.entity.ActivityInfo.ActivityInfoStatus;
import cn.product.treasuremall.entity.ActivityInfoFirstchargePrize;
import cn.product.treasuremall.entity.ActivityInfoFirstchargePrize.ActivityInfoFirstchargePrizeType;
import cn.product.treasuremall.entity.ActivityInfoRechargePrize;
import cn.product.treasuremall.entity.ActivityInfoRechargePrize.ActivityInfoRechargePrizeType;
import cn.product.treasuremall.entity.CapitalAccount;
import cn.product.treasuremall.entity.CapitalPlatform;
import cn.product.treasuremall.entity.CapitalPlatform.CapitalPlatformTransType;
import cn.product.treasuremall.entity.CapitalPlatform.CapitalPlatformType;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUserAccount;
import cn.product.treasuremall.entity.FrontUserAccount.FrontUserAccountStatus;
import cn.product.treasuremall.entity.FrontUserRechargeAmountSet;
import cn.product.treasuremall.entity.FrontUserRechargeAmountSet.FrontUserRechargeAmountSetStatus;
import cn.product.treasuremall.entity.FrontUserRechargeOrder;
import cn.product.treasuremall.entity.FrontUserRechargeOrder.FrontUserRechargeOrderStatus;
import cn.product.treasuremall.entity.FrontUserRechargeOrder.FrontUserRechargeOrderType;
import cn.product.treasuremall.entity.SystemParam;
import cn.product.treasuremall.entity.SystemParam.SystemParamKey;
import cn.product.treasuremall.entity.base.Constants;
import cn.product.treasuremall.service.front.FrontUserRechargeService;
import cn.product.treasuremall.util.Base64Util;
import cn.product.treasuremall.util.JSONUtils;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.util.jinzun.JinzunUtlity;
import cn.product.treasuremall.util.unionpay.UnionPayUtlity;
import cn.product.treasuremall.util.xingda.AcicpayUtlity;

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
	
	@Autowired
    private ActivityInfoDao activityInfoDao;
	
	@Autowired
    private ActivityInfoFirstchargePrizeDao activityInfoFirstchargePrizeDao;
	
	@Autowired
    private ActivityInfoRechargePrizeDao activityInfoRechargePrizeDao;


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
	}

	@Override
	public void byAlipay(InputParams params, DataResult<Object> result) {
	}

	@Override
	public void byUnion(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	String capitalAccount = paramsMap.get("capitalAccount") == null ? "" : paramsMap.get("capitalAccount").toString();
    	String amountStr = paramsMap.get("amount") == null ? "" : paramsMap.get("amount").toString();
    	String dAmountStr = paramsMap.get("dAmount") == null ? "" : paramsMap.get("dAmount").toString();
//    	Boolean isFree = paramsMap.get("isFree") == null ? false : Boolean.valueOf(paramsMap.get("isFree").toString());
    	String returnUrl = paramsMap.get("returnUrl") == null ? "" : paramsMap.get("returnUrl").toString();
    	String ip = paramsMap.get("ip") == null ? "" : paramsMap.get("ip").toString();
    	
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
		
		//增加可用次数判断
		Boolean isUse = isHaveRechargeTimes(frontUser, capitalAccount, ca.getUserPreReceive() == null ? -1 : ca.getUserPreReceive());
		if(!isUse) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("渠道账户到达每日收款上限，请选择其他充值方式！");
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
		
		//最低限额限制
		if(ca.getMin().compareTo(amount) > 0) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("充值最低限额："+ca.getMin().toString()+"元");
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
//		if(isFree) {//是自定义金额
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
					FrontUserRechargeAmountSet furas = list.get(i);
					//判断相等
					if(amount.compareTo(list.get(i).getAmount()) == 0) {//是否相等
						dAmount = furas.getdAmount().add(list.get(i).getGiveDAmount());
						break;
					}
					if(amount.compareTo(list.get(i).getAmount()) > 0 && amount.compareTo(list.get(i + 1).getAmount()) < 0) {
						dAmount = amount.multiply(rate).add(list.get(i).getGiveDAmount());
						break;
					}
				}
			}
		} else {
			dAmount = amount.multiply(rate);
		}
//		}
		
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
		furo.setIsFirsttime(false);
		
		//20200603增加首充活动与充值返利活动判断
		setActivityInfo(furo, frontUser, capitalAccount, amount);
		
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
		paramsls.put("clientIp", ip);
		
		//20200629新增支付宝转银行卡的相关内容
		if(CapitalPlatformTransType.ALIPAY_BANKCARD.equals(cp.getTransType())) {
			if(Utlity.checkStringNull(transData)) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("请填写正确的付款人信息！");
				return;
			}
			paramsls.put("holder", transData);
		}
		
		
		if(!Utlity.checkStringNull(returnUrl)) {
			paramsls.put("returnUrl", Base64Util.getFromBase64(returnUrl));
		}
		try {
			Map<String, Object> resultMap = UnionPayUtlity.createOrder4APIwap(paramsls);
//			Map<String, Object> resultMap = new HashMap<String, Object>();
//			resultMap.put("result", true);
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

	/**
	 * 兴达支付接口调用
	 * 目前包括两种渠道：支付宝扫码和快捷
	 */
	@Override
	public void byAcicpay(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	String capitalAccount = paramsMap.get("capitalAccount") == null ? "" : paramsMap.get("capitalAccount").toString();
    	String amountStr = paramsMap.get("amount") == null ? "" : paramsMap.get("amount").toString();
    	String dAmountStr = paramsMap.get("dAmount") == null ? "" : paramsMap.get("dAmount").toString();
//    	Boolean isFree = paramsMap.get("isFree") == null ? false : Boolean.valueOf(paramsMap.get("isFree").toString());
    	String returnUrl = paramsMap.get("returnUrl") == null ? "" : paramsMap.get("returnUrl").toString();
    	
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
		
		//增加可用次数判断
		Boolean isUse = isHaveRechargeTimes(frontUser, capitalAccount, ca.getUserPreReceive() == null ? -1 : ca.getUserPreReceive());
		if(!isUse) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("渠道账户到达每日收款上限，请选择其他充值方式！");
			return;
		}
		
		CapitalPlatform cp = this.capitalPlatformDao.get(ca.getCapitalPlatform());
		if(cp == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("渠道账户有误，请选择其他充值方式！");
			return;
		}
		
		if(!CapitalPlatformType.ACICPAY.equals(cp.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("充值渠道有误，请选择其他充值方式！");
			return;
		}
		
		//最低限额限制
		if(ca.getMin().compareTo(amount) > 0) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("充值最低限额："+ca.getMin().toString()+"元");
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
//		if(isFree) {//是自定义金额
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
					FrontUserRechargeAmountSet furas = list.get(i);
					//判断相等
					if(amount.compareTo(list.get(i).getAmount()) == 0) {//是否相等
						dAmount = furas.getdAmount().add(list.get(i).getGiveDAmount());
						break;
					}
					if(amount.compareTo(list.get(i).getAmount()) > 0 && amount.compareTo(list.get(i + 1).getAmount()) < 0) {
						dAmount = amount.multiply(rate).add(list.get(i).getGiveDAmount());
						break;
					}
				}
			}
		} else {
			dAmount = amount.multiply(rate);
		}
//		}
		
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
		furo.setIsFirsttime(false);
		
		//20200603增加首充活动与充值返利活动判断
		setActivityInfo(furo, frontUser, capitalAccount, amount);
		
		
		//兴达支付创建订单，封装订单参数和签名返回到前台
		Map<String, Object> paramsls = new HashMap<String, Object>();//封装参数
		//获取渠道ID
//		String codeNo = AcicpayUtlity.CHANNEL_BANKNO_ALIPAYCODE;
//		if(!Utlity.checkStringNull(ca.getData())) {
//			Map<String, Object> data = JSONUtils.json2map(ca.getData());
//			codeNo = data.get("alipaycode").toString();
//		}
		String codeNo = ca.getAccountNum();
		paramsls.put("codeNo", codeNo);
		paramsls.put("createtime", Utlity.timeSpanToString(furo.getCreatetime()));
		paramsls.put("orderNum", furo.getOrderNum());
		paramsls.put("totalAmount", amount);
		paramsls.put("orderId", furo.getUuid());
		paramsls.put("name", "用户充值");
		if(!Utlity.checkStringNull(returnUrl)) {
			paramsls.put("returnUrl", Base64Util.getFromBase64(returnUrl));
		}
		
		try {
			Map<String, String> resultMap = AcicpayUtlity.createOrderInfo(paramsls);
			this.frontUserRechargeOrderDao.insert(furo);
			result.setData(resultMap);
			result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("网络繁忙，请稍后重试！");
			return;
		}		
	}

	/**
	 * Jinzun支付接口调用
	 * 
	 */
	@Override
	public void byJinzun(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	String capitalAccount = paramsMap.get("capitalAccount") == null ? "" : paramsMap.get("capitalAccount").toString();
    	String amountStr = paramsMap.get("amount") == null ? "" : paramsMap.get("amount").toString();
    	String dAmountStr = paramsMap.get("dAmount") == null ? "" : paramsMap.get("dAmount").toString();
//    	Boolean isFree = paramsMap.get("isFree") == null ? false : Boolean.valueOf(paramsMap.get("isFree").toString());
    	String returnUrl = paramsMap.get("returnUrl") == null ? "" : paramsMap.get("returnUrl").toString();
    	
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
		
		//增加可用次数判断
		Boolean isUse = isHaveRechargeTimes(frontUser, capitalAccount, ca.getUserPreReceive() == null ? -1 : ca.getUserPreReceive());
		if(!isUse) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("渠道账户到达每日收款上限，请选择其他充值方式！");
			return;
		}
		
		CapitalPlatform cp = this.capitalPlatformDao.get(ca.getCapitalPlatform());
		if(cp == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("渠道账户有误，请选择其他充值方式！");
			return;
		}
		
		if(!CapitalPlatformType.JINZUN.equals(cp.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("充值渠道有误，请选择其他充值方式！");
			return;
		}
		
		//最低限额限制
		if(ca.getMin().compareTo(amount) > 0) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("充值最低限额："+ca.getMin().toString()+"元");
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
//		if(isFree) {//是自定义金额
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
					FrontUserRechargeAmountSet furas = list.get(i);
					//判断相等
					if(amount.compareTo(list.get(i).getAmount()) == 0) {//是否相等
						dAmount = furas.getdAmount().add(list.get(i).getGiveDAmount());
						break;
					}
					if(amount.compareTo(list.get(i).getAmount()) > 0 && amount.compareTo(list.get(i + 1).getAmount()) < 0) {
						dAmount = amount.multiply(rate).add(list.get(i).getGiveDAmount());
						break;
					}
				}
			}
		} else {
			dAmount = amount.multiply(rate);
		}
//		}
		
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
		furo.setIsFirsttime(false);
		
		//20200603增加首充活动与充值返利活动判断
		setActivityInfo(furo, frontUser, capitalAccount, amount);
		
		//谨遵支付创建订单，封装订单参数和签名返回到前台
		Map<String, Object> paramsls = new HashMap<String, Object>();//封装参数
		
		String codeNo = ca.getAccountNum();
		paramsls.put("codeNo", codeNo);
		paramsls.put("createtime", System.currentTimeMillis() + "");
		paramsls.put("orderNum", furo.getOrderNum());
		paramsls.put("totalAmount", amount);
		paramsls.put("orderId", furo.getUuid());
		paramsls.put("returnUrl", Base64Util.getFromBase64(returnUrl));
		
		try {
			Map<String, Object> resultMap = JinzunUtlity.createOrder(paramsls);
			if((Boolean)resultMap.get("request")){
				this.frontUserRechargeOrderDao.insert(furo);
				result.setData(resultMap);
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("操作成功！");
			}else{
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage(resultMap.get("message").toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("网络繁忙，请稍后重试！");
			return;
		}		
	}
	
	private void  setActivityInfo(FrontUserRechargeOrder furo, String frontUser, String capitalAccount, BigDecimal amount) {
		StringBuilder activity = new StringBuilder();
		Map<String, Object> prize = new HashMap<String, Object>();
		Boolean isActivity = false;
		if(isActivity(frontUser, capitalAccount, ActivityInfoName.FIRSTCHARGE, amount, prize)) {
			activity.append(ActivityInfoName.FIRSTCHARGE);
			isActivity = true;
		}
		if(isActivity(frontUser, capitalAccount, ActivityInfoName.RECHARGE, null, prize)) {
			if(activity.length() > 0) {
				activity.append(",").append(ActivityInfoName.RECHARGE);
			} else {
				activity.append(ActivityInfoName.RECHARGE);
			}
			isActivity = true;
		}
		if(isActivity(frontUser, capitalAccount, ActivityInfoName.RECOMMEND, null, null)) {
			if(activity.length() > 0) {
				activity.append(",").append(ActivityInfoName.RECOMMEND);
			} else {
				activity.append(ActivityInfoName.RECOMMEND);
			}
			isActivity = true;
		}
		furo.setIsActivity(isActivity);
		furo.setActivityId(activity.toString());
		furo.setPrize(JSONUtils.obj2json(prize));
	}
	
	private boolean isActivity(String frontUser, String capitalAccount, String activityId, BigDecimal amount, Map<String, Object> prize){
		boolean isActivity = false;
		ActivityInfo ai = this.activityInfoDao.get(activityId);
		if(ai != null && ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {//活动开启
			if(ActivityInfoName.FIRSTCHARGE.equals(activityId)) {//首充活动

				boolean isFirst = true;
//				//是否是首次充值
//				Map<String, Object> searchMap = new HashMap<String, Object>();
//				searchMap.put("frontUser", frontUser);
//				searchMap.put("status", FrontUserRechargeOrderStatus.CHECKED);
//				Integer totalCount = this.frontUserRechargeOrderDao.getCountByParams(searchMap);
//				if(totalCount != null && totalCount > 0) {
//					isFirst = false;
//				}
				//20200610改为判断是否首次参与活动
				Boolean isPartakeFirstcharge = this.frontUserRechargeOrderDao.isPartakeFirstcharge(frontUser);
				if(isPartakeFirstcharge) {
					isFirst = false;
				}
				List<ActivityInfoFirstchargePrize> list = activityInfoFirstchargePrizeDao.getListByActivityInfo(activityId);
				if(list != null && list.size() > 0) {
					for(ActivityInfoFirstchargePrize aifp : list) {
						if(isFirst && (Utlity.checkStringNull(aifp.getCapitalAccount()) || capitalAccount.equals(aifp.getCapitalAccount())) && amount.compareTo(aifp.getAmount()) == 0) {//符合条件
							isActivity = true;
							if(ActivityInfoFirstchargePrizeType.VOUCHER.equals(aifp.getPrizeType())) {
								prize.put(Constants.ACTIVITY_INFO_PRIZE_TYPE_FIRSTCHARGE_VOUCHER, aifp.getPrize());
							}
						}
					}
				}
			} else if (ActivityInfoName.RECHARGE.equals(activityId)) {//充值返利活动
				List<ActivityInfoRechargePrize> list = activityInfoRechargePrizeDao.getListByActivityInfo(activityId);
				if(list != null && list.size() > 0) {
					for(ActivityInfoRechargePrize airp : list) {
						if(capitalAccount.equals(airp.getCapitalAccount())) {
							isActivity = true;
							if(ActivityInfoRechargePrizeType.VOUCHER.equals(airp.getPrizeType())) {
								prize.put(Constants.ACTIVITY_INFO_PRIZE_TYPE_RECHARGE_VOUCHER, airp.getPrize());
							} else if(ActivityInfoRechargePrizeType.GOLD.equals(airp.getPrizeType())) {
								prize.put(Constants.ACTIVITY_INFO_PRIZE_TYPE_RECHARGE_GOLD, airp.getPrize());
							}
						}
					}
				}
			} else if (ActivityInfoName.RECOMMEND.equals(activityId)) {//邀请新人-在生效时间内充值。
				//判断时间
				//规则：被邀请人，注册七天内，第一次首充之后每笔，邀请人均可享受返利
				//需要判断用户七天内是否有充值成功的记录；
				//先判断是否在注册七天内，是的话，就直接记录参与活动
				//否则，判断注册七天内是否充值，如果充值则满足条件，否则不参与活动
				FrontUser fu = this.frontUserDao.get(frontUser);
				//判断是否为被邀请用户
				if(!Utlity.checkStringNull(fu.getAgent())) {//有邀请人
					long timeline = 1000*60*60*24*7L;
					if(Utlity.checkOrderTime(fu.getCreatetime().getTime(), System.currentTimeMillis(), timeline)) {//超出7天范围
						if(this.frontUserRechargeOrderDao.isInSevenDayFirstcharge(fu)) {//注册7天内有有效充值
							isActivity = true;
						}
					} else {
						isActivity = true;
					}
				}
			}
		}
		
		return isActivity;
	}
	
	private Boolean isHaveRechargeTimes(String frontUser, String capitalAccount, Integer totalTimes) {
		//增加单个用户对账户可用付款次数判断
		Integer userReceiveTimes = totalTimes;
		if(totalTimes.intValue() > -1) {
			
			Integer count = this.frontUserRechargeOrderDao.getCountByParams4Days(frontUser, capitalAccount, 0);
			userReceiveTimes = totalTimes.intValue() - count.intValue();
			if(userReceiveTimes > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}
	public static void main(String[] args) {
		String c = "";
		String b = "666";
		System.out.println(Utlity.checkStringNull(c) || b.equals(b));
	}
}
