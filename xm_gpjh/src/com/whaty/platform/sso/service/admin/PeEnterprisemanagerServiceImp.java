package com.whaty.platform.sso.service.admin;

import java.util.List;

import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.imp.GeneralServiceImp;

import java.util.List;

import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PrBzzPriManagerEnterprise;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.util.RandomString;

/**
 * @param
 * @version 创建时间：2009-6-29 下午04:51:56
 * @return
 * @throws PlatformException
 * 类说明
 */
public class PeEnterprisemanagerServiceImp extends GeneralServiceImp {

	@Override
	public int deleteByIds(List ids) throws EntityException {
		// TODO Auto-generated method stub
		int i = 0;
		try{
			for (Object object : ids) {
				String id = (String)object;
				PeEnterpriseManager instance = (PeEnterpriseManager)this.getGeneralDao().getById(id);
				this.getGeneralDao().delete(instance);
				i++;
			}
		}catch(RuntimeException e){
			throw new EntityException(e);
		}
		return i;
	}

	@Override
	public AbstractBean save(AbstractBean transientInstance) throws EntityException {
		// TODO Auto-generated method stub
		PeEnterpriseManager managerInstance = null;
		PeEnterpriseManager instance = (PeEnterpriseManager)transientInstance;
		if(instance.getSsoUser() != null){
			instance.getSsoUser().setPePriRole(instance.getPePriRole());
			instance.getSsoUser().setLoginId(instance.getLoginId());
			instance.setName(instance.getName());
			try{
				managerInstance = (PeEnterpriseManager)this.getGeneralDao().save(instance);
			}catch(RuntimeException e){
				throw new EntityException(e);
			}
			return managerInstance;
		}
		try{
			SsoUser ssoUser = new SsoUser();
			ssoUser.setLoginId(instance.getLoginId());
			String pwsd = RandomString.getString(8);
			if(ssoUser.getPassword() == null)
			{
				//ssoUser.setPassword("1111");
				ssoUser.setPassword(pwsd);
			}
			ssoUser.setPePriRole(instance.getPePriRole());
			ssoUser.setEnumConstByFlagIsvalid(instance.getEnumConstByFlagIsvalid());
			SsoUser ssoUserInstance = (SsoUser)this.getGeneralDao().save(ssoUser);
			instance.setName(instance.getName());
			instance.setSsoUser(ssoUserInstance);
			managerInstance = (PeEnterpriseManager)this.getGeneralDao().save(instance);
			//设置企业权限默认为管理员所在企业
			if(managerInstance.getPeEnterprise() != null) {
				PrBzzPriManagerEnterprise managerSite = new PrBzzPriManagerEnterprise();
				managerSite.setPeEnterprise(managerInstance.getPeEnterprise());
				managerSite.setSsoUser(ssoUserInstance);
				managerSite = (PrBzzPriManagerEnterprise)this.getGeneralDao().save(managerSite);
			}
		}catch(RuntimeException e){
			throw new EntityException(e);
		}
		return managerInstance;
	}
		
}
