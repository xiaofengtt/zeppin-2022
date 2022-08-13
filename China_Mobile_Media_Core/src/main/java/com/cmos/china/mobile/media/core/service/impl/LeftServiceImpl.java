package com.cmos.china.mobile.media.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cmos.china.mobile.media.core.bean.Entity;
import com.cmos.china.mobile.media.core.service.ILeftService;
import com.cmos.china.mobile.media.core.vo.iface.WebCategoryVO;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;

public class LeftServiceImpl extends BaseServiceImpl implements ILeftService {

	@Override
	public void list(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("level", "1");
		paramMap.put("status", Entity.GerneralStatusType.NORMAL);
		List<WebCategoryVO> list = this.getBaseDao().queryForList("category_getWebListByParams", paramMap, WebCategoryVO.class);
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		for(WebCategoryVO wcat : list){
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("id", wcat.getId());
			dataMap.put("name", wcat.getName());
			Map<String, String> childParamMap = new HashMap<String, String>();
			childParamMap.put("parent", wcat.getId());
			List<WebCategoryVO> childlist = this.getBaseDao().queryForList("category_getWebListByParams", childParamMap, WebCategoryVO.class);
			List<Map<String,Object>> childLists = new ArrayList<Map<String,Object>>();
			for(WebCategoryVO wcats:childlist){
				Map<String,Object> childMap = new HashMap<String,Object>();
				childMap.put("id", wcats.getId());
				childMap.put("name", wcats.getName());
				childLists.add(childMap);
			}
			dataMap.put("child", childLists);
			dataList.add(dataMap);
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("totalResultCount", dataList.size());
		
		outputObject.setBeans(dataList);
		outputObject.setObject(resultMap);
	}

}
