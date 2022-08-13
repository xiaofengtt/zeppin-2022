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
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.first.PeRecStudentBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

public class PeRecStudentNoexamCheckAction extends PeRecStudentBaseAction {

	@Override
	public void initGrid() {
		UserSession us = (UserSession)ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String userLoginType = "";//保存管理员类型
		if (us!=null) {
			userLoginType = us.getUserLoginType();
		}
		
		this.getGridConfig().setTitle(this.getText("免试资格审核"));
		this.getGridConfig().setCapability(false, false, false);

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
				"enumConstByFlagRecStatus.name",false);
		this.getGridConfig().addColumn(this.getText("录取状态"),
				"enumConstByFlagMatriculate.name");
		ColumnConfig column1 = new ColumnConfig(this.getText("免试审核状态"), "enumConstByFlagNoexamStatus.name");
		column1.setComboSQL(" select t.id,t.name from enum_const t where t.namespace='FlagNoexamStatus' and t.code!='3'   ");
		this.getGridConfig().addColumn(column1);
		this
				.getGridConfig()
				.addRenderFunction(
						this.getText("查看详细信息"),
						"<a href=\"/entity/recruit/recruitStu_viewDetail.action?bean.id=${value}\" target=\"_blank\">查看报名信息</a>",
						"id");
		this.getGridConfig().addMenuFunction(this.getText("分站审核通过"), "enumConstByFlagNoexamStatus.id",
				this.getMyListService().getEnumConstByNamespaceCode("FlagNoexamStatus", "5").getId());
		this.getGridConfig().addMenuFunction(this.getText("分站审核不通过"), "enumConstByFlagNoexamStatus.id",
				this.getMyListService().getEnumConstByNamespaceCode("FlagNoexamStatus", "4").getId());
		if(userLoginType.equals("3")){
		EnumConst enumConstByFlagNoexamStatus = this.getMyListService()
				.getEnumConstByNamespaceCode("FlagNoexamStatus", "1");
		this.getGridConfig().addMenuFunction(this.getText("总站审核通过"), "enumConstByFlagNoexamStatus.id",
				enumConstByFlagNoexamStatus.getId());
		enumConstByFlagNoexamStatus = this.getMyListService()
				.getEnumConstByNamespaceCode("FlagNoexamStatus", "0");
		this.getGridConfig().addMenuFunction(this.getText("总站审核不通过"), "enumConstByFlagNoexamStatus.id",
				enumConstByFlagNoexamStatus.getId());

		this.getGridConfig().addMenuFunction(this.getText("未通过审核的设置为考试生"), "enumConstByFlagNoexam.id",
				this.getMyListService().getEnumConstByNamespaceCode("FlagNoexam", "0").getId());
		}
	}
	
	public void checkBeforeUpdateColumn(List idList) throws EntityException {
		this.checkTime();
		
			if (!this.isActive(idList)) {
				throw new EntityException("只能操作当前活动招生批次下的数据!");
			}
			List<PeRecStudent> peRecStudentList = this.getMyListService().getByIds(
					PeRecStudent.class, idList);
			//判断如果是通过审核的操作，需要检查学生状态为已审核的学生
			EnumConst enumConst = (EnumConst) this.getMyListService().getById(
					EnumConst.class, this.getValue());
			// 如果是改为考试生的操作，需判断免试资格审核状态是否未通过。
			if (this.getColumn().equals("enumConstByFlagNoexam.id")) {
				for (PeRecStudent peRecStudent : peRecStudentList) {
					if (!peRecStudent.getEnumConstByFlagNoexamStatus().getCode().equals("0")
							&&!peRecStudent.getEnumConstByFlagNoexamStatus().getCode().equals("4")) {
						throw new EntityException("只能操作免试资格审核不通过的学生!");
					}
				}
			}else {
			if (enumConst.getCode().equals("1")) {
				for (PeRecStudent peRecStudent : peRecStudentList) {
					if (peRecStudent.getEnumConstByFlagMatriculate().getCode().equals("1")) {
						throw new EntityException("包含已录取的学生，无法完成操作!");
					}
					if (!peRecStudent.getEnumConstByFlagRecStatus().getCode().equals("3")) {
						throw new EntityException("只能操作学生状态已审核的学生!");	
					}
					if(!peRecStudent.getEnumConstByFlagNoexamStatus().getCode().equals("5")
							&&!peRecStudent.getEnumConstByFlagNoexamStatus().getCode().equals("0")){
						throw new EntityException("只能操作分站审核通过或总站审核不通过的学生!");	
					}
				}
			} else if(enumConst.getCode().equals("0")){
				for (PeRecStudent peRecStudent : peRecStudentList) {
					if (peRecStudent.getEnumConstByFlagMatriculate().getCode().equals("1")) {
						throw new EntityException("包含已录取的学生，无法完成操作!");
					}
					if(!peRecStudent.getEnumConstByFlagNoexamStatus().getCode().equals("5")
							&&!peRecStudent.getEnumConstByFlagNoexamStatus().getCode().equals("1")){
						throw new EntityException("只能操作分站审核通过或总站审核通过的学生!");	
					}
				}
				
			} else if(enumConst.getCode().equals("5")){
				for (PeRecStudent peRecStudent : peRecStudentList) {
					if (peRecStudent.getEnumConstByFlagMatriculate().getCode().equals("1")) {
						throw new EntityException("包含已录取的学生，无法完成操作!");
					}
					if (!peRecStudent.getEnumConstByFlagRecStatus().getCode().equals("3")) {
						throw new EntityException("只能操作学生状态已审核的学生!");	
					}
					if(!peRecStudent.getEnumConstByFlagNoexamStatus().getCode().equals("2")
							&&!peRecStudent.getEnumConstByFlagNoexamStatus().getCode().equals("0")
							&&!peRecStudent.getEnumConstByFlagNoexamStatus().getCode().equals("4")){
						throw new EntityException("只能操作未审核或分站审核不通过或总站审核不通过的学生!");	
					}
				}
				
			} else if(enumConst.getCode().equals("4")){
				for (PeRecStudent peRecStudent : peRecStudentList) {
					if (peRecStudent.getEnumConstByFlagMatriculate().getCode().equals("1")) {
						throw new EntityException("包含已录取的学生，无法完成操作!");
					}
					if(!peRecStudent.getEnumConstByFlagNoexamStatus().getCode().equals("5")
							&&!peRecStudent.getEnumConstByFlagNoexamStatus().getCode().equals("2")){
						throw new EntityException("只能操作未审核或分站审核通过的学生!");	
					}
				}
			}

			}	
	}
	public void setBean(PeRecStudent instance) {
		super.superSetBean(instance);

	}

	public PeRecStudent getBean() {
		return (PeRecStudent) super.superGetBean();
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
		dc.createCriteria("enumConstByFlagRecStatus","enumConstByFlagRecStatus")
			.add(Restrictions.eq("code", "3"));
		dc.createCriteria("enumConstByFlagNoexamStatus",
				"enumConstByFlagNoexamStatus", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagMatriculate", "enumConstByFlagMatriculate");
		DetachedCriteria dcEnumConstByFlagNoexam = dc.createCriteria(
				"enumConstByFlagNoexam", "enumConstByFlagNoexam");
		dcEnumConstByFlagNoexam.add(Restrictions.eq("code", "1"));
		return dc;
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeRecStudent.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/peRecStudentNoexamCheck";
	}

}
