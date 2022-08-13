package com.whaty.platform.entity.web.action.study;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeTrainingPlan;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PeCompletionAction extends MyBaseAction {

	private String planId;// 培训计划id
	private PeTrainingPlan plan;
	private String msg;
	private String spotTraining;//是否现场培训
	private String onlineAnswer;//是否在线答疑
	private String shamTestTimes;//模拟考试次数

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public PeTrainingPlan getPlan() {
		return plan;
	}

	public void setPlan(PeTrainingPlan plan) {
		this.plan = plan;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("结业设置"));
		this.getGridConfig().setCapability(false, false, false, true);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("培训计划名称"), "name");
		this.getGridConfig().addColumn(this.getText("培训类型"),
				"enumConstByTrainingType.name");
		this.getGridConfig().addColumn(this.getText("版本号"),
				"enumConstByVersion.name");
		this.getGridConfig().addColumn(this.getText("学分要求"), "creditRequire");
		this.getGridConfig().addColumn(this.getText("培训天数"), "daysLimit");
		this.getGridConfig().addColumn(this.getText("是否需要参加现场培训"), "spotTraining", false, false, false, null);
		this.getGridConfig().addRenderScript("是否需要参加现场培训", "{if(${value}=='1') return '是'; else return '否';}", "spotTraining");
		this.getGridConfig().addColumn(this.getText("是否需要参加在线答疑"), "onlineAnswer", false, false, false, null);
		this.getGridConfig().addRenderScript("是否需要参加在线答疑", "{if(${value}=='1') return '是'; else return '否';}", "onlineAnswer");
		this.getGridConfig().addColumn(this.getText("模拟考试次数"), "shamtestTimes");
		this
				.getGridConfig()
				.addRenderFunction(
						this.getText("设置结业条件"),
						"<a href=# onclick=window.open('/entity/study/peCompletion_setupConditions.action?planId=${value}','','left=200,top=100,resizable=yes,scrollbars=no,height=540,width=1000,location=no')>"
								+ this.getText("设置") + "</a>", "id");

	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeTrainingPlan.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/study/peCompletion";

	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		this.getGeneralService().getGeneralDao().setEntityClass(
				PeTrainingPlan.class);
		DetachedCriteria dc = DetachedCriteria.forClass(PeTrainingPlan.class);
		dc.createCriteria("enumConstByTrainingType", "enumConstByTrainingType");
		dc.createCriteria("enumConstByVersion", "enumConstByVersion");
		return dc;
	}

	public String setupConditions() {
		this.getGeneralService().getGeneralDao().setEntityClass(
				PeTrainingPlan.class);
		try {
			PeTrainingPlan plan = (PeTrainingPlan)this.getGeneralService().getById(this.planId);
			if(plan != null) {
				this.setPlan(plan);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "set_conditions";
	}
	
	public String setup() {
		this.getGeneralService().getGeneralDao().setEntityClass(
				PeTrainingPlan.class);
		try {
			PeTrainingPlan plan = (PeTrainingPlan)this.getGeneralService().getById(this.planId);
			if(plan != null) {
				plan.setOnlineAnswer(onlineAnswer);
				plan.setSpotTraining(spotTraining);
				plan.setShamtestTimes(Long.parseLong(shamTestTimes));
				plan = (PeTrainingPlan)this.getGeneralService().save(plan);
				if(plan != null) {
					this.setPlan(plan);
					this.setMsg("设置结业条件成功");
				}
			}
		} catch (EntityException e) {
			this.setMsg("设置结业条件失败");
			e.printStackTrace();
		}
		return "set_conditions";
	}

	public String getSpotTraining() {
		return spotTraining;
	}

	public void setSpotTraining(String spotTraining) {
		this.spotTraining = spotTraining;
	}

	public String getOnlineAnswer() {
		return onlineAnswer;
	}

	public void setOnlineAnswer(String onlineAnswer) {
		this.onlineAnswer = onlineAnswer;
	}

	public String getShamTestTimes() {
		return shamTestTimes;
	}

	public void setShamTestTimes(String shamTestTimes) {
		this.shamTestTimes = shamTestTimes;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
