package com.whaty.platform.entity.service.studentStatas;

import com.whaty.platform.entity.bean.PrStuChangeSite;
import com.whaty.platform.entity.exception.EntityException;

public interface PeChangeSiteService {
	
	/**
	 * 学生转学习中心，同时修改学生学分标准
	 * @param prStuChangeSite
	 * @return
	 * @throws EntityException
	 */
	public abstract PrStuChangeSite save(PrStuChangeSite prStuChangeSite)throws EntityException;
	
}
