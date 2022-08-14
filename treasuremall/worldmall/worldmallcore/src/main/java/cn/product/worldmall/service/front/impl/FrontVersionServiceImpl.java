package cn.product.worldmall.service.front.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.dao.InternationalInfoVersionDao;
import cn.product.worldmall.dao.VersionDao;
import cn.product.worldmall.entity.InternationalInfo.InternationalInfoCode;
import cn.product.worldmall.entity.InternationalInfoVersion;
import cn.product.worldmall.entity.Version;
import cn.product.worldmall.entity.Version.VersionChannel;
import cn.product.worldmall.entity.Version.VersionType;
import cn.product.worldmall.service.front.FrontVersionService;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.front.VersionVO;

@Service("frontVersionService")
public class FrontVersionServiceImpl implements FrontVersionService{
	
	@Autowired
	private VersionDao versionDao;
	
	@Autowired
	private InternationalInfoVersionDao internationalInfoVersionDao;

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String channel = paramsMap.get("channel") == null ? "" : paramsMap.get("channel").toString();
    	String version = paramsMap.get("version") == null ? "" : paramsMap.get("version").toString();
    	String bundleid = paramsMap.get("bundleid") == null ? "" : paramsMap.get("bundleid").toString();
    	String countryCode = paramsMap.get("countryCode") == null ? "" : paramsMap.get("countryCode").toString();
    	
    	if(VersionType.IOS.equals(channel)) {
    		channel = VersionChannel.APPSTORE;
    	}
    	if(Utlity.checkStringNull(countryCode)) {
    		countryCode = InternationalInfoCode.US;
    	}
    	if(Utlity.checkStringNull(bundleid)) {//不带带包名时，走缓存数据
    		Version ver = this.versionDao.getByChannelVersion(channel, version);
    		if(ver != null) {
    			VersionVO vvo = new VersionVO(ver);
    			
    			//20210112增加提现开关控制
    			InternationalInfoVersion iiv = this.internationalInfoVersionDao.getByInternationalInfoVersion(countryCode, channel, version);
    			if(iiv != null) {
    				vvo.setFlagfund(iiv.getFlagWithdraw());
    			}
    			result.setData(vvo);
    			result.setStatus(ResultStatusType.SUCCESS);
    			result.setMessage("Successful!");
    		} else {
    			result.setData(new VersionVO());
    			result.setStatus(ResultStatusType.SUCCESS);
    			result.setMessage("Successful!");
    		}
    		return;
    	}
    	
    	Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("channel", channel);
		searchMap.put("name", version);
		searchMap.put("bundleid", bundleid);
    	List<Version> list = this.versionDao.getListByParams(searchMap);
		if(list != null && !list.isEmpty()) {
			VersionVO vvo = new VersionVO(list.get(0));
			
			//20210112增加提现开关控制
			InternationalInfoVersion iiv = this.internationalInfoVersionDao.getByInternationalInfoVersion(countryCode, channel, version);
			if(iiv != null) {
				vvo.setFlagfund(iiv.getFlagWithdraw());
			}
			result.setData(vvo);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("Successful!");
		} else {
			result.setData(new VersionVO());
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("Successful成功！");
		}
		return;
	}
}
