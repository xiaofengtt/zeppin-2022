package cn.zeppin.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Log;

public interface ILogService extends IBaseService<Log, Integer>
{
	public Integer getLogCountByParams(Map<String, Object> map); 
	
	public List<Log> getLogListByParams(Map<String, Object> map, int offset, int length); 
	
	public List<String> getTableList();
}
