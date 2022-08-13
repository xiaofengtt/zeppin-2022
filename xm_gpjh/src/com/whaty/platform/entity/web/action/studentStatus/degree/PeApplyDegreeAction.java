package com.whaty.platform.entity.web.action.studentStatus.degree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.SystemApply;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.SystemApplyService;
import com.whaty.platform.entity.web.action.SystemApplayAction;

public class PeApplyDegreeAction extends SystemApplayAction {

	private SystemApplyService systemApplyService;
	
	@Override
	public void initGrid() {
		this.getGridConfig().addMenuFunction(this.getText("给予学位"), "degree","");
		this.getGridConfig().addMenuFunction(this.getText("取消学位"), "canceldegree","");
		
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("申请学位列表"));
		
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("学习中心"), "peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("年级"), "peStudent.peGrade.name");
		this.getGridConfig().addColumn(this.getText("专业"), "peStudent.peMajor.name");
		this.getGridConfig().addColumn(this.getText("层次"), "peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("入学时间"), "peStudent.recruitDate");
		this.getGridConfig().addColumn(this.getText("申请时间"), "applyDate");
		this.getGridConfig().addColumn(this.getText("申请状态"), "enumConstByFlagApplyStatus.name");
		//this.getGridConfig().addColumn(this.getText("申请原因"), "applyNote");
		this.getGridConfig().addColumn(this.getText("专科毕业时间"), "peStudent.prStudentInfo.graduateYear");
		this.getGridConfig().addColumn(this.getText("学位外语成绩"),"peStudent.scoreDegreeEnglish");
		//this.getGridConfig().addColumn(this.getText("毕业论文成绩"),"f");
	
	}


	@Override
	public void setServletPath() {
		this.servletPath = "/entity/studentStatus/peApplyDegree";
	}
	
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
		dc.createAlias("enumConstByApplyType", "enumConstByApplyType").add(Restrictions.eq("enumConstByApplyType.code", "9"))
			.createAlias("enumConstByFlagApplyStatus", "enumConstByFlagApplyStatus")
			.createCriteria("peStudent", "peStudent")
			.createAlias("peSite", "peSite")
			.createAlias("peGrade", "peGrade")
			.createAlias("peMajor", "peMajor")
			.createAlias("peEdutype", "peEdutype")
			.createAlias("prStudentInfo", "prStudentInfo");
		return dc;
	}
	
	public SystemApplyService getSystemApplyService() {
		return systemApplyService;
	}

	public void setSystemApplyService(SystemApplyService systemApplyService) {
		this.systemApplyService = systemApplyService;
	}
	
	@Override
	public Map updateColumn() {
		Map map = new HashMap();
		int count = 0;
		if (this.getIds() != null && this.getIds().length() > 0){
			String[] ids = getIds().split(",");
			List idList = new ArrayList();			
			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}
			try {
				checkBeforeUpdateColumn(idList);
			} catch (EntityException e) {
				map.clear();
				map.put("success", "false");
				map.put("info", e.getMessage());
				return map;
			}
			try {
				if(this.getColumn().equals("degree")){
					count = this.getSystemApplyService().updateForGraduation(idList,true);
					map.put("success", "true");
					map.put("info", this.getText(String.valueOf(count)+"个学生通过学位审核..."));
				}else if(this.getColumn().equals("canceldegree")){
					count = this.getSystemApplyService().updateForCancelGraduation(idList,true);
					map.put("success", "true");
					map.put("info", this.getText(String.valueOf(count)+"个学生取消学位审核..."));					
				}else{					
				}
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
