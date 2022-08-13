package com.whaty.platform.entity.web.action.teaching.result;

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

public class DegreeEnglishAuditAction extends SystemApplayAction {

	private SystemApplyService systemApplyService;

	public SystemApplyService getSystemApplyService() {
		return systemApplyService;
	}

	public void setSystemApplyService(SystemApplyService systemApplyService) {
		this.systemApplyService = systemApplyService;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().addMenuFunction(this.getText("审核通过"), "reapply",
				"");
		this.getGridConfig().addMenuFunction(this.getText("审核不通过"),
				"rejection", "");

		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("学位英语申请列表"));

		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "peStudent.regNo");
		this.getGridConfig()
				.addColumn(this.getText("姓名"), "peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("学习中心"),
				"peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("年级"),
				"peStudent.peGrade.name");
		this.getGridConfig().addColumn(this.getText("专业"),
				"peStudent.peMajor.name");
		this.getGridConfig().addColumn(this.getText("层次"),
				"peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("申请时间"), "applyDate");
		this.getGridConfig().addColumn(this.getText("申请状态"),
				"enumConstByFlagApplyStatus.name");

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/degreeEnglishAudit";
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {

		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
		dc.createCriteria("enumConstByApplyType", "enumConstByApplyType");
		dc.createCriteria("enumConstByFlagApplyStatus",
				"enumConstByFlagApplyStatus");
		dc.createCriteria("peStudent", "peStudent").createAlias("peSite",
				"peSite").createAlias("peMajor", "peMajor").createAlias(
				"peEdutype", "peEdutype").createAlias("peGrade", "peGrade");
		dc.add(Restrictions.eq("enumConstByApplyType.code", "11"));

		return dc;
	}
	
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
				if(this.getColumn().equals("reapply")){
					count = this.getSystemApplyService().updateForPaperReapply(idList,true);
					map.put("success", "true");
					map.put("info", this.getText(String.valueOf(count)+"个学生学位英语申请通过。"));
				}else if(this.getColumn().equals("rejection")){
					count = this.getSystemApplyService().updateForPaperReapply(idList,false);
					map.put("success", "true");
					map.put("info", this.getText(String.valueOf(count)+"个学生学位英语申请不通过。"));					
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
