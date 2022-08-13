package com.whaty.platform.entity.service.studentStatas;

import com.whaty.platform.entity.bean.PrStuChangeEdutype;
import com.whaty.platform.entity.exception.EntityException;

public interface PeChangeTypeService {
	/**
	 * 学生转层次，同时修改学生学分标准
	 * @param prStuChangeSite
	 * @return
	 * @throws EntityException
	 */
	public abstract PrStuChangeEdutype save(PrStuChangeEdutype prStuChangeEdutype)throws EntityException;
}
