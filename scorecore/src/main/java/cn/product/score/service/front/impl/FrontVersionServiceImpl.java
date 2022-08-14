package cn.product.score.service.front.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.score.api.base.BaseResult.ResultStatusType;
import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.dao.VersionDao;
import cn.product.score.entity.Version;
import cn.product.score.service.front.FrontVersionService;
import cn.product.score.vo.front.VersionVO;

@Service("frontVersionService")
public class FrontVersionServiceImpl implements FrontVersionService{
	
	@Autowired
	private VersionDao versionDao;

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String channel = paramsMap.get("channel") == null ? "" : paramsMap.get("channel").toString();
    	String version = paramsMap.get("version") == null ? "" : paramsMap.get("version").toString();
    	String bundleid = paramsMap.get("bundleid") == null ? "" : paramsMap.get("bundleid").toString();
    	
    	Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("channel", channel);
		searchMap.put("name", version);
		searchMap.put("bundleid", bundleid);
    	List<Version> list = this.versionDao.getListByParams(searchMap);
		if(list != null && !list.isEmpty()) {
			VersionVO vvo = new VersionVO(list.get(0));
			result.setData(vvo);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("获取成功！");
		} else {
			result.setData(new VersionVO());
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("获取成功！");
		}
		return;
	}
}
