package com.whaty.platform.entity.web.action.exam.mainCourse;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeExamMaincourseRoom;
import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.web.action.MyBaseAction;
/**
 * 主干课程考试考场信息管理：
 * 添加、修改、删除、设定考试地点
 * @author Administrator
 *
 */
public class PeExamMaincourseRoomAction extends MainCourseBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText(this.getSemesterName() + "主干课程考试考场信息管理"));
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		this.getGridConfig().addColumn(this.getText("所属学习中心"),"peSite.name");
		this.getGridConfig().addColumn(this.getText("考场名称"),"name");
		this.getGridConfig().addColumn(this.getText("考场编号"),"code");
		this.getGridConfig().addColumn(this.getText("考试地点"),"examPlace");
		this.getGridConfig().addColumn(this.getText("监考人A"),"invigilatorA");
		this.getGridConfig().addColumn(this.getText("监考人B"),"invigilatorB");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeExamMaincourseRoom.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/exam/peExamMaincourseRoom";
	}

	public void setBean(PeExamMaincourseRoom instance){
		super.superSetBean(instance);
	}
	
	public PeExamMaincourseRoom getBean(){
		return (PeExamMaincourseRoom) super.superGetBean();
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeExamMaincourseRoom.class);
		dc.createCriteria("peSite", "peSite");
		return dc;
	}
}
