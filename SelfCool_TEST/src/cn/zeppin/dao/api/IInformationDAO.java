package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Information;

public interface IInformationDAO extends IBaseDAO<Information, Integer> {
	
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public int getInformationCount(Map<String, Object> map);

	/**
	 * 
	 * @param map
	 * @param sort
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Information> getInformations(Map<String, Object> map, String sort, int offset, int length);
	
}
