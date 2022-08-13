package cn.zeppin.service.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.ILogDao;
import cn.zeppin.entity.Log;
import cn.zeppin.service.ILogService;

public class LogServiceImpl extends BaseServiceImpl<Log, Integer> implements
	ILogService
{

    private ILogDao logDao;

    public ILogDao getLogDao()
    {
    	return logDao;
    }

    public void setLogDao(ILogDao logDao)
    {
    	this.logDao = logDao;
    }

    public Integer getLogCountByParams(Map<String, Object> map){
    	return this.logDao.getLogCountByParams(map);
    }
	
	public List<Log> getLogListByParams(Map<String, Object> map, int offset, int length){
		return this.logDao.getLogListByParams(map,offset,length);
	}
    
	public List<String> getTableList(){
		return this.logDao.getTableList();
	}
	
    @Override
    public Log add(Log t){
    	return logDao.add(t);
    }
}
