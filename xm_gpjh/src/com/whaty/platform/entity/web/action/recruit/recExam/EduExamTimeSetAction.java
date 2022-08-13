package com.whaty.platform.entity.web.action.recruit.recExam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeRecruitplan;
import com.whaty.platform.entity.bean.PrRecExamCourseTime;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.recruit.recExam.PrRecExamStuCourseService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.Container;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

/**
 * 入学考试时间设置
 * 
 * @author 李冰
 * 
 */
public class EduExamTimeSetAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle("考试时间设置");
		this.getGridConfig().setCapability(true, true, true, true);
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("招生批次"),
				"peRecruitplan.name");
		this.getGridConfig().addColumn(this.getText("考试课程"),
				"peRecExamcourse.name");
		this.getGridConfig().addColumn(this.getText("考试开始时间"), "startDatetime");
		this.getGridConfig().addColumn(this.getText("考试结束时间"), "endDatetime");
	}


	/**
	 * 添加之前检查所添加时间是否合法
	 */
	public void checkBeforeAdd() throws EntityException {
		this.checkTime();
	}

	/**
	 * 修改之前检查所修改的时间是否合法
	 */
	public void checkBeforeUpdate() throws EntityException {
		this.checkTime();
	}

	/**
	 * 对于考试时间的检验
	 * 
	 * @throws EntityException
	 */
	private void checkTime() throws EntityException {
		Date start = this.getBean().getStartDatetime();
		Date end = this.getBean().getEndDatetime();
		if (!end.after(start))
			throw new EntityException("考试结束时间应晚于考试开始时间");
		DetachedCriteria dc = DetachedCriteria.forClass(PeRecruitplan.class);
		dc
				.add(Restrictions.eq("id", this.getBean().getPeRecruitplan()
						.getId()));
		List<PeRecruitplan> list = new ArrayList();
		list = this.getGeneralService().getList(dc);
		if (!(list.size() > 0))
			throw new EntityException("考试课程或招生批次错误");
		Date startPlan = list.get(0).getExamStartDate();
		Date endPlan = list.get(0).getExamEndDate();
		if (!start.after(startPlan))
			throw new EntityException("考试开始时间不能早于招生批次的考试开始时间");
		if (!Const.compareDate(end, endPlan))
			throw new EntityException("考试结束时间不能晚于招生批次的考试结束时间");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrRecExamCourseTime.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/eduExamTimeSet";
	}

	public void setBean(PrRecExamCourseTime instance) {
		super.superSetBean(instance);

	}

	public PrRecExamCourseTime getBean() {
		return (PrRecExamCourseTime) super.superGetBean();
	}

	public DetachedCriteria initDetachedCriteria() {
		JsonUtil.setDateformat("yyyy-MM-dd HH:mm:ss");
		DetachedCriteria dc = DetachedCriteria
				.forClass(PrRecExamCourseTime.class);
		dc.createCriteria("peRecruitplan", "peRecruitplan");
		dc.createCriteria("peRecExamcourse", "peRecExamcourse");
		return dc;
	}

}
