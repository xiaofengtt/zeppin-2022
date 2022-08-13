package com.whaty.platform.entity.web.action.study;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.entity.bean.PeBzzStudent;

/**
 * @param
 * @version 创建时间：2009-7-5 上午10:01:35
 * @return
 * @throws PlatformException
 * 类说明
 */
public class PrBzzStudyAction extends MyBaseAction {

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		this.getGridConfig().setTitle("学员列表");
		this.getGridConfig().setCapability(false, false, false, true);
		this.getGridConfig().addColumn(this.getText("ID"),"id",false);
		this.getGridConfig().addColumn(this.getText("姓名"),"trueName");
		this.getGridConfig().addColumn(this.getText("学号"),"regNo");
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath="/entity/study/prBzzStudy";
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		// TODO Auto-generated method stub
		DetachedCriteria dcd = DetachedCriteria.forClass(PeBzzStudent.class);
		return dcd;
	}

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return "stu";
	}

}
