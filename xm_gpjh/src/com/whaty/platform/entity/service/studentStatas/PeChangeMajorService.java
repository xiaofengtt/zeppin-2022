package com.whaty.platform.entity.service.studentStatas;

import com.whaty.platform.entity.bean.PrStuChangeMajor;
import com.whaty.platform.entity.exception.EntityException;

public interface PeChangeMajorService {
	/**
	 * 学生转专业，同时修改学生学分标准
	 * @param prStuChangeSite
	 * @return
	 * @throws EntityException
	 */
	public abstract PrStuChangeMajor save(PrStuChangeMajor prStuChangeMajor)throws EntityException;
}
