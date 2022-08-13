package cn.zeppin.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.FunCategory;
import cn.zeppin.entity.OrgaCateMap;

public interface IOrgaCateMapService extends IBaseService<OrgaCateMap, Integer> {
	public List<OrgaCateMap> findByRoleId(short roleId, int level);
	
	
	public List<OrgaCateMap> getListByParams(Map<String, Object> params,String sort,int offset,int length);
	
	public int getCountByParams(Map<String, Object> params);
	
	public void updateMore(OrgaCateMap ocm);
	
	public void addMore(Map<String, Object>params);
}
