package com.whaty.platform.entity.dao.hibernate.recruit;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.whaty.platform.entity.bean.PeRecExamcourse;
import com.whaty.platform.entity.dao.recruit.PeRecExamcourseDao;
import com.whaty.platform.entity.util.Page;

public class PeRecExamcourseHibernateDAO extends HibernateDaoSupport implements
	PeRecExamcourseDao {
	
	private static final String LOAD_BY_ID = " from PeRecExamcourse where id = ? ";
	private static final String LOAD_BY_NAME = " from PeRecExamcourse where name = ? ";
	private static final String DELETE_BY_IDS ="delete from PeRecExamcourse where id in(:ids)"; 
	
	/**
	 * 根据id获得考试批次信息
	 */
	public PeRecExamcourse getPeRecExamcourseById(
			String id) {
		
		List<PeRecExamcourse> list = this.getHibernateTemplate().find(LOAD_BY_ID,id);
		if(list ==null || list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}

	/**
	 * 根据考试批次编号获得考试批次信息
	 */
	public PeRecExamcourse getPeRecExamcourseByName(
			String name) {
		
		List<PeRecExamcourse> list = this.getHibernateTemplate().find(LOAD_BY_NAME,name);
		if(list ==null || list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}
	
	
	public List getPeRecExamcourseList(final DetachedCriteria detachedCriteria){
		return this.getHibernateTemplate().executeFind(new HibernateCallback(){
    		public Object doInHibernate(Session session)throws HibernateException,SQLException{
    			Criteria criteria = detachedCriteria.getExecutableCriteria(session);
    			return criteria.list();
    		}
    	});
	}

	/**
	 * 保存和更新考试批次信息
	 */
	public PeRecExamcourse save(
			PeRecExamcourse recExam) {
		
		this.getHibernateTemplate().saveOrUpdate(recExam);
		return recExam;
	}

	/**
	 * 删除考试批次信息
	 */
	public void delete(
			PeRecExamcourse recExamcourse) {
		
		//this.getHibernateTemplate().delete(teacher);
		
	}
	
	
	
	/**
	 * 根据条件分页获得考试批次信息
	 */
	public Page getPeRecExamcourseByPage(final DetachedCriteria detachedCriteria,
			final int pageSize, final int startIndex) {

		return (Page) this.getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = detachedCriteria
								.getExecutableCriteria(session);
						
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
						
						List<PeRecExamcourse> items = criteria
								.setFirstResult(startIndex).setMaxResults(
										pageSize).list();
						
						Page pg = new Page(items,totalCount,pageSize,startIndex);
						return pg;
					}
				},true);

	}
//	public Page getRecExamByPage(final DetachedCriteria detachedCriteria,
//			final int pageSize, final int startIndex) {
//
//		
//		return (Page) this.getHibernateTemplate().execute(
//				new HibernateCallback() {
//
//					public Object doInHibernate(Session session)
//							throws HibernateException {
//						Criteria criteria = detachedCriteria
//								.getExecutableCriteria(session);
//						List<PeRecExamcourse> items = criteria
//								.setFirstResult(startIndex).setMaxResults(
//										pageSize).list();
//						return items;
//					}
//				}, true);
//		
//
//	}

	public int deletePeRecExamcourseByIds(final List ids) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException,
					SQLException {
				Integer i=new Integer(0);
				Query query = s.createQuery(DELETE_BY_IDS);
				query.setParameterList("ids", ids);
				i=query.executeUpdate();
				return i;
			}
		});
		
	}

	public Integer getPeRecExamcoursesTotalCount(final DetachedCriteria detachedCriteria) {
		//下面注释的程序有问题
		return (Integer) this.getHibernateTemplate().execute(
				new HibernateCallback() {
						public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = detachedCriteria.getExecutableCriteria(session);
						Integer totalCount = ((Integer) criteria.setProjection(
								Projections.rowCount()).uniqueResult());
						criteria.setProjection(null);
						return totalCount;
					}
				}, true);
		
		/*List list=this.getHibernateTemplate().find("select count(*) from PeRecExamcourse");
		if(list!=null && list.size()>0)
			return ((Long)list.get(0)).intValue();
		else
			return 0;
		*/
	}
}

