package com.whaty.platform.entity.web.action.study;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.util.Const;

/**
 * @param
 * @version 创建时间：2009-6-22 下午03:29:27
 * @return
 * @throws PlatformException
 * 类说明
 */
public class PrBzzStudentStudyAction extends MyBaseAction<PeBzzStudent>{
	
	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		this.getGridConfig().setTitle("学员列表");
		this.getGridConfig().setCapability(false, false, false, true);
		
		this.getGridConfig().addColumn(this.getText("ID"),"id",false);
		
		this.getGridConfig().addColumn(this.getText("学号"), "regNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
		
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass=PeBzzStudent.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath="/entity/study/prBzzStudentStudy";
	}
	
	public void setBean(PeBzzStudent instance) {
		super.superSetBean(instance);
	}
	
	public PeBzzStudent getBean(){
		return super.superGetBean();
	}
	
}
