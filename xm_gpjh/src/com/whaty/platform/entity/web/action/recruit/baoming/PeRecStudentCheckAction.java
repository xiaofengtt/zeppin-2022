package com.whaty.platform.entity.web.action.recruit.baoming;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.bean.PeRecruitplan;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.first.PeRecStudentBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

/**
 * 学生报名信息审核
 * 
 * @author 李冰
 * 
 */
public class PeRecStudentCheckAction extends PeRecStudentBaseAction {

	@Override
	public void initGrid() {
		UserSession us = (UserSession)ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String userLoginType = "";//保存管理员类型
		if (us!=null) {
			userLoginType = us.getUserLoginType();
		}
		
		this.getGridConfig().setTitle(this.getText("报名信息审核"));
		this.getGridConfig().setCapability(false, false, false);

		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name");
		this.getGridConfig().addColumn(this.getText("性别"), "gender");
		this.getGridConfig().addColumn(this.getText("证件类型"), "cardType");
		this.getGridConfig().addColumn(this.getText("证件号码"), "cardNo");
		this.getGridConfig().addColumn(this.getText("招生批次"),
				"prRecPlanMajorSite.prRecPlanMajorEdutype.peRecruitplan.name");
		this.getGridConfig().addColumn(this.getText("学生状态"),
				"enumConstByFlagRecStatus.name");
		this.getGridConfig().addColumn(this.getText("是否免试生"),
				"enumConstByFlagNoexam.name");
		this.getGridConfig().addColumn(this.getText("录取状态"),
				"enumConstByFlagMatriculate.name");
		this.getGridConfig().addColumn(this.getText("学习中心"),
				"prRecPlanMajorSite.peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"),
				"prRecPlanMajorSite.prRecPlanMajorEdutype.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"),
				"prRecPlanMajorSite.prRecPlanMajorEdutype.peMajor.name");
		// this.getGridConfig().addRenderFunction(this.getText("打印报名表"), "<a
		// href=\"/entity/recruit/recStudent_pdfPrint.action?student.id=${value}\"
		// target=\"_blank\">打印报名表</a>", "id");
		this
				.getGridConfig()
				.addRenderFunction(
						this.getText("操作"),
						"<a href=\"/entity/recruit/recruitStu_viewDetail.action?bean.id=${value}\" target=\"_blank\">查看详细信息</a>",
						"id");
		EnumConst enumConstByFlagRecStatus = this.getMyListService()
				.getEnumConstByNamespaceCode("FlagRecStatus", "1");
		this.getGridConfig()
				.addMenuFunction(this.getText("确认交费"),
						"enumConstByFlagRecStatus.id",
						enumConstByFlagRecStatus.getId());
//		enumConstByFlagRecStatus = this.getMyListService()
//				.getEnumConstByNamespaceCode("FlagRecStatus", "2");
//		this.getGridConfig()
//				.addMenuFunction(this.getText("提交总校"),
//						"enumConstByFlagRecStatus.id",
//						enumConstByFlagRecStatus.getId());
		if(userLoginType.equals("3")){
		this.getGridConfig().addMenuFunction(
				this.getText("审核通过"),
				"enumConstByFlagRecStatus.id",
				this.getMyListService().getEnumConstByNamespaceCode(
						"FlagRecStatus", "3").getId());
		this.getGridConfig().addMenuFunction(
				this.getText("审核不通过"),
				"enumConstByFlagRecStatus.id",
				this.getMyListService().getEnumConstByNamespaceCode(
						"FlagRecStatus", "4").getId());
		}

	}

	public void checkBeforeUpdateColumn(List idList) throws EntityException {
		this.checkTime();
		if (!this.isActive(idList)) {
			throw new EntityException("只能操作当前活动招生批次下的数据!");
		}
		EnumConst enumConst = (EnumConst) this.getMyListService().getById(
				EnumConst.class, this.getValue());
		List<PeRecStudent> peRecStudentList = this.getMyListService().getByIds(
				PeRecStudent.class, idList);

		if (enumConst.getCode().equals("1")) {
			for (PeRecStudent peRecStudent : peRecStudentList) {
				if (!peRecStudent.getEnumConstByFlagRecStatus().getCode()
						.equals("0"))
					throw new EntityException("只能操作未审核状态的学生！");
			}
		} else if (enumConst.getCode().equals("2")) {
			for (PeRecStudent peRecStudent : peRecStudentList) {
				if (!(peRecStudent.getEnumConstByFlagRecStatus().getCode()
						.equals("1") || peRecStudent
						.getEnumConstByFlagRecStatus().getCode().equals("4")))
					throw new EntityException("只能操作已交费和驳回状态的学生！");
			}
		} else if (enumConst.getCode().equals("3")) {
			for (PeRecStudent peRecStudent : peRecStudentList) {
				if (!(peRecStudent.getEnumConstByFlagRecStatus().getCode()
						.equals("1")|| peRecStudent
						.getEnumConstByFlagRecStatus().getCode().equals("4")))
					throw new EntityException("只能操作已交费和审核不通过状态的学生！");
			}
		} else if (enumConst.getCode().equals("4")) {
			for (PeRecStudent peRecStudent : peRecStudentList) {
				if (peRecStudent.getEnumConstByFlagMatriculate().getCode().equals("1")){
					throw new EntityException("不能操作已录取的学生！");
				}
			}
		} else {
			throw new EntityException("操作失败！");
		}

	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeRecStudent.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/peRecStudentCheck";
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
				"prRecPlanMajorSite", "prRecPlanMajorSite").createAlias(
				"peSite", "peSite").createCriteria("prRecPlanMajorEdutype",
				"prRecPlanMajorEdutype");
		dcMajorEdutype.createAlias("peEdutype", "peEdutype").createAlias(
				"peMajor", "peMajor");
		dcMajorEdutype.createAlias("peRecruitplan", "peRecruitplan");
		dc.createCriteria("enumConstByFlagRecStatus",
				"enumConstByFlagRecStatus", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagNoexam", "enumConstByFlagNoexam");
		dc.createCriteria("enumConstByFlagMatriculate",
				"enumConstByFlagMatriculate");
		return dc;
	}

}
