package com.whaty.platform.entity.web.action.recruit.setting;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeRecruitplan;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.FlagActiveService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

/**
 * 招生考试批次管理
 * 
 * @author 李冰
 * 
 */
public class PeRecruitplanAction extends MyBaseAction {
	private FlagActiveService flagActiveService;
	public FlagActiveService getFlagActiveService() {
		return flagActiveService;
	}

	public void setFlagActiveService(FlagActiveService flagActiveService) {
		this.flagActiveService = flagActiveService;
	}

	public void checkBeforeAdd() throws EntityException {
		this.getBean().setFlagActive("0");
		String infor = "";// 保存提示信息
		infor = this.checkDate();
		if (infor.length()>0) {
			throw new EntityException(infor);
		}

	}

	public void checkBeforeUpdate() throws EntityException {
		String infor = "";// 保存提示信息
		infor = this.checkDate();
		if (infor.length()>0) {
			throw new EntityException(infor);
		}
	}
	
	/**
	 * 检查时间是否符合要求
	 */
	private String checkDate(){
		String infor = "";
		// 判断开始结束时间
		if (!Const.compareDate(this.getBean().getStartDate(), this.getBean()
				.getEndDate())) {
			infor += "结束时间不能早于开始时间<br/>";
		}
		// 判断报名时间
		if (!Const.compareDate(this.getBean().getRegisterStartDate(), this
				.getBean().getRegisterEndDate())) {
			infor += "报名结束时间不能早于报名开始时间<br/>";
		}
		
		if (!Const.compareDate(this.getBean().getStartDate(), this
				.getBean().getRegisterStartDate())) {
			infor += "报名开始时间不能早于招生批次开始时间<br/>";
		}
		// 判断入学考试时间
		if (this.getBean().getExamStartDate().before(this.getBean().getRegisterEndDate())) {
			infor += "考试开始时间不能早于报名结束时间<br/>";
		}
		if (!Const.compareDate(this.getBean().getExamStartDate(), this
				.getBean().getExamEndDate())) {
			infor += "考试结束时间不能早于考试开始时间<br/>";
		}
		// 判断成绩时间
		if (!this.getBean().getScoreStartDate().after(this.getBean().getExamEndDate())) {
			infor += "成绩录入开始时间应在考试结束时间之后<br/>";
		}
		if (!Const.compareDate(this.getBean().getScoreStartDate(), this
				.getBean().getScoreEndDate())) {
			infor += "成绩录入结束时间不能早于成绩录入开始时间<br/>";
		}
		// 判断录取结束时间
		if (!this.getBean().getMatriculateEndDate().after(this.getBean().getScoreEndDate())) {
			infor += "录取结束时间应在成绩录入结束时间之后<br/>";
		}
		if (this.getBean().getMatriculateEndDate().after(this.getBean().getEndDate())) {
			infor += "录取结束时间不能晚于招生批次结束时间<br/>";
		}
		// 判断分站录入时间
		if (!Const.compareDate(this.getBean().getSiteImportStartDate(), this
				.getBean().getSiteImportEndDate())) {
			infor += "分站录入结束时间不能早于分站录入开始时间<br/>";
		}
		if (!this.getBean().getSiteImportEndDate().before(this.getBean().getExamStartDate())) {
			infor += "分站录入结束时间不能晚于考试开始时间<br/>";
		}
		// 判断分站删除时间
		if (!Const.compareDate(this.getBean().getSiteEditStartDate(), this
				.getBean().getSiteEditEndDate())) {
			infor += "分站删除修改结束时间不能早于分站删除修改开始时间<br/>";
		}
		if (!this.getBean().getSiteEditEndDate().before(this.getBean().getExamStartDate())) {
			infor += "分站删除修改结束时间不能晚于考试开始时间<br/>";
		}
		// 判断分站审核时间
		if (!Const.compareDate(this.getBean().getSiteCheckStartDate(), this
				.getBean().getSiteCheckEndDate())) {
			infor += "分站资料审核结束时间不能早于分站资料审核开始时间<br/>";
		}
		if (!this.getBean().getSiteCheckEndDate().before(this.getBean().getExamStartDate())) {
			infor += "分站资料审核结束时间不能晚于考试开始时间<br/>";
		}
		return infor;
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeRecruitplan.class);
		dc.createCriteria("peGrade","peGrade");
		return dc;
	}

	public void setBean(PeRecruitplan instance) {
		super.superSetBean(instance);

	}

	public PeRecruitplan getBean() {
		return (PeRecruitplan) super.superGetBean();
	}

	@Override
	public Map updateColumn() {
		Map map = new HashMap();

		if (this.getColumn().equals("flagActive")) {
			if (this.getIds() == null || this.getIds().split(",").length > 1) {
				map.put("success", false);
				map.put("info", this.getText("只能操作一条记录!"));
				return map;
			}
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(PeRecruitplan.class);
			List<PeRecruitplan> list = null;
			try {
				list = this.getGeneralService().getList(detachedCriteria);
			} catch (EntityException e1) {
				e1.printStackTrace();
			}
			this.getFlagActiveService().savePeRecruitplanFlagActive(list, this.getIds().split(",")[0]);

			map.put("success", true);
			map.put("info", this.getText("状态已经成功改变"));

			return map;
		}
		return map;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("招生考试批次管理"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("招生批次名称"), "name");
		this.getGridConfig().addColumn(this.getText("编号"), "code");
		this.getGridConfig().addColumn(this.getText("开始日期"), "startDate");
		this.getGridConfig().addColumn(this.getText("结束日期"), "endDate");
		this.getGridConfig().addColumn(this.getText("年级"), "peGrade.name");
		this.getGridConfig().addColumn(this.getText("报名开始时间"),
				"registerStartDate");
		this.getGridConfig().addColumn(this.getText("报名结束时间"),
				"registerEndDate");
		this.getGridConfig().addColumn(this.getText("入学考试开始时间"),
				"examStartDate");
		this.getGridConfig().addColumn(this.getText("入学考试结束时间"), "examEndDate");
		this.getGridConfig().addColumn(this.getText("成绩录入开始时间"),
				"scoreStartDate");
		this.getGridConfig()
				.addColumn(this.getText("成绩录入结束时间"), "scoreEndDate");
		this.getGridConfig().addColumn(this.getText("录取结束时间"),
				"matriculateEndDate");
		this.getGridConfig().addColumn(this.getText("分站录入开始时间"),
				"siteImportStartDate");
		this.getGridConfig().addColumn(this.getText("分站录入结束时间"),
				"siteImportEndDate");
		this.getGridConfig().addColumn(this.getText("分站删除修改开始时间"),
				"siteEditStartDate");
		this.getGridConfig().addColumn(this.getText("分站删除修改结束时间"),
				"siteEditEndDate");
		this.getGridConfig().addColumn(this.getText("分站资料审核开始时间"),
				"siteCheckStartDate");
		this.getGridConfig().addColumn(this.getText("分站资料审核结束时间"),
				"siteCheckEndDate");
		this.getGridConfig().addColumn(this.getText("是否为当前批次"), "flagActive",
				false, false, false, "");
		this
				.getGridConfig()
				.addRenderScript(
						this.getText("是否为当前批次"),
						"{if (${value}=='0') return '非当前批次'; if (${value}=='1') return '当前批次';}",
						"flagActive");

		this.getGridConfig().addMenuFunction("设置为当前批次", "flagActive", "1");

	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeRecruitplan.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/peRecruitplan";
	}

}
