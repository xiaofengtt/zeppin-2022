package cn.zeppin.service;

import java.util.List;

import cn.zeppin.entity.OrgaCateMap;

public interface IOrgaCateMapService extends IBaseService<OrgaCateMap, Integer> {
	public List<OrgaCateMap> findByRoleId(short roleId, int level);
}
