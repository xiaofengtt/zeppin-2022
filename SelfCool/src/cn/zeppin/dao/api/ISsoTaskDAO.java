package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.SsoTask;

public interface ISsoTaskDAO extends IBaseDAO<SsoTask, Long> {

	
	/**
	 * 获取用户任务个数
	 * @param map
	 * @return
	 */
	public int getSsoTaskCountByMap(Map<String, Object> map);

	/**
	 * 获取用户任务列表
	 * @param map
	 * @param sorts
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<SsoTask> getSsoTaskByMap(Map<String, Object> map, String sorts, int offset, int length);
	
}
