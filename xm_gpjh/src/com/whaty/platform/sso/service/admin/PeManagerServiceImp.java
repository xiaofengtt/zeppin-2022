package com.whaty.platform.sso.service.admin;

import java.util.List;

import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.service.imp.GeneralServiceImp;

public class PeManagerServiceImp extends GeneralServiceImp {

	@Override
	public int deleteByIds(List ids) throws EntityException {
		int i = 0;
		try{
			for (Object object : ids) {
				String id = (String)object;
				PeManager instance = (PeManager)this.getGeneralDao().getById(id);
				SsoUser user = instance.getSsoUser();
				this.getGeneralDao().delete(instance);
				if(user!=null){
					this.getGeneralDao().delete(user);
				}
				i++;
			}
		}catch(RuntimeException e){
			throw new EntityException(e);
		}
		return i;
	}

	@Override
	public PeManager save(AbstractBean transientInstance) throws EntityException {
		PeManager managerInstance = null;
		PeManager instance = (PeManager)transientInstance;
		if(instance.getSsoUser() == null){
			try{
				managerInstance = (PeManager)this.getGeneralDao().save(instance);
			}catch(RuntimeException e){
				throw new EntityException(e);
			}
			return managerInstance;
		}
		try{
			SsoUser ssoUser = instance.getSsoUser();
//			ssoUser.setLoginId(instance.getLoginId());
			SsoUser ssoUserInstance = (SsoUser)this.getGeneralDao().save(ssoUser);
			instance.setSsoUser(ssoUserInstance);
			managerInstance = (PeManager)this.getGeneralDao().save(instance);
		}catch(RuntimeException e){
			throw new EntityException(e);
		}
		return managerInstance;
	}
	
	

}
