package cn.product.payment.controller.api;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.payment.controller.base.ActionParam;
import cn.product.payment.controller.base.ActionParam.DataType;
import cn.product.payment.controller.base.ApiResult;
import cn.product.payment.controller.base.ApiResult.ApiResultStatus;
import cn.product.payment.controller.base.BaseController;
import cn.product.payment.controller.base.Result;
import cn.product.payment.controller.base.ResultManager;
import cn.product.payment.entity.Channel;
import cn.product.payment.entity.Company;
import cn.product.payment.entity.Company.CompanyStatus;
import cn.product.payment.entity.CompanyAccount;
import cn.product.payment.entity.CompanyChannel;
import cn.product.payment.entity.CompanyChannel.CompanyChannelStatus;
import cn.product.payment.entity.CompanyChannel.CompanyChannelType;
import cn.product.payment.entity.UserWithdraw;
import cn.product.payment.entity.UserWithdraw.UserWithdrawStatus;
import cn.product.payment.locker.Locker;
import cn.product.payment.locker.ZkCuratorLocker;
import cn.product.payment.service.ChannelAccountService;
import cn.product.payment.service.ChannelService;
import cn.product.payment.service.CompanyAccountService;
import cn.product.payment.service.CompanyChannelService;
import cn.product.payment.service.CompanyService;
import cn.product.payment.service.UserWithdrawService;
import cn.product.payment.util.JSONUtils;
import cn.product.payment.util.PaymentUtil;
import cn.product.payment.util.Utlity;
import cn.product.payment.util.api.ApiResultUtlity.ApiResultCode;
import cn.product.payment.util.api.PaymentException;


/**
 * 提现api接口
 */

@Controller
@RequestMapping(value = "/api/withdraw")
public class ApiWithdrawController extends BaseController{
	
	@Autowired
	private UserWithdrawService userWithdrawService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private CompanyAccountService companyAccountService;
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private ChannelAccountService channelAccountService;
	
	@Autowired
	private CompanyChannelService companyChannelService;
	
	@Autowired
    private Locker locker;
	
	@RequestMapping(value="/createOrder",method=RequestMethod.POST)
	@ActionParam(key = "sign", message="签名", type = DataType.STRING, required = true)
	@ActionParam(key = "version", message="版本号", type = DataType.STRING, required = true)
	@ActionParam(key = "company", message="商户ID", type = DataType.STRING, required = true)
	@ActionParam(key = "channel", message="渠道ID", type = DataType.STRING, required = true)
	@ActionParam(key = "notifyUrl", message="回调地址", type = DataType.STRING, required = true)
	@ActionParam(key = "timestamp", message="时间戳", type = DataType.STRING, required = true, maxLength = 13)
	@ActionParam(key = "title", message="订单标题", type = DataType.STRING, required = true, maxLength = 256)
	@ActionParam(key = "amount", message="付款金额", type = DataType.STRING, required = true, maxLength = 10)
	@ActionParam(key = "orderNum", message="唯一订单号", type = DataType.STRING, required = true, maxLength = 50)
	@ActionParam(key = "data", message="交易参数", type = DataType.STRING, required = true)
	@ActionParam(key = "transData", message="回传参数", type = DataType.STRING, maxLength = 512)
	@ResponseBody
	public Result createOrder(String sign, String version,String company, String channel, String notifyUrl, String timestamp, String title, String amount,
			String orderNum, String data, String transData){
		Map<String, String> result = new HashMap<String, String>();
		
		try {
			//时间戳
			if((System.currentTimeMillis() - Long.parseLong(timestamp)) > 60000){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_TIMESTAMP_TIMEOUT);
			}
			
			//商户
			Company c = this.companyService.getByCode(company);
			if(c == null || !CompanyStatus.NORMAL.equals(c.getStatus())){
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
			if(!c.getUuid().equals(cc.getCompany()) || !CompanyChannelType.WITHDRAW.equals(cc.getType())){
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
			
			Map<String, Object> dataMap = new HashMap<String, Object>();
			try{
				dataMap = JSONUtils.json2map(data);
			}catch (Exception e){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_DATA_ERROR);
			}
			dataMap.put("title", title);
			
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
			signMap.put("data", data);
			if(transData != null){
				signMap.put("transData", transData);
			}
			
			if(!PaymentUtil.checkSign(signMap, c.getCompanyPublic())){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_SIGN_ERROR);
			}
			
			CompanyAccount ca = this.companyAccountService.get(c.getUuid());
			if(ca.getBalance().compareTo(totalAmount) < 0){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_ACCOUNT_LACK);
			}
			
