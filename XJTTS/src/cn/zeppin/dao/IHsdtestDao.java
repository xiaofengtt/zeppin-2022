package cn.zeppin.dao;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Hsdtest;

public interface IHsdtestDao extends IBaseDao<Hsdtest, Integer> {
	
	public int getHsdTestCount(Map<String,Object> map);
	public List<Hsdtest> getHsdTest(Map<String,Object> map,int offset,int length);
	
}
