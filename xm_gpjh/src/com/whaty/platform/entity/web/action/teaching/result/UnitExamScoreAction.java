package com.whaty.platform.entity.web.action.teaching.result;

import java.io.File;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.examScore.ExamScoreBatchService;

public class UnitExamScoreAction extends DegreeEnglishScoreAvtion {
	private ExamScoreBatchService examScoreBatchService;
	private File upload;
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, true);
		this.getGridConfig().setTitle(this.getText("统考成绩管理"));
		this.getGridConfig().addMenuFunction("导入统考成绩", 1);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "regNo", true, false, true, "TextField",
				false, 50);
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName", true, false, true, "TextField",
				false, 50);
		this.getGridConfig().addColumn(this.getText("身份证号"),
				"prStudentInfo.cardNo");
		this.getGridConfig().addColumn(this.getText("学习中心"), "peSite.name", true, false, true, "TextField",
				false, 50);
		this.getGridConfig().addColumn(this.getText("专业"), "peMajor.name", true, false, true, "TextField",
				false, 50);
		this.getGridConfig().addColumn(this.getText("层次"), "peEdutype.name", true, false, true, "TextField",
				false, 50);
		this.getGridConfig().addColumn(this.getText("年级"), "peGrade.name", true, false, true, "TextField",
				false, 50);
		
		this.getGridConfig().addColumn(this.getText("统考英语A"), "enumConstByScoreUniteEnglishA.name",true,true,true,"TextField",true,50);

		this.getGridConfig().addColumn(this.getText("统考英语B"), "enumConstByScoreUniteEnglishB.name",true,true,true,"TextField",true,50);
		this.getGridConfig().addColumn(this.getText("统考英语C"), "enumConstByScoreUniteEnglishC.name",true,true,true,"TextField",true,50);
		this.getGridConfig().addColumn(this.getText("统考数学B"), "enumConstByScoreUniteShuxue.name",true,true,true,"TextField",true,50);
		this.getGridConfig().addColumn(this.getText("统考语文B"), "enumConstByScoreUniteYuwen.name",true,true,true,"TextField",true,50);

		this.getGridConfig().addColumn(this.getText("统考计算机"), "enumConstByScoreUniteComputer.name",true,true,true,"TextField",true,50);
	}
	/**
	 * 批量上传学生统考成绩
	 * @return
	 */
	public String usualScoreBatch(){
		this.setTogo("back");
		int count = 0;
		try {
			count = this.getExamScoreBatchService().saveUnitExamScore(this.getUpload());
		} catch (Exception e) {
			this.setMsg(e.getMessage());
			return "msg";
		}
		this.setMsg("共" + count + "条数据上传成功！");
		return "msg";
	}
	
	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/unitExamScore";

	}

	@Override
	public void checkBeforeUpdate() throws EntityException {
		int num = 0;
//		if (this.getBean().getEnumConstByScoreUniteEnglishA()!=null && this.getBean().getEnumConstByScoreUniteEnglishB()!=null) {
//			throw new EntityException("只能录入一个英语统考成绩");
//		}
		if(this.getBean().getEnumConstByScoreUniteEnglishA() != null){
			num ++;
		}
		if(this.getBean().getEnumConstByScoreUniteEnglishB() != null){
			num ++;
		}
		if(this.getBean().getEnumConstByScoreUniteEnglishC() !=null ){
			num ++;
		}
		if(this.getBean().getEnumConstByScoreUniteComputer() !=null){
			num ++;
		}
		if(this.getBean().getEnumConstByScoreUniteShuxue() !=null){
			num ++;
		}
		if(this.getBean().getEnumConstByScoreUniteYuwen() !=null){
			num ++;
		}
		if(num>2){
			throw new EntityException("最多录入两个统考科目成绩");
		}
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.createCriteria("prStudentInfo","prStudentInfo");
		dc.createCriteria("peSite","peSite");
		dc.createCriteria("peMajor","peMajor");
		dc.createCriteria("peEdutype","peEdutype").add(Restrictions.like("name", "本", MatchMode.ANYWHERE));
		dc.createCriteria("peGrade","peGrade");
		dc.createCriteria("enumConstByFlagStudentStatus","enumConstByFlagStudentStatus");
		dc.createCriteria("enumConstByScoreUniteEnglishA", "enumConstByScoreUniteEnglishA",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByScoreUniteEnglishB", "enumConstByScoreUniteEnglishB",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByScoreUniteEnglishC", "enumConstByScoreUniteEnglishC",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByScoreUniteShuxue", "enumConstByScoreUniteShuxue",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByScoreUniteYuwen", "enumConstByScoreUniteYuwen",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByScoreUniteComputer", "enumConstByScoreUniteComputer",DetachedCriteria.LEFT_JOIN);
		dc.add(Restrictions.eq("enumConstByFlagStudentStatus.code", "4"));
		return dc;
	}
	
	
}
