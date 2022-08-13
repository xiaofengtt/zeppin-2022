package cn.zeppin.dao.imp;

import java.util.List;

import cn.zeppin.dao.api.IInvigilationTeacherOldDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.InvigilationTeacherOld;

/**
 * ClassName: InvigilationTeacherDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 */
public class InvigilationTeacherOldDAO extends HibernateTemplateDAO<InvigilationTeacherOld, Integer>
		implements IInvigilationTeacherOldDAO {
	@Override
	public InvigilationTeacherOld getTeacherOldByIdcard(String idcard) {
		StringBuilder hql = new StringBuilder();
		hql.append(" from InvigilationTeacherOld t where 1=1 ");
		hql.append(" and t.idcard='"+idcard+"'");
		
		List<InvigilationTeacherOld> userlist = this.getByHQL(hql.toString());
		if (userlist != null && userlist.size() > 0) {
			return userlist.get(0);
		}
		return null;
	}
}
