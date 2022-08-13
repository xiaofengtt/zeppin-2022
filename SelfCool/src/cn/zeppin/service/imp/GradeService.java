/**
 * Project Name:Self_Cool File Name:GradeService.java Package
 * Name:cn.zeppin.service.imp Copyright (c) 2014, Zeppin All Rights Reserved.
 * 
 */
package cn.zeppin.service.imp;

import cn.zeppin.dao.api.IGradeDAO;
import cn.zeppin.entity.Grade;
import cn.zeppin.service.api.IGradeService;

/**
 * ClassName: GradeService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年7月14日 下午7:33:47 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class GradeService implements IGradeService {

	private IGradeDAO gradeDAO;
	
	public IGradeDAO getGradeDAO() {
		return gradeDAO;
	}

	public void setGradeDAO(IGradeDAO gradeDAO) {
		this.gradeDAO = gradeDAO;
	}
	
	
	/**
	 * 递归得到学段全称
	 * 
	 * @author Clark
	 * @date: 2014年7月14日 下午7:32:20 <br/>
	 * @param grade
	 * @return
	 */
	@Override
	public String getGradeFullName(Grade grade) {
		// TODO Auto-generated method stub
		if (grade.getGrade() == null) {
			return grade.getName();
		} else {
			return getGradeFullName(grade.getGrade()) + ">>" + grade.getName();
		}
	}
	/**
	 * 根据 分类id来获取 分类
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:12:39 <br/>
	 * @param id
	 * @return
	 */
	@Override
	public Grade getGradeById(Integer id) {
		return this.getGradeDAO().get(id);
	}

}
