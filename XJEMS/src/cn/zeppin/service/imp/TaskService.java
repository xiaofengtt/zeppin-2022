package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ITaskDAO;
import cn.zeppin.entity.Task;
import cn.zeppin.service.api.ITaskService;

public class TaskService implements ITaskService {

	private ITaskDAO taskDAO;

	public ITaskDAO getTaskDAO() {
		return taskDAO;
	}

	public void setTaskDAO(ITaskDAO taskDAO) {
		this.taskDAO = taskDAO;
	}

	/**
	 * 根据id 获取任务信息
	 */
	@Override
	public Task getById(int id) {
		return this.getTaskDAO().get(id);
	}

	/**
	 * 获取任务列表
	 * 
	 * @param map
	 * @param sorts
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Task> getTasksByMap(Map<String, Object> map, String sorts, int offset, int length) {
		return this.getTaskDAO().getTasksByMap(map, sorts, offset, length);
	}

}
