package com.whaty.platform.entity.service.programJudge;

import com.whaty.platform.entity.bean.PeProApply;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;

public interface ProgramJudgmentService extends GeneralService<PeProApply>{

	public PeProApply saveMangerFirstCheck(PeProApply bean,boolean isForce)
		throws EntityException;
	
	public PeProApply saveMangerFinalCheck(PeProApply bean)
	throws EntityException;
	
	public String saveMangerForceCheck(String ids,String value) throws EntityException;
}