package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Medal;

public interface IMedalDAO extends IBaseDAO<Medal,Integer> {
	
	/**
	 * 添加
	 * 
	 * @author Administrator
	 * @date: 2014年11月4日 上午11:06:41 <br/>
	 * @param medal
	 */
	public void addMedal(Medal medal);

	/**
	 * 获取个数
	 * 
	 * @author Administrator
	 * @date: 2014年11月4日 上午11:37:20 <br/>
	 * @param map
	 * @return
	 */
	public int getMedalCount(Map<String, Object> map);

	/**
	 * 获取列表
	 * @author Administrator
	 * @date: 2014年11月4日 上午11:38:26 <br/> 
	 * @param map
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Medal> getMedals(Map<String, Object> map, int offset, int length);
	
}
