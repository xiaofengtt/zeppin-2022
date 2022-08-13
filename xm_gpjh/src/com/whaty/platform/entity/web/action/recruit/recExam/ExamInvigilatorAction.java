package com.whaty.platform.entity.web.action.recruit.recExam;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeRecRoom;
import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class ExamInvigilatorAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle("监考查询");
		this.getGridConfig().setCapability(false, false, true);
		
		this.getGridConfig().addColumn(this.getText("id"), "id",false);
		this.getGridConfig().addColumn(this.getText("考场（全称）"), "name", true, false, true, "");
		this.getGridConfig().addColumn(this.getText("考场号"),"code", true, false, true, "");
		this.getGridConfig().addColumn(this.getText("招生考试批次"), "peRecruitplan.name", false, false, true, "");
		this.getGridConfig().addColumn(this.getText("监考人1"), "invigilatorA");
		this.getGridConfig().addColumn(this.getText("监考人2"), "invigilatorB");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeRecRoom.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/examinvigilator";
	}
	
	public void setBean(PeRecRoom instance) {
		super.superSetBean(instance);
		
	}
	
	public PeRecRoom getBean(){
		return (PeRecRoom) super.superGetBean();
	}
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeRecRoom.class);
		dc.createCriteria("peRecruitplan","peRecruitplan")
			.add(Restrictions.eq("flagActive", "1"));
		return dc;
	}

}
