package cn.zeppin.dao.impl;

import java.util.List;

import org.hibernate.Query;

import cn.zeppin.dao.ITrainingAdminDao;
import cn.zeppin.entity.TrainingAdmin;

public class TrainingAdminDaoImpl extends BaseDaoImpl<TrainingAdmin, Integer> implements ITrainingAdminDao {
	public int checkUserInfo(Object[] pars) {
		String sql = "select * from sys_user t where (t.idcard=? or t.mobile=? or t.email=?) and role=2 ";

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
}
