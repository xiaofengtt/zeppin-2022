package com.whaty.platform.entity.dao.hibernate.recruit;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.whaty.platform.entity.bean.PeGrade;

/**
 * Data access object (DAO) for domain model class PeGrade.
 * 
 * @see com.whaty.platform.entity.bean.basic.PeGrade
 * @author MyEclipse Persistence Tools
 */

public class PeGradeHibernateDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(PeGradeHibernateDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String CODE = "code";

	protected void initDao() {
		// do nothing
	}

	public void save(PeGrade transientInstance) {
		log.debug("saving PeGrade instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed");
			throw re;
		}
	}

	public void delete(PeGrade persistentInstance) {
		log.debug("deleting PeGrade instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed");
			throw re;
		}
	}

	public PeGrade findById(java.lang.String id) {
		log.debug("getting PeGrade instance with id: " + id);
		try {
			PeGrade instance = (PeGrade) getHibernateTemplate().get(
					"com.whaty.platform.entity.bean.basic.PeGrade", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed");
			throw re;
		}
	}

	public List findByExample(PeGrade instance) {
		log.debug("finding PeGrade instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed");
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding PeGrade instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from PeGrade as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed");
			throw re;
		}
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findByCode(Object code) {
		return findByProperty(CODE, code);
	}

	public List findAll() {
		log.debug("finding all PeGrade instances");
		try {
			String queryString = "from PeGrade";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed");
			throw re;
		}
	}

	public PeGrade merge(PeGrade detachedInstance) {
		log.debug("merging PeGrade instance");
		try {
			PeGrade result = (PeGrade) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed");
			throw re;
		}
	}

	public void attachDirty(PeGrade instance) {
		log.debug("attaching dirty PeGrade instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed");
			throw re;
		}
	}

	public void attachClean(PeGrade instance) {
		log.debug("attaching clean PeGrade instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed");
			throw re;
		}
	}

	public static PeGradeHibernateDAO getFromApplicationContext(ApplicationContext ctx) {
		return (PeGradeHibernateDAO) ctx.getBean("PeGradeDAO");
	}
}