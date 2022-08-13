package cn.zeppin.dao;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.OrgaCateMap;

public interface IOrgaCateMapDao extends IBaseDao<OrgaCateMap, Integer> {
	public List<OrgaCateMap> findByRoleId(short roleId, int level);
	
	public List<OrgaCateMap> getListByParams(Map<String, Object> params,String sort,int offset,int length);
	
	public int getCountByParams(Map<String, Object> params);
}
