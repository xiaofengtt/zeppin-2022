package com.whaty.platform.entity.service.programApply;

import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.PeFeeActualBudget;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;

public interface ProgramImpletationService extends GeneralService{

	public abstract AbstractBean save(AbstractBean transientInstance)
			throws EntityException;

	public abstract PeFeeActualBudget savePeFeeActualBudget(AbstractBean transientInstance)
		throws EntityException;
}