package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ISsoTaskDAO;
import cn.zeppin.entity.SsoTask;
import cn.zeppin.service.api.ISsoTaskService;

public class SsoTaskService implements ISsoTaskService {

	private ISsoTaskDAO ssoTaskDAO;

	public ISsoTaskDAO getSsoTaskDAO() {
		return ssoTaskDAO;
	}

	public void setSsoTaskDAO(ISsoTaskDAO ssoTaskDAO) {
		this.ssoTaskDAO = ssoTaskDAO;
	}

	/**
	 * 获取用户任务个数
	 * 
	 * @param map
	 * @return
	 */
	public int getSsoTaskCountByMap(Map<String, Object> map) {
		return this.getSsoTaskDAO().getSsoTaskCountByMap(map);
	}

	/**
	 * 获取用户任务列表
	 * 
	 * @param map
	 * @param sorts
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<SsoTask> getSsoTaskByMap(Map<String, Object> map, String sorts, int offset, int length) {
		return this.getSsoTaskDAO().getSsoTaskByMap(map, sorts, offset, length);
	}

	@Override
	public void addSsoTask(SsoTask st) {
		this.getSsoTaskDAO().save(st);
	}

}
