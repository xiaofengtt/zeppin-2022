package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ITaskDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Task;

public class TaskDAO extends HibernateTemplateDAO<Task, Integer> implements ITaskDAO {

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

		StringBuilder hql = new StringBuilder();
		hql.append("from Task where 1=1 ");

		if (map.containsKey("type") && map.get("type") != null) {
			hql.append(" and type = ").append(map.get("type"));
		}

		if (map.containsKey("status") && map.get("status") != null) {
			hql.append(" and status = ").append(map.get("status"));
		}

		return this.getByHQL(hql.toString(), offset, length);

	}

}
