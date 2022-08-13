package com.whaty.platform.entity.web.action.teaching.paper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PrTchStuPaper;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.paper.FinalScoreService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.JsonUtil;

public class ReplyTimeSetAction extends MyBaseAction<PrTchStuPaper> {
	private FinalScoreService finalScoreService;
	/**
	 * 
	 */
	private static final long serialVersionUID = -773833977309924208L;

	/**
	 * 设置答辩时间
	 * 
	 */
	public String exe() {
		this.setTogo("back");
		try {
			this.setMsg(this.getFinalScoreService().saveRejoinSection());
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg(e.getMessage());
			
		}
		return "msg";
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, true);
		this.getGridConfig().setTitle(this.getText("学生答辩时间查询"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig()
				.addColumn(this.getText("姓名"), "peStudent.trueName",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("学号"), "peStudent.regNo",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("年级"),"peStudent.peGrade.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("学习中心"),
				"peStudent.peSite.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("层次"),
				"peStudent.peEdutype.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("专业"),
				"peStudent.peMajor.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("指导教师"),"prTchPaperTitle.peTeacher.name",true,false,true,"");
//		this.getGridConfig().addColumn(this.getText("答辩时间段"), "peTchRejoinSection.name");
		ColumnConfig rejoinSection = new ColumnConfig(this.getText("答辩时间段"),"peTchRejoinSection.name");
		rejoinSection.setComboSQL("select t.id, t.name   from pe_tch_rejoin_section t, pe_semester semester  where t.fk_semester_id = semester.id   and semester.flag_active = '1'");
		this.getGridConfig().addColumn(rejoinSection);
//		this.getGridConfig().addColumn(this.getText("答辩时间"), "peTchRejoinSection.rejoinDatetime",true,false,true,"");
//		this.getGridConfig().addColumn(this.getText("答辩教室"), "peTchRejoinRoom.name");
		ColumnConfig rejoinRoom = new ColumnConfig(this.getText("答辩教室"),"peTchRejoinRoom.name");
		rejoinRoom.setComboSQL("select t.id, t.name   from pe_tch_rejoin_room  t, pe_semester semester  where t.fk_semester_id = semester.id   and semester.flag_active = '1'");
		this.getGridConfig().addColumn(rejoinRoom);
//		this.getGridConfig().addColumn(this.getText("答辩地点"), "peTchRejoinRoom.trueName",true,false,true,"");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchStuPaper.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/replyTimeSet";
	}

	public void setBean(PrTchStuPaper instance) {
		super.superSetBean(instance);
	}

	public PrTchStuPaper getBean() {
		return super.superGetBean();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		JsonUtil.setDateformat("yyyy-MM-dd HH:mm:ss");
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchStuPaper.class);
		dc.createCriteria("peStudent","peStudent").createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus").createAlias("peSite", "peSite").createAlias("peEdutype", "peEdutype").createAlias("peMajor", "peMajor").createAlias("peGrade", "peGrade");
		dc.createCriteria("enumConstByFlagPaperRejoin","enumConstByFlagPaperRejoin");
		dc.add(Restrictions.eq("enumConstByFlagPaperRejoin.code", "1"));
		dc.createCriteria("prTchPaperTitle", "prTchPaperTitle").createAlias("peTeacher", "peTeacher").createAlias("peSemester", "peSemester");
		dc.add(Restrictions.eq("peSemester.flagActive", "1"));
		dc.add(Restrictions.eq("enumConstByFlagStudentStatus.code", "4"));
		dc.createCriteria("peTchRejoinSection", "peTchRejoinSection",DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peTchRejoinRoom", "peTchRejoinRoom",DetachedCriteria.LEFT_JOIN);
		return dc;
	}

	public FinalScoreService getFinalScoreService() {
		return finalScoreService;
	}

	public void setFinalScoreService(FinalScoreService finalScoreService) {
		this.finalScoreService = finalScoreService;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}
