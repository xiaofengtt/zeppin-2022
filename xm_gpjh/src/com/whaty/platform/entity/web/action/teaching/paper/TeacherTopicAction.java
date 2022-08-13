package com.whaty.platform.entity.web.action.teaching.paper;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PrTchPaperTitle;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class TeacherTopicAction extends MyBaseAction<PrTchPaperTitle> {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("毕业论文题目查询"));
		this.getGridConfig().setCapability(false, false, false);

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学期"), "peSemester.name");
		this.getGridConfig().addColumn(this.getText("专业"), "peTeacher.peMajor.name");
		this.getGridConfig().addColumn(this.getText("教师名"), "peTeacher.name");
		this.getGridConfig().addColumn(this.getText("毕业论文题目"), "title");
		this.getGridConfig().addColumn(this.getText("题目最多选题人数"), "stuCountLimit", false, true, true,"regex:new RegExp(/^\\d{1,2}?$/),regexText:'人数输入格式：1到2位整数',");
		this.getGridConfig().addColumn(this.getText("题目备注"),"titleMemo");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchPaperTitle.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/teacherTopic";
	}

	public void setBean(PrTchPaperTitle instance) {
		super.superSetBean(instance);
	}

	public PrTchPaperTitle getBean() {
		return super.superGetBean();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchPaperTitle.class);
		dc.createCriteria("peTeacher","peTeacher").createAlias("peMajor", "peMajor");
		dc.createCriteria("peSemester","peSemester");
		dc.addOrder(Order.desc("peSemester.name"));
		return dc;
	}
}
