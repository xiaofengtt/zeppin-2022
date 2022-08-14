package cn.product.payment.controller.api;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.payment.controller.base.ActionParam;
import cn.product.payment.controller.base.ActionParam.DataType;
import cn.product.payment.controller.base.ApiResult.ApiResultStatus;
import cn.product.payment.controller.base.BaseController;
import cn.product.payment.controller.base.Result;
import cn.product.payment.controller.base.ResultManager;
import cn.product.payment.entity.Channel;
import cn.product.payment.entity.ChannelAccount;
import cn.product.payment.entity.Company;
import cn.product.payment.entity.CompanyChannel;
import cn.product.payment.entity.CompanyChannel.CompanyChannelStatus;
import cn.product.payment.entity.CompanyChannel.CompanyChannelType;
import cn.product.payment.entity.UserRecharge;
import cn.product.payment.entity.UserRecharge.UserRechargeStatus;
import cn.product.payment.service.ChannelAccountService;
import cn.product.payment.service.ChannelService;
import cn.product.payment.service.CompanyChannelService;
import cn.product.payment.service.CompanyService;
import cn.product.payment.service.UserRechargeService;
import cn.product.payment.util.JSONUtils;
import cn.product.payment.util.PaymentUtil;
import cn.product.payment.util.Utlity;
import cn.product.payment.util.alipay.AliUtlity;
import cn.product.payment.util.api.ApiResultUtlity.ApiResultCode;

/**
 * 充值api接口
 */

@Controller
@RequestMapping(value = "/api/recharge")
public class ApiRechargeController extends BaseController{
	
	@Autowired
	private UserRechargeService userRechargeService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private ChannelAccountService channelAccountService;
	
	@Autowired
	private CompanyChannelService companyChannelService;
	
