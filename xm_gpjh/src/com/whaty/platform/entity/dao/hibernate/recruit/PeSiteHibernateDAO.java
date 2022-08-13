package com.whaty.platform.entity.dao.hibernate.recruit;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.whaty.platform.entity.bean.PeSite;

/**
 * Data access object (DAO) for domain model class PeSite.
 * 
 * @see com.whaty.platform.entity.bean.basic.PeSite
 * @author MyEclipse Persistence Tools
 */

public class PeSiteHibernateDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(PeSiteHibernateDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String CODE = "code";
	public static final String FULLNAME = "fullname";
	public static final String MANAGER = "manager";
	public static final String PHONE = "phone";
	public static final String EMAIL = "email";
	public static final String LINKMAN = "linkman";
	public static final String LINK_PHONE = "linkPhone";
	public static final String LINK_EMAIL = "linkEmail";
	public static final String FAX = "fax";
	public static final String ZIP_CODE = "zipCode";
	public static final String ADDRESS = "address";
	public static final String PAL = "pal";
	public static final String PAL_MANAGER = "palManager";
	public static final String SITE_URL = "siteUrl";
	public static final String NOTE = "note";

	protected void initDao() {
		// do nothing
	}

	public void save(PeSite transientInstance) {
		log.debug("saving PeSite instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed");
			throw re;
		}
	}

	public void delete(PeSite persistentInstance) {
		log.debug("deleting PeSite instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed");
			throw re;
		}
	}

	public PeSite findById(java.lang.String id) {
		log.debug("getting PeSite instance with id: " + id);
		try {
			PeSite instance = (PeSite) getHibernateTemplate().get(
					"com.whaty.platform.entity.bean.basic.PeSite", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed");
			throw re;
		}
	}

	public List findByExample(PeSite instance) {
		log.debug("finding PeSite instance by example");
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
		log.debug("finding PeSite instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from PeSite as model where model."
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

	public List findByShortname(Object fullname) {
		return findByProperty(FULLNAME, fullname);
	}

	public List findByManager(Object manager) {
		return findByProperty(MANAGER, manager);
	}

	public List findByPhone(Object phone) {
		return findByProperty(PHONE, phone);
	}

	public List findByEmail(Object email) {
		return findByProperty(EMAIL, email);
	}

	public List findByLinkman(Object linkman) {
		return findByProperty(LINKMAN, linkman);
	}

	public List findByLinkPhone(Object linkPhone) {
		return findByProperty(LINK_PHONE, linkPhone);
	}

	public List findByLinkEmail(Object linkEmail) {
		return findByProperty(LINK_EMAIL, linkEmail);
	}

	public List findByFax(Object fax) {
		return findByProperty(FAX, fax);
	}

	public List findByZipCode(Object zipCode) {
		return findByProperty(ZIP_CODE, zipCode);
	}

	public List findByAddress(Object address) {
		return findByProperty(ADDRESS, address);
	}

	public List findByPal(Object pal) {
		return findByProperty(PAL, pal);
	}

	public List findByPalManager(Object palManager) {
		return findByProperty(PAL_MANAGER, palManager);
	}

	public List findBySiteUrl(Object siteUrl) {
		return findByProperty(SITE_URL, siteUrl);
	}

	public List findByNote(Object note) {
		return findByProperty(NOTE, note);
	}

	public List findAll() {
		log.debug("finding all PeSite instances");
		try {
			String queryString = "from PeSite";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed");
			throw re;
		}
	}

	public PeSite merge(PeSite detachedInstance) {
		log.debug("merging PeSite instance");
		try {
			PeSite result = (PeSite) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed");
			throw re;
		}
	}

	public void attachDirty(PeSite instance) {
		log.debug("attaching dirty PeSite instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed");
			throw re;
		}
	}

	public void attachClean(PeSite instance) {
		log.debug("attaching clean PeSite instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed");
			throw re;
		}
	}

	public static PeSiteHibernateDAO getFromApplicationContext(ApplicationContext ctx) {
		return (PeSiteHibernateDAO) ctx.getBean("PeSiteHibernateDAO");
	}
	public static void main(String[] args) {
		PeSite ps=new PeSite();
		ps.setId("1");
		ps.setName("name");
		ps.setCode("a");
		new PeSiteHibernateDAO().save(ps);
	}
}