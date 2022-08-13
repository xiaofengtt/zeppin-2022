package com.whaty.platform.entity.web.action.recruit.recExam;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeRecExamcourse;
import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 入学考试课程设置
 * 
 * @author 李冰
 * 
 */
public class ExamCourseSetAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("入学考试课程设置"));
		this.getGridConfig().addColumn(this.getText("id"), "id",false);
		this.getGridConfig().addColumn(this.getText("课程名称"), "name");
		this.getGridConfig().addColumn(this.getText("是否是排考场课程"), "enumConstByFlagArrangeRoom.name");
		this.getGridConfig().addColumn(this.getText("备注"), "note", true, true,
				true, "TextArea", true, 1000);
	}


	@Override
	public void setEntityClass() {
		this.entityClass = PeRecExamcourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/examcourseset";
	}

	public void setBean(PeRecExamcourse instance) {
		super.superSetBean(instance);

	}

	public PeRecExamcourse getBean() {
		return (PeRecExamcourse) super.superGetBean();
	}
	
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeRecExamcourse.class);
		dc.createCriteria("enumConstByFlagArrangeRoom", "enumConstByFlagArrangeRoom",
				DetachedCriteria.LEFT_JOIN);
		return dc;
	}
}
