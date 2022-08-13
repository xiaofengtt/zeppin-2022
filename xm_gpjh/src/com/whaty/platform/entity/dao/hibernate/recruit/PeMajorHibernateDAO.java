package com.whaty.platform.entity.dao.hibernate.recruit;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.whaty.platform.entity.bean.PeMajor;

/**
 * Data access object (DAO) for domain model class PeMajor.
 * 
 * @see com.whaty.platform.entity.bean.basic.PeMajor
 * @author MyEclipse Persistence Tools
 */

public class PeMajorHibernateDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(PeMajorHibernateDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String CODE = "code";

	protected void initDao() {
		// do nothing
	}

	public void save(PeMajor transientInstance) {
		log.debug("saving PeMajor instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed");
			throw re;
		}
	}

	public void delete(PeMajor persistentInstance) {
		log.debug("deleting PeMajor instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed");
			throw re;
		}
	}

	public PeMajor findById(java.lang.String id) {
		log.debug("getting PeMajor instance with id: " + id);
		try {
			PeMajor instance = (PeMajor) getHibernateTemplate().get(
					"com.whaty.platform.entity.bean.basic.PeMajor", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed");
			throw re;
		}
	}

	public List findByExample(PeMajor instance) {
		log.debug("finding PeMajor instance by example");
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
		log.debug("finding PeMajor instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from PeMajor as model where model."
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
		log.debug("finding all PeMajor instances");
		try {
			String queryString = "from PeMajor";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed");
			throw re;
		}
	}

	public PeMajor merge(PeMajor detachedInstance) {
		log.debug("merging PeMajor instance");
		try {
			PeMajor result = (PeMajor) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed");
			throw re;
		}
	}

	public void attachDirty(PeMajor instance) {
		log.debug("attaching dirty PeMajor instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed");
			throw re;
		}
	}

	public void attachClean(PeMajor instance) {
		log.debug("attaching clean PeMajor instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed");
			throw re;
		}
	}

	public static PeMajorHibernateDAO getFromApplicationContext(ApplicationContext ctx) {
		return (PeMajorHibernateDAO) ctx.getBean("PeMajorDAO");
	}
}