package com.whaty.platform.entity.web.action.teaching.basicInfo;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeTchBook;
import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.PrTchCourseBook;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 课程管理
 * @author 赵玉晓
 *
 */
public class PrCourseTeacherAction extends MyBaseAction<PeTchCourse> {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("课程管理"));
		this.getGridConfig().setCapability(true, true, true, true, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程名称"), "name");
		this.getGridConfig().addColumn(this.getText("课程编码"),"code");
		this.getGridConfig().addColumn(this.getText("学分"),"credit");
		this.getGridConfig().addColumn(this.getText("是否有效"),"enumConstByFlagIsvalid.name");
		this.getGridConfig().addColumn(this.getText("备注"), "note",true,true,true,"TextField",true,225);
		this.getGridConfig().addRenderFunction(this.getText("查看本课程已分配教师"), "<a href=# onclick=window.open('/entity/teaching/peTchCourseAction_withInCourse.action?bean.peTrainingPlan.id=${value}','','left=500,top=300,resizable=yes,scrollbars=no,height=270,width=500,location=no')>"+this.getText("查看")+"</a>", "id");
		this.getGridConfig().addRenderFunction(this.getText("为本课程分配教师"), "<a href=# onclick=window.open('/entity/teaching/peTchCourseAction_withOutCourse.action?bean.peTrainingPlan.id=${value}','','left=500,top=300,resizable=yes,scrollbars=no,height=270,width=500,location=no')>"+this.getText("查看")+"</a>", "id");
	}

	public String withInCourse(){
		return "";
	}
	public String withOutCourse(){
		return "";
	}
	@Override
	public void setEntityClass() {
		this.entityClass = PeTchCourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/peTchCourseAction";
	}
	public void setBean(PeTchCourse instance) {
		super.superSetBean(instance);
	}

	public PeTchCourse getBean() {
		return super.superGetBean();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeTchCourse.class);
		dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
//		dc.createCriteria("peTchBook", "peTchBook");
		return dc;
	}
}
