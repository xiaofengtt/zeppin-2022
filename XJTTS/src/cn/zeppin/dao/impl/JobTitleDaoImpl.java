package cn.zeppin.dao.impl;

import java.util.List;

import org.hibernate.Query;

import cn.zeppin.dao.IJobTitleDao;
import cn.zeppin.entity.JobTitle;

public class JobTitleDaoImpl extends BaseDaoImpl<JobTitle, Integer> implements IJobTitleDao{
	@SuppressWarnings("unchecked")
	@Override
	public List<JobTitle> findByName(String value) {

		String queryString = "from JobTitle j where j.name=?";
		Query query = this.getCurrentSession().createQuery(queryString);
		query.setParameter(0, value);

		return query.list();

	}
}
