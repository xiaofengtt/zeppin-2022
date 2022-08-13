package com.whaty.platform.entity.web.action.studentStatus.stuInfo;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;

public class PrStudentOffenceAddAction extends PeStudentInfoAction {

	private static final long serialVersionUID = -6792836971156482720L;
	
	public void initGrid() {
		this.getGridConfig().addMenuScript(this.getText("test.back"),"{window.history.back();}");
		
		this.getGridConfig().setCapability(false, false, true,true);
		this.getGridConfig().setTitle(this.getText("添加修改学生违纪记录"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "regNo",true,false,true,"");//(name, dataIndex, search, add, list, textFieldParameters)
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName",true,false,true,"");
		//this.getGridConfig().addColumn(this.getText("证件号码"), "prStudentInfo.cardNo",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("性别"), "prStudentInfo.gender",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("学习中心"), "peSite.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("年级"), "peGrade.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("专业"), "peMajor.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("层次"), "peEdutype.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("违纪"), "enumConstByFlagDisobey.name",true,true,true,"");
		this.getGridConfig().addColumn(this.getText("违纪明细"), "disobeyNote",true,true,true,"");
	}
	@Override
	public void setServletPath() {
		this.servletPath="/entity/studentStatus/prStudentOffenceAdd";
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.createAlias("prStudentInfo", "prStudentInfo")
			.createAlias("peSite", "peSite")
			.createAlias("peMajor", "peMajor")
			.createAlias("peEdutype","peEdutype")
			.createAlias("enumConstByFlagDisobey","enumConstByFlagDisobey")
			.createAlias("peGrade", "peGrade")
			.createAlias("enumConstByFlagStudentStatus","enumConstByFlagStudentStatus")
			.add(Restrictions.eq("enumConstByFlagStudentStatus.code", "4"))
			;
		return dc;
	}

	
}
