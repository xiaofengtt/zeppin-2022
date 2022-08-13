package cn.zeppin.dao.impl;

import java.util.List;

import org.hibernate.Query;

import cn.zeppin.dao.ITrainingCollegeDao;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.TrainingSubject;

public class TrainingCollegeDaoImpl extends BaseDaoImpl<TrainingCollege, Integer> implements ITrainingCollegeDao {

	@Override
	public int checkUserInfo(Object[] pars) {
		// TODO Auto-generated method stub
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

	public List<TrainingCollege> getTrainingCollegeListForRange(){
		String hql="from TrainingCollege";
		List<TrainingCollege> list=this.getListByHSQL(hql);
		return list;
	}
	
	public List<TrainingCollege> getTrainingCollegeByName(String name){
		StringBuilder sb = new StringBuilder();
		sb.append("from TrainingCollege where name like '%");
		sb.append(name).append("%'");
		return this.getListByHSQL(sb.toString());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TrainingCollege> findByName(String value) {

		String queryString = "from TrainingCollege t where t.name=?";
		Query query = this.getCurrentSession().createQuery(queryString);
		query.setParameter(0, value);

		return query.list();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrainingCollege> getTrainingCollegeByToken(String token) {
		// TODO Auto-generated method stub
		String queryString = "from TrainingCollege t where t.token=?";
		Query query = this.getCurrentSession().createQuery(queryString);
		query.setParameter(0, token);

		return query.list();
	}
	
}
