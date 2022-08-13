package com.whaty.platform.entity.web.action.basic;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.PeTrainingClass;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PeTrainingClassAction extends MyBaseAction<PeTrainingClass> {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(true, true, true, true);
		this.getGridConfig().setTitle("培训班信息列表");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("培训班名称"), "name");
		this.getGridConfig().addColumn(this.getText("开班时间"), "startDate");
		this.getGridConfig().addRenderFunction(this.getText("查看学员"), "<a href=# onclick=window.open('/entity/basic/peTraineeView.action?classId=${value}','','left=500,top=300,toolbars=0,statusbars=no,menubars=no,resizable=no,scrollbars=no,height=600,width=800,location=no')>查看</a>", "id");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeTrainingClass.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/peTrainingClass";
	}
	
	public void setBean(PeTrainingClass instance) {
		super.superSetBean(instance);
	}

	public PeTrainingClass getBean() {
		return super.superGetBean();
	}
	
	public void checkBeforeAdd() throws EntityException {
		this.checkBeforeUpdate();
	}
	
	public void checkBeforeUpdate() throws EntityException {
		
		DetachedCriteria dc = DetachedCriteria.forClass(PeTrainingClass.class);
		dc.add(Restrictions.eq("name", this.getBean().getName()));
		
		List list = this.getGeneralService().getList(dc);
		if(list !=null && list.size() > 0) {
			throw new EntityException("'" + this.getBean().getName() + "' 培训班已经存在！");
		}
		
	}
	
	public void checkBeforeDelete(List idList) throws EntityException {
		
		DetachedCriteria dc = DetachedCriteria.forClass(PeTrainee.class);
		dc.createCriteria("peTrainingClass","peTrainingClass");
		for(int i=0; i<idList.size(); i++) {
			dc.add(Restrictions.eq("peTrainingClass.id", (String)idList.get(i)));
			List list = this.getGeneralService().getList(dc);
			if(list !=null && list.size() > 0) {
				throw new EntityException("培训班 '"+((PeTrainee)list.get(0)).getPeTrainingClass().getName()+"' 中还有学员存在，不能删除！");
			}
		}
	}

}
