package com.whaty.platform.entity.service.imp.basic;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeEdutype;
import com.whaty.platform.entity.bean.PeFeeLevel;
import com.whaty.platform.entity.bean.PeMajor;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.bean.PrEduMajorSiteFeeLevel;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.PrEduMajorSiteFeeLevelService;

public class PrEduMajorSiteFeeLevelServiceImp implements
		PrEduMajorSiteFeeLevelService {
	private GeneralDao generalDao;


	public void saveEdutype(PeEdutype peEdutype) throws EntityException {
		peEdutype = (PeEdutype) this.getGeneralDao().save(peEdutype);

		DetachedCriteria dcPeSite = DetachedCriteria
				.forClass(PeSite.class);
		List<PeSite> peSiteList = this.getGeneralDao().getList(
				dcPeSite);

		DetachedCriteria dcPeMajor = DetachedCriteria.forClass(PeMajor.class);
		List<PeMajor> peMajorList = this.getGeneralDao().getList(dcPeMajor);

		if (peSiteList.size() > 0 && peMajorList.size() > 0) {

			DetachedCriteria dcPeFeeLevel = DetachedCriteria
					.forClass(PeFeeLevel.class);
			dcPeFeeLevel.createCriteria("enumConstByFlagDefault",
					"enumConstByFlagDefault").add(Restrictions.eq("code", "1"));
			List<PeFeeLevel> peFeeLevelList = this.getGeneralDao().getList(
					dcPeFeeLevel);
			if (peFeeLevelList.size() == 0) {
				throw new EntityException("尚未设置默认收费标准，请设置默认收费标准后再添加层次!");
			}
			PeFeeLevel peFeeLevel = peFeeLevelList.get(0);

			/**
			 * 遍历层次和专业，设置关系表。并添加学习中心。
			 */
			for (PeSite peSite : peSiteList) {
				for (PeMajor peMajor : peMajorList) {

					PrEduMajorSiteFeeLevel prEduMajorSiteFeeLevel = new PrEduMajorSiteFeeLevel();
					prEduMajorSiteFeeLevel.setPeEdutype(peEdutype);
					prEduMajorSiteFeeLevel.setPeFeeLevel(peFeeLevel);
					prEduMajorSiteFeeLevel.setPeMajor(peMajor);
					prEduMajorSiteFeeLevel.setPeSite(peSite);
					this.getGeneralDao().save(prEduMajorSiteFeeLevel);

				}
			}
		}
	}


	public void saveMajor(PeMajor peMajor) throws EntityException {
		peMajor = (PeMajor) this.getGeneralDao().save(peMajor);

		DetachedCriteria dcPeEdutype = DetachedCriteria
				.forClass(PeEdutype.class);
		List<PeEdutype> peEdutypeList = this.getGeneralDao().getList(
				dcPeEdutype);

		DetachedCriteria dcPeSite = DetachedCriteria.forClass(PeSite.class);
		List<PeSite> peSiteList = this.getGeneralDao().getList(dcPeSite);

		if (peEdutypeList.size() > 0 && peSiteList.size() > 0) {

			DetachedCriteria dcPeFeeLevel = DetachedCriteria
					.forClass(PeFeeLevel.class);
			dcPeFeeLevel.createCriteria("enumConstByFlagDefault",
					"enumConstByFlagDefault").add(Restrictions.eq("code", "1"));
			List<PeFeeLevel> peFeeLevelList = this.getGeneralDao().getList(
					dcPeFeeLevel);
			if (peFeeLevelList.size() == 0) {
				throw new EntityException("尚未设置默认收费标准，请设置默认收费标准后再添加专业!");
			}
			PeFeeLevel peFeeLevel = peFeeLevelList.get(0);

			/**
			 * 遍历层次和专业，设置关系表。并添加学习中心。
			 */
			for (PeEdutype peEdutype : peEdutypeList) {
				for (PeSite peSite : peSiteList) {

					PrEduMajorSiteFeeLevel prEduMajorSiteFeeLevel = new PrEduMajorSiteFeeLevel();
					prEduMajorSiteFeeLevel.setPeEdutype(peEdutype);
					prEduMajorSiteFeeLevel.setPeFeeLevel(peFeeLevel);
					prEduMajorSiteFeeLevel.setPeMajor(peMajor);
					prEduMajorSiteFeeLevel.setPeSite(peSite);
					this.getGeneralDao().save(prEduMajorSiteFeeLevel);

				}
			}
		}
	}


	public void saveSite(PeSite peSite) throws EntityException {
		peSite = (PeSite) this.getGeneralDao().save(peSite);

		DetachedCriteria dcPeEdutype = DetachedCriteria
				.forClass(PeEdutype.class);
		List<PeEdutype> peEdutypeList = this.getGeneralDao().getList(
				dcPeEdutype);

		DetachedCriteria dcPeMajor = DetachedCriteria.forClass(PeMajor.class);
		List<PeMajor> peMajorList = this.getGeneralDao().getList(dcPeMajor);

		if (peEdutypeList.size() > 0 && peMajorList.size() > 0) {

			DetachedCriteria dcPeFeeLevel = DetachedCriteria
					.forClass(PeFeeLevel.class);
			dcPeFeeLevel.createCriteria("enumConstByFlagDefault",
					"enumConstByFlagDefault").add(Restrictions.eq("code", "1"));
			List<PeFeeLevel> peFeeLevelList = this.getGeneralDao().getList(
					dcPeFeeLevel);
			if (peFeeLevelList.size() == 0) {
				throw new EntityException("尚未设置默认收费标准，请设置默认收费标准后再添加学习中心!");
			}
			PeFeeLevel peFeeLevel = peFeeLevelList.get(0);

			/**
			 * 遍历层次和专业，设置关系表。并添加学习中心。
			 */
			for (PeEdutype peEdutype : peEdutypeList) {
				for (PeMajor peMajor : peMajorList) {

					PrEduMajorSiteFeeLevel prEduMajorSiteFeeLevel = new PrEduMajorSiteFeeLevel();
					prEduMajorSiteFeeLevel.setPeEdutype(peEdutype);
					prEduMajorSiteFeeLevel.setPeFeeLevel(peFeeLevel);
					prEduMajorSiteFeeLevel.setPeMajor(peMajor);
					prEduMajorSiteFeeLevel.setPeSite(peSite);
					this.getGeneralDao().save(prEduMajorSiteFeeLevel);

				}
			}
		}
	}

	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

}
