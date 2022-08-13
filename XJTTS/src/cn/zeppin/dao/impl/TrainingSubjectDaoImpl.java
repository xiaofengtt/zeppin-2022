package cn.zeppin.dao.impl;

import java.util.List;

import org.hibernate.Query;

import cn.zeppin.dao.ITrainingSubjectDao;
import cn.zeppin.entity.TrainingSubject;

public class TrainingSubjectDaoImpl extends	BaseDaoImpl<TrainingSubject, Short> implements ITrainingSubjectDao {

	
	@SuppressWarnings("unchecked")
	@Override
	public List<TrainingSubject> findByName(String value) {
		// TODO Auto-generated method stub
		String queryString = "from TrainingSubject t where t.name=?";
		Query query = this.getCurrentSession().createQuery(queryString);
		query.setParameter(0, value);

		return query.list();

	}
	
	public List<TrainingSubject> getTrainingSubjectList(){
		String hql="from TrainingSubject";
		List<TrainingSubject> list=this.getListByHSQL(hql);
		return list;
	}
}
