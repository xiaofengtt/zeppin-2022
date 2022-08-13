package com.whaty.platform.entity.service.studentStatas;

import com.whaty.platform.entity.bean.PrStuChangeSchool;
import com.whaty.platform.entity.exception.EntityException;

public interface PrStuChangeSchoolService {
	
	/**
	 * 学籍异动（开除学籍_退学操作）
	 * @param prStuChangeSchool
	 * @return
	 * @throws EntityException
	 */
	public abstract PrStuChangeSchool save(PrStuChangeSchool prStuChangeSchool) throws EntityException;

	/**
	 * 单个取消学籍异动（开除学籍_退学操作）
	 * @param prStuChangeSchool
	 * @return
	 * @throws EntityException
	 */
	public abstract boolean delForCancel(PrStuChangeSchool prStuChangeSchool) throws EntityException;
	
	/**
	 * 多个取消学籍异动（开除学籍_退学操作）
	 * @param ids
	 * @return
	 * @throws EntityException
	 */
	public abstract int delForCancel(java.util.List ids) throws EntityException;
	
}
