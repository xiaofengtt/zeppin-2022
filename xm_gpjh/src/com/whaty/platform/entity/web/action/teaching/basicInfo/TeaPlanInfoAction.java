package com.whaty.platform.entity.web.action.teaching.basicInfo;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeTchProgram;
import com.whaty.platform.entity.bean.PeTchProgramGroup;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class TeaPlanInfoAction extends MyBaseAction<PeTchProgramGroup> {

	private static final long serialVersionUID = 8712710413711449934L;

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, true);
		
		String tchProgramName = ((PeTchProgram)this.getMyListService().getById(PeTchProgram.class, this.getBean().getPeTchProgram().getId())).getName();
		
		this.getGridConfig().setTitle(this.getText(tchProgramName + "的课程类型列表"));
		this.getGridConfig().addMenuScript(this.getText("test.back"), "{window.history.back();}");
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("类型名称"), "peTchCoursegroup.name", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("最低学分要求"), "minCredit", true, true, true,"regex:new RegExp(/^\\d{1,3}(\\.\\d{0,1})?$/),regexText:'学分输入格式：1至3位整数 0到1位小数',");
		this.getGridConfig().addColumn(this.getText("最高学分限制"), "maxCredit", true, true, true,"regex:new RegExp(/^\\d{1,3}(\\.\\d{0,1})?$/),regexText:'学分输入格式：1至3位整数 0到1位小数',");
		this.getGridConfig().addRenderFunction(this.getText("课程信息"), "<a href=\"teachPlanInfoCourse.action?bean.peTchProgramGroup.id=${value}\" target='_self'>查看课程</a>","id");

	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeTchProgramGroup.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/teachPlanInfo";
		
	}

	public void setBean(PeTchProgramGroup instance) {
		super.superSetBean(instance);
	}
	
	public PeTchProgramGroup getBean() {
		return super.superGetBean();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeTchProgramGroup.class);
		if (null != this.getBean().getPeTchCoursegroup()) {
			dc.add(Restrictions.eq("peTchProgram.id", this.getBean().getPeTchProgram().getId()));
		}
		dc.createCriteria("peTchCoursegroup","peTchCoursegroup");
		dc.createCriteria("peTchProgram","peTchProgram");
		return dc;
	}
	@Override
	public void checkBeforeUpdate() throws EntityException {
		if (this.getBean().getMinCredit() > this.getBean().getMaxCredit()) {
			throw new EntityException("最小学分不能大于最大学分");
		}
		super.checkBeforeUpdate();
	}

}
