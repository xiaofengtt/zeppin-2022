package com.whaty.platform.entity.web.action.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.SystemApply;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.SystemApplyService;
import com.whaty.platform.entity.web.action.studentStatus.register.PrRecPriPayApplyAction;

/**
 * 课程免考申请审核
 * @author Administrator
 *
 */
public class ExamAvoidApplyAction extends PrRecPriPayApplyAction {
	private SystemApplyService systemApplyService;
	@Override
	public void initGrid() {
		this.getGridConfig().addMenuFunction("申请通过", "CheckForPass","");
		this.getGridConfig().addMenuFunction("取消申请通过", "CancelForPass", "");
		this.getGridConfig().addMenuFunction("申请不通过", "CheckForNoPass", "");
		this.getGridConfig().addMenuFunction("取消申请不通过", "CancelForNoPass", "");
		
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("课程免考申请列表"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("peStudent.trueName"), "peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("证件号码"), "peStudent.prStudentInfo.cardNo");
		this.getGridConfig().addColumn(this.getText("peStudent.peGrade.name"), "peStudent.peGrade.name");
		this.getGridConfig().addColumn(this.getText("peStudent.peSite.name"), "peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("peStudent.peEdutype.name"), "peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("peStudent.peMajor.name"), "peStudent.peMajor.name");
		this.getGridConfig().addColumn(this.getText("审核状态"), "enumConstByFlagApplyStatus.name");
		this.getGridConfig().addColumn(this.getText("申请时间"), "applyDate");
		this.getGridConfig().addColumn(this.getText("审核时间"), "checkDate");
		this.getGridConfig().addColumn("申请的课程", "applyNote", true, false,
				false, "TextField", false, 100);
		this.getGridConfig().addRenderScript(this.getText("申请的课程"),
				"{var va=${value};var num = va.indexOf(','); var n = va.length; " +
				"if (n>num) { return va.substring(num+1,n);} else{return '';}}",
				"applyNote");
//		this.getGridConfig().addRenderFunction(this.getText("查看申请课程"),
//				"<a href=\"/entity/studentStatus/prStudentGoodApply_viewNote.action?bean.id=${value}\" target=\"_blank\">申请课程</a>",
//				"id");
	}
	
	public Map updateColumn() {
		
		Map map = new HashMap();
		int count = 0;
		if (this.getIds() != null && this.getIds().length() > 0) {
//			getIds().substring(beginIndex, endIndex)
			String[] ids = getIds().split(",");
			List idList = new ArrayList();
			
			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}
			try {
				if (this.getColumn().equals("CheckForPass")) {
					count = this.getSystemApplyService().updateAvoidPass(idList);
				} else if (this.getColumn().equals("CancelForPass")) {
					count = this.getSystemApplyService().updateAvoidCancelPass(idList);
				} else if (this.getColumn().equals("CheckForNoPass")) {
					count = this.getSystemApplyService().updateAvoidNoPass(idList);
				} else if (this.getColumn().equals("CancelForNoPass")) {
					count = this.getSystemApplyService().updateAvoidCancelNoPass(idList);
				}
			} catch (EntityException e) {
				e.printStackTrace();
				map.put("success", "false");
				map.put("info", e.getMessage());
				return map;
			}
			map.clear();
			map.put("success", "true");
			map.put("info", this.getText(String.valueOf(count)+"条记录操作成功"));

		} else {
			map.put("success", "false");
			map.put("info", "parameter value error");
		}
		return map;
	}
	@Override
	public void setServletPath() {
		this.servletPath="/entity/exam/examAvoidApply";
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
		dc.createAlias("enumConstByFlagApplyStatus", "enumConstByFlagApplyStatus")
		  .createAlias("enumConstByApplyType", "enumConstByApplyType")
		  .add(Restrictions.and(Restrictions.eq("enumConstByApplyType.namespace", "ApplyType"), Restrictions.eq("enumConstByApplyType.code", "15")))
			.createCriteria("peStudent", "peStudent")
			.createAlias("prStudentInfo", "prStudentInfo").createAlias("peSite", "peSite")
			.createAlias("peMajor", "peMajor")
			.createAlias("peEdutype","peEdutype")
			.createAlias("peGrade", "peGrade")
			.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")
			.add(Restrictions.ge("enumConstByFlagStudentStatus.code", "4"))
			;
		return dc;
	}

	public SystemApplyService getSystemApplyService() {
		return systemApplyService;
	}

	public void setSystemApplyService(SystemApplyService systemApplyService) {
		this.systemApplyService = systemApplyService;
	}
	

}
