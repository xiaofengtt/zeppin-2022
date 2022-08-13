package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

/**
 * @param
 * @version 创建时间：2009-6-21 上午10:40:06
 * @return
 * @throws PlatformException
 * 类说明
 */
public class PeBzzCourseManagerAction extends MyBaseAction<PeBzzTchCourse> {

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		this.getGridConfig().setTitle("课程列表");
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程名称"), "name");
		this.getGridConfig().addColumn(this.getText("课程代码"), "code",true,true,true,Const.coursecode_for_extjs);
		UserSession usersession  = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if(usersession.getUserLoginType().equals("3")){
			this.getGridConfig().addColumn(this.getText("是否有效"), "enumConstByFlagIsvalid.name");
			this.getGridConfig().addColumn(this.getText("排列序号"), "suqNum", false, true, true,Const.number_for_extjs);
			this.getGridConfig().addColumn(this.getText("课程类型"), "enumConstByFlagCourseCategory.name");
		}
		this.getGridConfig().addColumn(this.getText("课程性质"),"enumConstByFlagCourseType.name");
		this.getGridConfig().addColumn(this.getText("学时"),"time", false, true, true,Const.twoNum_for_extjs);
		this.getGridConfig().addColumn(this.getText("授课教师"),"teacher",true,true,true,"TextField",true,25);
		this.getGridConfig().addColumn(this.getText("教师简介"),"teacherNote",false,true,false,"TextField",true,200);
		this.getGridConfig().addColumn(this.getText("课程简介"), "note", false, true, false, "TextArea", true, 500);
		
		
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if(us.getRoleId().equals("3"))
			{
			this.getGridConfig().addRenderFunction(this.getText("学习资源管理"), "<a href=\"/sso/bzzinteraction_InteractioManage.action?course_id=${value}&teacher_id=teacher1\"  target='_blank'>管理学习资源</a>","id");
			}
		else
		{
			this.getGridConfig().addRenderFunction(this.getText("查看学习资源"), "<a href=\"/sso/bzzinteraction_InteractioManage.action?course_id=${value}&teacher_id=teacher1\"  target='_blank'>查看学习资源</a>","id");

		}
		
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass=PeBzzTchCourse.class;

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath="/entity/teaching/peBzzCourseManager";
	}
	
	public void setBean(PeBzzTchCourse instance) {
		super.superSetBean(instance);
		
	}
	
	public PeBzzTchCourse getBean(){
		return super.superGetBean();
	}
	
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
				DetachedCriteria criteria = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
				criteria.createCriteria("peBzzTchCourse", "peBzzTchCourse");
				criteria.add(Restrictions.in("peBzzTchCourse.id", ids));
				
				try {
					List<PeEnterprise> plist = this.getGeneralService().getList(criteria);
					if(plist.size()>0){
						map.clear();
						map.put("success", "false");
						map.put("info", "该课程已经开课,无法删除!");
						return map;
					}
				} catch (EntityException e) {
					e.printStackTrace();
				}
				try{
					this.getGeneralService().deleteByIds(idList);
					map.put("success", "true");
					map.put("info", "删除成功");
				}catch(RuntimeException e){
					return this.checkForeignKey(e);
				}catch(Exception e1){
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
	
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzTchCourse.class);
		dc.createCriteria("enumConstByFlagIsvalid","enumConstByFlagIsvalid");
		dc.createCriteria("enumConstByFlagCourseType","enumConstByFlagCourseType");
		dc.createCriteria("enumConstByFlagCourseCategory", "enumConstByFlagCourseCategory",DetachedCriteria.LEFT_JOIN);
		//dc.addOrder(Order.asc("suqNum"));
		return dc;
	}

}
