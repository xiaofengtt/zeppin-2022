package cn.product.payment.service.api.impl;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.payment.controller.base.ApiResult;
import cn.product.payment.controller.base.ApiResult.ApiResultStatus;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.dao.ChannelAccountDailyDao;
import cn.product.payment.dao.ChannelAccountDao;
import cn.product.payment.dao.ChannelDao;
import cn.product.payment.dao.CompanyChannelAccountDao;
import cn.product.payment.dao.CompanyChannelDao;
import cn.product.payment.dao.CompanyDao;
import cn.product.payment.dao.UserRechargeDao;
import cn.product.payment.entity.Channel;
import cn.product.payment.entity.ChannelAccount;
import cn.product.payment.entity.ChannelAccount.ChannelAccountStatus;
import cn.product.payment.entity.ChannelAccountDaily;
import cn.product.payment.entity.Company;
import cn.product.payment.entity.CompanyChannel;
import cn.product.payment.entity.CompanyChannel.CompanyChannelStatus;
import cn.product.payment.entity.CompanyChannel.CompanyChannelType;
import cn.product.payment.entity.CompanyChannelAccount;
import cn.product.payment.entity.UserRecharge;
import cn.product.payment.entity.UserRecharge.UserRechargeStatus;
import cn.product.payment.service.api.ApisRechargeService;
import cn.product.payment.util.Base64Util;
import cn.product.payment.util.DESUtil;
import cn.product.payment.util.HttpClientUtil;
import cn.product.payment.util.JSONUtils;
import cn.product.payment.util.PaymentUtil;
import cn.product.payment.util.Utlity;
import cn.product.payment.util.alipay.AliUtlity;
import cn.product.payment.util.api.ApiResultUtlity.ApiResultCode;
import cn.product.payment.util.wechat.WechatUtlity;

/**
 * 充值api接口
 */

@Service("apisRechargeService")
public class ApisRechargeServiceImpl  implements ApisRechargeService{
	
	@Autowired
	private UserRechargeDao userRechargeDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private ChannelAccountDao channelAccountDao;
	
	@Autowired
	private CompanyChannelAccountDao companyChannelAccountDao;
	
	@Autowired
	private ChannelAccountDailyDao channelAccountDailyDao;
	
	@Autowired
	private CompanyChannelDao companyChannelDao;
	
	/**
	 * 创建订单
	 */
	public void createOrder(InputParams params, ApiResult result){
		Map<String, Object> paramsMap = params.getParams();
		String sign = paramsMap.get("sign").toString();
		String company = paramsMap.get("company").toString();
		String channel = paramsMap.get("channel").toString();
		String notifyUrl = paramsMap.get("notifyUrl").toString();
		String timestamp = paramsMap.get("timestamp").toString();
		String title = paramsMap.get("title").toString();
		String amount = paramsMap.get("amount").toString();
		String orderNum = paramsMap.get("orderNum").toString();
		String returnUrl = paramsMap.get("returnUrl") == null ? null : paramsMap.get("returnUrl").toString();
		String passbackParams = paramsMap.get("passbackParams") == null ? null : paramsMap.get("passbackParams").toString();
		String timeout = paramsMap.get("timeout") == null ? null : paramsMap.get("timeout").toString();
		String clientIp = paramsMap.get("clientIp") == null ? null : paramsMap.get("clientIp").toString();
		String holder = paramsMap.get("holder") == null ? null : paramsMap.get("holder").toString();
		String ip = paramsMap.get("ip") == null ? null : paramsMap.get("ip").toString();
		
		Map<String, String> resultData = new HashMap<String, String>();
		try {
			//时间戳
			if((System.currentTimeMillis() - Long.parseLong(timestamp)) > 60000){
				//请求超时
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_TIMESTAMP_TIMEOUT);
				return;
			}
			
			//商户
			Company c = this.companyDao.getByCode(company);
			if(c == null){
				//商户不存在
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_COMPANY_ERROR);
				return;
			}
			
