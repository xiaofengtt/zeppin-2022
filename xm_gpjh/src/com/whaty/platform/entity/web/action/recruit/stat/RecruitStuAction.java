package com.whaty.platform.entity.web.action.recruit.stat;

import java.text.SimpleDateFormat;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeFeeLevel;
import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 报名信息查询
 * 
 * @author 李冰
 * 
 */
public class RecruitStuAction extends MyBaseAction {
	private PeRecStudent student;
	String examStudent;
	/**
	 * 转向查看详细页面
	 * 
	 * @return
	 */
	public String viewDetail() {
		try {
			this.setStudent((PeRecStudent)this.getGeneralService().getById(this.getBean().getId()));
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg("操作失败");
			this.setTogo("back");
			return "msg";
		}
		String str = this.getStudent().getEnumConstByFlagNoexam().getCode();
		if (str.equals("1")) {
			this.setExamStudent("免试生");
		} else {
			this.setExamStudent("考试生");
		}
		return "viewDetail";
	}

	@Override
	public void initGrid() {

		this.getGridConfig().setTitle(this.getText("学生信息"));
		this.getGridConfig().setCapability(false, false, false, true);

		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name");
		this.getGridConfig().addColumn(this.getText("性别"), "gender");
		this.getGridConfig().addColumn(this.getText("证件类型"), "cardType");
		this.getGridConfig().addColumn(this.getText("证件号码"), "cardNo");
		this.getGridConfig().addColumn(this.getText("招生批次"),
				"prRecPlanMajorSite.prRecPlanMajorEdutype.peRecruitplan.name");

		this.getGridConfig().addColumn(this.getText("是否免试生"),
				"enumConstByFlagNoexam.name");
		 this.getGridConfig().addColumn(this.getText("学生状态"),
		 "enumConstByFlagRecStatus.name");
		this.getGridConfig().addColumn(this.getText("录取状态"),
				"enumConstByFlagMatriculate.name");
		this.getGridConfig().addColumn(this.getText("学习中心"),
				"prRecPlanMajorSite.peSite.name");
		this.getGridConfig().addColumn(this.getText("层次"),
				"prRecPlanMajorSite.prRecPlanMajorEdutype.peEdutype.name");
		this.getGridConfig().addColumn(this.getText("专业"),
				"prRecPlanMajorSite.prRecPlanMajorEdutype.peMajor.name");
		this.getGridConfig().addColumn(this.getText("专业备注"),"enumConstByFlagMajorType.name");
		this
				.getGridConfig()
				.addRenderFunction(
						this.getText("查看报名信息"),
						"<a href=\"/entity/recruit/recruitStu_viewDetail.action?bean.id=${value}\" target=\"_blank\">查看报名信息</a>",
						"id");
		this
				.getGridConfig()
				.addRenderFunction(
						this.getText("修改报名信息"),
						"<a href=\"/entity/recruit/addPeRecStudent_toEditStudent.action?bean.id=${value}\" target=\"_blank\">修改报名信息</a>",
						"id");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeRecStudent.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/recruitStu";
	}

	public void setBean(PeRecStudent instance) {
		super.superSetBean(instance);

	}

	public PeRecStudent getBean() {
		return (PeRecStudent) super.superGetBean();
	}

	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeRecStudent.class);
		DetachedCriteria dcMajorEdutype = dc.createCriteria("prRecPlanMajorSite", "prRecPlanMajorSite")
		.createAlias("peSite", "peSite")
				.createCriteria("prRecPlanMajorEdutype",
						"prRecPlanMajorEdutype");
		dcMajorEdutype.createAlias("peEdutype", "peEdutype").createAlias(
				"peMajor", "peMajor");
		dcMajorEdutype.createAlias("peRecruitplan", "peRecruitplan");
		dc.createCriteria("enumConstByFlagRecStatus",
				"enumConstByFlagRecStatus");
		dc.createCriteria("enumConstByFlagNoexam", "enumConstByFlagNoexam");
		dc.createCriteria("enumConstByFlagMatriculate", "enumConstByFlagMatriculate");
		dc.createAlias("enumConstByFlagMajorType", "enumConstByFlagMajorType", DetachedCriteria.LEFT_JOIN);
		return dc;
	}

	public PeRecStudent getStudent() {
		return student;
	}

	public void setStudent(PeRecStudent student) {
		this.student = student;
	}

	public String getExamStudent() {
		return examStudent;
	}

	public void setExamStudent(String examStudent) {
		this.examStudent = examStudent;
	}
}
