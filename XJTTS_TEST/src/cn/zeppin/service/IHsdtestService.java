package cn.zeppin.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Hsdtest;

public interface IHsdtestService extends IBaseService<Hsdtest, Integer> {

	public int getHsdTestCount(Map<String, Object> map);

	public List<Hsdtest> getHsdTest(Map<String, Object> map, int offset, int length);

}