			//ip白名单校验
			if(c.getWhiteUrl() != null && !"".equals(c.getWhiteUrl()) && !"*".equals(c.getWhiteUrl())){
				String[] whiteUrls = c.getWhiteUrl().split("/");
				boolean flag = false;
				for(String whiteUrl : whiteUrls){
					if(ip.indexOf(whiteUrl.trim()) > -1){
						flag = true;
					}
				}
				if(!flag){
					result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_WHITEURL_ERROR);
					return;
				}
			}
			
			//商户渠道
			CompanyChannel cc = this.companyChannelDao.get(channel);
			if(cc == null || CompanyChannelStatus.DELETE.equals(cc.getStatus())){
				//商户渠道不存在
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_CHANNEL_ERROR);
				return;
			}
			if(!c.getUuid().equals(cc.getCompany()) || !CompanyChannelType.RECHARGE.equals(cc.getType())){
				//商户渠道不匹配
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_CHANNEL_ERROR);
				return;
			}
			if(!CompanyChannelStatus.NORMAL.equals(cc.getStatus())){
				//商户渠道不可用
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_CHANNEL_DISABLE);
				return;
			}
			
			//渠道
			Channel ch = this.channelDao.get(cc.getChannel());
			if(ch == null || !CompanyChannelStatus.NORMAL.equals(ch.getStatus())){
				//渠道不存在 或不可用
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_CHANNEL_DISABLE);
				return;
			}
			//金额
			if(!Utlity.isNumeric(amount)){
				//不是数字
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_AMOUNT_ILLEGAL);
				return;
			}
			BigDecimal totalAmount = new BigDecimal(amount);
			if(totalAmount.compareTo(cc.getMax()) > 0){
				//超过上限
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_AMOUNT_MORE);
				return;
			}
			if(totalAmount.compareTo(cc.getMin()) < 0){
				//不足下限
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_AMOUNT_LESS);
				return;
			}
			
			//拼装签名
			Map<String, String> signMap = new HashMap<String, String>();
			signMap.put("sign", sign);
			signMap.put("company", company);
			signMap.put("channel", channel);
			signMap.put("notifyUrl", notifyUrl);
			signMap.put("timestamp", timestamp);
			signMap.put("amount", amount);
			signMap.put("orderNum", orderNum);
			
			//验签
			if(!PaymentUtil.checkApisSign(signMap, c.getSystemPublic())){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_SIGN_ERROR);
				return;
			}
			
			
			//订单号
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("company", c.getUuid());
			searchMap.put("companyOrderNum", orderNum);
			Integer orderNumCount = this.userRechargeDao.getCountByParams(searchMap);
			if(orderNumCount > 0){
				//订单号重复校验
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_ORDERNUM_REPEAT);
				return;
			}
			
			//渠道账户
			ChannelAccount ca = null;
			searchMap.clear();
			searchMap.put("companyChannel", cc.getUuid());
			List<CompanyChannelAccount> ccaList = this.companyChannelAccountDao.getListByParams(searchMap);
			if(ccaList.size() > 0){
				//有绑定固定账户 取可用账户
				searchMap.clear();
				searchMap.put("companyChannel", cc.getUuid());
				searchMap.put("status", ChannelAccountStatus.NORMAL);
				List<ChannelAccount> caList = this.companyChannelAccountDao.getChannelAccountListByParams(searchMap);
				if(caList == null || caList.size() == 0){
					//无可用账户
					result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_CHANNEL_EMPTY);
					return;
				}
				
				//可用账户随机取一
				ca = getRandomAccount(caList, totalAmount);
			}else{
				//未绑定固定账户 取所有该渠道账户
				searchMap.clear();
				searchMap.put("channel", ch.getUuid());
				searchMap.put("status", ChannelAccountStatus.NORMAL);
				List<ChannelAccount> caList = this.channelAccountDao.getListByParams(searchMap);
				if(caList == null || caList.size() == 0){
					//无可用账户
					result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_CHANNEL_EMPTY);
					return;
				}
				
				//可用账户随机取一
				ca = getRandomAccount(caList, totalAmount);
			}
			
			
			if(ca == null){
				//无可用账户
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_CHANNEL_EMPTY);
				return;
			}
			
			//超时时间
			Long timeoutL;
			if(timeout == null){
				//未设置 取默认值
				if(Utlity.CHANNEL_RECHARGE_BANKCARD_PERSON.equals(ch.getCode()) || Utlity.CHANNEL_RECHARGE_BANKCARD_COMPANY.equals(ch.getCode())
						|| Utlity.CHANNEL_RECHARGE_ALIPAY_BANKCARD.equals(ch.getCode())){
					//银行卡相关
					timeoutL = Utlity.BILL_BANKCARD_TIMEOUT;
				}else{
					//其他
					timeoutL = Utlity.BILL_DEFAULT_TIMEOUT;
				}
			}else{
				//商户有设置
				if(!Utlity.isNumeric(timeout)){
					//不是数字
					result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_TIMEOUT_ILLEGAL);
					return;
				}
				timeoutL = Long.valueOf(timeout);
				if(timeoutL < Utlity.BILL_MIN_TIMEOUT){
					//时间过短
					result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_TIMEOUT_LESS);
					return;
				}
			}
			
			//交易信息
			Map<String, Object> transDataMap = new HashMap<>();
			transDataMap.put("title", title);
			transDataMap.put("returnUrl", returnUrl);
			
			//手续费
			BigDecimal poundage = BigDecimal.ZERO;
			if(cc.getPoundage() != null){
				//固定手续费
				poundage = cc.getPoundage();
			}
			if(cc.getPoundageRate() != null){
				//比例手续费
				poundage = totalAmount.multiply(cc.getPoundageRate()).setScale(0, BigDecimal.ROUND_HALF_UP);
			}
			
			//封装对象
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
			ur.setCompanyData(passbackParams);
			ur.setStatus(UserRechargeStatus.NORMAL);
			ur.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			if(Utlity.CHANNEL_RECHARGE_ALIPAY_COMPANY.equals(ch.getCode())){
				//支付宝商户
				Map<String, Object> acParams = JSONUtils.json2map(ca.getData());
				acParams.put("pid", ca.getAccountNum());
				
				//封装支付宝请求参数
				Map<String, String> aliParams = new HashMap<String, String>();
				aliParams.put("notify_url", ca.getTransferUrl() + AliUtlity.ALI_TRANSFER_CALLBACK_API);
				aliParams.put("body", "");
				aliParams.put("subject", title);
				aliParams.put("passback_params", URLEncoder.encode(ur.getUuid(), "UTF-8"));
				aliParams.put("time_expire", Utlity.timestampToMinuteString(ur.getTimeout()));
				aliParams.put("out_trade_no", ur.getOrderNum());
				aliParams.put("quit_url", returnUrl == null ? "" : returnUrl);
				aliParams.put("total_amount", ur.getTotalAmount().divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("acParamsStr", JSONUtils.obj2json(acParams));
				paramMap.put("aliParamsStr", JSONUtils.obj2json(aliParams));
				
				//调用转发服务器
				String resultStr = HttpClientUtil.post(ca.getTransferUrl() + AliUtlity.ALI_TRANSFER_CREATE_ORDER_API, paramMap);
				Map<String, Object> resultMap = JSONUtils.json2map(resultStr);
				if(!"SUCCESS".equals(resultMap.get("status").toString())){
					//调用失败
					result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_FAIL);
					return;
				}
				
				Map<String, Object> aliResult = JSONUtils.json2map(resultMap.get("data").toString());
				if ((boolean)aliResult.get("request")) {
					if((boolean)aliResult.get("result")) {
						//下单成功
						this.userRechargeDao.insert(ur);
						resultData.put("company", company);
						resultData.put("channel", channel);
						resultData.put("timestamp", System.currentTimeMillis() + "");
						resultData.put("orderNum", ur.getCompanyOrderNum());
						resultData.put("paymentOrderNum", ur.getOrderNum());
						resultData.put("amount", ur.getTotalAmount().toString());
						resultData.put("poundage", ur.getPoundage().toString());
						resultData.put("passbackParams", ur.getCompanyData());
						resultData.put("data", aliResult.get("response").toString());
						resultData.put("sign", PaymentUtil.getApisSign(resultData, c.getSystemPublic()));
						
						result.setData(resultData, ApiResultStatus.SUCCESS, ApiResultCode.TRADE_CREATE_SUCCESS);
						return;
					} else {
						//下单失败
						result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_FAIL);
						return;
					}
				} else {
					//下单失败
					result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_FAIL);
					return;
				}
			}else if(Utlity.CHANNEL_RECHARGE_WECHAT_COMPANY.equals(ch.getCode())){
				//企业微信
				Map<String, Object> acParams = JSONUtils.json2map(ca.getData());
				acParams.put("mchid", ca.getAccountNum());
				
				//封装微信请求参数
				Map<String, String> wechatParams = new HashMap<String, String>();
				wechatParams.put("body", title);
				wechatParams.put("attach", ur.getUuid());
				wechatParams.put("out_trade_no", ur.getOrderNum());
				wechatParams.put("total_fee", ur.getTotalAmount().toString());
				wechatParams.put("notify_url", ca.getTransferUrl() + WechatUtlity.WECHAT_TRANSFER_CALLBACK_API);
				wechatParams.put("time_expire", Utlity.timestampToWechatString(ur.getTimeout()));
				wechatParams.put("spbill_create_ip", clientIp);
				
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("acParamsStr", JSONUtils.obj2json(acParams));
				paramMap.put("wechatParamsStr", JSONUtils.obj2json(wechatParams));
				
				//调用转发服务器
				String resultStr = HttpClientUtil.post(ca.getTransferUrl() + WechatUtlity.WECHAT_TRANSFER_CREATE_ORDER_API, paramMap);
				Map<String, Object> resultMap = JSONUtils.json2map(resultStr);
				if(!"SUCCESS".equals(resultMap.get("status").toString())){
					//调用失败
					result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_FAIL);
					return;
				}
				
				Map<String, Object> wechatResult = JSONUtils.json2map(resultMap.get("data").toString());
				if ((boolean)wechatResult.get("request")) {
					if((boolean)wechatResult.get("result")) {
						//下单成功
						this.userRechargeDao.insert(ur);
						resultData.put("company", company);
						resultData.put("channel", channel);
						resultData.put("timestamp", System.currentTimeMillis() + "");
						resultData.put("orderNum", ur.getCompanyOrderNum());
						resultData.put("paymentOrderNum", ur.getOrderNum());
						resultData.put("amount", ur.getTotalAmount().toString());
						resultData.put("poundage", ur.getPoundage().toString());
						resultData.put("passbackParams", ur.getCompanyData());
						resultData.put("data", wechatResult.get("response").toString());
						resultData.put("sign", PaymentUtil.getApisSign(resultData, c.getSystemPublic()));
						
						result.setData(resultData, ApiResultStatus.SUCCESS, ApiResultCode.TRADE_CREATE_SUCCESS);
						return;
					} else {
						//下单失败
						result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_FAIL);
						return;
					}
				} else {
					//下单失败
					result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_FAIL);
					return;
				}
			}else if(Utlity.CHANNEL_RECHARGE_ALIPAY_CODE.equals(ch.getCode())){
				//支付宝扫码
				this.userRechargeDao.insert(ur);
				resultData.put("company", company);
				resultData.put("channel", channel);
				resultData.put("timestamp", System.currentTimeMillis() + "");
				resultData.put("orderNum", ur.getCompanyOrderNum());
				resultData.put("paymentOrderNum", ur.getOrderNum());
				resultData.put("amount", ur.getTotalAmount().toString());
				resultData.put("poundage", ur.getPoundage().toString());
				resultData.put("passbackParams", ur.getCompanyData());
				
				//生成token
				String token = URLEncoder.encode(Base64Util.getBase64(DESUtil.encryptStr(ur.getUuid() + ur.getOrderNum())), "UTF-8");
				resultData.put("data", ca.getTransferUrl() + Utlity.CHANNEL_RECHARGE_ALIPAY_CODE_PATH + "?token=" + token);
				resultData.put("sign", PaymentUtil.getApisSign(resultData, c.getSystemPublic()));
				
				result.setData(resultData, ApiResultStatus.SUCCESS, ApiResultCode.TRADE_CREATE_SUCCESS);
				return;
			}else if(Utlity.CHANNEL_RECHARGE_WECHAT_CODE.equals(ch.getCode())){
				//微信扫码
				this.userRechargeDao.insert(ur);
				resultData.put("company", company);
				resultData.put("channel", channel);
				resultData.put("timestamp", System.currentTimeMillis() + "");
				resultData.put("orderNum", ur.getCompanyOrderNum());
				resultData.put("paymentOrderNum", ur.getOrderNum());
				resultData.put("amount", ur.getTotalAmount().toString());
				resultData.put("poundage", ur.getPoundage().toString());
				resultData.put("passbackParams", ur.getCompanyData());
				
				//生成token
				String token = URLEncoder.encode(Base64Util.getBase64(DESUtil.encryptStr(ur.getUuid() + ur.getOrderNum())), "UTF-8");
				resultData.put("data", ca.getTransferUrl() + Utlity.CHANNEL_RECHARGE_WECHAT_CODE_PATH + "?token=" + token);
				resultData.put("sign", PaymentUtil.getApisSign(resultData, c.getSystemPublic()));
				
				result.setData(resultData, ApiResultStatus.SUCCESS, ApiResultCode.TRADE_CREATE_SUCCESS);
				return;
			}else if(Utlity.CHANNEL_RECHARGE_BANKCARD_PERSON.equals(ch.getCode())){
				//个人银行卡付款
				String remark = Utlity.getRemark();
				
				Map<String, Object> dataMap = new HashMap<String, Object>();
				Map<String, Object> caDataMap = JSONUtils.json2map(ca.getData());		
				dataMap.put("bank", caDataMap.get("bank"));
				dataMap.put("holder", caDataMap.get("holder"));
				dataMap.put("bankcard", ca.getAccountNum());
				dataMap.put("remark", remark);
				
				transDataMap.put("remark", remark);
				ur.setTransData(JSONUtils.obj2json(transDataMap));
				this.userRechargeDao.insert(ur);
				
				resultData.put("company", company);
				resultData.put("channel", channel);
				resultData.put("timestamp", System.currentTimeMillis() + "");
				resultData.put("orderNum", ur.getCompanyOrderNum());
				resultData.put("paymentOrderNum", ur.getOrderNum());
				resultData.put("amount", ur.getTotalAmount().toString());
				resultData.put("poundage", ur.getPoundage().toString());
				resultData.put("passbackParams", ur.getCompanyData());
				resultData.put("data", JSONUtils.obj2json(dataMap));
				resultData.put("sign", PaymentUtil.getApisSign(resultData, c.getSystemPublic()));
				
				result.setData(resultData, ApiResultStatus.SUCCESS, ApiResultCode.TRADE_CREATE_SUCCESS);
				return;
			}else if(Utlity.CHANNEL_RECHARGE_BANKCARD_COMPANY.equals(ch.getCode())){
				//企业银行卡付款
				String remark = Utlity.getRemark();
				
				Map<String, Object> dataMap = new HashMap<String, Object>();
				Map<String, Object> caDataMap = JSONUtils.json2map(ca.getData());		
				dataMap.put("bank", caDataMap.get("bank"));
				dataMap.put("holder", caDataMap.get("holder"));
				dataMap.put("branchBank", caDataMap.get("branchBank"));
				dataMap.put("bankcard", ca.getAccountNum());
				dataMap.put("remark", remark);
				
				transDataMap.put("remark", remark);
				ur.setTransData(JSONUtils.obj2json(transDataMap));
				this.userRechargeDao.insert(ur);
				
				resultData.put("company", company);
				resultData.put("channel", channel);
				resultData.put("timestamp", System.currentTimeMillis() + "");
				resultData.put("orderNum", ur.getCompanyOrderNum());
				resultData.put("paymentOrderNum", ur.getOrderNum());
				resultData.put("amount", ur.getTotalAmount().toString());
				resultData.put("poundage", ur.getPoundage().toString());
				resultData.put("passbackParams", ur.getCompanyData());
				resultData.put("data", JSONUtils.obj2json(dataMap));
				resultData.put("sign", PaymentUtil.getApisSign(resultData, c.getSystemPublic()));
				
				result.setData(resultData, ApiResultStatus.SUCCESS, ApiResultCode.TRADE_CREATE_SUCCESS);
				return;
			}else if(Utlity.CHANNEL_RECHARGE_ALIPAY_BANKCARD.equals(ch.getCode())){
				//支付宝转银行卡银行卡付款
				if(Utlity.checkStringNull(holder)){
					result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_DATA_ERROR);
				}
				
				Map<String, Object> dataMap = new HashMap<String, Object>();
				Map<String, Object> caDataMap = JSONUtils.json2map(ca.getData());		
				dataMap.put("bank", caDataMap.get("bank"));
				dataMap.put("holder", caDataMap.get("holder"));
				dataMap.put("branchBank", caDataMap.get("branchBank"));
				dataMap.put("bankcard", ca.getAccountNum());
				
				transDataMap.put("holder", holder);
				ur.setTransData(JSONUtils.obj2json(transDataMap));
				this.userRechargeDao.insert(ur);
				
				resultData.put("company", company);
				resultData.put("channel", channel);
				resultData.put("timestamp", System.currentTimeMillis() + "");
				resultData.put("orderNum", ur.getCompanyOrderNum());
				resultData.put("paymentOrderNum", ur.getOrderNum());
				resultData.put("amount", ur.getTotalAmount().toString());
				resultData.put("poundage", ur.getPoundage().toString());
				resultData.put("passbackParams", ur.getCompanyData());
				resultData.put("data", JSONUtils.obj2json(dataMap));
				resultData.put("sign", PaymentUtil.getApisSign(resultData, c.getSystemPublic()));
				
				result.setData(resultData, ApiResultStatus.SUCCESS, ApiResultCode.TRADE_CREATE_SUCCESS);
				return;
			}else{
				//不明渠道
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_CHANNEL_ERROR);
				return;
			}
		} catch (Exception e) {
			//操作失败
			e.printStackTrace();
			result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_FAIL);
			return;
		}
	}
	
	/**
	 * 查询订单
	 */
	public void queryOrder(InputParams params, ApiResult result){
		Map<String, Object> paramsMap = params.getParams();
		String sign = paramsMap.get("sign").toString();
		String company = paramsMap.get("company").toString();
		String timestamp = paramsMap.get("timestamp").toString();
		String orderNum = paramsMap.get("orderNum") == null ? "" : paramsMap.get("orderNum").toString();
		String paymentOrderNum = paramsMap.get("paymentOrderNum") == null ? "" : paramsMap.get("paymentOrderNum").toString();
		String ip = paramsMap.get("ip") == null ? null : paramsMap.get("ip").toString();
		
		Map<String, String> resultData = new HashMap<String, String>();
		try {
			//时间戳
			if((System.currentTimeMillis() - Long.parseLong(timestamp)) > 60000){
				//请求超时
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_QUERY_TIMESTAMP_TIMEOUT);
				return;
			}
			
			//商户
			Company c = this.companyDao.getByCode(company);
			if(c == null){
				//商户不存在
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_QUERY_COMPANY_ERROR);
				return;
			}
			
			//ip白名单校验
			if(c.getWhiteUrl() != null && !"".equals(c.getWhiteUrl()) && !"*".equals(c.getWhiteUrl())){
				String[] whiteUrls = c.getWhiteUrl().split("/");
				boolean flag = false;
				for(String whiteUrl : whiteUrls){
					if(ip.indexOf(whiteUrl.trim()) > -1){
						flag = true;
					}
				}
				if(!flag){
					result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_QUERY_WHITEURL_ERROR);
					return;
				}
			}
			
			//订单号
			if(Utlity.checkStringNull(orderNum) && Utlity.checkStringNull(paymentOrderNum)){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_QUERY_ORDERNUM_EMPTY);
				return;
			}
			
			//拼装签名
			Map<String, String> signMap = new HashMap<String, String>();
			signMap.put("sign", sign);
			signMap.put("company", company);
			signMap.put("timestamp", timestamp);
			signMap.put("orderNum", orderNum);
			signMap.put("paymentOrderNum", paymentOrderNum);
			
			//验签
			if(!PaymentUtil.checkApisSign(signMap, c.getSystemPublic())){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_QUERY_SIGN_ERROR);
				return;
			}
			
			//查询参数
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("company", c.getUuid());
			if(!Utlity.checkStringNull(orderNum)){
				//商户订单号
				searchMap.put("companyOrderNum", orderNum);
			}
			if(!Utlity.checkStringNull(paymentOrderNum)){
				//平台订单号
				searchMap.put("orderNum", paymentOrderNum);
			}
			List<UserRecharge> urList = this.userRechargeDao.getListByParams(searchMap);
			if(urList == null || urList.size() == 0){
				//订单不存在
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_QUERY_OREDER_NULL);
				return;
			}
			UserRecharge ur = urList.get(0);
			resultData.put("company", company);
			resultData.put("channel", ur.getCompanyChannel());
			resultData.put("timestamp", System.currentTimeMillis() + "");
			resultData.put("orderNum", ur.getCompanyOrderNum());
			resultData.put("paymentOrderNum", ur.getOrderNum());
			resultData.put("amount", ur.getTotalAmount().toString());
			resultData.put("passbackParams", ur.getCompanyData());
			resultData.put("status", ur.getStatus());
			
			resultData.put("sign", PaymentUtil.getApisSign(resultData, c.getSystemPublic()));
			result.setData(resultData, ApiResultStatus.SUCCESS, ApiResultCode.TRADE_QUERY_SUCCESS);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_QUERY_FAIL);
			return;
		}
	}
	
	/**
	 * 关闭订单
	 */
	public void closeOrder(InputParams params, ApiResult result){
		Map<String, Object> paramsMap = params.getParams();
		String sign = paramsMap.get("sign").toString();
		String company = paramsMap.get("company").toString();
		String timestamp = paramsMap.get("timestamp").toString();
		String orderNum = paramsMap.get("orderNum") == null ? "" : paramsMap.get("orderNum").toString();
		String paymentOrderNum = paramsMap.get("paymentOrderNum") == "" ? null : paramsMap.get("paymentOrderNum").toString();
		String ip = paramsMap.get("ip") == null ? null : paramsMap.get("ip").toString();
		
		Map<String, String> resultData = new HashMap<String, String>();
		try {
			//时间戳
			if((System.currentTimeMillis() - Long.parseLong(timestamp)) > 300000){
				//请求超时
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_TIMESTAMP_TIMEOUT);
				return;
			}
			
			//商户
			Company c = this.companyDao.getByCode(company);
			if(c == null){
				//商户不存在
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_COMPANY_ERROR);
				return;
			}
			
			//ip白名单校验
			if(c.getWhiteUrl() != null && !"".equals(c.getWhiteUrl()) && !"*".equals(c.getWhiteUrl())){
				String[] whiteUrls = c.getWhiteUrl().split("/");
				boolean flag = false;
				for(String whiteUrl : whiteUrls){
					if(ip.indexOf(whiteUrl.trim()) > -1){
						flag = true;
					}
				}
				if(!flag){
					result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_WHITEURL_ERROR);
					return;
				}
			}
			
			//订单号
			if(Utlity.checkStringNull(orderNum) && Utlity.checkStringNull(paymentOrderNum)){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_ORDERNUM_EMPTY);
				return;
			}
			
			//拼装签名
			Map<String, String> signMap = new HashMap<String, String>();
			signMap.put("sign", sign);
			signMap.put("company", company);
			signMap.put("timestamp", timestamp);
			signMap.put("orderNum", orderNum);
			signMap.put("paymentOrderNum", paymentOrderNum);
			
			//验签
			if(!PaymentUtil.checkApisSign(signMap, c.getSystemPublic())){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_SIGN_ERROR);
				return;
			}
			
			//查询参数
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("company", c.getUuid());
			if(!Utlity.checkStringNull(orderNum)){
				//商户订单号
				searchMap.put("companyOrderNum", orderNum);
			}
			if(!Utlity.checkStringNull(paymentOrderNum)){
				//平台订单号
				searchMap.put("orderNum", paymentOrderNum);
			}
			List<UserRecharge> urList = this.userRechargeDao.getListByParams(searchMap);
			if(urList == null || urList.size() == 0){
				//订单不存在
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_OREDER_NULL);
				return;
			}
			UserRecharge ur = urList.get(0);
			
			if(UserRechargeStatus.SUCCESS.equals(ur.getStatus())){
				//订单已成功 不能关闭
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_OREDER_DISABLE);
				return;
			}
			if(UserRechargeStatus.CLOSE.equals(ur.getStatus())){
				//订单已关闭
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_OREDER_ALREADY);
				return;
			}
			this.userRechargeDao.processOrder(ur, null, UserRechargeStatus.CLOSE);
			
			resultData.put("company", company);
			resultData.put("channel", ur.getCompanyChannel());
			resultData.put("timestamp", System.currentTimeMillis() + "");
			resultData.put("orderNum", ur.getCompanyOrderNum());
			resultData.put("paymentOrderNum", ur.getOrderNum());
			resultData.put("amount", ur.getTotalAmount().toString());
			resultData.put("passbackParams", ur.getCompanyData());
			resultData.put("status", UserRechargeStatus.CLOSE);
			
			resultData.put("sign", PaymentUtil.getApisSign(resultData, c.getSystemPublic()));
			result.setData(resultData, ApiResultStatus.SUCCESS, ApiResultCode.TRADE_CLOSE_SUCCESS);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_FAIL);
			return;
		}
	}
	
	private ChannelAccount getRandomAccount(List<ChannelAccount> caList, BigDecimal amount){
		if(caList.size() == 0){
			return null;
		}
		
		//随机数
		Random random = new Random();
		Integer randomInt = 0;
		if(caList.size() > 1){
			randomInt = random.nextInt(caList.size() - 1);
		}
		ChannelAccount ca = caList.get(randomInt);
		
		//日额度
		ChannelAccountDaily cad = this.channelAccountDailyDao.get(ca.getUuid());
		if(cad != null){
			//超额暂停渠道 重新取
			if(cad.getAmount().add(amount).compareTo(ca.getDailyMax()) > 0){
				ca.setStatus(ChannelAccountStatus.SUSPEND);
				this.channelAccountDao.update(ca);
				caList.remove(ca);
				return getRandomAccount(caList, amount);
			}
		}
		
		//总额度
		if(ca.getBalance().add(amount).compareTo(ca.getTotalMax()) > 0){
			//超额关闭渠道  重新取
			ca.setStatus(ChannelAccountStatus.DISABLE);
			this.channelAccountDao.update(ca);
			caList.remove(ca);
			return getRandomAccount(caList, amount);
		}
		return ca;
	}
}