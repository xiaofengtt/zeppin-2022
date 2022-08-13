package com.whaty.platform.entity.dao.hibernate.exam;

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

import com.whaty.platform.entity.bean.PeExamno;
import com.whaty.platform.entity.bean.PeRecExam;
import com.whaty.platform.entity.dao.exam.PeExamnoDAO;
import com.whaty.platform.entity.util.Page;

public class PeExamnoHibernateDAO extends HibernateDaoSupport implements
		PeExamnoDAO {

	private static final String LOAD_BY_ID = " from PeExamno where id = ? ";

	private static final String LOAD_BY_NAME = " from PeExamno where name = ? ";

	private static final String DELETE_BY_IDS = "delete from PeExamno where id in(:ids)";

	public void deletePeExamno(PeExamno examno) {
		this.getHibernateTemplate().delete(examno);
	}

	public int deletePeExamnoByIds(final List ids) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						Integer i = new Integer(0);
						Query query = s.createQuery(DELETE_BY_IDS);
						query.setParameterList("ids", ids);
						i = query.executeUpdate();
						return i;
					}
				});
	}

	public List getList(final DetachedCriteria detachedCriteria) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = detachedCriteria
						.getExecutableCriteria(session);
				return criteria.list();
			}
		});
	}

	/**
	 * 根据id获得场次信息
	 */
	public PeExamno getPeExamnoById(String id) {
		List<PeExamno> list = this.getHibernateTemplate().find(LOAD_BY_ID, id);
		if (list == null || list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}

	/**
	 * 根据场次名称获得场次信息
	 */
	public PeExamno getPeExamnoByName(String name) {
		List<PeExamno> list = this.getHibernateTemplate().find(LOAD_BY_NAME,
				name);
		if (list == null || list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}

	public Page getPeExamnoByPage(final DetachedCriteria detachedCriteria,
			final int pageSize, final int startIndex) {
		return (Page) this.getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = detachedCriteria
								.getExecutableCriteria(session);

						CriteriaImpl impl = (CriteriaImpl) criteria;
						// 先把Projection和OrderBy条件取出来,清空两者来执行Count操作
						Projection projection = impl.getProjection();

						Integer totalCount = ((Integer) criteria.setProjection(
								Projections.rowCount()).uniqueResult());

						// 将之前的Projection和OrderBy条件重新设回去
						criteria.setProjection(projection);
						if (projection == null) {
							criteria
									.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
						}

						List<PeRecExam> items = criteria.setFirstResult(
								startIndex).setMaxResults(pageSize).list();

						Page pg = new Page(items, totalCount, pageSize,
								startIndex);
						return pg;
					}
				}, true);
	}

	public Integer getPeExamnoTotalCount(final DetachedCriteria detachedCriteria) {
		return (Integer) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = detachedCriteria
								.getExecutableCriteria(session);
						Integer totalCount = ((Integer) criteria.setProjection(
								Projections.rowCount()).uniqueResult());
						criteria.setProjection(null);
						return totalCount;
					}
				}, true);
	}

	/**
	 * 保存和更新场次信息
	 */
	public PeExamno savePeExamno(PeExamno examno) {
		this.getHibernateTemplate().saveOrUpdate(examno);
		return examno;
	}

}
