package cn.zeppin.dao.impl;

import java.util.List;

import org.hibernate.Query;

import cn.zeppin.dao.ISubjectDao;
import cn.zeppin.entity.Subject;

public class SubjectDaoImpl extends BaseDaoImpl<Subject, Short> implements
	ISubjectDao
{
	@SuppressWarnings("unchecked")
	@Override
	public List<Subject> findByName(String value) {

		String queryString = "from Subject s where s.name=?";
		Query query = this.getCurrentSession().createQuery(queryString);
		query.setParameter(0, value);

		return query.list();

	}
}