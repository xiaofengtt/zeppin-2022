package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ISsoTaskDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.SsoTask;

public class SsoTaskDAO extends HibernateTemplateDAO<SsoTask, Long> implements ISsoTaskDAO {

	/**
	 * 获取用户任务个数
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public int getSsoTaskCountByMap(Map<String, Object> map) {

		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from SsoTask where 1=1 ");

		if (map.containsKey("user.id") && map.get("user.id") != null) {
			sb.append(" and ssoUser = ").append(map.get("user.id"));
		}
		
		if(map.containsKey("subject.id") && map.get("subject.id")!=null){
			sb.append(" and subject = ").append(map.get("subject.id"));
		}
		
		if(map.containsKey("currentdate") && map.get("currentdate")!=null){
			sb.append(" and taskDate = ").append("'").append(map.get("currentdate")).append("'");
		}
		
		Object result = this.getResultByHQL(sb.toString());
		if (result != null) {
			return Integer.valueOf(result.toString());
		}else{
			return 0;
		}

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
	@Override
	public List<SsoTask> getSsoTaskByMap(Map<String, Object> map, String sorts, int offset, int length) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("from SsoTask where 1=1 ");

		if (map.containsKey("user.id") && map.get("user.id") != null) {
			sb.append(" and ssoUser = ").append(map.get("user.id"));
		}
		
		if(map.containsKey("subject.id") && map.get("subject.id")!=null){
			sb.append(" and subject = ").append(map.get("subject.id"));
		}
		
		if(map.containsKey("currentdate") && map.get("currentdate")!=null){
			sb.append(" and taskDate = ").append("'").append(map.get("currentdate")).append("'");
		}
		
		return this.getByHQL(sb.toString(), offset, length);
	}

}
