package com.whaty.platform.entity.web.action.recruit.baoming;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.bean.PeRecruitplan;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrFeeDetail;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.first.PeRecStudentBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

/**
 * 教师资格审查action 只显示具有教师资格的学生
 * 
 * @author 李冰
 * 
 */
public class PeRecStudentTeacherCheckAction extends PeRecStudentBaseAction {

	@Override
	public void initGrid() {

		this.getGridConfig().setTitle(this.getText("教师资格审查"));
		this.getGridConfig().setCapability(false, false, false, true);

		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name");
		this.getGridConfig().addColumn(this.getText("性别"), "gender");
		this.getGridConfig().addColumn(this.getText("证件类型"), "cardType");
		this.getGridConfig().addColumn(this.getText("证件号码"), "cardNo");
		this.getGridConfig().addColumn(this.getText("招生批次"),
				"prRecPlanMajorSite.prRecPlanMajorEdutype.peRecruitplan.name");
		this.getGridConfig().addColumn(this.getText("学习中心"),
				"prRecPlanMajorSite.peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"),
				"prRecPlanMajorSite.prRecPlanMajorEdutype.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"),
				"prRecPlanMajorSite.prRecPlanMajorEdutype.peMajor.name");
		this.getGridConfig().addColumn(this.getText("学生状态"),
				"enumConstByFlagRecStatus.name");
		this.getGridConfig().addColumn(this.getText("录取状态"),
				"enumConstByFlagMatriculate.name");
		ColumnConfig column1 = new ColumnConfig(this.getText("教师资格审核状态"), "enumConstByFlagTeacherStatus.name");
		column1.setComboSQL(" select t.id,t.name from enum_const t where t.namespace='FlagTeacherStatus' and t.code!='3'    ");
		this.getGridConfig().addColumn(column1);		
		this.getGridConfig().addRenderFunction(this.getText("查看报名信息"),
						"<a href=\"/entity/recruit/recruitStu_viewDetail.action?bean.id=${value}\" target=\"_blank\">查看报名信息</a>",
						"id");
		EnumConst enumConstByTeacherStatus = this.getMyListService()
				.getEnumConstByNamespaceCode("FlagTeacherStatus", "1");
		this.getGridConfig().addMenuFunction(this.getText("审核通过"), "enumConstByFlagTeacherStatus.id",
				enumConstByTeacherStatus.getId());
		enumConstByTeacherStatus = this.getMyListService()
				.getEnumConstByNamespaceCode("FlagTeacherStatus", "0");
		this.getGridConfig().addMenuFunction(this.getText("审核不通过"), "enumConstByFlagTeacherStatus.id",
				enumConstByTeacherStatus.getId());
	}

	public void checkBeforeUpdateColumn(List idList) throws EntityException {

//			this.checkTime();
//			if (!this.isActive(idList)) {
//				throw new EntityException("只能操作当前活动招生批次下的数据!");
//			}
			List<PeRecStudent> peRecStudentList = this.getMyListService().getByIds(
					PeRecStudent.class, idList);
			//判断如果是通过审核的操作，需要检查学生状态为已审核的学生
			EnumConst enumConst = (EnumConst) this.getMyListService().getById(
					EnumConst.class, this.getValue());
			if (enumConst.getCode().equals("1")) {
//				for (PeRecStudent peRecStudent : peRecStudentList) {
//					if (!peRecStudent.getEnumConstByFlagRecStatus().getCode().equals("3")) {
//						throw new EntityException("只能操作学生状态已审核的学生!");	
//					}
//				}
			} 
				else {
				for (PeRecStudent peRecStudent : peRecStudentList) {
					if (peRecStudent.getEnumConstByFlagMatriculate().getCode().equals("1")) {
						DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
						dc.createCriteria("peRecStudent", "peRecStudent").add(Restrictions.eq("id", peRecStudent.getId()));
						List<PeStudent> studentList = this.getGeneralService().getList(dc);
						if(studentList!=null&&studentList.size()>0){
							DetachedCriteria dcFee = DetachedCriteria.forClass(PrFeeDetail.class);
							dcFee.add(Restrictions.eq("peStudent", studentList.get(0)));
							List<PrFeeDetail> feeList = this.getGeneralService().getList(dcFee);
							if(feeList!=null&&feeList.size()>0){
								throw new EntityException("学生"+peRecStudent.getName()+"已经通过教师减免费用操作，无法改变审核状态!");
							}
							
						}
						
					}
				}
			}
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/peRecStudentTeacherCheck";
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeRecStudent.class;
	}

	public void setBean(PeRecStudent instance) {
		super.superSetBean(instance);

	}

	public PeRecStudent getBean() {
		return super.superGetBean();
	}

	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeRecStudent.class);
		DetachedCriteria dcMajorEdutype = dc.createCriteria(
				"prRecPlanMajorSite", "prRecPlanMajorSite",
				DetachedCriteria.LEFT_JOIN).createAlias("peSite", "peSite")
				.createCriteria("prRecPlanMajorEdutype",
						"prRecPlanMajorEdutype");
		dcMajorEdutype.createAlias("peEdutype", "peEdutype").createAlias(
				"peMajor", "peMajor");
		dcMajorEdutype.createAlias("peRecruitplan", "peRecruitplan");
		dc.createCriteria("enumConstByFlagRecStatus",
				"enumConstByFlagRecStatus", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagNoexam", "enumConstByFlagNoexam",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagMatriculate", "enumConstByFlagMatriculate");
		DetachedCriteria dcEnumConstByFlagTeacher = dc.createCriteria(
				"enumConstByFlagTeacher", "enumConstByFlagTeacher");
		dc.createCriteria("enumConstByFlagTeacherStatus",
				"enumConstByFlagTeacherStatus", DetachedCriteria.LEFT_JOIN);
		dcEnumConstByFlagTeacher.add(Restrictions.eq("code", "1"));
		return dc;
	}

}
