package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ISsoUserTestDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.SsoUserTest;
import cn.zeppin.entity.Subject;

public class SsoUserTestDAO extends HibernateTemplateDAO<SsoUserTest, Long> implements ISsoUserTestDAO {

	/**
	 * 获取 用户答题记录数
	 * 
	 * @param map
	 * @return
	 */
	public int getUserTestCount(Map<String, Object> map) {

		StringBuilder hql = new StringBuilder();

		hql.append("select count(*) from SsoUserTest where 1=1 ");

		if (map.containsKey("user.id") && map.get("user.id") != null) {
			hql.append(" and ssoUser = ").append(map.get("user.id"));
		}

		if (map.containsKey("startime") && map.get("startime") != null) {
			hql.append(" and createtime > ").append("'").append(map.get("startime")).append("'");
		}

		if (map.containsKey("endtime") && map.get("endtime") != null) {
			hql.append(" and createtime < ").append("'").append(map.get("endtime")).append("'");
		}

		if (map.containsKey("!status") && map.get("!status") != null) {
			hql.append(" and status != ").append(map.get("status"));
		}

		if (map.containsKey("status") && map.get("status") != null) {
			hql.append(" and status = ").append(map.get("status"));
		}

		Object result = this.getResultByHQL(hql.toString());
		if (result != null) {
			return Integer.valueOf(result.toString());
		} else {
			return 0;
		}
	}

	/**
	 * 获取用户答题记录
	 * 
	 * @param map
	 * @param sorts
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<SsoUserTest> getUserTestByMap(Map<String, Object> map, String sorts, int offset, int length) {

		StringBuilder hql = new StringBuilder();

		hql.append("from SsoUserTest where 1=1 ");

		if (map.containsKey("user.id") && map.get("user.id") != null) {
			hql.append(" and ssoUser = ").append(map.get("user.id"));
		}

		if (map.containsKey("startime") && map.get("startime") != null) {
			hql.append(" and createtime > ").append("'").append(map.get("startime")).append("'");
		}

		if (map.containsKey("endtime") && map.get("endtime") != null) {
			hql.append(" and createtime < ").append("'").append(map.get("endtime")).append("'");
		}

		if (map.containsKey("!status") && map.get("!status") != null) {
			hql.append(" and status != ").append(map.get("!status"));
		}

		if (map.containsKey("status") && map.get("status") != null) {
			hql.append(" and status = ").append(map.get("status"));
		}

		return this.getByHQL(hql.toString(), offset, length);
	}

	
	/**
	 * 获取用户错题本的答题SsoUserTest
	 * @param currentUser
	 * @param subject
	 * @return
	 */
	@Override
	public SsoUserTest getWrongbookUserTest(SsoUser currentUser, Subject subject) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" from SsoUserTest where 1=1");
		hql.append(" and ssoUser=").append(currentUser.getId());
		hql.append(" and paper is null");
		List<SsoUserTest> ssoUserTests = this.getByHQL(hql.toString());
		if (ssoUserTests.size() > 0 ) {
			return ssoUserTests.get(0);
		}
		return null;
	}

}
