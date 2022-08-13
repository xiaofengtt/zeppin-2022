package com.whaty.platform.sso.service.admin;

import java.util.List;

import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.bean.PrPriManagerSite;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.imp.GeneralServiceImp;

public class PeSitemanagerServiceImp extends GeneralServiceImp {
	
	@Override
	public int deleteByIds(List ids) throws EntityException {
		int i = 0;
		try{
			for (Object object : ids) {
				String id = (String)object;
				PeSitemanager instance = (PeSitemanager)this.getGeneralDao().getById(id);
				this.getGeneralDao().delete(instance);
				i++;
			}
		}catch(RuntimeException e){
			throw new EntityException(e);
		}
		return i;
	}

	@Override
	public PeSitemanager save(AbstractBean transientInstance) throws EntityException {
		PeSitemanager managerInstance = null;
		PeSitemanager instance = (PeSitemanager)transientInstance;
		if(instance.getSsoUser() != null){
			instance.getSsoUser().setPePriRole(instance.getPePriRole());
			instance.getSsoUser().setLoginId(instance.getLoginId());
			instance.setName(instance.getLoginId()+"/"+instance.getTrueName());
			try{
				managerInstance = (PeSitemanager)this.getGeneralDao().save(instance);
			}catch(RuntimeException e){
				throw new EntityException(e);
			}
			return managerInstance;
		}
		try{
			SsoUser ssoUser = new SsoUser();
			ssoUser.setLoginId(instance.getLoginId());
			if(ssoUser.getPassword() == null)
				ssoUser.setPassword("1111");
			ssoUser.setPePriRole(instance.getPePriRole());
			SsoUser ssoUserInstance = (SsoUser)this.getGeneralDao().save(ssoUser);
			instance.setName(instance.getLoginId()+"/"+instance.getTrueName());
			instance.setSsoUser(ssoUserInstance);
			managerInstance = (PeSitemanager)this.getGeneralDao().save(instance);
			//设置站点权限默认为本学习中心
			if(managerInstance.getPeSite() != null) {
				PrPriManagerSite managerSite = new PrPriManagerSite();
				managerSite.setPeSite(managerInstance.getPeSite());
				managerSite.setSsoUser(ssoUserInstance);
				managerSite = (PrPriManagerSite)this.getGeneralDao().save(managerSite);
			}
		}catch(RuntimeException e){
			throw new EntityException(e);
		}
		return managerInstance;
	}
	

}
