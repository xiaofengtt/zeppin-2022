package com.whaty.platform.entity.service.studentStatas;

import java.io.File;
import java.util.List;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.exception.EntityException;

public interface PeStudentSerive {

	/**
	 * 修改学生信息时，把页面获得的学生信息保存下来
	 * @param bean
	 * 			页面获得的学生信息
	 * @return
	 * 			保存后的数据库中的学生信息
	 * @throws EntityException
	 */
	public abstract PeStudent save(PeStudent bean) throws EntityException;
	
	/**
	 * 学生注册，注入学生学号，修改学生状态为在籍
	 * @param idsList
	 * 		所要注册的学生ID
	 * @return 操作信息
	 */
	public abstract String createRegister(List<String> idsList)throws EntityException;


	/**
	 * 取消学生注册，注销学生学号，修改学生状态为已经交费
	 * @param idsList
	 * 		所要取消注册的学生ID
	 * @return 操作信息
	 */
	public abstract String delRegister(List<String> idsList)throws EntityException;
	
	/**
	 * 保存毕业证编号学位证编号
	 * @param file
	 * @return
	 * @throws EntityException
	 */
	public int saveCertificateNo(File file) throws EntityException;
}