package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Task;

public interface ITaskDAO extends IBaseDAO<Task, Integer> {

	/**
	 * 获取任务列表
	 * @param map
	 * @param sorts
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Task> getTasksByMap(Map<String, Object> map, String sorts, int offset, int length);
	
}
