package com.whaty.platform.entity.web.action.exam.finalExam;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PrExamBooking;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.exam.finalExam.PrExamBookingService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;
/**
 * 期末考试成绩单个、批量录入
 * @author zqf
 *
 */
public class ExamScoreAction extends MyBaseAction {
	private File upload;
	private PrExamBookingService prExamBookingService;
	
	
	/**
	 * 上传处理功能
	 */
	public String batchexe() {
		int count = 0;
		try {
			count = this.getPrExamBookingService().saveUploadScore(this.getUpload());
		} catch (Exception e) {
			this.setMsg(e.getMessage());
			this.setTogo("back");
			return "msg";
		}
		this.setMsg("共" + count + "条数据上传成功！");
		this.setTogo("back");
		return "msg";
	}
	
	public PrExamBookingService getPrExamBookingService() {
		return prExamBookingService;
	}

	public void setPrExamBookingService(PrExamBookingService prExamBookingService) {
		this.prExamBookingService = prExamBookingService;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, true);
		this.getGridConfig().setTitle(this.getText("期末考试成绩录入"));
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		this.getGridConfig().addColumn(this.getText("考试学期"),"peSemester.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("学号"),"prTchStuElective.peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("学生姓名"),"prTchStuElective.peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("考试课程"),"prTchStuElective.prTchProgramCourse.peTchCourse.name");
		this.getGridConfig().addColumn(this.getText("学习中心"),"prTchStuElective.peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"),"prTchStuElective.peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"),"prTchStuElective.peStudent.peMajor.name");
		this.getGridConfig().addColumn(this.getText("年级"),"prTchStuElective.peStudent.peGrade.name");
		this.getGridConfig().addColumn(this.getText("考试成绩"),"scoreExam",true,true,true,Const.score_for_extjs);
//		this.getGridConfig().addColumn(this.getText("考试成绩状态"),"enumConstByFlagScoreStatus.name",true,true,true,"TextField",true,50);
		ColumnConfig column = new ColumnConfig(this.getText("考试成绩状态"),"enumConstByFlagScoreStatus.name");
		column.setComboSQL("select id,name from enum_const where namespace='FlagScoreStatus' and code<='4'");
		this.getGridConfig().addColumn(column);
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrExamBooking.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/exam/examScore";
	}
	public void setBean(PrExamBooking instance){
		this.superSetBean(instance);
	}
	
	public PrExamBooking getBean(){
		return (PrExamBooking)this.superGetBean();
	}
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrExamBooking.class);
		dc.createCriteria("peSemester", "peSemester");
		DetachedCriteria dcPrTchStuElective = dc.createCriteria("prTchStuElective", "prTchStuElective");
		dcPrTchStuElective.createCriteria("peStudent", "peStudent")
				.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")
				.createAlias("peSite", "peSite")
				.createAlias("peEdutype", "peEdutype")
				.createAlias("peMajor", "peMajor")
				.createAlias("peGrade", "peGrade");
		dcPrTchStuElective.createCriteria("prTchProgramCourse", "prTchProgramCourse")
						.createCriteria("peTchCourse", "peTchCourse");
		dc.createAlias("enumConstByFlagScoreStatus", "enumConstByFlagScoreStatus");
		dc.add(Restrictions.eq("enumConstByFlagStudentStatus.code", "4"));
		return dc;
	}
	//重写父类的update方法，以满足在更新预约表中成绩的时候，动态的修改选课表中的成绩
	public Map update() {
		
		Map map = new HashMap();
		
		PrExamBooking instance = new PrExamBooking();
		
		try {
			instance = (PrExamBooking)this.getGeneralService().getById(this.getBean().getId());
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.superSetBean((PrExamBooking)setSubIds(instance,this.getBean()));
		try {
			checkBeforeUpdate();
		} catch (EntityException e1) {
			map.put("success", "false");
			map.put("info", e1.getMessage());
			return map;
		}
		

		try {
			this.getPrExamBookingService().save_Score(this.getBean());
			map.put("success", "true");
			map.put("info", "更新成功");
			
		} catch (Exception e) {
			map.clear();
			map.put("success", "false");
			map.put("info", "更新失败");
			
		}

		return map;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

}
