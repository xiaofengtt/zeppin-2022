package cn.product.treasuremall.service.front.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.AreaDao;
import cn.product.treasuremall.entity.Area;
import cn.product.treasuremall.service.front.FrontAreaService;

@Service("frontAreaService")
public class FrontAreaServiceImpl implements FrontAreaService{
	
	
	@Autowired
	private AreaDao areaDao;
	

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String level = paramsMap.get("level") == null ? "" : paramsMap.get("level").toString();
		String pid = paramsMap.get("pid") == null ? "" : paramsMap.get("pid").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("level", level);
		searchMap.put("pid", pid);
		
		List<Area> list = areaDao.getListByParams(searchMap);
		
		result.setData(list);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}
	
}
