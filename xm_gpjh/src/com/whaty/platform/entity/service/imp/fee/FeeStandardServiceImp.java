package com.whaty.platform.entity.service.imp.fee;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeFeeLevel;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.fee.FeeStandardService;

public class FeeStandardServiceImp implements FeeStandardService {
	private GeneralDao generalDao;

	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.fee.FeeStandardService#updateSetDefault(com.whaty.platform.entity.bean.PeFeeLevel, com.whaty.platform.entity.bean.PeFeeLevel)
	 */
	public void updateSetDefault(java.util.List ids) throws EntityException{
		try{
			com.whaty.platform.entity.bean.EnumConst enumConstStandard = this.getGeneralDao().getEnumConstByNamespaceCode("FlagDefault", "1");
			com.whaty.platform.entity.bean.EnumConst enumConsNo = this.getGeneralDao().getEnumConstByNamespaceCode("FlagDefault", "0");
			DetachedCriteria dc = DetachedCriteria.forClass(PeFeeLevel.class);
			dc.createAlias("enumConstByFlagDefault", "enumConstByFlagDefault")
				.add(Restrictions.eq("enumConstByFlagDefault.code", "1"));
			java.util.List oldStandards = this.getGeneralDao().getList(dc);
			for(Object o:oldStandards){
				PeFeeLevel peFeeLevel = (PeFeeLevel)o;
				peFeeLevel.setEnumConstByFlagDefault(enumConsNo);
				this.getGeneralDao().save(peFeeLevel);				
			}
			for(Object id:ids){
				PeFeeLevel peFeeLevel = (PeFeeLevel)this.getGeneralDao().getById(id.toString());
				if(peFeeLevel.getEnumConstByFlagDefault().equals(enumConstStandard)){
					throw new EntityException("标准已经是默认标准了，不用设置！");
				}else{
					peFeeLevel.setEnumConstByFlagDefault(enumConstStandard);
					this.getGeneralDao().save(peFeeLevel);
				}
			}
			
		}catch(RuntimeException e){
			throw new EntityException(e.getMessage());
		}
	}
	
	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}
}
