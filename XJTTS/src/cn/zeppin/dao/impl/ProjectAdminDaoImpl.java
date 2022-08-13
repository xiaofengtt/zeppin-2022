package cn.zeppin.dao.impl;

import java.util.List;

import org.hibernate.Query;

import cn.zeppin.dao.IProjectAdminDao;
import cn.zeppin.entity.ProjectAdmin;

public class ProjectAdminDaoImpl extends BaseDaoImpl<ProjectAdmin, Integer> implements IProjectAdminDao {

	@Override
	public int getProjectAdminCount(String searchName, String searchType, String sortName, String sortType, boolean isAdmin, int organizationId, int projectId, String organizations, short status) {

		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from ProjectAdmin t,Organization oz where 1=1 and t.organization=oz.id ");

		//20180313增加按状态筛选功能
		if(status > 0) {
			sb.append(" and t.status=").append(status);
		}
		
		
		if (organizationId != 0) {
			// 不是超级管理员
			sb.append(" and t.id!= " + projectId);
			if (isAdmin) {
				// 是当前级别的 管理员 包括同级以及下级所有管理员
				if (!organizations.equals("all")) {
					sb.append(" and oz.scode like'").append(organizations).append("%' ");
				}
			} else {
				// 获取出下级所有管理员 不包括同級
				if (organizations.equals("all")) {
					sb.append(" and  oz.id!=" + organizationId + " ");
				} else {
					sb.append(" and oz.id!=" + organizationId + " ");
					sb.append(" and oz.scode like'").append(organizations).append("%' ");
				}
			}
		}
		// 以后其他搜索参数
		if (searchName != null && searchName.trim().length() > 0 && searchType != null && searchType.trim().length() > 0) {

			if (searchType.equals("organization")) {
				sb.append(" and (oz.name like '%" + searchName + "%')");
			} else {
				sb.append(" and (t." + searchType + " like '%" + searchName + "%') ");
			}
		}

		// 排序 参数
		if (sortName != null && !sortName.equals("") && sortType != null && !sortType.equals("")) {
			sb.append(" order by t." + sortName + " " + sortType);
		}

		Object obj = this.getObjectByHql(sb.toString(), null);
		return Integer.valueOf(obj.toString());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getProjectAdmin(String searchName, String searchType, String sortName, String sortType, boolean isAdmin, int organizationId, int projectId, String organizations, int offset, int length, short status) {

		StringBuilder sb = new StringBuilder();
		sb.append("from ProjectAdmin t,Organization oz where 1=1 and t.organization=oz.id ");

		//20180313增加按状态筛选功能
		if(status > 0) {
			sb.append(" and t.status=").append(status);
		}
		
		if (organizationId != 0) {
			sb.append(" and t.id!= " + projectId);
			// 不是超级管理员
			if (isAdmin) {
				// 是当前级别的 管理员 包括同级以及下级所有管理员
				if (organizations.equals("all")) {
				} else {
					sb.append(" and oz.scode like'").append(organizations).append("%' ");
				}
			} else {
				if (organizations.equals("all")) {
					sb.append(" and  oz.id!=" + organizationId + " ");
				} else {
					sb.append(" and oz.id!=" + organizationId + " ");
					sb.append(" and oz.scode like'").append(organizations).append("%' ");
				}
			}
		}
		// 以后其他搜索参数
		if (searchName != null && searchName.trim().length() > 0 && searchType != null && searchType.trim().length() > 0) {
			if (searchType.equals("organization")) {
				sb.append(" and (oz.name like '%" + searchName + "%')");
			} else {
				sb.append(" and (t." + searchType + " like '%" + searchName + "%') ");
			}
		}

		// 排序 参数
		if (sortName != null && !sortName.equals("") && sortType != null && !sortType.equals("")) {
			sb.append(" order by t." + sortName + " " + sortType);
		}

		return this.getListForPageByParams(sb.toString(), offset, length, null);

	}

	@Override
	public int getHigherAdminCount(String searchName, String searchType, String sortName, String sortType, int organizationId) {

		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from ProjectAdmin t,Organization oz where 1=1 and t.organization=oz.id and t.status=1 ");
		
		sb.append(" and oz.id=").append(organizationId);
		
		// 以后其他搜索参数
		if (searchName != null && searchName.trim().length() > 0 && searchType != null && searchType.trim().length() > 0) {

			if (searchType.equals("organization")) {
				sb.append(" and (oz.name like '%" + searchName + "%')");
			} else {
				sb.append(" and (t." + searchType + " like '%" + searchName + "%') ");
			}
		}

		// 排序 参数
		if (sortName != null && !sortName.equals("") && sortType != null && !sortType.equals("")) {
			sb.append(" order by t." + sortName + " " + sortType);
		}

		Object obj = this.getObjectByHql(sb.toString(), null);
		return Integer.valueOf(obj.toString());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getHigherAdmin(String searchName, String searchType, String sortName, String sortType,int organizationId, int offset, int length) {

		StringBuilder sb = new StringBuilder();
		sb.append("from ProjectAdmin t,Organization oz where 1=1 and t.organization=oz.id and t.status=1 ");

		sb.append(" and oz.id=").append(organizationId);
		
		// 以后其他搜索参数
		if (searchName != null && searchName.trim().length() > 0 && searchType != null && searchType.trim().length() > 0) {
			if (searchType.equals("organization")) {
				sb.append(" and (oz.name like '%" + searchName + "%')");
			} else {
				sb.append(" and (t." + searchType + " like '%" + searchName + "%') ");
			}
		}

		// 排序 参数
		if (sortName != null && !sortName.equals("") && sortType != null && !sortType.equals("")) {
			sb.append(" order by t." + sortName + " " + sortType);
		}

		return this.getListForPageByParams(sb.toString(), offset, length, null);

	}
	
	@SuppressWarnings("rawtypes")
	public int checkUserInfo(Object[] pars) {
		String sql = "select * from sys_user t where 1=1 and (t.idcard=? or t.mobile=? or t.email=?) and t.role=1 ";

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

	public List<ProjectAdmin> getAdminByOrganization(Integer organizationId, int offset, int length) {
		// TODO Auto-generated method stub
//		String sql = "select * from project_admin where 1=1 and organization=? and status=1 limit ?,?";
		StringBuilder sb = new StringBuilder();
		sb.append("from ProjectAdmin pa where 1=1 and status=1 ");
		
		if (organizationId != 0) {
			sb.append(" and pa.organization="+organizationId);
		}
//		Query query = this.getCurrentSession().createSQLQuery(sql);
//		if(organizationId != null){
//			query.setParameter(0, organizationId);
//			query.setParameter(1, offset);
//			query.setParameter(2, length);
//		}
//		List li = query.list();
//		if(li.isEmpty()){
//			return null;
//		}
//		return li;
		return this.getListForPageByParams(sb.toString(), offset, length, null);
	}

	@Override
	public List getProjectAdminStatusList(String searchName, String searchType, boolean isAdmin,
			int organizationId, int projectId, String organizations) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("select t.`STATUS`,count(*) from project_admin t left join organization oz on t.organization=oz.id where 1=1 ");
		if (organizationId != 0) {
			// 不是超级管理员
			sb.append(" and t.id!= " + projectId);
			if (isAdmin) {
				// 是当前级别的 管理员 包括同级以及下级所有管理员
				if (!organizations.equals("all")) {
					sb.append(" and oz.scode like'").append(organizations).append("%' ");
				}
			} else {
				// 获取出下级所有管理员 不包括同級
				if (organizations.equals("all")) {
					sb.append(" and  oz.id!=" + organizationId + " ");
				} else {
					sb.append(" and oz.id!=" + organizationId + " ");
					sb.append(" and oz.scode like'").append(organizations).append("%' ");
				}
			}
		}
		// 以后其他搜索参数
		if (searchName != null && searchName.trim().length() > 0 && searchType != null && searchType.trim().length() > 0) {

			if (searchType.equals("organization")) {
				sb.append(" and (oz.name like '%" + searchName + "%')");
			} else {
				sb.append(" and (t." + searchType + " like '%" + searchName + "%') ");
			}
		}
		sb.append(" GROUP BY t.`STATUS`");
		return this.getListBySQL(sb.toString(), null);
	}

}
