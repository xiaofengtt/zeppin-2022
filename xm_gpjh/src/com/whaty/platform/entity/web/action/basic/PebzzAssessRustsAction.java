package com.whaty.platform.entity.web.action.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeBzzAssess;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeBzzSuggestion;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PebzzAssessRustsAction extends MyBaseAction{
	private String courseid;
	private String stuid;
	private String type;
	private PrBzzTchOpencourse prBzzTchOpencourse;
	private PeBzzSuggestion peBzzSuggestion;
	private List<PeBzzAssess> PeBzzAssesslist = new ArrayList<PeBzzAssess>();
	
	private String ssoid;

	public List<PeBzzAssess> getPeBzzAssesslist() {
		return PeBzzAssesslist;
	}

	public void setPeBzzAssesslist(List<PeBzzAssess> peBzzAssesslist) {
		PeBzzAssesslist = peBzzAssesslist;
	}

	public String getStuid() {
		return stuid;
	}

	public void setStuid(String stuid) {
		this.stuid = stuid;
	}

	public String getCourseid() {
		return courseid;
	}

	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}

	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false, true);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		
		if(type.equals("1")){
			this.getGridConfig().addColumn(this.getText("姓名"),"trueName");
			//this.getGridConfig().addColumn(this.getText("性别"),"gender",false);
			this.getGridConfig().addColumn(this.getText("所属学期"),"peBzzBatch.name");
			this.getGridConfig().addColumn(this.getText("所属企业"),"peEnterprise.name");
			this.getGridConfig().addRenderFunction(this.getText("查看"), "<a href=\"/entity/teaching/pebzzAssessRusts_toReviwes.action?stuid=${value}&type=1&courseid="+this.getCourseid()+"\">查看评估</a>","id");
		}else{
			this.getGridConfig().addColumn(this.getText("姓名"),"name");
			//this.getGridConfig().addColumn(this.getText("性别"),"enumConstByGender.name",false);
			this.getGridConfig().addColumn(this.getText("职位"),"position");
			this.getGridConfig().addColumn(this.getText("所属企业"),"peEnterprise.name");
			this.getGridConfig().addRenderFunction(this.getText("查看"), "<a href=\"/entity/teaching/pebzzAssessRusts_toReviwes.action?stuid=${value}&type=2&courseid="+this.getCourseid()+"\">查看评估</a>","id");
		}
		this.getGridConfig().addMenuScript(this.getText("返回"),"{window.history.back()}");
		
	}

	public String toReviwes() {
		try{
		//String courseid =(String)ServletActionContext.getContext().getSession().get("courseid");
		String t_courseid=this.getCourseid();
		String t_ssoid=this.getSsoid();
		
		DetachedCriteria PeBzzStudentdc = null;
		if(type.equals("1")){
			PeBzzStudentdc = DetachedCriteria.forClass(PeBzzStudent.class);
		}else{
			PeBzzStudentdc = DetachedCriteria.forClass(PeEnterpriseManager.class);
		}
		if(this.getStuid()!=null && !this.getStuid().equals(""))
			PeBzzStudentdc.add(Restrictions.eq("id", stuid));
		if(this.getSsoid()!=null && !this.getSsoid().equals(""))
			PeBzzStudentdc.add(Restrictions.eq("ssoUser.id", ssoid));
		PeBzzStudentdc.setProjection(Projections.property("ssoUser"));
		
		DetachedCriteria PeBzzAssessdc = DetachedCriteria.forClass(PeBzzAssess.class);
		PeBzzAssessdc.add(Restrictions.eq("prBzzTchOpencourse.id", t_courseid));
		PeBzzAssessdc.add(Subqueries.propertyEq("ssoUser", PeBzzStudentdc));
		DetachedCriteria peBzzCourseFeedbackdc = PeBzzAssessdc.createCriteria("peBzzCourseFeedback", "peBzzCourseFeedback");
		peBzzCourseFeedbackdc.add(Restrictions.sqlRestriction("1=1 order by to_number(px)"));
		
		this.setPeBzzAssesslist(this.getGeneralService().getList(PeBzzAssessdc));
		
		
		DetachedCriteria rrdc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
		rrdc.add(Restrictions.eq("id", t_courseid));
		List tmpList=this.getGeneralService().getList(rrdc);
		if(tmpList!=null && tmpList.size()>0)
		{
			this.setPrBzzTchOpencourse((PrBzzTchOpencourse)tmpList.get(0));
		}
		
		
		DetachedCriteria PeBzzSuggestiondc =DetachedCriteria.forClass(PeBzzSuggestion.class);
		PeBzzSuggestiondc.add(Restrictions.eq("prBzzTchOpencourse.id", t_courseid));
		PeBzzSuggestiondc.add(Subqueries.propertyEq("ssoUser", PeBzzStudentdc));
		tmpList=this.getGeneralService().getList(PeBzzSuggestiondc);
		if(tmpList!=null && tmpList.size()>0)
		{
			this.setPeBzzSuggestion((PeBzzSuggestion)tmpList.get(0));
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "toReviwes";
	}
	
	
	
	public void setEntityClass() {
//		this.entityClass=PeBzzAssess.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/teaching/pebzzAssessRusts";
	}
	
	public DetachedCriteria initDetachedCriteria() {
		
		//Map session =ActionContext.getContext().getSession();
		//session.put("courseid", courseid);
		if(type.equals("1")){
			DetachedCriteria tdc = DetachedCriteria.forClass(PeBzzAssess.class);
			tdc.setProjection(Projections.distinct(Projections.property("ssoUser")));
			tdc.add(Restrictions.eq("prBzzTchOpencourse.id", courseid));
			DetachedCriteria tempdc = DetachedCriteria.forClass(PeBzzStudent.class);
			tempdc.createAlias("peEnterprise", "peEnterprise");
			tempdc.createAlias("peBzzBatch", "peBzzBatch");
			tempdc.add(Subqueries.propertyIn("ssoUser", tdc));
			return tempdc;
		}else{
			DetachedCriteria tdc = DetachedCriteria.forClass(PeBzzAssess.class);
			tdc.setProjection(Projections.distinct(Projections.property("ssoUser")));
			tdc.add(Restrictions.eq("prBzzTchOpencourse.id", courseid));
			DetachedCriteria tempdc = DetachedCriteria.forClass(PeEnterpriseManager.class);
			tempdc.createAlias("peEnterprise", "peEnterprise");
			tempdc.add(Subqueries.propertyIn("ssoUser", tdc));
			return tempdc;			
		}
		
	}

	public PrBzzTchOpencourse getPrBzzTchOpencourse() {
		return prBzzTchOpencourse;
	}

	public void setPrBzzTchOpencourse(PrBzzTchOpencourse prBzzTchOpencourse) {
		this.prBzzTchOpencourse = prBzzTchOpencourse;
	}

	public PeBzzSuggestion getPeBzzSuggestion() {
		return peBzzSuggestion;
	}

	public void setPeBzzSuggestion(PeBzzSuggestion peBzzSuggestion) {
		this.peBzzSuggestion = peBzzSuggestion;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSsoid() {
		return ssoid;
	}

	public void setSsoid(String ssoid) {
		this.ssoid = ssoid;
	}
	
}
