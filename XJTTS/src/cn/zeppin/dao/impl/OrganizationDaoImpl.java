package cn.zeppin.dao.impl;

import java.util.List;

import org.hibernate.Query;

import cn.zeppin.dao.IOrganizationDao;
import cn.zeppin.entity.Organization;
import cn.zeppin.utility.DictionyMap;

public class OrganizationDaoImpl extends BaseDaoImpl<Organization, Integer> implements IOrganizationDao {

	/**
	 * 通过部门，获取所属所有下级部门
	 * @param organization
	 * @return
	 */
	@Override
	public List<Organization> getALLSubOrganizations(Organization organization) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" from Organization where 1=1");
		sb.append(" and scode like '").append(organization.getScode()).append("%' ");
		sb.append(" and status=").append(DictionyMap.ORGANIZATION_STATUS_NORMAL);
		List<Organization> result = this.getListByHSQL(sb.toString());
		return result;
	}
	
	/**
	 * 通过父级部门，获取所属派出学校列表
	 * 
	 * @author Clark 2014.05.27
	 * @param organization 父级部门
	 * @return List<Organization>学校列表
	 */
	public List<Organization> getAllChildSchool(Organization organization) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" from Organization where 1=1");
		sb.append(" and scode like '").append(organization.getScode()).append("%' ");
		sb.append(" and status=").append(DictionyMap.ORGANIZATION_STATUS_NORMAL);
		sb.append(" and isschool=1");
		List<Organization> result = this.getListByHSQL(sb.toString());
		return result;
	}

	/**
	 * 获取下级组织机构列表
	 * 
	 * @author Clark 2014.05.28
	 * @param organization
	 *            部门
	 * @return List<Organization>学校列表
	 */
	public List<Organization> getSubOrganizations(Organization organization) {
		// TODO Auto-generated method stub
		StringBuilder hqlString = new StringBuilder();
		hqlString.append("from Organization where pid=");
		hqlString.append(organization.getId());
		List<Organization> result = this.getListByHSQL(hqlString.toString());
		return result;
	}

	@Override
	public int checkUserInfo(Object[] pars) {

		String sql = "select * from sys_user t where t.idcard=? or t.mobile=? or t.email=?";
		Query query = this.getCurrentSession().createSQLQuery(sql);

		for (int i = 0; i < pars.length; i++) {
			query.setParameter(i, pars[i]);
		}
		List li = query.list();

		if (li != null && li.size() > 0) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public List<Object> getLevelOrganization(int pid) {
		// TODO Auto-generated method stub
		String sql = "select t.id,t.name,t.scode,t.level from organization t where 1=1 and t.status=1 ";
		if (pid == 0) {
			sql += " and t.pid is null";
		} else {
			sql += " and t.pid=" + pid;
		}

		Query query = this.getCurrentSession().createSQLQuery(sql);
		List li = query.list();
		return li;
	}

	@Override
	public int getOrgHasChild(int pid) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from organization t where 1=1 ";
		if (pid == 0) {
			sql += " and t.pid is null";
		} else {
			sql += " and t.pid=" + pid;
		}

		Query query = this.getCurrentSession().createSQLQuery(sql);
		List li = query.list();
		if (li != null && li.size() > 0) {
			int flag = Integer.parseInt(li.get(0).toString());
			return flag;
		} else {
			return 0;
		}
	}

	@Override
	public void addOrganization(Organization organization) {
		// TODO Auto-generated method stub
		super.add(organization);
		String str = String.format("%010d", organization.getId());
		if (organization.getOrganization() != null) {
			str = organization.getOrganization().getScode() + str;
		}
		organization.setScode(str);
		this.update(organization);
	}


	
}
