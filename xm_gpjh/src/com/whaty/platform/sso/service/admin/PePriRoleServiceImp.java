package com.whaty.platform.sso.service.admin;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.PrPriRole;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.imp.GeneralServiceImp;

public class PePriRoleServiceImp extends GeneralServiceImp {

	@Override
	public int deleteByIds(List ids) throws EntityException {
		
		int i = 0;
		try{
			for (Object object : ids) {
				String id = (String)object;
				PePriRole instance = (PePriRole)this.getGeneralDao().getById(id);
				this.getGeneralDao().delete(instance);
				i++;
			}
		}catch(RuntimeException e){
			throw new EntityException(e);
		}
		return i;
	}

	@Override
	public AbstractBean save(AbstractBean transientInstance)
			throws EntityException {
		
		PePriRole instance = (PePriRole)transientInstance;
		
		String id = transientInstance.getId();
		try{
			if(instance.getPrPriRoles() != null && !instance.getPrPriRoles().isEmpty()){
				for (Object obj : instance.getPrPriRoles()) {
					this.getGeneralDao().delete((PrPriRole)obj);
				}
			}
			
			DetachedCriteria dc1 = DetachedCriteria.forClass(PrPriRole.class);
			dc1.createCriteria("pePriRole", "pePriRole").createAlias("enumConstByFlagRoleType", "enumConstByFlagRoleType");
			dc1.add(Restrictions.eq("enumConstByFlagRoleType.id", instance.getEnumConstByFlagRoleType().getId()));
			List<PrPriRole> prPriRoles = this.getGeneralDao().getList(dc1);
			
			if(prPriRoles == null || prPriRoles.isEmpty()){
				throw new EntityException("您第一次添加"+instance.getEnumConstByFlagRoleType().getId()+"类型角色，请联系数据库管理员");
			}
			
			DetachedCriteria dc = DetachedCriteria.forClass(PrPriRole.class);
			dc.createCriteria("pePriRole", "pePriRole").createAlias("enumConstByFlagRoleType", "enumConstByFlagRoleType");
			dc.createAlias("pePriority", "pePriority");
			dc.add(Restrictions.eq("pePriRole.id", prPriRoles.get(0).getPePriRole().getId()));
			List<PrPriRole> prPriRoless = this.getGeneralDao().getList(dc);
			
			instance = (PePriRole)this.getGeneralDao().save(transientInstance);
			
			for (PrPriRole prPriRole : prPriRoless) {
				PrPriRole prPriRoleInstance = new PrPriRole();
				prPriRoleInstance.setPePriority(prPriRole.getPePriority());
				prPriRoleInstance.setPePriRole(instance);
				prPriRoleInstance.setFlagIsvalid("1");
				this.getGeneralDao().save(prPriRoleInstance);
			}
			
			
		}catch(RuntimeException e){
			throw new EntityException(e);
		}
		return instance;
	}
	
	

}
