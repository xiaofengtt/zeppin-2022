package com.whaty.platform.sso.web.action.admin;

import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeEdutype;
import com.whaty.platform.entity.bean.PeGrade;
import com.whaty.platform.entity.bean.PeMajor;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.bean.PrPriManagerEdutype;
import com.whaty.platform.entity.bean.PrPriManagerGrade;
import com.whaty.platform.entity.bean.PrPriManagerMajor;
import com.whaty.platform.entity.bean.PrPriManagerSite;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.web.action.EntityBaseAction;
import com.whaty.platform.sso.service.admin.PrPriRangeRightService;

public class ManagerRangeRightAction extends EntityBaseAction{
	
	private String managerId;
	
	private String managerType;
	
	private SsoUser ssoUser;
	
	private String[] site;
	
	private String[] major;
	
	private String[] edutype;
	
	private String[] grade;
	
	private PrPriRangeRightService prPriRangeRightService;
	
	public List<PeGrade> getGrades() {
		List<PeGrade> list = null;
		DetachedCriteria dcSite = DetachedCriteria.forClass(PeGrade.class);
		try {
			list = getPrPriRangeRightService().getList(dcSite);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return list;
	}


	public List<PeMajor> getMajors() {
		List<PeMajor> list = null;
		DetachedCriteria dcSite = DetachedCriteria.forClass(PeMajor.class);
		try {
			list = getPrPriRangeRightService().getList(dcSite);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return list;
	}

	
	public List<PeEdutype> getEdutypes() {
		List<PeEdutype> list = null;
		DetachedCriteria dcSite = DetachedCriteria.forClass(PeEdutype.class);
		try {
			list = getPrPriRangeRightService().getList(dcSite);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return list;
	}

	
	public List<PeSite> getSites() {
		List<PeSite> list = null;
		DetachedCriteria dcSite = DetachedCriteria.forClass(PeSite.class);
		try {
			list = getPrPriRangeRightService().getList(dcSite);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return list;
	}

	
	public PrPriRangeRightService getPrPriRangeRightService() {
		return prPriRangeRightService;
	}

	public void setPrPriRangeRightService(PrPriRangeRightService prPriRangeRightService) {
		this.prPriRangeRightService = prPriRangeRightService;
	}

	public String showRangeRight(){
		try{
			if("3".equals(managerType)){
				getPrPriRangeRightService().getGeneralDao().setEntityClass(PeManager.class);
				ssoUser = ((PeManager)getPrPriRangeRightService().getById(managerId)).getSsoUser();
			}else if("2".equals(managerType)){
				getPrPriRangeRightService().getGeneralDao().setEntityClass(PeSitemanager.class);
				ssoUser = ((PeSitemanager)getPrPriRangeRightService().getById(managerId)).getSsoUser();
			}
			}catch(EntityException e){
			e.printStackTrace();
			
		}
		return "show_range_right";
	}
	
	public String updateRangeRight(){
		
		this.getPrPriRangeRightService().getGeneralDao().setEntityClass(PeManager.class);
		try{
			this.getPrPriRangeRightService().updateRangeRight(major, grade, edutype, site, ssoUser);
		}catch(EntityException e){
			e.printStackTrace();
		}
		return "show_update_range_right";
	}
	
	public boolean checkeSite(String id){
		boolean checked = false;
		Set set = ssoUser.getPrPriManagerSites();
		for (Object object : set) {
			if(((PrPriManagerSite)object).getPeSite().getId().equals(id)){
				checked = true;
				break;
			}
		}
		return checked;
	}
	
	public boolean checkeGrade(String id){
		boolean checked = false;
		Set set = ssoUser.getPrPriManagerGrades();
		for (Object object : set) {
			if(((PrPriManagerGrade)object).getPeGrade().getId().equals(id)){
				checked = true;
				break;
			}
		}
		return checked;
	}
	
	public boolean checkeMajor(String id){
		boolean checked = false;
		Set set = ssoUser.getPrPriManagerMajors();
		for (Object object : set) {
			if(((PrPriManagerMajor)object).getPeMajor().getId().equals(id)){
				checked = true;
				break;
			}
		}
		return checked;
	}
	
	public boolean checkeEdutype(String id){
		boolean checked = false;
		Set set = ssoUser.getPrPriManagerEdutypes();
		for (Object object : set) {
			if(((PrPriManagerEdutype)object).getPeEdutype().getId().equals(id)){
				checked = true;
				break;
			}
		}
		return checked;
	}

	public String[] getSite() {
		return site;
	}

	public void setSite(String[] site) {
		this.site = site;
	}

	public String[] getMajor() {
		return major;
	}

	public void setMajor(String[] major) {
		this.major = major;
	}

	public String[] getEdutype() {
		return edutype;
	}

	public void setEdutype(String[] edutype) {
		this.edutype = edutype;
	}

	public String[] getGrade() {
		return grade;
	}

	public void setGrade(String[] grade) {
		this.grade = grade;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getManagerType() {
		return managerType;
	}

	public void setManagerType(String managerType) {
		this.managerType = managerType;
	}


	public SsoUser getSsoUser() {
		return ssoUser;
	}


	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}

	
}
