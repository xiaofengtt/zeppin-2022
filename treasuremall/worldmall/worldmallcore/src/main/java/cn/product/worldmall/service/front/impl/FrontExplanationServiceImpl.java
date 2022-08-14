package cn.product.worldmall.service.front.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.dao.SystemParamDao;
import cn.product.worldmall.dao.VersionDao;
import cn.product.worldmall.entity.SystemParam;
import cn.product.worldmall.entity.Version;
import cn.product.worldmall.entity.SystemParam.SystemParamKey;
import cn.product.worldmall.service.front.FrontExplanationService;
import cn.product.worldmall.util.Utlity;

@Service("frontExplanationService")
public class FrontExplanationServiceImpl implements FrontExplanationService{
	
	
	@Autowired
	private SystemParamDao systemParamDao;
	
	@Autowired
	private VersionDao versionDao;


	@Override
	public void recharge(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		//20201106增加马甲商品查询区分
    	String demoFlag = paramsMap.get("demoFlag") == null ? "" : paramsMap.get("demoFlag").toString();
    	String version = paramsMap.get("version") == null ? "" : paramsMap.get("version").toString();
    	String channel = paramsMap.get("channel") == null ? "" : paramsMap.get("channel").toString();
    	
    	if(Utlity.checkStringNull(demoFlag)) {
    		Boolean isDemo = isDemo(version, channel);
    		if(isDemo) {
    			demoFlag = "1";
    		} else {
    			demoFlag = "0";
    		}
    	}
    	
    	String explanation = "";
    	SystemParam sp = null;
    	if("0".equals(demoFlag)) {
    		sp = this.systemParamDao.get(SystemParamKey.RECHARGE_EXPLANATION);
    	} else {
    		sp = this.systemParamDao.get(SystemParamKey.RECHARGE_EXPLANATION_DEMO);
    	}
		
		if(sp != null) {
			explanation = sp.getParamValue();
		}
		result.setData(explanation);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("Successful!");
		return;
	}


	@Override
	public void withdraw(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		//20201106增加马甲商品查询区分
    	String demoFlag = paramsMap.get("demoFlag") == null ? "" : paramsMap.get("demoFlag").toString();
    	String version = paramsMap.get("version") == null ? "" : paramsMap.get("version").toString();
    	String channel = paramsMap.get("channel") == null ? "" : paramsMap.get("channel").toString();
    	
    	if(Utlity.checkStringNull(demoFlag)) {
    		Boolean isDemo = isDemo(version, channel);
    		if(isDemo) {
    			demoFlag = "1";
    		} else {
    			demoFlag = "0";
    		}
    	}
    	
    	String explanation = "";
    	SystemParam sp = null;
    	if("0".equals(demoFlag)) {
    		sp = this.systemParamDao.get(SystemParamKey.WITHDRAW_EXPLANATION);
    	} else {
    		sp = this.systemParamDao.get(SystemParamKey.WITHDRAW_EXPLANATION_DEMO);
    	}
		
		if(sp != null) {
			explanation = sp.getParamValue();
		}
		result.setData(explanation);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("Successful!");
		return;
	}	
	
	/**
	 * 判断是否为马甲
	 * @param version
	 * @param channel
	 * @return
	 */
	private Boolean isDemo(String version, String channel) {
		if(Utlity.checkStringNull(version) || Utlity.checkStringNull(channel)) {
			return true;
		}
		Version ver = this.versionDao.getByChannelVersion(channel, version);
		if(ver != null) {
			return !ver.getFlag();
		} else {
			return true;
		}
	}
	
}
