package com.whaty.platform.entity.web.action.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.SystemApply;
import com.whaty.platform.entity.exception.EntityException;
/**
 * 统考计算机的免考审核
 * @author 李冰
 *
 */
public class ComputerAvoidApplyAction extends EnglishAAvoidApplyAction {
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
				if (this.getColumn().equals("CheckForPass")) {
					count = this.getSystemApplyService().updateUniteExamPass(idList,"C");
				} else if (this.getColumn().equals("CancelForPass")) {
					count = this.getSystemApplyService().updateUniteExamCancelPass(idList,"C");
				} else if (this.getColumn().equals("CheckForNoPass")) {
					count = this.getSystemApplyService().updateUniteExamNoPass(idList);
				} else if (this.getColumn().equals("CancelForNoPass")) {
					count = this.getSystemApplyService().updateUniteExamCancelNoPass(idList);
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
		this.servletPath="/entity/exam/computerAvoidApply";
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
		dc.createAlias("enumConstByFlagApplyStatus", "enumConstByFlagApplyStatus")
		  .createAlias("enumConstByApplyType", "enumConstByApplyType")
		  .add(Restrictions.and(Restrictions.eq("enumConstByApplyType.namespace", "ApplyType"), Restrictions.eq("enumConstByApplyType.code", "6")))
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
}
