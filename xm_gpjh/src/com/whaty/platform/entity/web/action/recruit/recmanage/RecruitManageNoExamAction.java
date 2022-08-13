package com.whaty.platform.entity.web.action.recruit.recmanage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.recruit.recmanage.RecruitManageMatriculatService;
import com.whaty.platform.entity.web.action.first.PeRecStudentBaseAction;

public class RecruitManageNoExamAction extends PeRecStudentBaseAction {
	RecruitManageMatriculatService recruitManageMatriculatService;

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);

		this.getGridConfig().setTitle(this.getText("免试生录取"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name");
		this.getGridConfig().addColumn(this.getText("性别"), "gender");
		this.getGridConfig().addColumn(this.getText("证件类型"), "cardType");
		this.getGridConfig().addColumn(this.getText("证件号码"), "cardNo");
		this.getGridConfig().addColumn(this.getText("学生状态"),
				"enumConstByFlagRecStatus.name",false,false,true,"");
		this.getGridConfig().addColumn(this.getText("是否免试生"),
				"enumConstByFlagNoexam.name",false,false,true,"");
		this.getGridConfig().addColumn(this.getText("免试审核状态"),
				"enumConstByFlagNoexamStatus.name",false,false,true,"");
		this.getGridConfig().addColumn(this.getText("录取状态"),
				"enumConstByFlagMatriculate.name");
		this.getGridConfig().addColumn(this.getText("学历验证状态"),"enumConstByFlagXueliCheck.name");
		this.getGridConfig().addColumn(this.getText("学习中心"),
				"prRecPlanMajorSite.peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"),
				"prRecPlanMajorSite.prRecPlanMajorEdutype.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"),
				"prRecPlanMajorSite.prRecPlanMajorEdutype.peMajor.name");
		this.getGridConfig().addMenuFunction(
				"录取",
				"enumConstByFlagMatriculate.id",
				this.getMyListService().getEnumConstByNamespaceCode(
						"FlagMatriculate", "1").getId());

	}

	public void checkBeforeUpdateColumn(List idList) throws EntityException {
		if (idList != null && idList.size() > 0) {
			if (!this.checkNoExamDate()) {
				throw new EntityException("免试生录取操作日期应在招生考试批次开始-录取结束的时间范围内!");
			}
			List<PeRecStudent> list = this.getMyListService().getByIds(
					PeRecStudent.class, idList);
			for (PeRecStudent peRecStudent : list) {
				if (!peRecStudent.getEnumConstByFlagNoexamStatus().getCode()
						.equals("1"))
					throw new EntityException("只能操作通过免试审核的学生，请重新选择！");
				if (peRecStudent.getEnumConstByFlagMatriculate().getCode()
						.equals("1"))
					throw new EntityException("包含已录取的学生，无法完成操作，请重新选择！");
				if (!peRecStudent.getEnumConstByFlagRecStatus().getCode().equals("3"))
					throw new EntityException("只能操作学生状态为已审核的学生，请重新选择！");
			}
		} else {
			throw new EntityException("请选择至少一条记录");
		}
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
				checkBeforeUpdateColumn(idList);
			} catch (EntityException e) {
				map.clear();
				map.put("success", "false");
				map.put("info", e.getMessage());
				return map;
			}
			try {
				List<PeRecStudent> peRecStudentList = this.getMyListService()
						.getByIds(PeRecStudent.class, idList);
				msg = this.getRecruitManageMatriculatService().saveMatriculat(
						peRecStudentList, this.getValue());
			} catch (Exception e) {
				e.printStackTrace();
				map.clear();
				map.put("success", "false");
				map.put("info", this.getText("操作失败"));
				return map;
			}
			map.clear();
			map.put("success", "true");
			map.put("info", msg);

		} else {
			map.put("success", "false");
			map.put("info", "parameter value error");
		}
		return map;
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeRecStudent.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/recruitManageNoExam";

	}

	public void setBean(PeRecStudent instance) {
		super.superSetBean(instance);

	}

	public PeRecStudent getBean() {
		return (PeRecStudent) super.superGetBean();
	}

	public RecruitManageMatriculatService getRecruitManageMatriculatService() {
		return recruitManageMatriculatService;
	}

	public void setRecruitManageMatriculatService(
			RecruitManageMatriculatService recruitManageMatriculatService) {
		this.recruitManageMatriculatService = recruitManageMatriculatService;
	}

	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeRecStudent.class);
		DetachedCriteria dcMajorEdutype = dc.createCriteria(
				"prRecPlanMajorSite", "prRecPlanMajorSite")
				.createAlias("peSite", "peSite")
				.createCriteria("prRecPlanMajorEdutype",
						"prRecPlanMajorEdutype");
		dcMajorEdutype.createAlias("peEdutype", "peEdutype").createAlias(
				"peMajor", "peMajor");
		dcMajorEdutype.createCriteria("peRecruitplan", "peRecruitplan").add(
				Restrictions.eq("flagActive", "1"));
		DetachedCriteria dcEnumConstByFlagRecStatus = dc.createCriteria("enumConstByFlagRecStatus",
				"enumConstByFlagRecStatus");
		dcEnumConstByFlagRecStatus.add(Restrictions.eq("code", "3"));
		DetachedCriteria dcEnumConstByFlagNoexam = dc.createCriteria(
				"enumConstByFlagNoexam", "enumConstByFlagNoexam");
		DetachedCriteria dcEnumConstByFlagNoexamStatus = dc.createCriteria(
				"enumConstByFlagNoexamStatus", "enumConstByFlagNoexamStatus");
		String[] codes = new String[]{"2","3"};
		dc.createCriteria("enumConstByFlagXueliCheck",
				"enumConstByFlagXueliCheck").add(Restrictions.in("code", codes));
		dcEnumConstByFlagNoexamStatus.add(Restrictions.eq("code", "1"));
		dc.createCriteria("enumConstByFlagMatriculate",
				"enumConstByFlagMatriculate");
		dcEnumConstByFlagNoexam.add(Restrictions.eq("code", "1"));
		return dc;
	}
}
