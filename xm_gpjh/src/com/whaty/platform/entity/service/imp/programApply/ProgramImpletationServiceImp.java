package com.whaty.platform.entity.service.imp.programApply;

import java.util.List;

import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.PeFeeActualBudget;
import com.whaty.platform.entity.bean.PeFeeActualBudgetDetail;
import com.whaty.platform.entity.bean.PeFeeBudget;
import com.whaty.platform.entity.bean.PeFeeBudgetDetail;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.imp.GeneralServiceImp;
import com.whaty.platform.entity.service.programApply.ProgramImpletationService;

public class ProgramImpletationServiceImp extends GeneralServiceImp implements ProgramImpletationService {

	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.imp.programApply.ProgramImpletation#save(com.whaty.platform.entity.bean.AbstractBean)
	 */
	@Override
	public PeFeeBudget save(AbstractBean transientInstance)
			throws EntityException {
		PeFeeBudget peFeeBudget = (PeFeeBudget)transientInstance;
		PeFeeBudgetDetail peFeeBudgetDetail = (PeFeeBudgetDetail) this.getGeneralDao().save(peFeeBudget.getPeFeeBudgetDetail());
		peFeeBudget.setPeFeeBudgetDetail(peFeeBudgetDetail);
		return (PeFeeBudget)this.getGeneralDao().save(peFeeBudget);
	}
	
	public PeFeeActualBudget savePeFeeActualBudget(AbstractBean transientInstance)
	   throws EntityException {
		PeFeeActualBudget peFeeBudget = (PeFeeActualBudget)transientInstance;
		PeFeeActualBudgetDetail peFeeBudgetDetail = (PeFeeActualBudgetDetail) this.getGeneralDao().save(peFeeBudget.getPeFeeActualBudgetDetail());
		peFeeBudget.setPeFeeActualBudgetDetail(peFeeBudgetDetail);
		return (PeFeeActualBudget)this.getGeneralDao().save(peFeeBudget);
	}

	@Override
	public GeneralDao getGeneralDao() {
		return super.getGeneralDao();
	}

	@Override
	public void setGeneralDao(GeneralDao generalDao) {
		super.setGeneralDao(generalDao);
	}
	
}
