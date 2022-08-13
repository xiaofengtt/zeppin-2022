package com.whaty.platform.sso.service.admin;

import com.whaty.platform.entity.bean.PeEdutype;
import com.whaty.platform.entity.bean.PeGrade;
import com.whaty.platform.entity.bean.PeMajor;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.bean.PrPriManagerEdutype;
import com.whaty.platform.entity.bean.PrPriManagerGrade;
import com.whaty.platform.entity.bean.PrPriManagerMajor;
import com.whaty.platform.entity.bean.PrPriManagerSite;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.imp.GeneralServiceImp;

public class PrPriRangeRightServiceImp extends GeneralServiceImp implements PrPriRangeRightService{
	
	public boolean updateRangeRight(String[] major, String[] grade, String[] edutype, String[] site, SsoUser ssoUser) throws EntityException{
		
		String hql1 = "delete from PrPriManagerSite where ssoUser.id='"+ssoUser.getId()+"'";
		String hql2 = "delete from PrPriManagerMajor where ssoUser.id='"+ssoUser.getId()+"'";
		String hql3 = "delete from PrPriManagerEdutype where ssoUser.id='"+ssoUser.getId()+"'";
		String hql4 = "delete from PrPriManagerGrade where ssoUser.id='"+ssoUser.getId()+"'";
		try{
			this.getGeneralDao().executeByHQL(hql1);
			this.getGeneralDao().executeByHQL(hql2);
			this.getGeneralDao().executeByHQL(hql3);
			this.getGeneralDao().executeByHQL(hql4);
		}catch(RuntimeException e){
			throw new EntityException(e);
		}
		
		if(major != null){
			for (String string : major) {
				PrPriManagerMajor instance = new PrPriManagerMajor();
				PeMajor peMajor = new PeMajor();
				peMajor.setId(string);
				instance.setSsoUser(ssoUser);
				instance.setPeMajor(peMajor);
				try{
					this.getGeneralDao().save(instance);
				}catch(RuntimeException e){
					throw new EntityException(e);
				}
			}
		}
		
		if(grade != null){
			for (String string : grade) {
				PrPriManagerGrade instance = new PrPriManagerGrade();
				PeGrade peGrade = new PeGrade();
				peGrade.setId(string);
				instance.setSsoUser(ssoUser);
				instance.setPeGrade(peGrade);
				try{
					this.getGeneralDao().save(instance);
				}catch(RuntimeException e){
					throw new EntityException(e);
				}
			}
		}
		
		if(edutype != null){
			for (String string : edutype) {
				PrPriManagerEdutype instance = new PrPriManagerEdutype();
				PeEdutype peEdutype = new PeEdutype();
				peEdutype.setId(string);
				instance.setSsoUser(ssoUser);
				instance.setPeEdutype(peEdutype);
				try{
					this.getGeneralDao().save(instance);
				}catch(RuntimeException e){
					throw new EntityException(e);
				}
			}
		}
		
		if(site != null){
			for (String string : site) {
				PrPriManagerSite instance = new PrPriManagerSite();
				PeSite peSite = new PeSite();
				peSite.setId(string);
				instance.setSsoUser(ssoUser);
				instance.setPeSite(peSite);
				try{
					this.getGeneralDao().save(instance);
				}catch(RuntimeException e){
					throw new EntityException(e);
				}
			}
		}

		return true;
	}

}
