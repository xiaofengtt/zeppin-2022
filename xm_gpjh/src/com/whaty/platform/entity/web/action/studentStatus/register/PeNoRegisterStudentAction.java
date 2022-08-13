package com.whaty.platform.entity.web.action.studentStatus.register;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.studentStatus.stuInfo.PeStudentInfoAction;

public class PeNoRegisterStudentAction extends PeStudentInfoAction  {

	private static final long serialVersionUID = -5842892132079758099L;

	@Override
	public void initGrid() {
		this.getGridConfig().addMenuFunction("注册学生","Register", "");
		this.getGridConfig().addMenuFunction("取消注册","cancelRegister", "");
		
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("未注册学生列表"));
		
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("peRecStudent.examCardNum"), "peRecStudent.examCardNum");
		this.getGridConfig().addColumn(this.getText("peStudent.regNo"), "regNo",false);
		this.getGridConfig().addColumn(this.getText("peStudent.trueName"), "trueName");
		this.getGridConfig().addColumn(this.getText("peStudent.prStudentInfo.cardNo"), "prStudentInfo.cardNo");
		this.getGridConfig().addColumn(this.getText("peStudent.peSite.name"), "peSite.name");
		this.getGridConfig().addColumn(this.getText("peStudent.peGrade.name"), "peGrade.name",false);
		this.getGridConfig().addColumn(this.getText("peStudent.peEdutype.name"), "peEdutype.name");
		this.getGridConfig().addColumn(this.getText("peStudent.peMajor.name"), "peMajor.name");
		ColumnConfig column = new ColumnConfig(this.getText("学生状态"),"enumConstByFlagStudentStatus.name");
		column.setComboSQL("select id,name from enum_const where namespace='FlagStudentStatus' and code in ('3','4')");
		this.getGridConfig().addColumn(column);
		//this.getGridConfig().addColumn(this.getText("学生状态"), "enumConstByFlagStudentStatus.name");
		
	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/studentStatus/peNoRegisterStudent";
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.createAlias("prStudentInfo", "prStudentInfo").createAlias("peSite", "peSite")
			.createAlias("peMajor", "peMajor")
			.createAlias("peEdutype","peEdutype")
			.createAlias("peGrade", "peGrade")
			.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")
			.add(Restrictions.or(Restrictions.eq("enumConstByFlagStudentStatus.code", "3"),Restrictions.eq("enumConstByFlagStudentStatus.code", "4")))
			.createCriteria("peRecStudent", "peRecStudent")
				//添加考试批次关联信息
				.createCriteria("prRecPlanMajorSite","prRecPlanMajorSite")
				.createCriteria("prRecPlanMajorEdutype", "prRecPlanMajorEdutype")
				.createCriteria("peRecruitplan", "peRecruitplan")
				.add(Restrictions.eq("peRecruitplan.flagActive", "1"));		
		return dc;
	}
	
	public Map updateColumn() {
		String msg = "";
		Map map = new HashMap();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			List idList = new ArrayList();

			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}
			try {
				if(this.getColumn().equals("Register")) {
					msg = this.getPeStudentService().createRegister(idList);
					map.put("success", "true");
					map.put("info", msg);
				}else if(this.getColumn().equals("cancelRegister")){
					msg = this.getPeStudentService().delRegister(idList);
					map.put("success", "true");
					map.put("info", msg);
				}
			} catch (Exception e) {
				map.put("success", "false");
				map.put("info", e.getMessage());
			}
		} else {
			map.put("success", "false");
			map.put("info", "请选择学生！");
		}
		return map;
	}
}
