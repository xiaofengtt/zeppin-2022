package cn.product.treasuremall.service.front.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.CapitalAccountDao;
import cn.product.treasuremall.dao.CapitalPlatformDao;
import cn.product.treasuremall.dao.FrontUserRechargeOrderDao;
import cn.product.treasuremall.dao.ResourceDao;
import cn.product.treasuremall.dao.SystemParamDao;
import cn.product.treasuremall.entity.CapitalAccount;
import cn.product.treasuremall.entity.CapitalAccount.CapitalAccountStatus;
import cn.product.treasuremall.entity.CapitalPlatform;
import cn.product.treasuremall.entity.CapitalPlatform.CapitalPlatformStatus;
import cn.product.treasuremall.entity.SystemParam.SystemParamKey;
import cn.product.treasuremall.entity.Resource;
import cn.product.treasuremall.entity.SystemParam;
import cn.product.treasuremall.service.front.FrontCapitalAccountService;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.vo.front.CapitalAccountVO;

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
			result.setMessage("用户未登录！");
			return;
    	}
    	
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("status", CapitalAccountStatus.NORMAL);
		searchMap.put("level", level);
		searchMap.put("frontUserStatus", frontUserStatus);
		
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
	
}
