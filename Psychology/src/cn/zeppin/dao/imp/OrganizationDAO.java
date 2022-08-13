/** 
 * Project Name:CETV_TEST 
 * File Name:OrganizationDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.imp;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.dao.api.IOrganizationDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Organization;

/**
 * ClassName: OrganizationDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月22日 下午12:45:14 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class OrganizationDAO extends HibernateTemplateDAO<Organization, Integer> implements IOrganizationDAO {

	@Override
	public Organization getOrganizationByName(String name) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" from Organization o where 1=1 ");

		if (name != null && name.trim().length() > 0) {
			sb.append(" and o.name like'%").append(name).append("%'");
		}

		List<Organization> liT = this.getByHQL(sb.toString());
		if (liT != null && liT.size() > 0) {
			return liT.get(0);
		}

		return null;
	}

	@Override
	public int getOrganizationCountByParams(HashMap<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) ").append(" from Organization o where 1=1 ");

		if (map.containsKey("type")) {
			Object type = map.get("type");
			if (type != null) {
				int itype = Integer.valueOf(type.toString());
				sb.append(" and o.type=").append(itype);
			}
		}

		if (map.containsKey("name")) {
			Object name = map.get("name");
			if (name != null) {
				String sname = name.toString();
				sb.append(" and o.name like'%").append(sname).append("%' ");
			}
		}

		Object result = this.getResultByHQL(sb.toString());
		if (result != null) {
			return Integer.valueOf(result.toString());
		}
		return 0;
	}
	
	@Override
	public List<Organization> getAllOrganization(){
		StringBuilder sb = new StringBuilder();
		sb.append(" from Organization o");
		return this.getByHQL(sb.toString());
	}
	
	@Override
	public List<Organization> getOrganizationByParams(HashMap<String, Object> map, int offset, int length) {

		StringBuilder sb = new StringBuilder();
		sb.append(" from Organization o where 1=1 ");

		if (map.containsKey("type")) {
			Object type = map.get("type");
			if (type != null) {
				int itype = Integer.valueOf(type.toString());
				sb.append(" and o.type=").append(itype);
			}
		}
		if (map.containsKey("organization")) {
			Object organization = map.get("organization");
			if (organization != null) {
				int iorganization = Integer.valueOf(organization.toString());
				sb.append(" and o.id=").append(iorganization);
			}
		}
		if (map.containsKey("name")) {
			Object name = map.get("name");
			if (name != null) {
				String sname = name.toString();
				sb.append(" and o.name like'%").append(sname).append("%' ");
			}
		}

		if (map.containsKey("sorts")) {
			String sorts = map.get("sorts").toString();
			// 排序
			if (sorts != null && sorts.length() > 0) {
				String[] sortArray = sorts.split(",");
				sb.append(" order by ");
				String comma = "";
				for (String sort : sortArray) {
					sb.append(comma);
					sb.append("o.").append(sort);
					comma = ",";
				}
			}

		}

		return this.getByHQL(sb.toString(), offset, length);
	}

	/**
	 * 获取机构下用用户个数
	 * 
	 * @author Administrator
	 * @date: 2014年7月23日 上午10:44:43 <br/>
	 * @param organizationId
	 * @return
	 */
	@Override
	public int getOrganizationSysUserCount(int organizationId) {

		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from SysUser s where 1=1 ");
		if (organizationId > 0) {
			sb.append(" and s.organization=").append(organizationId);
		}

		Object result = this.getResultByHQL(sb.toString());
		if (result != null) {
			return Integer.valueOf(result.toString());
		}
		return 0;

	}

}