			//订单号
			Map<String, Object>searchMap = new HashMap<>();
			searchMap.put("company", company);
			searchMap.put("companyOrderNum", orderNum);
			Integer orderNumCount = this.userWithdrawService.getCountByParams(searchMap);
			if(orderNumCount > 0){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_ORDERNUM_REPEAT);
			}
			
			BigDecimal poundage = BigDecimal.ZERO;
			if(cc.getPoundage() != null){
				poundage = cc.getPoundage();
			}
			if(cc.getPoundageRate() != null){
				poundage = totalAmount.multiply(cc.getPoundageRate()).setScale(0, BigDecimal.ROUND_UP);
			}
			
			UserWithdraw uw = new UserWithdraw();
			uw.setUuid(UUID.randomUUID().toString());
			uw.setCompany(c.getUuid());
			uw.setCompanyChannel(channel);
			uw.setChannel(cc.getChannel());
			uw.setOrderNum(Utlity.getOrderNum(Utlity.BILL_ROLE_USER,ch.getCode(),Utlity.BILL_TYPE_WITHDRAW));
			uw.setAmount(totalAmount.subtract(poundage));
			uw.setPoundage(poundage);
			uw.setTotalAmount(totalAmount);
			uw.setTransData(JSONUtils.obj2json(dataMap));
			uw.setCompanyOrderNum(orderNum);
			uw.setCompanyNotifyUrl(notifyUrl);
			uw.setCompanyData(transData);
			uw.setStatus(UserWithdrawStatus.CHECKING);
			uw.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			//银行卡个人
			if(Utlity.CHANNEL_WITHDRAW_BANKCARD_PERSON.equals(ch.getCode())){
				if(dataMap.get("bank") == null || dataMap.get("bankcard") == null || dataMap.get("holder") == null){
					return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_DATA_ERROR);
				}
				
				locker.lock(ZkCuratorLocker.ACCOUNT_UPDATE_LOCK, ()-> {
					this.userWithdrawService.insertWithdraw(uw);
				});
				result.put("company", company);
				result.put("channel", channel);
				result.put("timestamp", System.currentTimeMillis() + "");
				result.put("orderNum", uw.getCompanyOrderNum());
				result.put("paymentOrderNum", uw.getOrderNum());
				result.put("amount", uw.getAmount().toString());
				result.put("poundage", uw.getPoundage().toString());
				result.put("sign", PaymentUtil.getSign(result, c.getSystemPrivate()));
				
				return ResultManager.createApiResult(result, ApiResultStatus.SUCCESS, ApiResultCode.TRADE_CREATE_SUCCESS);
				
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
			List<UserWithdraw> uwList = this.userWithdrawService.getListByParams(searchMap);
			if(uwList == null || uwList.size() == 0){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_QUERY_OREDER_NULL);
			}
			UserWithdraw uw = uwList.get(0);
			result.put("company", company);
			result.put("channel", uw.getCompanyChannel());
			result.put("timestamp", System.currentTimeMillis() + "");
			result.put("orderNum", uw.getCompanyOrderNum());
			result.put("paymentOrderNum", uw.getOrderNum());
			result.put("amount", uw.getTotalAmount().toString());
			result.put("transData", uw.getCompanyData());
			result.put("status", uw.getStatus());
			
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
	public ApiResult closeOrder(String sign, String version, String company, String orderNum, String paymentOrderNum, String timestamp){
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
			List<UserWithdraw> uwList = this.userWithdrawService.getListByParams(searchMap);
			if(uwList == null || uwList.size() == 0){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_OREDER_NULL);
			}
			UserWithdraw uw = uwList.get(0);
			
			if(UserWithdrawStatus.SUCCESS.equals(uw.getStatus()) || UserWithdrawStatus.CHECKED.equals(uw.getStatus())){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_OREDER_DISABLE);
			}
			if(UserWithdrawStatus.CLOSE.equals(uw.getStatus())){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_OREDER_ALREADY);
			}
			
			List<String> errorList = new ArrayList<String>();
			locker.lock(ZkCuratorLocker.ACCOUNT_UPDATE_LOCK, ()-> {
				try {
					this.userWithdrawService.processOrder(uw, UserWithdrawStatus.CLOSE);
				} catch (PaymentException e) {
					errorList.add(e.getMessage());
					e.printStackTrace();
				}
			});
			if(errorList.size() > 0){
				return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_FAIL);
			}
			
			result.put("company", company);
			result.put("channel", uw.getCompanyChannel());
			result.put("timestamp", System.currentTimeMillis() + "");
			result.put("orderNum", uw.getCompanyOrderNum());
			result.put("paymentOrderNum", uw.getOrderNum());
			result.put("amount", uw.getTotalAmount().toString());
			result.put("transData", uw.getCompanyData());
			result.put("status", uw.getStatus());
			
			result.put("sign", PaymentUtil.getSign(result, c.getSystemPrivate()));
			return ResultManager.createApiResult(result, ApiResultStatus.SUCCESS, ApiResultCode.TRADE_CLOSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultManager.createApiResult(result, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_FAIL);
		}
	}
}