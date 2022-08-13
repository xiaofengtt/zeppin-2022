package cn.zeppin.dao.imp;

import java.util.Map;
import java.util.List;

import cn.zeppin.dao.api.ISsoUserDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.SsoUser;

public class SsoUserDAO extends HibernateTemplateDAO<SsoUser, Integer> implements ISsoUserDAO {
	/**
	 * 通过Map查询用户
	 * @param searchMap
	 */
	public List<SsoUser> getSsoUserByMap(Map<String,Object> map){
		StringBuilder sb = new StringBuilder();
		sb.append("from SsoUser su where 1=1 ");

		if (map.containsKey("phone") && map.get("phone") != null) {
			sb.append(" and su.phone='").append(map.get("phone")).append("'");
		}

		if (map.containsKey("password") && map.get("password") != null) {
			sb.append(" and su.password= '").append(map.get("password")).append("'");
		}

		return this.getByHQL(sb.toString());
	}
	
	/**
	 * 通过Map查询用户量
	 * @param searchMap
	 */
	public Integer getSsoUserCountByMap(Map<String,Object> map){
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from sso_user su where 1=1 ");

		if (map.containsKey("phone") && map.get("phone") != null) {
			sb.append(" and su.phone='").append(map.get("phone")).append("'");
		}

		if (map.containsKey("password") && map.get("password") != null) {
			sb.append(" and su.password='").append(map.get("password")).append("'");
		}
		return Integer.valueOf(this.getResultBySQL(sb.toString()).toString());
	}
}
