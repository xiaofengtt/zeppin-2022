package com.whaty.platform.entity.web.action.teaching.basicInfo;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeTeacher;
import com.whaty.platform.entity.bean.PrTchCourseTeacher;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class TeacherCourseAction extends PrTchCourseTeacherInAction {

	private static final long serialVersionUID = -4286731321440061326L;

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		
		String teacherName = ((PeTeacher)this.getMyListService().getById(PeTeacher.class, this.getBean().getPeTeacher().getId())).getTrueName();
		
		this.getGridConfig().setTitle(this.getText("教师"+teacherName+"的课程"));
		this.getGridConfig().addMenuScript(this.getText("test.back"), "{window.history.back();}");

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程编号"), "peTchCourse.code", true, true, true, "TextField", false, 30);
		this.getGridConfig().addColumn(this.getText("课程名称"), "peTchCourse.name", true, true, true, "TextField", false, 30);
		this.getGridConfig().addColumn(this.getText("责任类型"),"enumConstByFlagTeacherType.name");

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/teacherCourse";
		
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchCourseTeacher.class);
		dc.createCriteria("peTchCourse", "peTchCourse");
		dc.createCriteria("peTeacher","peTeacher");
		dc.createCriteria("enumConstByFlagTeacherType","enumConstByFlagTeacherType");
		return dc;
	}

}
