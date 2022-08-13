package cn.zeppin.dao;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Log;

public interface ILogDao extends IBaseDao<Log, Integer> {
	public Integer getLogCountByParams(Map<String, Object> map); 
	
	public List<Log> getLogListByParams(Map<String, Object> map, int offset, int length); 
	
	public List<String> getTableList();
}