	@RequestMapping(value="/createOrder",method=RequestMethod.POST)
	@ActionParam(key = "sign", message="签名", type = DataType.STRING, required = true)
	@ActionParam(key = "version", message="版本号", type = DataType.STRING, required = true)
	@ActionParam(key = "company", message="商户ID", type = DataType.STRING, required = true)
	@ActionParam(key = "channel", message="充值渠道ID", type = DataType.STRING, required = true)
	@ActionParam(key = "notifyUrl", message="回调地址", type = DataType.STRING, required = true)
	@ActionParam(key = "timestamp", message="时间戳", type = DataType.STRING, required = true, maxLength = 13)
	@ActionParam(key = "title", message="订单标题", type = DataType.STRING, required = true, maxLength = 256)
	@ActionParam(key = "amount", message="付款金额", type = DataType.STRING, required = true, maxLength = 10)
	@ActionParam(key = "orderNum", message="唯一订单号", type = DataType.STRING, required = true, maxLength = 50)
	@ActionParam(key = "transData", message="回传参数", type = DataType.STRING, maxLength = 512)
	@ActionParam(key = "infomation", message="交易信息", type = DataType.STRING, maxLength = 128)
	@ActionParam(key = "timeout", message="超时时间", type = DataType.STRING, maxLength = 11)
	@ResponseBody
	public Result createOrder(String sign, String version,String company, String channel, String notifyUrl, String timestamp, String title, String amount,
			String orderNum, String transData, String infomation, String timeout){
		Map<String, String> result = new HashMap<String, String>();
		
		try {
			//时间戳
			if((System.currentTimeMillis() - Long.parseLong(timestamp)) > 60000){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_TIMESTAMP_TIMEOUT);
			}
			
			Long timeoutL;
			//超时时间
			if(timeout == null){
				timeoutL = Utlity.BILL_DEFAULT_TIMEOUT;
			}else{
				if(!Utlity.isNumeric(timeout)){
					return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_TIMEOUT_ILLEGAL);
				}
				timeoutL = Long.valueOf(timeout);
				if(timeoutL < Utlity.BILL_MIN_TIMEOUT){
					return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_TIMEOUT_LESS);
				}
			}
			
			//商户
			Company c = this.companyService.getByCode(company);
			if(c == null){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_COMPANY_ERROR);
			}
			if(c.getCompanyPublic() == null){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_COMPANY_ERROR);
			}
			//渠道
			CompanyChannel cc = this.companyChannelService.get(channel);
			if(cc == null || CompanyChannelStatus.DELETE.equals(cc.getStatus())){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_CHANNEL_ERROR);
			}
			if(!c.getUuid().equals(cc.getCompany()) || !CompanyChannelType.RECHARGE.equals(cc.getType())){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_CHANNEL_ERROR);
			}
			if(!CompanyChannelStatus.NORMAL.equals(cc.getStatus())){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_CHANNEL_DISABLE);
			}
			Channel ch = this.channelService.get(cc.getChannel());
			if(ch == null || !CompanyChannelStatus.NORMAL.equals(ch.getStatus())){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_CHANNEL_DISABLE);
			}
			//金额
			if(!Utlity.isNumeric(amount)){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_AMOUNT_ILLEGAL);
			}
			BigDecimal totalAmount = new BigDecimal(amount);
			if(totalAmount.compareTo(cc.getMax()) > 0){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_AMOUNT_MORE);
			}
			if(totalAmount.compareTo(cc.getMin()) < 0){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_AMOUNT_LESS);
			}
			
			//验签
			Map<String, String> signMap = new HashMap<String, String>();
			signMap.put("sign", sign);
			signMap.put("version", version);
			signMap.put("company", company);
			signMap.put("channel", channel);
			signMap.put("notifyUrl", notifyUrl);
			signMap.put("timestamp", timestamp);
			signMap.put("title", title);
			signMap.put("amount", amount);
			signMap.put("orderNum", orderNum);
			if(transData != null){
				signMap.put("transData", transData);
			}
			if(infomation != null){
				signMap.put("infomation", infomation);
			}
			if(timeout != null){
				signMap.put("timeout", timeout);
			}
			
			if(!PaymentUtil.checkSign(signMap, c.getCompanyPublic())){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_SIGN_ERROR);
			}
			
			
			//订单号
			Map<String, Object>searchMap = new HashMap<>();
			searchMap.put("company", company);
			searchMap.put("companyOrderNum", orderNum);
			Integer orderNumCount = this.userRechargeService.getCountByParams(searchMap);
			if(orderNumCount > 0){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_ORDERNUM_REPEAT);
			}
			
			searchMap.clear();
			searchMap.put("channel", ch.getUuid());
			List<ChannelAccount> caList = this.channelAccountService.getListByParams(searchMap);
			if(caList == null || caList.size() == 0){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_CHANNEL_EMPTY);
			}
			
			Random random = new Random();
			Integer randomInt = 0;
			if(caList.size() > 1){
				randomInt = random.nextInt(caList.size() - 1);
			}
			ChannelAccount ca = caList.get(randomInt);
			
			Map<String, Object> transDataMap = new HashMap<>();
			transDataMap.put("title", title);
			transDataMap.put("infomation", infomation);
			
			BigDecimal poundage = BigDecimal.ZERO;
			if(cc.getPoundage() != null){
				poundage = cc.getPoundage();
			}
			if(cc.getPoundageRate() != null){
				poundage = totalAmount.multiply(cc.getPoundageRate()).setScale(0, BigDecimal.ROUND_UP);
			}
			
			UserRecharge ur = new UserRecharge();
			ur.setUuid(UUID.randomUUID().toString());
			ur.setCompany(c.getUuid());
			ur.setCompanyChannel(channel);
			ur.setChannel(cc.getChannel());
			ur.setChannelAccount(ca.getUuid());
			ur.setOrderNum(Utlity.getOrderNum(Utlity.BILL_ROLE_USER,ch.getCode(),Utlity.BILL_TYPE_RECHARGE));
			ur.setTimeout(new Timestamp(Long.parseLong(timestamp) + timeoutL));
			ur.setTotalAmount(totalAmount);
			ur.setPoundage(poundage);
			ur.setAmount(totalAmount.subtract(poundage));
			ur.setTransData(JSONUtils.obj2json(transDataMap));
			ur.setCompanyOrderNum(orderNum);
			ur.setCompanyNotifyUrl(notifyUrl);
			ur.setCompanyData(transData);
			ur.setStatus(UserRechargeStatus.NORMAL);
			ur.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			//支付宝对公
			if(Utlity.CHANNEL_RECHARGE_ALIPAY_COMPANY.equals(ch.getCode())){
				Map<String, Object> acParams = JSONUtils.json2map(ca.getData());
				acParams.put("pid", ca.getAccountNum());
				
				Map<String, String> params = new HashMap<String, String>();
				params.put("notify_url", ch.getCallbackUrl());
				params.put("body", infomation == null ? "" : infomation);
				params.put("subject", title);
				params.put("passback_params", URLEncoder.encode(ur.getUuid(), "UTF-8"));
				params.put("time_expire", Utlity.timestampToMinuteString(ur.getTimeout()));
				params.put("out_trade_no", ur.getOrderNum());
				params.put("total_amount", ur.getTotalAmount().divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN).toString());
				
				Map<String, Object> aliResult = AliUtlity.createOrder4wap(acParams, params);
				if ((boolean)aliResult.get("request")) {
					if((boolean)aliResult.get("result")) {
						this.userRechargeService.insert(ur);
						result.put("company", company);
						result.put("channel", channel);
						result.put("timestamp", System.currentTimeMillis() + "");
						result.put("orderNum", ur.getCompanyOrderNum());
						result.put("paymentOrderNum", ur.getOrderNum());
						result.put("amount", ur.getTotalAmount().toString());
						result.put("poundage", ur.getPoundage().toString());
						result.put("data", aliResult.get("response").toString());
						result.put("sign", PaymentUtil.getSign(result, c.getSystemPrivate()));
						
						return ResultManager.createApiResult(result, ApiResultStatus.SUCCESS, ApiResultCode.TRADE_CREATE_SUCCESS);
					} else {
						return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_FAIL);
					}
				} else {
					return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_FAIL);
				}
			}else{
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_CHANNEL_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_FAIL);
		}
	}
	
	@RequestMapping(value="/queryOrder",method=RequestMethod.POST)
	@ActionParam(key = "sign", message="签名", type = DataType.STRING)
	@ActionParam(key = "version", message="版本号", type = DataType.STRING, required = true)
	@ActionParam(key = "company", message="商户ID", type = DataType.STRING, required = true)
	@ActionParam(key = "orderNum", message="唯一订单号", type = DataType.STRING, maxLength = 50)
	@ActionParam(key = "paymentOrderNum", message="平台订单号", type = DataType.STRING, maxLength = 30)
	@ActionParam(key = "timestamp", message="时间戳", type = DataType.STRING, required = true, maxLength = 13)
	@ResponseBody
	public Result queryOrder(String sign, String version, String company, String orderNum, String paymentOrderNum, String timestamp){
		Map<String, String> result = new HashMap<String, String>();
		
		try {
			//时间戳
			if((System.currentTimeMillis() - Long.parseLong(timestamp)) > 60000){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_QUERY_TIMESTAMP_TIMEOUT);
			}
			
			//商户
			Company c = this.companyService.getByCode(company);
			if(c == null){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_QUERY_COMPANY_ERROR);
			}
			if(c.getCompanyPublic() == null){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_COMPANY_ERROR);
			}
			
			//订单号
			if(Utlity.checkStringNull(orderNum) && Utlity.checkStringNull(paymentOrderNum)){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_QUERY_ORDERNUM_EMPTY);
			}
			
			//验签
			Map<String, String> signMap = new HashMap<String, String>();
			signMap.put("sign", sign);
			signMap.put("version", version);
			signMap.put("company", company);
			signMap.put("timestamp", timestamp);
			if(orderNum != null){
				signMap.put("orderNum", orderNum);
			}
			if(paymentOrderNum != null){
				signMap.put("paymentOrderNum", paymentOrderNum);
			}
			
			if(!PaymentUtil.checkSign(signMap, c.getCompanyPublic())){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_QUERY_SIGN_ERROR);
			}
			
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("company", c.getUuid());
			searchMap.put("companyOrderNum", orderNum);
			searchMap.put("orderNum", paymentOrderNum);
			List<UserRecharge> urList = this.userRechargeService.getListByParams(searchMap);
			if(urList == null || urList.size() == 0){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_QUERY_OREDER_NULL);
			}
			UserRecharge ur = urList.get(0);
			result.put("company", company);
			result.put("channel", ur.getCompanyChannel());
			result.put("timestamp", System.currentTimeMillis() + "");
			result.put("orderNum", ur.getCompanyOrderNum());
			result.put("paymentOrderNum", ur.getOrderNum());
			result.put("amount", ur.getTotalAmount().toString());
			result.put("transData", ur.getCompanyData());
			result.put("status", ur.getStatus());
			
			result.put("sign", PaymentUtil.getSign(result, c.getSystemPrivate()));
			return ResultManager.createApiResult(result, ApiResultStatus.SUCCESS, ApiResultCode.TRADE_QUERY_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_QUERY_FAIL);
		}
	}
	
	@RequestMapping(value="/closeOrder",method=RequestMethod.POST)
	@ActionParam(key = "sign", message="签名", type = DataType.STRING)
	@ActionParam(key = "version", message="版本号", type = DataType.STRING, required = true)
	@ActionParam(key = "company", message="商户ID", type = DataType.STRING, required = true)
	@ActionParam(key = "orderNum", message="唯一订单号", type = DataType.STRING, maxLength = 50)
	@ActionParam(key = "paymentOrderNum", message="平台订单号", type = DataType.STRING, maxLength = 30)
	@ActionParam(key = "timestamp", message="时间戳", type = DataType.STRING, required = true, maxLength = 13)
	@ResponseBody
	public Result closeOrder(String sign, String version, String company, String orderNum, String paymentOrderNum, String timestamp){
		Map<String, String> result = new HashMap<String, String>();
		
		try {
			//时间戳
			if((System.currentTimeMillis() - Long.parseLong(timestamp)) > 300000){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_TIMESTAMP_TIMEOUT);
			}
			
			//商户
			Company c = this.companyService.getByCode(company);
			if(c == null){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_COMPANY_ERROR);
			}
			if(c.getCompanyPublic() == null){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_COMPANY_ERROR);
			}
			
			//订单号
			if(Utlity.checkStringNull(orderNum) && Utlity.checkStringNull(paymentOrderNum)){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_ORDERNUM_EMPTY);
			}
			
			//验签
			Map<String, String> signMap = new HashMap<String, String>();
			signMap.put("sign", sign);
			signMap.put("version", version);
			signMap.put("company", company);
			signMap.put("timestamp", timestamp);
			if(orderNum != null){
				signMap.put("orderNum", orderNum);
			}
			if(paymentOrderNum != null){
				signMap.put("paymentOrderNum", paymentOrderNum);
			}
			
			if(!PaymentUtil.checkSign(signMap, c.getCompanyPublic())){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_SIGN_ERROR);
			}
			
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("company", c.getUuid());
			searchMap.put("companyOrderNum", orderNum);
			searchMap.put("orderNum", paymentOrderNum);
			List<UserRecharge> urList = this.userRechargeService.getListByParams(searchMap);
			if(urList == null || urList.size() == 0){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_OREDER_NULL);
			}
			UserRecharge ur = urList.get(0);
			
			if(UserRechargeStatus.SUCCESS.equals(ur.getStatus())){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_OREDER_DISABLE);
			}
			if(UserRechargeStatus.CLOSE.equals(ur.getStatus())){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_OREDER_ALREADY);
			}
			this.userRechargeService.processOrder(ur, null, UserRechargeStatus.CLOSE);
			
			result.put("company", company);
			result.put("channel", ur.getCompanyChannel());
			result.put("timestamp", System.currentTimeMillis() + "");
			result.put("orderNum", ur.getCompanyOrderNum());
			result.put("paymentOrderNum", ur.getOrderNum());
			result.put("amount", ur.getTotalAmount().toString());
			result.put("transData", ur.getCompanyData());
			result.put("status", ur.getStatus());
			
			result.put("sign", PaymentUtil.getSign(result, c.getSystemPrivate()));
			return ResultManager.createApiResult(result, ApiResultStatus.SUCCESS, ApiResultCode.TRADE_CLOSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_FAIL);
		}
	}
}