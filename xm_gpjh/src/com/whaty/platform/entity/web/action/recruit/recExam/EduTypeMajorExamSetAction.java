package com.whaty.platform.entity.web.action.recruit.recExam;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PrRecCourseMajorEdutype;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 层次专业考试课程关系设置
 * 
 * @author 李冰
 * 
 */
public class EduTypeMajorExamSetAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("层次专业考试课程关系设置"));
		this.getGridConfig().setCapability(true, true, false);

		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("层次名称"), "peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业名称"), "peMajor.name");
		this.getGridConfig().addColumn(this.getText("课程名"),
				"peRecExamcourse.name");
	}

	/**
	 * 添加之前检查所添加的关系在数据库表中是否已经存在，如果存在则无法添加
	 */
	public void checkBeforeAdd() throws EntityException {
		DetachedCriteria dc = DetachedCriteria
				.forClass(PrRecCourseMajorEdutype.class);
		DetachedCriteria dcPeEdutype = dc.createCriteria("peEdutype",
				"peEdutype");
		DetachedCriteria dcPeMajor = dc.createCriteria("peMajor", "peMajor");
		DetachedCriteria dcRecExamcourse = dc.createCriteria("peRecExamcourse",
				"peRecExamcourse");
		dcPeEdutype.add(Restrictions.eq("name", this.getBean().getPeEdutype()
				.getName()));
		dcPeMajor.add(Restrictions.eq("name", this.getBean().getPeMajor()
				.getName()));
		dcRecExamcourse.add(Restrictions.eq("name", this.getBean()
				.getPeRecExamcourse().getName()));
		List<PrRecCourseMajorEdutype> list = new ArrayList();
		list = this.getGeneralService().getList(dc);
		if (list.size() > 0) {
			throw new EntityException("所添加的层次专业考试课程关系已经存在，无法重复添加");
		}
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrRecCourseMajorEdutype.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/eduTypeMajorExamSet";
	}

	public void setBean(PrRecCourseMajorEdutype instance) {
		super.superSetBean(instance);

	}

	public PrRecCourseMajorEdutype getBean() {
		return (PrRecCourseMajorEdutype) super.superGetBean();
	}

	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria
				.forClass(PrRecCourseMajorEdutype.class);
		dc.createCriteria("peEdutype", "peEdutype");
		dc.createCriteria("peMajor", "peMajor");
		dc.createCriteria("peRecExamcourse", "peRecExamcourse");
		return dc;
	}

}
