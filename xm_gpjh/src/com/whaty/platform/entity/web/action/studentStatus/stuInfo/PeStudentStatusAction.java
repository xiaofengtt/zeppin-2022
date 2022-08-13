package com.whaty.platform.entity.web.action.studentStatus.stuInfo;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrStuChangeSchool;


public class PeStudentStatusAction extends PeStudentExpelAction {

	private static final long serialVersionUID = 3448890422809689054L;


	public String turntoDropoutSearch() {
		return "turntoDropoutSearch";
	}

	public String turntoDropoutChange() {
		try{
			if(this.getBean().getPeStudent().getRegNo()==null||this.getBean().getPeStudent().getRegNo().trim().length()<=0){
				this.setMsg("请输入学号！");
				return "turntoDropoutSearch";
			}
			DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
			dc.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")
				.add(Restrictions.eq("enumConstByFlagStudentStatus", this.getMyListService().getEnumConstByNamespaceCode("FlagStudentStatus", "4")))
				.add(Restrictions.eq("regNo", this.getBean().getPeStudent().getRegNo().trim()));
			java.util.List studentList = this.getGeneralService().getList(dc);
			if(studentList==null||studentList.size()<=0){
				this.setMsg("没有查询到学号为"+this.getBean().getPeStudent().getRegNo().trim()+"的学生,请确认学号正确！");
				return "turntoDropoutSearch";
			}else{
				PeStudent student = (PeStudent)studentList.remove(0);
				this.getBean().setPeStudent(student);
				return "turntoDropoutChange";
			}
		}catch(Exception e){
			e.printStackTrace();
			this.setMsg("请输入正确学号！");
			return "turntoDropoutSearch";
		}
	}
	
	public String executeDropoutChange(){
		PrStuChangeSchool prStuChangeSchool = this.getBean();
		try{
			this.getBean().setEnumConstByFlagAbortschoolType(this.getMyListService().getEnumConstByNamespaceCode("FlagAbortschoolType", "1"));
			prStuChangeSchool = this.getPrStuChangeSchoolService().save(this.getBean());
			if(prStuChangeSchool!=null){
				this.setMsg("学生退学成功！");
			}else{
				prStuChangeSchool = this.getBean();
				this.setMsg("学生退学失败！");				
			}
		}catch(Exception e){
			e.printStackTrace();
			prStuChangeSchool = this.getBean();
			this.setMsg(e.getMessage());			
		}
		this.setBean(prStuChangeSchool);
		return "turntoDropoutChange";
	}
	
	public String turntoExpelSearch() {
		return "turntoExpelSearch";
	}
	
	public String turntoExpelChange() {
		try{
			if(this.getBean().getPeStudent().getRegNo()==null||this.getBean().getPeStudent().getRegNo().trim().length()<=0){
				this.setMsg("请输入学号！");
				return "turntoExpelSearch";
			}
			DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
			dc.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")
				.add(Restrictions.eq("enumConstByFlagStudentStatus", this.getMyListService().getEnumConstByNamespaceCode("FlagStudentStatus", "4")))
				.add(Restrictions.eq("regNo", this.getBean().getPeStudent().getRegNo().trim()));
			java.util.List studentList = this.getGeneralService().getList(dc);
			if(studentList==null||studentList.size()<=0){
				this.setMsg("没有查询到学号为"+this.getBean().getPeStudent().getRegNo().trim()+"的学生,请确认学号正确！");
				return "turntoExpelSearch";
			}else{
				PeStudent student = (PeStudent)studentList.remove(0);
				this.getBean().setPeStudent(student);
				return "turntoExpelChange";
			}
		}catch(Exception e){
			e.printStackTrace();
			this.setMsg("请输入正确学号！");
			return "turntoExpelSearch";
		}
	}
	
	public String executeExpelChange(){
		PrStuChangeSchool prStuChangeSchool = this.getBean();
		try{
			this.getBean().setEnumConstByFlagAbortschoolType(this.getMyListService().getEnumConstByNamespaceCode("FlagAbortschoolType", "0"));
			prStuChangeSchool = this.getPrStuChangeSchoolService().save(this.getBean());
			if(prStuChangeSchool!=null){
				this.setMsg("学生开除学籍成功！");
			}else{
				prStuChangeSchool = this.getBean();
				this.setMsg("学生开除学籍失败！");				
			}
		}catch(Exception e){
			e.printStackTrace();
			prStuChangeSchool = this.getBean();
			this.setMsg(e.getMessage());			
		}
		this.setBean(prStuChangeSchool);
		return "turntoExpelChange";
	}
	
	public String executeCancel(){
		PrStuChangeSchool prStuChangeSchool = this.getBean();
		String back = "";String temp = "";
		if(prStuChangeSchool.getEnumConstByFlagAbortschoolType().getCode().equals("0")){
			back = "turntoExpelChange";temp="开除学籍";
		}else if(prStuChangeSchool.getEnumConstByFlagAbortschoolType().getCode().equals("1")){
			back = "turntoDropoutChange";temp="退学";
		}else{
			back = "msg";
		}
		try{
			if( this.getPrStuChangeSchoolService().delForCancel(this.getBean())){
				this.setMsg("学生取消"+temp+"操作成功！");				
			}else{
				this.setMsg("学生取消"+temp+"操作失败！");	
			}
		}catch(Exception e){
			e.printStackTrace();
			this.setMsg(e.getMessage());			
		}
		prStuChangeSchool.setId(null);
		this.setBean(prStuChangeSchool);
		return back;
	}
	
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, true, true);
		this.getGridConfig().setTitle(this.getText("学籍注销列表"));
		
		this.getGridConfig().addMenuFunction(this.getText("取消变动"), "cancel", "");
		
		this.getGridConfig().addColumn(this.getText("id"), "id",false);
		this.getGridConfig().addColumn(this.getText("学号"), "peStudent.regNo",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("姓名"), "peStudent.trueName",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("学习中心"),"peStudent.peSite.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("层次"),"peStudent.peEdutype.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("专业"), "peStudent.peMajor.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("年级"), "peStudent.peGrade.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("注销类型"), "enumConstByFlagAbortschoolType.name",false,false,true,"");
		this.getGridConfig().addColumn(this.getText("注销时间"),"opDate",false,true,true,"Date",false,50);
		this.getGridConfig().addColumn(this.getText("注销原因"),"note",false,true,true,"TextArea",true,1000);	
	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/studentStatus/peStudentStatus";
		
	}
}
