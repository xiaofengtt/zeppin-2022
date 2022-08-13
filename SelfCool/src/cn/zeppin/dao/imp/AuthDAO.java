package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IAuthDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Auth;

public class AuthDAO extends HibernateTemplateDAO<Auth, Integer> implements IAuthDAO {

	/**
	 * 获取认证数据
	 * 
	 * @param map
	 * @param sort
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Auth> getAuthsByMap(Map<String, Object> map, String sort, int offset, int length) {

		StringBuilder sb = new StringBuilder();
		sb.append("from Auth au where 1=1 ");

		if (map.containsKey("uid") && map.get("uid") != null) {
			sb.append(" and au.uid='").append(map.get("uid")).append("'");
		}

		if (map.containsKey("authType") && map.get("authType") != null) {
			sb.append(" and au.authType=").append(map.get("authType"));
		}

		return this.getByHQL(sb.toString(), offset, length);

	}
}
