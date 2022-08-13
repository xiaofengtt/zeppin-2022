package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.elective.ElectiveCancelService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.JsonUtil;

public class PeStuElectiveCoursesAction extends MyBaseAction<PrTchStuElective> {

	private ElectiveCancelService electiveCancelService;
	
	public ElectiveCancelService getElectiveCancelService() {
		return electiveCancelService;
	}

	public void setElectiveCancelService(ElectiveCancelService electiveCancelService) {
		this.electiveCancelService = electiveCancelService;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, true, false);
		
		this.getGridConfig().setTitle("学生已选课列表");
//		this.getGridConfig().setNote("提示：可以删除已开课课程，删除后自动退费，请谨慎操作");
		
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		this.getGridConfig().addColumn(this.getText("学期"), "prTchOpencourse.peSemester.name");
		this.getGridConfig().addColumn(this.getText("课程号"), "prTchOpencourse.peTchCourse.code");
		this.getGridConfig().addColumn(this.getText("课程名"), "prTchOpencourse.peTchCourse.name");
		this.getGridConfig().addColumn(this.getText("选课时间"), "electiveDate");
		this.getGridConfig().addMenuScript(this.getText("返回"), "{window.history.back()}");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchStuElective.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/peStuElectiveCourses";
	}
	
	public void setBean(PrTchStuElective instance) {
		super.superSetBean(instance);
	}
	
	public PrTchStuElective getBean() {
		return super.superGetBean();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchStuElective.class);
		dc.createCriteria("peStudent","peStudent");
		dc.createCriteria("prTchOpencourse","prTchOpencourse").createAlias("peSemester", "peSemester").createAlias("peTchCourse","peTchCourse");
		JsonUtil.setDateformat("yyyy-MM-dd hh:mm:ss");
		return dc;
	}

	@Override
	public void checkBeforeDelete(List idList) throws EntityException {
		
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchStuElective.class);
		dc.add(Restrictions.in("id", idList));
		List prTchStuElectiveList = this.getGeneralService().getList(dc);
		StringBuffer sb = new StringBuffer();
		
		for (Iterator iter = prTchStuElectiveList.iterator(); iter.hasNext();) {
			PrTchStuElective prTchStuElective = (PrTchStuElective) iter.next();
			if (prTchStuElective.getPrTchOpencourse().getPeSemester().getFlagNextActive().equals("0")) {
				sb.append(prTchStuElective.getPrTchOpencourse().getPeTchCourse().getName() + "不在当前选课学期内所选，不能删除！");
			}
		}
		
		if (sb.toString().length() > 0) {
			throw new EntityException(sb.toString());
		}
	}

	@Override
	public Map delete() {
		Map map = new HashMap();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String str = this.getIds();
			if (str != null && str.length() > 0) {
				String[] ids = str.split(",");
				List idList = new ArrayList();
				for (int i = 0; i < ids.length; i++) {
					idList.add(ids[i]);
				}
				
				try{
					String result = this.getElectiveCancelService().delElective(idList);
					map.put("success", "true");
					map.put("info", "删除成功" + result);
				}catch(Exception e){
					e.printStackTrace();
					map.clear();
					map.put("success", "false");
					map.put("info", "删除失败");
				}

			} else {
				map.put("success", "false");
				map.put("info", "请选择操作项");
			}
		}
		return map;
	}
	
}
