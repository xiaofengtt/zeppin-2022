package com.whaty.platform.entity.dao.hibernate.recruit;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.whaty.platform.entity.bean.PrRecExamCourseTime;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.recruit.PrRecExamCourseTimeDAo;

public class PrRecExamCourseTimeHibernateDAO extends
		AbstractEntityHibernateDao<PrRecExamCourseTime,String> implements PrRecExamCourseTimeDAo {

	public PrRecExamCourseTimeHibernateDAO(){
		this.entityClass=PrRecExamCourseTime.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}
	public Integer getTotalCount(final DetachedCriteria detachedCriteria){
		return (Integer) this.getHibernateTemplate().execute(
				new HibernateCallback() {
						public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = detachedCriteria.getExecutableCriteria(session);
						
						CriteriaImpl impl = (CriteriaImpl) criteria;
						 //先把Projection和OrderBy条件取出来,清空两者来执行Count操作
                     Projection projection = impl.getProjection();
                     
						Integer totalCount = ((Integer) criteria.setProjection(
								Projections.rowCount()).uniqueResult());
						
						 // 将之前的Projection和OrderBy条件重新设回去
	                        criteria.setProjection(projection);
	                        if (projection == null) {
	                            criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
	                        }
						return totalCount;
					}
				}, true);
		
	}

}
