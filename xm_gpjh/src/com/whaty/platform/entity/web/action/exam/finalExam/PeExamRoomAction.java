package com.whaty.platform.entity.web.action.exam.finalExam;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeExamRoom;
import com.whaty.platform.entity.web.action.MyBaseAction;
/**
 * 考场管理，可添加删除考场
 * 添加、修改、删除监考人员
 * @author zqf
 *
 */
public class PeExamRoomAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("考场管理"));
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		this.getGridConfig().addColumn(this.getText("所属考试场次"),"peExamNo.name");
		this.getGridConfig().addColumn(this.getText("所属学习中心"),"peSite.name");		
		this.getGridConfig().addColumn(this.getText("考场名称"),"name");
		this.getGridConfig().addColumn(this.getText("考场编号"),"code");
		this.getGridConfig().addColumn(this.getText("教室"),"classroom");
		this.getGridConfig().addColumn(this.getText("监考人员A"),"invigilatorA");
		this.getGridConfig().addColumn(this.getText("监考人员B"),"invigilatorB");
	}
	public void setBean(PeExamRoom instance){
		this.superSetBean(instance);
	}
	
	public PeExamRoom getBean(){
		return (PeExamRoom)this.superGetBean();
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PeExamRoom.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/exam/peExamRoom";
	}
	
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeExamRoom.class);
		dc.createCriteria("peExamNo", "peExamNo");
		dc.createCriteria("peSite", "peSite", DetachedCriteria.LEFT_JOIN);
		return dc;
	}
	

}
