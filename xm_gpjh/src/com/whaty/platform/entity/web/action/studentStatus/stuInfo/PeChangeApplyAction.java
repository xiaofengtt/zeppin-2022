package com.whaty.platform.entity.web.action.studentStatus.stuInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.SystemApply;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.SystemApplyService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.SystemApplayAction;

public class PeChangeApplyAction extends SystemApplayAction {

	private SystemApplyService systemApplyService;
	
	public SystemApplyService getSystemApplyService() {
		return systemApplyService;
	}

	public void setSystemApplyService(SystemApplyService systemApplyService) {
		this.systemApplyService = systemApplyService;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		
		this.getGridConfig().addMenuFunction(this.getText("审核通过"), "reapply",
		"");
		this.getGridConfig().addMenuFunction(this.getText("审核不通过"),
		"rejection", "");
		this.getGridConfig().setTitle("学籍变动申请列表");
		
		this.getGridConfig().addColumn(this.getText("id"), "id" , false);
		this.getGridConfig().addColumn(this.getText("学号"), "peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("学习中心"), "peStudent.peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"), "peStudent.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"), "peStudent.peMajor.name");
		this.getGridConfig().addColumn(this.getText("申请时间"),"applyDate");
		this.getGridConfig().addColumn(this.getText("审核状态"), "enumConstByFlagApplyStatus.name");
		//20090413 修改异动类型，本页面仅需要转专业 转层次 转学习中心3项(龚妮娜) 
		ColumnConfig column = new ColumnConfig(this.getText("异动类型"),"enumConstByApplyType.name");		
		column.setComboSQL("select t.id,t.name from enum_const t where t.namespace = 'ApplyType' and t.code in('12','13','14')");
		this.getGridConfig().addColumn(column);		
		//this.getGridConfig().addColumn(this.getText("异动类型"), "enumConstByApplyType.name");
		this.getGridConfig().addColumn("yd", "applyNote",false,false,false,"TextField",true,50);
		this.getGridConfig().addRenderScript("异动新学籍", "{var str=${value}; return str.substring(0,str.indexOf(':'))}", "applyNote");
//		this.getGridConfig().addColumn("原因","applyNote",false,false,false,"TextField",true,50);
//		this.getGridConfig().addRenderScript("原因", "{var str=${value}; return str.substring(str.indexOf(':')+1,str.length)}", "applyNote");
		this.getGridConfig().addRenderFunction(this.getText("原因"),
				"<a href=\"/entity/studentStatus/peChangeApply_viewDetail.action?bean.id=${value}\" target=\"_blank\">查看原因</a>",
				"id");
	}
	/**
	 * 转向查看详细页面
	 * 
	 * @return
	 */
	public String viewDetail() {
		try {
		 this.setBean((SystemApply)this.getGeneralService().getById(this.getBean().getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "detail";
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = SystemApply.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/studentStatus/peChangeApply";
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
		dc.createCriteria("enumConstByApplyType", "enumConstByApplyType");
		dc.createCriteria("enumConstByFlagApplyStatus", "enumConstByFlagApplyStatus");
		dc.createCriteria("peStudent", "peStudent").createAlias("peSite", "peSite").createAlias("peEdutype", "peEdutype").createAlias("peMajor", "peMajor");
		dc.add(Restrictions.or(Restrictions.eq("enumConstByApplyType.code", "12"), Restrictions.or(Restrictions.eq("enumConstByApplyType.code", "13"), Restrictions.eq("enumConstByApplyType.code", "14"))));
		return dc;
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
				if(this.getColumn().equals("reapply")){
					count = this.getSystemApplyService().updateForChangeStudentApply(idList,true);
					map.put("success", "true");
					map.put("info", this.getText(String.valueOf(count)+"个学生通过学籍异动审核。"));
				}else if(this.getColumn().equals("rejection")){
					count = this.getSystemApplyService().updateForChangeStudentApply(idList,false);
					map.put("success", "true");
					map.put("info", this.getText(String.valueOf(count)+"个学生没有通过学籍异动审核。"));					
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
