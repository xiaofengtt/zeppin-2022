package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Area;

public interface IAreaService {
	
	
	/**
	 * 获取地区信息
	 * @author Administrator
	 * @date: 2014年9月9日 上午11:02:58 <br/> 
	 * @param map
	 * @return
	 */
	public List<Area> getAreas(Map<String,Object> map);
	
	public Area getAreaById(int id);
	
	/**
	 * 跟新地区信息
	 * @author Administrator
	 * @date: 2014年9月9日 上午11:14:54 <br/> 
	 * @param area
	 */
	public void updateArea(Area area);
	
}
