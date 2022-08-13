package cn.zeppin.dao.impl;

import java.util.List;

import org.hibernate.Query;

import cn.zeppin.dao.IOrgaCateMapDao;
import cn.zeppin.entity.OrgaCateMap;

public class OrgaCateMapDaoImpl extends BaseDaoImpl<OrgaCateMap, Integer> implements IOrgaCateMapDao {

	/* (non-Javadoc)
	 * @see cn.zeppin.dao.IOrgaCateMapDao#findByRoleId(short, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrgaCateMap> findByRoleId(short roleId, int level) {
		// TODO Auto-generated method stub
		String hql = "from OrgaCateMap t where 1=1 and t.roleid=?";

		if (level == 0) {

		} else {
			hql += " and t.organizationLevel=" + level;
		}

		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameter(0, roleId);

		return query.list();
	}

}
