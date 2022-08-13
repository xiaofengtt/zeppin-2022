package com.whaty.platform.entity.web.action.teaching.result;

import java.io.File;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.examScore.ExamScoreBatchService;
import com.whaty.platform.entity.service.teaching.examScore.ExamScoreComposeService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

public class UsualScoreAction extends MyBaseAction<PrTchStuElective> {
	ExamScoreBatchService examScoreBatchService;
	private File upload;
	
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, true);
		this.getGridConfig().setTitle(this.getText("平时成绩列表"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "peStudent.regNo");
		this.getGridConfig()
				.addColumn(this.getText("姓名"), "peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("专业"),
				"peStudent.peMajor.name", true, false, true, "TextField",
				false, 50);
		this.getGridConfig().addColumn(this.getText("层次"),
				"peStudent.peEdutype.name", true, false, true, "TextField",
				false, 50);
		this.getGridConfig().addColumn(this.getText("年级"),
				"peStudent.peGrade.name", true, false, true, "TextField",
				false, 50);
		this.getGridConfig().addColumn(this.getText("学习中心"),
				"peStudent.peSite.name", true, false, true, "TextField", false,
				50);
		this.getGridConfig().addColumn(this.getText("课程名"),
				"prTchOpencourse.peTchCourse.name", true, false, true,
				"TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("平时成绩"), "scoreUsual", true, true, false,Const.score_for_extjs);
		this.getGridConfig().addRenderScript("平时成绩", "{if (${value}=='') return '未录入';return ${value}}", "scoreUsual");
//		this
//				.getGridConfig()
//				.addMenuScript("批量导入",
//						"{window.open('/entity/manager/teaching/usualScore_batch.jsp')}");

	}
	
	/**
	 * 批量上传学生平时成绩
	 * @return
	 */
	public String usualScoreBatch(){
		this.setTogo("back");
		int count = 0;
		try {
			count = this.getExamScoreBatchService().saveUsualScore(this.getUpload());
		} catch (Exception e) {
			this.setMsg(e.getMessage());
			return "msg";
		}
		this.setMsg("共" + count + "条数据上传成功！");
		return "msg";
	}
	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/usualScoreView";
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchStuElective.class;
	}
	
	public void setBean(PrTchStuElective instance) {
		super.superSetBean(instance);
	}

	public PrTchStuElective getBean() {
		return super.superGetBean();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchStuElective.class);
		dc.createCriteria("peStudent", "peStudent").createAlias("peSite",
				"peSite").createAlias("peEdutype", "peEdutype").createAlias(
				"peMajor", "peMajor").createAlias("peGrade", "peGrade").createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus");
		dc.createCriteria("prTchOpencourse", "prTchOpencourse").createAlias("peTchCourse", "peTchCourse").createAlias("peSemester", "peSemester");
		dc.createCriteria("enumConstByFlagElectiveAdmission", "enumConstByFlagElectiveAdmission");
		dc.add(Restrictions.eq("enumConstByFlagElectiveAdmission.code", "1"));
		dc.add(Restrictions.eq("enumConstByFlagStudentStatus.code", "4"));
		return dc;
	}

	@Override
	public void checkBeforeUpdate() throws EntityException {
		if (!(this.getBean().getPeStudent().getEnumConstByFlagStudentStatus().getCode().equals("4")&&this.getBean().getPrTchOpencourse().getPeSemester().getFlagActive().equals("1"))) {
			throw new EntityException("只能修改当前学期且在籍的学生。");
		}
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public ExamScoreBatchService getExamScoreBatchService() {
		return examScoreBatchService;
	}

	public void setExamScoreBatchService(ExamScoreBatchService examScoreBatchService) {
		this.examScoreBatchService = examScoreBatchService;
	}
	
	
}
