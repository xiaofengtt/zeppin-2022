package com.whaty.platform.entity.web.action.studentStatus.register;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.SystemApply;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.SystemApplyService;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PrRecPriPayApplyAction extends MyBaseAction<SystemApply> {
	
	private static final long serialVersionUID = 1222443374043213368L;
	
	private SystemApplyService systemApplyService;

	@Override
	public void initGrid() {
		this.getGridConfig().addMenuFunction("减免预交费审核通过", "CheckForPass","");
		this.getGridConfig().addMenuFunction("取消审核", "CancelForPass", "");
		this.getGridConfig().addMenuFunction("减免预交费审核不通过", "CheckForNoPass", "");
		this.getGridConfig().addMenuFunction("取消审核不通过", "CancelForNoPass", "");
			
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("预交费申请管理"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("peRecStudent.examCardNum"), "peStudent.peRecStudent.examCardNum");
		this.getGridConfig().addColumn(this.getText("peStudent.trueName"), "peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("招生考试批次"), "peStudent.peRecStudent.prRecPlanMajorSite.prRecPlanMajorEdutype.peRecruitplan.name");
		this.getGridConfig().addColumn(this.getText("peStudent.peSite.name"), "peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("peStudent.peEdutype.name"), "peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("peStudent.peMajor.name"), "peStudent.peMajor.name");
		this.getGridConfig().addColumn(this.getText("prRecPriPay.derateapplication"), "enumConstByFlagApplyStatus.name");
		this.getGridConfig().addColumn(this.getText("申请时间"), "applyDate");
		this.getGridConfig().addColumn(this.getText("审核时间"), "checkDate");

	}

	@Override
	public void setEntityClass() {
		this.entityClass = SystemApply.class;
		
	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/studentStatus/prRecPriPayApply";
		
	}
	public void setBean(SystemApply instance) {
		super.superSetBean(instance);
	}
	
	public SystemApply getBean(){
		return super.superGetBean();
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
		dc.createAlias("enumConstByApplyType","enumConstByApplyType").createAlias("enumConstByFlagApplyStatus","enumConstByFlagApplyStatus")
			.add(Restrictions.eq("enumConstByApplyType.code", "0"))
			
			.createCriteria("peStudent", "peStudent")
			.createAlias("prStudentInfo", "prStudentInfo")
			.createAlias("peSite", "peSite")
			.createAlias("peMajor", "peMajor")
			.createAlias("peEdutype","peEdutype")
			.createAlias("peGrade", "peGrade")			
			.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")
			.add(Restrictions.eq("enumConstByFlagStudentStatus.code", "0"))
			
			.createCriteria("peRecStudent", "peRecStudent", DetachedCriteria.LEFT_JOIN)
				//添加考试批次关联信息
				.createCriteria("prRecPlanMajorSite","prRecPlanMajorSite", DetachedCriteria.LEFT_JOIN)
				.createCriteria("prRecPlanMajorEdutype", "prRecPlanMajorEdutype",DetachedCriteria.LEFT_JOIN)
					.createCriteria("peRecruitplan", "peRecruitplan", DetachedCriteria.LEFT_JOIN)
				;
		return dc;
	}

	@Override
	public Map updateColumn() {
		Map map = new HashMap();
		int count = 0;
		if (this.getIds() != null && this.getIds().length() > 0) {			
			String[] ids = getIds().split(",");
			List<String> idList = new ArrayList<String>();			
			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}
			try {
				if(this.getColumn().equals("CheckForPass"))
					count = this.getSystemApplyService().updateForCheck(idList, true);
				else if(this.getColumn().equals("CancelForPass"))
					count = this.getSystemApplyService().updateForCancel(idList, true);
				else if(this.getColumn().equals("CheckForNoPass"))
					count = this.getSystemApplyService().updateForCheck(idList, false);
				else if(this.getColumn().equals("CancelForNoPass"))
					count = this.getSystemApplyService().updateForCancel(idList, false);
				else 
					count = this.getGeneralService().updateColumnByIds(idList, this.getColumn(), this.getValue());
				map.put("success", "true");
				map.put("info", this.getText(String.valueOf(count)+"条记录操作成功"));
			} catch (EntityException e) {
				e.printStackTrace();
				map.put("success", "false");
				map.put("info", e.getMessage());
			}
		} else {
			map.put("success", "false");
			map.put("info", "请选择一个学生操作！");
		}
		return map;
	}

	public SystemApplyService getSystemApplyService() {
		return systemApplyService;
	}

	public void setSystemApplyService(SystemApplyService systemApplyService) {
		this.systemApplyService = systemApplyService;
	}

}
