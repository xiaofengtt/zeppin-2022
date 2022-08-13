package cn.zeppin.dao.imp;

import java.util.List;

import cn.zeppin.dao.api.IRoleFuncationDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.RoleFuncation;

public class RoleFuncationDAO extends HibernateTemplateDAO<RoleFuncation, Integer> implements IRoleFuncationDAO {

	/**
	 * 根据角色获取功能
	 */
	@Override
	public List<RoleFuncation> getRoleFunctionByRoleid(int role) {

		StringBuilder sb = new StringBuilder();
		sb.append("from RoleFuncation rf where 1=1 ");
		sb.append(" and rf.role=").append(role);
		return this.getByHQL(sb.toString());

	}

}
