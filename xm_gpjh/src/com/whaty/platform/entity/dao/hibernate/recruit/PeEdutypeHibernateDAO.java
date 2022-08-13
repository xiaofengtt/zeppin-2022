package com.whaty.platform.entity.dao.hibernate.recruit;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.whaty.platform.entity.bean.PeEdutype;

/**
 * Data access object (DAO) for domain model class PeEdutype.
 * 
 * @see com.whaty.platform.entity.bean.basic.PeEdutype
 * @author MyEclipse Persistence Tools
 */

public class PeEdutypeHibernateDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(PeEdutypeHibernateDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String CODE = "code";

	protected void initDao() {
		// do nothing
	}

	public void save(PeEdutype transientInstance) {
		log.debug("saving PeEdutype instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed");
			throw re;
		}
	}

	public void delete(PeEdutype persistentInstance) {
		log.debug("deleting PeEdutype instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed");
			throw re;
		}
	}

	public PeEdutype findById(java.lang.String id) {
		log.debug("getting PeEdutype instance with id: " + id);
		try {
			PeEdutype instance = (PeEdutype) getHibernateTemplate().get(
					"com.whaty.platform.entity.bean.basic.PeEdutype", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed");
			throw re;
		}
	}

	public List findByExample(PeEdutype instance) {
		log.debug("finding PeEdutype instance by example");
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
		log.debug("finding PeEdutype instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from PeEdutype as model where model."
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
		log.debug("finding all PeEdutype instances");
		try {
			String queryString = "from PeEdutype";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed");
			throw re;
		}
	}

	public PeEdutype merge(PeEdutype detachedInstance) {
		log.debug("merging PeEdutype instance");
		try {
			PeEdutype result = (PeEdutype) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed");
			throw re;
		}
	}

	public void attachDirty(PeEdutype instance) {
		log.debug("attaching dirty PeEdutype instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed");
			throw re;
		}
	}

	public void attachClean(PeEdutype instance) {
		log.debug("attaching clean PeEdutype instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed");
			throw re;
		}
	}

	public static PeEdutypeHibernateDAO getFromApplicationContext(ApplicationContext ctx) {
		return (PeEdutypeHibernateDAO) ctx.getBean("PeEdutypeDAO");
	}
}