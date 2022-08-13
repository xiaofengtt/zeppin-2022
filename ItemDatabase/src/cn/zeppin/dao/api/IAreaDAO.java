package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Area;

public interface IAreaDAO extends IBaseDAO<Area, Integer> {
	
	/**
	 * 获取地区信息
	 * @author Administrator
	 * @date: 2014年9月9日 上午11:02:58 <br/> 
	 * @param map
	 * @return
	 */
	public List<Area> getAreas(Map<String,Object> map);
	
}
