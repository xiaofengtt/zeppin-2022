package com.whaty.platform.entity.web.action.studentStatus.graduate;

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

public class PrApplyGraduateAction extends SystemApplayAction {

	private SystemApplyService systemApplyService;
	
	@Override
	public void initGrid() {
		this.getGridConfig().addMenuFunction(this.getText("准许毕业"), "graduation","");
		this.getGridConfig().addMenuFunction(this.getText("取消毕业"), "cancelgraduation","");
		if(this.getGridConfig().checkBeforeAddMenu("/entity/studentStatus/prApplyGraduate.action")){
		this.getGridConfig().addMenuScript(this.getText("查看获得专科学历时间晚于入学时间的学生"),"{window.navigate('/entity/studentStatus/prApplyGraduate.action?msg=msg');}");
		}
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("已申请毕业学生列表"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("身份证号码"), "peStudent.prStudentInfo.cardNo");
		this.getGridConfig().addColumn(this.getText("手机"), "peStudent.prStudentInfo.mobilephone");
		this.getGridConfig().addColumn(this.getText("学习中心"), "peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("年级"), "peStudent.peGrade.name");
		this.getGridConfig().addColumn(this.getText("专业"), "peStudent.peMajor.name");
		this.getGridConfig().addColumn(this.getText("层次"), "peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("入学时间"), "peStudent.recruitDate");
		this.getGridConfig().addColumn(this.getText("申请时间"), "applyDate");
		this.getGridConfig().addColumn(this.getText("申请状态"), "enumConstByFlagApplyStatus.name");
		//this.getGridConfig().addColumn(this.getText("申请原因"), "applyNote");
		this.getGridConfig().addColumn(this.getText("专科毕业时间"), "peStudent.prStudentInfo.graduateYear");
//		this.getGridConfig().addColumn(this.getText("学位英语成绩"),"peStudent.scoreDegreeEnglish");
		//this.getGridConfig().addColumn(this.getText("是否申请学位"),"f");
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/studentStatus/prApplyGraduate";
	}

	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
		dc.createAlias("enumConstByApplyType", "enumConstByApplyType").add(Restrictions.eq("enumConstByApplyType.code", "8"))
			.createAlias("enumConstByFlagApplyStatus", "enumConstByFlagApplyStatus")
			.createCriteria("peStudent", "peStudent")
			.createAlias("peSite", "peSite")
			.createAlias("peGrade", "peGrade")
			.createAlias("peMajor", "peMajor")
			.createAlias("peEdutype", "peEdutype")
			.createAlias("prStudentInfo", "prStudentInfo");
			if(this.getMsg()!=null&&this.getMsg().equals("msg")){
				dc.add(Restrictions.sqlRestriction("to_char(recruit_date,'yyyymm')<substr(graduate_year,0,4)||substr(graduate_year,6,2)"));
			}
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
				if(this.getColumn().equals("graduation")){
					count = this.getSystemApplyService().updateForGraduation(idList,false);
					map.put("success", "true");
					map.put("info", this.getText(String.valueOf(count)+"个学生通过毕业申请，修改为毕业..."));
				}else if(this.getColumn().equals("cancelgraduation")){
					count = this.getSystemApplyService().updateForCancelGraduation(idList,false);
					map.put("success", "true");
					map.put("info", this.getText(String.valueOf(count)+"个学生取消了毕业..."));					
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
