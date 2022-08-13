package cn.zeppin.dao.impl;

import java.util.List;

import org.hibernate.Query;

import cn.zeppin.dao.IGradeDao;
import cn.zeppin.entity.Grade;

public class GradeDaoImpl extends BaseDaoImpl<Grade, Short> implements IGradeDao {
	@SuppressWarnings("unchecked")
	@Override
	public List<Grade> findByName(String value) {

		String queryString = "from Grade g where g.name=?";
		Query query = this.getCurrentSession().createQuery(queryString);
		query.setParameter(0, value);

		return query.list();

	}
}
