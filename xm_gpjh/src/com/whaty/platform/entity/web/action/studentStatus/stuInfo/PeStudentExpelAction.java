package com.whaty.platform.entity.web.action.studentStatus.stuInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PrStuChangeSchool;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.studentStatas.PrStuChangeSchoolService;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PeStudentExpelAction extends MyBaseAction<PrStuChangeSchool> {

	private static final long serialVersionUID = 7147201084863718198L;
	
	private PrStuChangeSchoolService prStuChangeSchoolService;
	
	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
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
		this.getGridConfig().addColumn(this.getText("注销类型"), "enumConstByFlagAbortschoolType.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("注销时间"),"opDate",false,true,true,"Date",false,50);
		this.getGridConfig().addColumn(this.getText("注销原因"),"note",false,true,true,"TextArea",true,1000);	
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrStuChangeSchool.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/studentStatus/peStudentExpel"	;	
	}
	public void setBean(PrStuChangeSchool instance) {
		super.superSetBean(instance);
	}
	
	public PrStuChangeSchool getBean(){
		return super.superGetBean();
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrStuChangeSchool.class);
		dc.createAlias("enumConstByFlagAbortschoolType", "enumConstByFlagAbortschoolType")
			.createCriteria("peStudent","peStudent")
			.createAlias("peSite", "peSite")
			.createAlias("peMajor", "peMajor")
			.createAlias("peEdutype","peEdutype")
			.createAlias("peGrade", "peGrade");;
		return dc;
	}

	public PrStuChangeSchoolService getPrStuChangeSchoolService() {
		return prStuChangeSchoolService;
	}

	public void setPrStuChangeSchoolService(
			PrStuChangeSchoolService prStuChangeSchoolService) {
		this.prStuChangeSchoolService = prStuChangeSchoolService;
	}

	@Override
	public Map updateColumn() {
		Map map = new HashMap();
		int count = 0;
		if (this.getIds() != null && this.getIds().length() > 0) {			
			String[] ids = getIds().split(",");
			List idList = new ArrayList();			
			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}
			
			try {
				if(this.getColumn().equals("cancel")){
					count = this.getPrStuChangeSchoolService().delForCancel(idList);
				}else{
					//count = this.getGeneralService().updateColumnByIds(idList, this.getColumn(), this.getValue());
				}
				map.put("success", "true");
				map.put("info", this.getText(String.valueOf(count)+"条记录操作成功"));
			} catch (EntityException e) {
				e.printStackTrace();
				map.put("success", "false");
				map.put("info", e.getMessage());
				return map;
			}
		} else {
			map.put("success", "false");
			map.put("info", "parameter value error");
		}
		return map;
	}

}
