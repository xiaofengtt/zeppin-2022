

package com.whaty.platform.entity.web.action.first;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.PeTrainingPlan;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class UserQueryTrainExpertAction extends MyBaseAction {
	private List subjects;//学科信息
	private String name;//专家姓名
	private String subject;//专家专业
	private List expert;
	public String regist() {
		
		return "regist";
		
	}
	
	/**
	 * 查询处学科信息用用户选择
	 * @return
	 */
	public String initData(){
		/*String sql="select id,name from pe_subject order by name";
		List list=new LinkedList();
		try {
			list=this.getGeneralService().getBySQL(sql);
			this.setSubjects(list);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return "initData";
	}
	public String query(){
		Map map=new HashMap();
		map.put("name", this.getName());
//		map.put("sname", this.getSubject());
		String sql="select p.name,zhicheng,zhiwu,workplace n1,w.name n2,p.email email,p.OFFICE_PHONE phone from pe_train_expert p " +
				"join pe_subject w on p.fk_subject=w.id join enum_const e on e.id=p.fk_status " +
				"where p.name=:name  and e.code ='1259' "; //目前只开放 重点推荐的专家的查询
		List list=new LinkedList();
		try {
			list=this.getGeneralService().getBySQL(sql, map);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setExpert(list);
		if(list==null||list.size()==0){
			this.setMsg("没有找到您要查询的专家，请确认姓名和学科填写无误");
			return this.initData();
		}
		
		return "queryResult";
	}

	@Override
	public void initGrid() {
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeTrainee.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/first/";
	}

	public List getSubjects() {
		return subjects;
	}

	public void setSubjects(List subjects) {
		this.subjects = subjects;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List getExpert() {
		return expert;
	}

	public void setExpert(List expert) {
		this.expert = expert;
	}

	
}

