package cn.zeppin.dao.impl;

import java.util.List;

import org.hibernate.Query;

import cn.zeppin.dao.IJobDutyDao;
import cn.zeppin.entity.JobDuty;

public class JobDutyDaoImpl extends BaseDaoImpl<JobDuty, Integer> implements
	IJobDutyDao
{
	@SuppressWarnings("unchecked")
	@Override
	public List<JobDuty> findByName(String value) {

		String queryString = "from JobDuty j where j.name=?";
		Query query = this.getCurrentSession().createQuery(queryString);
		query.setParameter(0, value);

		return query.list();

	}
}
