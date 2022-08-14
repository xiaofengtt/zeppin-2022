package cn.product.worldmall.service.front.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.dao.CapitalAccountDao;
import cn.product.worldmall.dao.CapitalPlatformDao;
import cn.product.worldmall.dao.FrontUserRechargeOrderDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.dao.SystemParamDao;
import cn.product.worldmall.dao.VersionDao;
import cn.product.worldmall.entity.CapitalAccount;
import cn.product.worldmall.entity.CapitalAccount.CapitalAccountStatus;
import cn.product.worldmall.entity.CapitalPlatform;
import cn.product.worldmall.entity.CapitalPlatform.CapitalPlatformStatus;
import cn.product.worldmall.entity.CapitalPlatform.CapitalPlatformType;
import cn.product.worldmall.entity.Resource;
import cn.product.worldmall.entity.SystemParam;
import cn.product.worldmall.entity.Version;
import cn.product.worldmall.entity.SystemParam.SystemParamKey;
import cn.product.worldmall.service.front.FrontCapitalAccountService;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.front.CapitalAccountVO;

@Service("frontCapitalAccountService")
public class FrontCapitalAccountServiceImpl implements FrontCapitalAccountService{

	@Autowired
	private CapitalPlatformDao capitalPlatformDao;
	
	@Autowired
	private CapitalAccountDao capitalAccountDao;
	
	@Autowired
	private ResourceDao resourceDao;

	@Autowired
	private SystemParamDao systemParamDao;

	@Autowired
	private FrontUserRechargeOrderDao frontUserRechargeOrderDao;
	
	@Autowired
	private VersionDao versionDao;

	@Override
	public void platformList(InputParams params, DataResult<Object> result) {
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("status", CapitalPlatformStatus.NORMAL);
		List<CapitalPlatform> list = capitalPlatformDao.getListByParams(searchMap);
		result.setData(list);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String capitalPlatform = paramsMap.get("capitalPlatform") == null ? "" : paramsMap.get("capitalPlatform").toString();
    	
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("capitalPlatform", capitalPlatform);
		searchMap.put("status", CapitalAccountStatus.NORMAL);
		
		List<CapitalAccount> list = capitalAccountDao.getListByParams(searchMap);
		List<CapitalAccountVO> voList = new ArrayList<CapitalAccountVO>();
		for(CapitalAccount ca : list){
			voList.add(new CapitalAccountVO(ca));
		}
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void accountList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String level = paramsMap.get("level") == null ? "" : paramsMap.get("level").toString();
    	String frontUserStatus = paramsMap.get("frontUserStatus") == null ? "" : paramsMap.get("frontUserStatus").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	
    	if(Utlity.checkStringNull(frontUser)) {
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("The user is not logged in!");
			return;
    	}
    	
    	//20201106增加马甲商品查询区分
    	String demoFlag = paramsMap.get("demoFlag") == null ? "" : paramsMap.get("demoFlag").toString();
    	String version = paramsMap.get("version") == null ? "" : paramsMap.get("version").toString();
    	String channel = paramsMap.get("channel") == null ? "" : paramsMap.get("channel").toString();
    	
    	if(Utlity.checkStringNull(demoFlag)) {
    		if(!Utlity.checkStringNull(version) && !Utlity.checkStringNull(channel)) {
        		Boolean isDemo = isDemo(version, channel);
        		if(isDemo) {
        			demoFlag = "1";
        		} else {
        			demoFlag = "0";
        		}
    		} else {
    			demoFlag = "1";
    		}
    	}
    	
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("status", CapitalAccountStatus.NORMAL);
		searchMap.put("level", level);
		searchMap.put("frontUserStatus", frontUserStatus);
		
		if("1".equals(demoFlag)) {//马甲,只取信用卡
			searchMap.put("type", CapitalPlatformType.CREDIT);
		}
		
		List<CapitalAccount> list = capitalAccountDao.getListByParams(searchMap);
		List<CapitalAccountVO> voList = new ArrayList<CapitalAccountVO>();
		
		SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
		String pathUrl = "";
		if(sp != null) {
			pathUrl = sp.getParamValue();
		} else {
			pathUrl = Utlity.IMAGE_PATH_URL;
		}
		
		for(CapitalAccount ca : list){
			CapitalAccountVO vo = new CapitalAccountVO(ca);
			
			//增加用户说明
			CapitalPlatform cp = this.capitalPlatformDao.get(ca.getCapitalPlatform());
			if(cp != null) {
				vo.setExplanation(cp.getExplanation());
				vo.setExplanImg(cp.getExplanImg());
				Resource explanImg = resourceDao.get(cp.getExplanImg());
				if(explanImg != null){
					vo.setExplanImgUrl(pathUrl + explanImg.getUrl());
				}
			}
			
			Resource re = this.resourceDao.get(ca.getLogo());
			if(re != null) {
				vo.setLogoUrl(pathUrl + re.getUrl());
			}
			
			//增加单个用户对账户可用付款次数判断
			Integer userReceiveTimes = ca.getUserPreReceive() == null ? -1 : ca.getUserPreReceive();
			if(userReceiveTimes.intValue() > -1) {
				Integer count = this.frontUserRechargeOrderDao.getCountByParams4Days(frontUser, ca.getUuid(), 0);
				userReceiveTimes = userReceiveTimes.intValue() - count.intValue();
				if(userReceiveTimes > -1) {
					vo.setUserPreReceive(userReceiveTimes);
				} else {
					vo.setUserPreReceive(0);//-1代表无线次数
				}
			} else {
				vo.setUserPreReceive(-1);//-1代表无线次数
			}
			voList.add(vo);
		}
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);	
	}

	/**
	 * 获取用户说明
	 */
	@Override
	public void explanation(InputParams params, DataResult<Object> result) {
		// TODO Auto-generated method stub
		
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
