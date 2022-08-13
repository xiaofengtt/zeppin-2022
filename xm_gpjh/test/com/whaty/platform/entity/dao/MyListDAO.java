package com.whaty.platform.entity.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.dao.hibernate.GeneralHibernateDao;

/**
 * Data access object (DAO) for domain model class C.
 * 
 * @see com.whaty.platform.entity.bean.C
 * @author MyEclipse Persistence Tools
 */

public class MyListDAO extends GeneralHibernateDao {

	public List getIdNameList(String bean) {
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		String hql = "";

		if (bean.startsWith("EnumConstBy")) {
			String namespace = bean.substring(11);
			hql = "select id, name from EnumConst where namespace='"
					+ namespace + "'";
		} else if (bean.startsWith("TraineeBy")) {//TraineeBy
			hql = "select id, name from PeTrainee pe where (exists (select 1 from PeTraineeAdu p where p.traineeByFkOldTrainee=pe.id ) or exists (select 1 from PeTraineeAdu p where p.traineeByFkNewTrainee=pe.id )) ";
		} else if (bean.indexOf("By") >= 0) {
			hql = "select id, name from "
					+ bean.substring(0, bean.indexOf("By"));
		} else if (bean.equals("PeTchCourse")) {
			hql = "select id, name from "
					+ bean
					+ " where enumConstByFlagIsvalid.id = "
					+ this.getEnumConstByNamespaceCode("FlagIsvalid", "1")
							.getId();
		} else {
			String condition = "";
			// if(bean.equals("PeSite")){
			// condition=" where"+ MyBaseAction.getSiteIdsCondition("id");
			// }else if(bean.equals("PeGrade")){
			// condition=" where"+MyBaseAction.getGradeIdsCondition("id");
			// }else if(bean.equals("PeEdutype")){
			// condition=" where"+MyBaseAction.getEdutypeIdsCondition("id");
			// }else if(bean.equals("PeMajor")){
			// condition=" where"+MyBaseAction.getMajorIdsCondition("id");
			// }
			// TODO 有问题 少where啊
			hql = "select id, name from " + bean + condition;
		}

		hql += " order by name";

		List list = session.createQuery(hql)
		// .setParameter("eid", "1")
				.list();
		session.clear();
		session.close();
		return list;
	}

	public String getIdByName(String bean, String name) {
		String Id = null;
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		String hql = "";
		if (bean.startsWith("EnumConstBy")) {
			String namespace = bean.substring(11);
			if (!name.equals("")) {
				hql = "select id from EnumConst where name='" + name
						+ "' and namespace='" + namespace + "'";
			} else {
				hql = "select id from EnumConst where isDefault='1' and namespace='"
						+ namespace + "'";
			}
		} else if (bean.indexOf("By") >= 0) {
			hql = "select id from " + bean.substring(0, bean.indexOf("By"))
					+ " where name ='" + name + "'";
		} else {
			hql = "select id from " + bean + " where name='" + name + "'";
		}
		List list = session.createQuery(hql).list();
		session.clear();
		session.close();
		if (list.size() == 1)
			Id = list.get(0).toString();
		return Id;
	}

	public List queryByHQL(String hql) {
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		List list = session.createQuery(hql).list();
		session.clear();
		session.close();
		return list;
	}

	public List queryBySQL(String sql) {
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		List list = session.createSQLQuery(sql).list();
		session.clear();
		session.close();
		return list;
	}

	public AbstractBean getById(Class clazz, String id) {
		AbstractBean instance = null;
		try {
			instance = (AbstractBean) getHibernateTemplate().get(clazz, id);
			return instance;
		} catch (RuntimeException re) {
			re.printStackTrace();
		}
		return instance;
	}

	public List getByIds(Class clazz, List ids) {
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		dc.add(Restrictions.in("id", ids));
		return this.getList(dc);
	}

	public EnumConst getEnumConstByNamespaceCode(String namespace, String code) {
		final String hql = "from EnumConst e where e.namespace='" + namespace
				+ "' and e.code='" + code + "'";
		List list = (List) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) {
						List list = new ArrayList();
						Query query = session.createQuery(hql);
						try {
							list = query.list();
						} catch (RuntimeException re) {
							re.printStackTrace();
						}
						return list;
					}
				});
		if (list != null && list.size() == 1) {
			return (EnumConst) list.get(0);
		}
		return null;
	}

	public String getSysValueByName(String name) {
		final String hql = "select sv.value from SystemVariables sv where sv.name='"
				+ name + "'";
		List list = (List) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) {
						List list = new ArrayList();
						Query query = session.createQuery(hql);
						try {
							list = query.list();
						} catch (RuntimeException re) {
							re.printStackTrace();
						}
						return list;
					}
				});
		if (list != null && list.size() == 1) {
			return (String) list.get(0);
		}
		return null;
	}
}
