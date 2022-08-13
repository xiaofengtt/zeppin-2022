package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeTchCoursegroup;
import com.whaty.platform.entity.bean.PeTchProgram;
import com.whaty.platform.entity.bean.PeTchProgramGroup;
import com.whaty.platform.entity.bean.PrTchProgramCourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class TeaPlanInfoCourseInfoAction extends MyBaseAction<PrTchProgramCourse> {

	private static final long serialVersionUID = -3069589229260158512L;

	@Override
	public void initGrid() {
		
		String tchCoursegroupName = ((PeTchCoursegroup)this.getMyListService().getById(PeTchCoursegroup.class, 
				((PeTchProgramGroup)this.getMyListService().getById(PeTchProgramGroup.class, this.getBean().getPeTchProgramGroup().getId()))  
				.getPeTchCoursegroup().getId())).getName();
		
		this.getGridConfig().setCapability(true, true, true);
		this.getGridConfig().setTitle(this.getText(tchCoursegroupName + " 的课程信息列表"));
		this.getGridConfig().addMenuScript(this.getText("test.back"), "{window.history.back();}");
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程编号"), "peTchCourse.code", true, true, true, "TextField", false, 50);
//		20090413 选择有效的课程，无效的课程不加入教学计划中。 (龚妮娜)
		ColumnConfig column = new ColumnConfig(this.getText("课程名称"),"peTchCourse.name");		
		column.setComboSQL("select t.id,t.name from PE_TCH_COURSE t ,enum_const m where  m.namespace = 'FlagIsvalid' and t.flag_isvalid=m.id and m.code=1 ");
		this.getGridConfig().addColumn(column);
		//this.getGridConfig().addColumn(this.getText("课程名称"), "peTchCourse.name", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("学分"), "credit", true, true, true,"regex:new RegExp(/^\\d{1,2}(\\.\\d{0,1})?$/),regexText:'学分输入格式：1到2位整数 0到1位小数',");
		this.getGridConfig().addColumn(this.getText("主干课程标记"), "enumConstByFlagIsMainCourse.name", true, true, true, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("建议学期"), "unit", true, true, true,"regex:new RegExp(/^\\d{1}?$/),regexText:'学期输入格式：1位整数',");
		
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchProgramCourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/teachPlanInfoCourse";
	}
	
	public void setBean(PrTchProgramCourse instance) {
		super.superSetBean(instance);
	}
	
	public PrTchProgramCourse getBean() {
		return super.superGetBean();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchProgramCourse.class);
		dc.createCriteria("peTchCourse","peTchCourse");
		dc.createCriteria("enumConstByFlagIsMainCourse","enumConstByFlagIsMainCourse");
		dc.createCriteria("peTchProgramGroup","peTchProgramGroup");
		if (null != this.getBean().getPeTchProgramGroup()) {
			dc.add(Restrictions.eq("peTchProgramGroup.id", this.getBean().getPeTchProgramGroup().getId()));
		}
		return dc;
	}

	@Override
	public void checkBeforeAdd() throws EntityException {
//		PrTchProgramCourse prTchProgramCourse = this.getBean();
//		PeTchProgramGroup peTchProgramGroup = (PeTchProgramGroup) this.getMyListService().getById(PeTchProgramGroup.class, this.getBean().getPeTchProgramGroup().getId()) ;
//		DetachedCriteria dc = DetachedCriteria.forClass(PrTchProgramCourse.class);
//		dc.createCriteria("peTchProgramGroup", "peTchProgramGroup").createAlias("peTchProgram", "peTchProgram");
//		dc.add(Restrictions.eq("peTchProgramGroup.peTchProgram", peTchProgramGroup.getPeTchProgram()));
//		List list = new ArrayList();
//		list = this.getGeneralService().getList(dc);
//		for (Iterator iter = list.iterator(); iter.hasNext();) {
//			PrTchProgramCourse instance = (PrTchProgramCourse) iter.next();
//			if ((prTchProgramCourse.getPeTchCourse().getId()).equals((instance.getPeTchCourse().getId()))) {
//				throw new EntityException("该课程已在教学计划中存在。");
//			}
//		}
		PrTchProgramCourse prTchProgramCourse = this.getBean();
		PeTchProgramGroup peTchProgramGroup = (PeTchProgramGroup) this.getMyListService().getById(PeTchProgramGroup.class, this.getBean().getPeTchProgramGroup().getId()) ;
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchProgramCourse.class);
		dc.createCriteria("peTchProgramGroup", "peTchProgramGroup").createAlias("peTchProgram", "peTchProgram");
		dc.add(Restrictions.eq("peTchProgramGroup.peTchProgram", peTchProgramGroup.getPeTchProgram()));
		dc.add(Restrictions.not(Restrictions.eq("peTchProgramGroup", peTchProgramGroup)));
		dc.add(Restrictions.eq("peTchCourse", prTchProgramCourse.getPeTchCourse()));
		List list = new ArrayList();
		list = this.getGeneralService().getList(dc);
		if (list.size() > 0) {
			throw new EntityException("该课程已在教学计划中出现。");
		}

	}

	@Override
	public void checkBeforeUpdate() throws EntityException {
		this.checkBeforeAdd();
	}

}
