package cn.product.payment.service.api.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import jodd.util.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.payment.controller.base.BaseResult.ResultStatusType;
import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.dao.ChannelAccountDao;
import cn.product.payment.dao.ResourceDao;
import cn.product.payment.dao.UserRechargeDao;
import cn.product.payment.entity.ChannelAccount;
import cn.product.payment.entity.Resource;
import cn.product.payment.entity.UserRecharge;
import cn.product.payment.entity.UserRecharge.UserRechargeStatus;
import cn.product.payment.service.api.TransferRechargeService;
import cn.product.payment.util.Base64Util;
import cn.product.payment.util.DESUtil;
import cn.product.payment.util.ImageBase64Utils;
import cn.product.payment.util.JSONUtils;

/**
 * 网关页面处理订单
 */

@Service("transferRechargeService")
public class TransferRechargeServiceImpl implements TransferRechargeService{
	
	@Autowired
	private UserRechargeDao userRechargeDao;

	@Autowired
	private ChannelAccountDao channelAccountDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public void aliGet(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String token = paramsMap.get("token").toString();
		
		Map<String, Object> resultData = new HashMap<String, Object>();
		try {
			String tokenStr = DESUtil.decryptStr(Base64Util.getFromBase64(URLDecoder.decode(token, "UTF-8")));
			String uuid = tokenStr.substring(0,36);
			String orderNum = tokenStr.substring(36);
			
			UserRecharge ur = this.userRechargeDao.get(uuid);
			if(ur == null){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("订单不存在！");
				return;
			}
			if(!orderNum.equals(ur.getOrderNum())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("订单信息错误！");
				return;
			}
			
			ChannelAccount ca = this.channelAccountDao.get(ur.getChannelAccount());
			if(!"a4ac649b-43f3-4f85-888c-dd85bbca25a6".equals(ca.getChannel())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("订单类型错误！");
				return;
			}
			BigDecimal totalAmount = ur.getTotalAmount();
			
			Map<String, Object> dataMap = JSONUtils.json2map(ca.getData());
			Map<String, Object> fixedMap = (Map<String, Object>)dataMap.get("fixed");
			
			String resourceUuid = dataMap.get("normal").toString();
			for(String key : fixedMap.keySet()){
				if(BigDecimal.valueOf(Double.valueOf(key)).multiply(new BigDecimal(100)).compareTo(totalAmount) == 0){
					resourceUuid = fixedMap.get(key).toString();
					break;
				}
			}
			
			Map<String, Object> transDataMap = JSONUtils.json2map(ur.getTransData());
			
			Resource resource = this.resourceDao.get(resourceUuid);
			String codeUrl = resource.getUrl();
			
			resultData.put("code", ImageBase64Utils.GetImageStr(codeUrl));
			resultData.put("codeType", resource.getType());
			resultData.put("timeout", ur.getTimeout());
			resultData.put("returnUrl", transDataMap.get("returnUrl") == null ? "" : transDataMap.get("returnUrl").toString());
			resultData.put("totalAmount", totalAmount.setScale(2).toString());
			
			result.setData(resultData);
			result.setStatus(ResultStatusType.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("订单有误，请重新下单！");
			return;
		}
	}


	@Override
	public void aliFinish(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String token = paramsMap.get("token").toString();
		String orderid = paramsMap.get("orderid").toString();
		
		Map<String, String> resultData = new HashMap<String, String>();
		try {
			String tokenStr = DESUtil.decryptStr(Base64Util.getFromBase64(URLDecoder.decode(token, "UTF-8")));
			String uuid = tokenStr.substring(0,36);
			String orderNum = tokenStr.substring(36);
			
			UserRecharge ur = this.userRechargeDao.get(uuid);
			if(ur == null){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("未找到订单，如已完成付款，请联系客服！");
				return;
			}
			if(!orderNum.equals(ur.getOrderNum())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("订单信息错误，如已完成付款，请联系客服！");
				return;
			}
			if(!"a4ac649b-43f3-4f85-888c-dd85bbca25a6".equals(ur.getChannel())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("订单类型错误！");
				return;
			}
			Map<String, Object> transDataMap = JSONUtils.json2map(ur.getTransData());
			transDataMap.put("orderid", orderid);
			ur.setTransData(JSONUtils.obj2json(transDataMap));
			ur.setStatus(UserRechargeStatus.CHECKING);
			this.userRechargeDao.update(ur);
			
			result.setData(resultData);
			result.setStatus(ResultStatusType.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("订单有误，如已完成付款，请联系客服！");
			return;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void wechatGet(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String token = paramsMap.get("token").toString();
		
		Map<String, Object> resultData = new HashMap<String, Object>();
		try {
			String tokenStr = DESUtil.decryptStr(Base64Util.getFromBase64(URLDecoder.decode(token, "UTF-8")));
			String uuid = tokenStr.substring(0,36);
			String orderNum = tokenStr.substring(36);
			
			UserRecharge ur = this.userRechargeDao.get(uuid);
			if(ur == null){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("订单不存在！");
				return;
			}
			if(!orderNum.equals(ur.getOrderNum())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("订单信息错误！");
				return;
			}
			
			ChannelAccount ca = this.channelAccountDao.get(ur.getChannelAccount());
			if(!"9298fcd6-36ee-41b2-8c99-31a7334d93dc".equals(ca.getChannel())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("订单类型错误！");
				return;
			}
			BigDecimal totalAmount = ur.getTotalAmount();
			
			Map<String, Object> dataMap = JSONUtils.json2map(ca.getData());
			Map<String, Object> fixedMap = (Map<String, Object>)dataMap.get("fixed");
			
			String resourceUuid = dataMap.get("normal").toString();
			for(String key : fixedMap.keySet()){
				if(BigDecimal.valueOf(Double.valueOf(key)).multiply(new BigDecimal(100)).compareTo(totalAmount) == 0){
					resourceUuid = fixedMap.get(key).toString();
					break;
				}
			}
			
			Map<String, Object> transDataMap = JSONUtils.json2map(ur.getTransData());
			
			Resource resource = this.resourceDao.get(resourceUuid);
			String codeUrl = resource.getUrl();
			
			resultData.put("code", ImageBase64Utils.GetImageStr(codeUrl));
			resultData.put("codeType", resource.getType());
			resultData.put("timeout", ur.getTimeout());
			resultData.put("returnUrl", transDataMap.get("returnUrl") == null ? "" : transDataMap.get("returnUrl").toString());
			resultData.put("totalAmount", totalAmount.setScale(2).toString());
			
			result.setData(resultData);
			result.setStatus(ResultStatusType.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("订单有误，请重新下单！");
			return;
		}
	}


	@Override
	public void wechatFinish(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String token = paramsMap.get("token").toString();
		String orderid = paramsMap.get("orderid").toString();
		
		Map<String, String> resultData = new HashMap<String, String>();
		try {
			String tokenStr = DESUtil.decryptStr(Base64Util.getFromBase64(URLDecoder.decode(token, "UTF-8")));
			String uuid = tokenStr.substring(0,36);
			String orderNum = tokenStr.substring(36);
			
			UserRecharge ur = this.userRechargeDao.get(uuid);
			if(ur == null){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("未找到订单，如已完成付款，请联系客服！");
				return;
			}
			if(!orderNum.equals(ur.getOrderNum())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("订单信息错误，如已完成付款，请联系客服！");
				return;
			}
			if(!"9298fcd6-36ee-41b2-8c99-31a7334d93dc".equals(ur.getChannel())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("订单类型错误！");
				return;
			}
			Map<String, Object> transDataMap = JSONUtils.json2map(ur.getTransData());
			transDataMap.put("orderid", orderid);
			ur.setTransData(JSONUtils.obj2json(transDataMap));
			ur.setStatus(UserRechargeStatus.CHECKING);
			this.userRechargeDao.update(ur);
			
			result.setData(resultData);
			result.setStatus(ResultStatusType.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("订单有误，如已完成付款，请联系客服！");
			return;
		}
	}
}