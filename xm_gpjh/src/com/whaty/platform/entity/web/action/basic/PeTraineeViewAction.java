package com.whaty.platform.entity.web.action.basic;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

public class PeTraineeViewAction extends MyBaseAction<PeTrainee> {
	
	private String classId;
	
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public void initGrid() {

		this.getGridConfig().setCapability(false, false, false, true, false);
		this.getGridConfig().setTitle(this.getText("学员查看"));
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学员姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("用户名"), "loginId", true, true, true, Const.userName_for_extjs);
		this.getGridConfig().addColumn(this.getText("性别"),
				"enumConstByGender.name");
		this.getGridConfig().addColumn(this.getText("身份证号"), "cardNo", true, true, true, Const.cardId_for_extjs);
		this.getGridConfig().addColumn(this.getText("是否有效"), 
				"ssoUser.enumConstByFlagIsvalid.name");
		this.getGridConfig().addColumn(this.getText("学员状态"),
				"enumConstByStatus.name");
		this.getGridConfig().addColumn(this.getText("所属培训班"),
				"peTrainingClass.name");
		this.getGridConfig().addColumn(this.getText("培训级别"),
				"enumConstByTrainingType.name");
		this.getGridConfig().addColumn(this.getText("是否在职"),
				"enumConstByFlagInJob.name");

	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeTrainee.class);
		dc.createCriteria("enumConstByGender", "enumConstByGender",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByStatus", "enumConstByStatus",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByTrainingType", "enumConstByTrainingType",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagInJob", "enumConstByFlagInJob",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peTrainingClass", "peTrainingClass",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("ssoUser", "ssoUser").createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		dc.add(Restrictions.eq("peTrainingClass.id", this.classId));
		return dc;
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeTrainee.class;
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/peTraineeView";
		
	}
	
}
