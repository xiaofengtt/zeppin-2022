package com.whaty.platform.entity.web.action.studentStatus.stuInfo;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class PrStudentOffenceAction extends PrStudentOffenceAddAction {

	private static final long serialVersionUID = -6792836971156482720L;
	
	public void initGrid() {		
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("违纪学生列表"));
		if(this.getGridConfig().checkBeforeAddMenu("/entity/studentStatus/prStudentOffenceAdd.action")){
		this.getGridConfig().addMenuScript(this.getText("添加修改学生违纪记录"),"{window.location='/entity/studentStatus/prStudentOffenceAdd.action?search=true';}");
		}
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "regNo",true,false,true,"");//(name, dataIndex, search, add, list, textFieldParameters)
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName",true,false,true,"");
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
		this.servletPath="/entity/studentStatus/prStudentOffence";
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {	
			
		return super.initDetachedCriteria().add(Restrictions.ne("enumConstByFlagDisobey.code", "0"));
			
		
	}
	/*public String addone(){
		return "addone";
	}
	public String addexe(){
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.add(Restrictions.eq("regNo", this.getBean().getRegNo()));
		try{
			java.util.List stlist = this.getGeneralService().getList(dc);
			if(stlist==null||stlist.size()<=0){
				this.setMsg("没有查到学号为"+this.getBean().getRegNo()+"的学生！请查证填写...");
				return "addone";
			}
			PeStudent student = (PeStudent)stlist.remove(0);
			
			student.setEnumConstByFlagDisobey((EnumConst)this.getMyListService().getById(EnumConst.class, this.getBean().getEnumConstByFlagDisobey().getId()));
			
			student.setDisobeyNote(this.getBean().getDisobeyNote());
			this.getGeneralService().save(student);
			this.setMsg("<font color='red'>添加违纪学生"+this.getBean().getRegNo()+"的违纪记录成功!</font>");
			this.setTogo("/entity/studentStatus/prStudentOffence.action?search=true");
			return "msg";
		}catch(Throwable e){
			this.setMsg("添加学号为"+this.getBean().getRegNo()+"的学生的违纪信息失败！请查证...");
			return "addone";
		}
	}*/
	
}
