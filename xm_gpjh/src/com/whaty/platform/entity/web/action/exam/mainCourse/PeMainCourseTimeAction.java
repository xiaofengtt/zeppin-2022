package com.whaty.platform.entity.web.action.exam.mainCourse;

import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.basic.PeSemesterAction;
/**
 * 设置主干课程报名时间
 * @author Administrator
 *
 */
public class PeMainCourseTimeAction extends PeSemesterAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false,false,true);
		this.getGridConfig().setTitle(this.getText("主干课程报名时间设置"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("考试学期"), "name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("报名开始时间"), "mainCourseStartDate");
		this.getGridConfig().addColumn(this.getText("报名结束时间"), "mainCourseEndDate");
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/exam/peMainCourseTime";
	}
	
	
	public void checkBeforeUpdate() throws EntityException{
		if(this.getBean().getMainCourseStartDate().after(this.getBean().getMainCourseEndDate())){
			throw new EntityException("主干课程考试报名开始时间不能晚于结束时间");
		}
	}


}
