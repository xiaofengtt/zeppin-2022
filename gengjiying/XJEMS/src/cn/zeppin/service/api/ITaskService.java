package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Task;

public interface ITaskService {

	/**
	 * 根据id 获取任务信息
	 * 
	 * @param id
	 * @return
	 */
	public Task getById(int id);

	
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
