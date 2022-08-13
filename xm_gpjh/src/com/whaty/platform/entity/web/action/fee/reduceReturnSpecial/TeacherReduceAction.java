package com.whaty.platform.entity.web.action.fee.reduceReturnSpecial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.fee.PrFeeDetailReduceService;
import com.whaty.platform.entity.web.action.MyBaseAction;
/**
 * 教师减免费用
 * @author 李冰
 *
 */
public class TeacherReduceAction extends MyBaseAction {
	private PrFeeDetailReduceService prFeeDetailReduceService;
	

	@Override
	public void initGrid() {
		this.getGridConfig().addMenuFunction("减免费用","reduce", "");
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("教师减免费用"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"),"regNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("年级"), "peGrade.name");
		this.getGridConfig().addColumn(this.getText("学习中心"), "peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"), "peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"), "peMajor.name");
		this.getGridConfig().addColumn(this.getText("学生状态"), "enumConstByFlagStudentStatus.name");
		this.getGridConfig().addColumn(this.getText("是否具有教师资格"), "peRecStudent.enumConstByFlagTeacher.name",false);
		this.getGridConfig().addColumn(this.getText("教师资格审核状态"), "peRecStudent.enumConstByFlagTeacherStatus.name",false);
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
				if(this.getColumn().equals("reduce")) {
					int count = this.getPrFeeDetailReduceService().saveTeacherReduce(idList);
					map.put("success", "true");
					map.put("info", "成功操作"+count+"条记录！");
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
	@Override
	public void setEntityClass() {
		this.entityClass = PeStudent.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/fee/teacherReduce";

	}
	public void setBean(PeStudent instance) {
		super.superSetBean(instance);

	}

	public PeStudent getBean() {
		return (PeStudent) super.superGetBean();
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		String sql = "  select t.fk_stu_id "
		+ "  from pr_fee_detail t, enum_const const "
		+ "   where t.flag_fee_type = const.id "
		+ "    and const.code = '7' ";
		List<String> list = null;
		try {
			list = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		DetachedCriteria dcPeRecStudent =	dc.createCriteria("peRecStudent", "peRecStudent");
		dc.createAlias("prStudentInfo", "prStudentInfo").createAlias("peSite", "peSite")
		.createAlias("peMajor", "peMajor")
		.createAlias("peEdutype","peEdutype")
		.createAlias("peGrade", "peGrade")
		.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus").add(Restrictions.eq("enumConstByFlagStudentStatus.code", "4"));
		dcPeRecStudent.createCriteria("enumConstByFlagTeacher", "enumConstByFlagTeacher").add(Restrictions.eq("code", "1"));
		dcPeRecStudent.createCriteria("enumConstByFlagTeacherStatus","enumConstByFlagTeacherStatus").add(Restrictions.eq("code", "1"));
		if(list!=null&&list.size()>0){
			dc.add(Restrictions.not(Restrictions.in("id", list)));
		}
		return dc;
	}

	public PrFeeDetailReduceService getPrFeeDetailReduceService() {
		return prFeeDetailReduceService;
	}
	public void setPrFeeDetailReduceService(
			PrFeeDetailReduceService prFeeDetailReduceService) {
		this.prFeeDetailReduceService = prFeeDetailReduceService;
	}
}
