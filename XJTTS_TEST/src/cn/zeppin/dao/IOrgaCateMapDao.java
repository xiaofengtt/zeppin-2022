package cn.zeppin.dao;

import java.util.List;

import cn.zeppin.entity.OrgaCateMap;

public interface IOrgaCateMapDao extends IBaseDao<OrgaCateMap, Integer> {
	public List<OrgaCateMap> findByRoleId(short roleId, int level);
}
