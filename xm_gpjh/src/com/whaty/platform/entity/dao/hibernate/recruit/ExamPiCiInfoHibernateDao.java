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

import com.whaty.platform.entity.bean.PeRecExam;
import com.whaty.platform.entity.dao.recruit.ExamPiCiInfoDao;
import com.whaty.platform.entity.util.Page;

public class ExamPiCiInfoHibernateDao extends HibernateDaoSupport implements
		ExamPiCiInfoDao {
	
	private static final String LOAD_BY_ID = " from PeRecExam where id = ? ";
	private static final String LOAD_BY_NAME = " from PeRecExam where name = ? ";
	private static final String DELETE_BY_IDS ="delete from PeRecExam where id in(:ids)"; 
	
	private Class entityClass;
	private String table;
	
	public ExamPiCiInfoHibernateDao(){
		this.entityClass = PeRecExam.class;
		this.table = this.getEntityClass().getName();
	}
	
	public Class getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class entityClass) {
		this.entityClass = entityClass;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	/**
	 * 根据id获得考试批次信息
	 */
	public PeRecExam getPeRecExamById(
			String id) {
		
		List<PeRecExam> list = this.getHibernateTemplate().find(LOAD_BY_ID,id);
		if(list ==null || list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}

	/**
	 * 根据考试批次编号获得考试批次信息
	 */
	public PeRecExam getPeRecExamByName(
			String name) {
		
		List<PeRecExam> list = this.getHibernateTemplate().find(LOAD_BY_NAME,name);
		if(list ==null || list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}

	/**
	 * 保存和更新考试批次信息
	 */
	public PeRecExam savePeRecExam(
			PeRecExam recExam) {
		
		this.getHibernateTemplate().saveOrUpdate(recExam);
		return recExam;
	}

	/**
	 * 删除考试批次信息
	 */
	public void deletePeRecExam(
			PeRecExam recExam) {
		
		//this.getHibernateTemplate().delete(teacher);
		
	}
	
	
	
	/**
	 * 根据条件分页获得考试批次信息
	 */
	public Page getRecExamByPage(final DetachedCriteria detachedCriteria,
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
						
						List<PeRecExam> items = criteria
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
//						List<PeRecExam> items = criteria
//								.setFirstResult(startIndex).setMaxResults(
//										pageSize).list();
//						return items;
//					}
//				}, true);
//		
//
//	}

	public int deletePeRecExamByIds(final List ids) {
		return (Integer)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException,
					SQLException {
				Integer i = new Integer(0);  
				Query query = s.createQuery(DELETE_BY_IDS);
				query.setParameterList("ids", ids);
				i=query.executeUpdate();
				return i;
			}
		});
		
	}

	public Integer getRecExamsTotalCount(final DetachedCriteria detachedCriteria) {
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
		
		/*List list=this.getHibernateTemplate().find("select count(*) from PeRecExam");
		if(list!=null && list.size()>0)
			return ((Long)list.get(0)).intValue();
		else
			return 0;
		*/
	}
	
	/**
	 * 根据条件查找
	 */
	public List getList(final DetachedCriteria detachedCriteria){
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = detachedCriteria
						.getExecutableCriteria(session);
				return criteria.list();
			}
		});
	}
	
	public int updateColumnByIds(final List ids,final String column,final String value){
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {

						int count = 0;

						String sql = "update " + table + " n set n." + column+ " = ? where n.id in(:ids)";
						Query query = s.createQuery(sql);
						query.setString(0, value);
						query.setParameterList("ids", ids);
						try {
							count = query.executeUpdate();
						} catch (Exception e) {
							
							throw new RuntimeException(e);
						}

						return count;
					}
				});
	}
}

