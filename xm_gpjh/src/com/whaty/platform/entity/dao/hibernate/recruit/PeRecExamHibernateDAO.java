package com.whaty.platform.entity.dao.hibernate.recruit;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.whaty.platform.entity.bean.PeRecExam;

/**
 * Data access object (DAO) for domain model class PeRecExam.
 * 
 * @see com.whaty.platform.entity.bean.recruit.PeRecExam
 * @author MyEclipse Persistence Tools
 */

public class PeRecExamHibernateDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(PeRecExamHibernateDAO.class);

	protected void initDao() {
		// do nothing
	}

	public void save(PeRecExam transientInstance) {
		log.debug("saving PeRecExam instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed");
			throw re;
		}
	}

	public void delete(PeRecExam persistentInstance) {
		log.debug("deleting PeRecExam instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed");
			throw re;
		}
	}

	public PeRecExam findById(java.lang.String id) {
		log.debug("getting PeRecExam instance with id: " + id);
		try {
			PeRecExam instance = (PeRecExam) getHibernateTemplate().get(
					"com.whaty.platform.entity.bean.recruit.PeRecExam", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed");
			throw re;
		}
	}

	public List findByExample(PeRecExam instance) {
		log.debug("finding PeRecExam instance by example");
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
		log.debug("finding PeRecExam instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from PeRecExam as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed");
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all PeRecExam instances");
		try {
			String queryString = "from PeRecExam";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed");
			throw re;
		}
	}

	public PeRecExam merge(PeRecExam detachedInstance) {
		log.debug("merging PeRecExam instance");
		try {
			PeRecExam result = (PeRecExam) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed");
			throw re;
		}
	}

	public void attachDirty(PeRecExam instance) {
		log.debug("attaching dirty PeRecExam instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed");
			throw re;
		}
	}

	public void attachClean(PeRecExam instance) {
		log.debug("attaching clean PeRecExam instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed");
			throw re;
		}
	}

	public static PeRecExamHibernateDAO getFromApplicationContext(ApplicationContext ctx) {
		return (PeRecExamHibernateDAO) ctx.getBean("PeRecExamHibernateDAO");
	}
}