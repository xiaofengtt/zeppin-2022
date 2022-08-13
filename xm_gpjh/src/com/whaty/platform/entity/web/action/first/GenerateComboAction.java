package com.whaty.platform.entity.web.action.first;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeEdutype;
import com.whaty.platform.entity.bean.PeMajor;
import com.whaty.platform.entity.bean.PeProApplyno;
import com.whaty.platform.entity.bean.PeProvince;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.bean.PeSubject;
import com.whaty.platform.entity.bean.PeUnit;
import com.whaty.platform.entity.bean.PrProgramUnit;
import com.whaty.platform.entity.bean.PrRecPlanMajorSite;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

/**
 * 生成下拉列表功能
 * 
 * @author 李冰
 * 
 */
public class GenerateComboAction extends MyBaseAction { 
	private String requestsiteid; 
	private String requestExpertName;
	private String requestedutypeid; // 用于级联显示，层次id
	private String subjectId;
	private String unitId;
	private String peTraineeId;

	public String getRequestsiteid() {
		return requestsiteid;
	}

	public void setRequestsiteid(String requestsiteid) {
		this.requestsiteid = requestsiteid;
	}

	public String getRequestedutypeid() {
		return requestedutypeid;
	}

	public void setRequestedutypeid(String requestedutypeid) {
		this.requestedutypeid = requestedutypeid;
	}

	@Override
	public void initGrid() {

	}

	@Override
	public void setEntityClass() {

	}

	@Override
	public void setServletPath() {

	}
	/**
	 * 申报批次
	 * @return
	 */
	public String getApplyno() {
		DetachedCriteria expcetdc = DetachedCriteria.forClass(PrProgramUnit.class);
		expcetdc.createCriteria("peUnit", "peUnit");
		expcetdc.add(Restrictions.eq("peUnit.id", this.getUnitId()));
		expcetdc.createAlias("peProApplyno", "peProApplyno");
		expcetdc.setProjection(Projections.distinct(Property.forName("peProApplyno.id")));
		
		DetachedCriteria dc = DetachedCriteria.forClass(PeProApplyno.class);
		dc.createAlias("enumConstByFkProgramStatus", "enumConstByFkProgramStatus");
		dc.add(Restrictions.or(Restrictions.and(Restrictions.eq("enumConstByFkProgramStatus.code", "2"),
				Restrictions.sqlRestriction("deadline-sysdate>0")),
				Restrictions.eq("enumConstByFkProgramStatus.code", "1")));
//		dc.add(Restrictions.eq("year", Const.getYear()));
		dc.add(Subqueries.propertyIn("id", expcetdc));
//		String sql = "select applyno.* from pe_pro_applyno applyno inner join enum_const ec on applyno.fk_program_status=ec.id " +
//				"where (ec.code = '2' and applyno.deadline-sysdate>0) or ec.code='1'";
		try {
			List<PeProApplyno> siteList = this.getGeneralService().getList(dc);
//			List<PeProApplyno> siteList = this.getGeneralService().getBySQL(sql);
			if(siteList==null||siteList.isEmpty()){
				PeProApplyno peProApplyno = new PeProApplyno();
				peProApplyno.setName("当前暂没有可选的培训项目");
				peProApplyno.setId("0");
				siteList.add(peProApplyno);
			}
			Map map = new HashMap();
			map.put("applyno", siteList);
			this.setJsonString(JsonUtil.toJSONString(map));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "json";
	}
	public String getApplynoAll() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeProApplyno.class);
//		dc.add(Restrictions.eq("year", Const.getYear()));
		try {
			List<PeProApplyno> siteList = this.getGeneralService().getList(dc);
			if(siteList==null||siteList.isEmpty()){
				PeProApplyno peProApplyno = new PeProApplyno();
				peProApplyno.setName("当前暂没有可选的培训项目");
				peProApplyno.setId("0");
				siteList.add(peProApplyno);
			}
			Map map = new HashMap();
			map.put("applyno", siteList);
			this.setJsonString(JsonUtil.toJSONString(map));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "json";
	}
	/**
	 * 申报学科
	 * @return
	 */
	public String getSubject() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeSubject.class);
		dc.addOrder(Order.asc("name"));
		try {
			List<PeSubject> siteList = this.getGeneralService().getList(dc);
			Map map = new HashMap();
			map.put("subject", siteList);
			this.setJsonString(JsonUtil.toJSONString(map));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "json";
	}
	
	/**
	 * 省份
	 * @return
	 */
	public String getProvince() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeProvince.class);
		dc.addOrder(Order.asc("name"));
		try {
			List<PeProvince> provinceList = this.getGeneralService().getList(dc);
			Map map = new HashMap();
			map.put("province", provinceList);
			this.setJsonString(JsonUtil.toJSONString(map));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "json";
	}
	
	/**
	 * 单位
	 * @return
	 */
	public String getPeUnit() {
//		List<String> applynoList = getPeProApplynoId(this.getPeTraineeId());
		String trainees[] = this.getPeTraineeId().split(",");
		String peTrainees = "";
		for(int i=0;i<trainees.length;i++){
			peTrainees += "'" + trainees[i]+"'";
			if(i<trainees.length-1){
				peTrainees += ",";
			}
		}
		String sql_1 = "SELECT DISTINCT T.FK_PRO_APPLYNO AS ID  FROM PE_TRAINEE T WHERE T.ID IN (" + peTrainees + ")";
		List<String> applynoList = null;
		try {
			 applynoList = this.getGeneralService().getBySQL(sql_1);
//			 applynoList.remove(null);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<PeUnit> unitList = null;
		Map map = new HashMap();
		if(applynoList.size()>1){
			 unitList = new LinkedList<PeUnit>();
			PeUnit peUnit = new PeUnit();
			peUnit.setId("00");
			peUnit.setName("存在不一致的培训项目...");
			unitList.add(peUnit);
		}else{
			DetachedCriteria dc = DetachedCriteria.forClass(PeUnit.class);
			dc.createAlias("enumConstByFkUnitType", "enumConstByFkUnitType");
			dc.add(Restrictions.eq("enumConstByFkUnitType.code", "1526"));
			dc.addOrder(Order.asc("name"));
			try {
				unitList = this.getGeneralService().getList(dc);
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}
		map.put("unit", unitList);
		this.setJsonString(JsonUtil.toJSONString(map));
		return "json";
	}
	
	
	/**
	 * @description 根据选择的承办单位查询该单位下的开设学科�
	 * @return
	 */
	public String getSubjectByUnit() {//where fk_unit='"+this.getSubjectId()+"'
		String trainees[] = this.getPeTraineeId().split(",");
		String peTrainees = "";
		for(int i=0;i<trainees.length;i++){
			peTrainees += "'" + trainees[i]+"'";
			if(i<trainees.length-1){
				peTrainees += ",";
			}
		}
		String sql ="SELECT DISTINCT P.ID AS ID FROM PE_PRO_APPLY T INNER JOIN PE_SUBJECT P ON T.FK_SUBJECT=P.ID WHERE T.FK_UNIT='"+this.getSubjectId()+"'" +
					" AND T.FK_APPLYNO = (SELECT DISTINCT T.FK_PRO_APPLYNO AS ID  FROM PE_TRAINEE T WHERE T.ID IN (" + peTrainees + "))";
			
		DetachedCriteria dc = DetachedCriteria.forClass(PeSubject.class);
		try {
			List<String> subList = this.getGeneralService().getBySQL(sql);
			List<PeSubject> unitList = null;
			Map map = new HashMap(); 
			
			if(subList.size() == 0) {
				unitList = new LinkedList<PeSubject>();
				PeSubject peSubject = new PeSubject();
				peSubject.setId("0");
				peSubject.setName("没有符合条件的学科�...");
				unitList.add(peSubject);
			}else {
				dc.add(Restrictions.in("id", subList));
				unitList = this.getGeneralService().getList(dc);
			}
			map.put("edutypes", unitList);
			this.setJsonString(JsonUtil.toJSONString(map));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "json";
	}
	
	/**
	 * 学习中心列表
	 * 
	 * @return
	 */
	public String getRecsites() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeSite.class);
		try {
			List<PeSite> siteList = this.getGeneralService().getList(dc);
			Map map = new HashMap();
			map.put("sites", siteList);
			this.setJsonString(JsonUtil.toJSONString(map));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "json";
	}

	public String getExpertByName(){
		String sql = "select t.zhicheng,p.name as pname,t.workplace  from pe_train_expert t inner join pe_province p on t.fk_province=p.id  where t.name = '"+this.getRequestExpertName()+"'";
		try {
			List<Object[]> listZhicheng = this.getGeneralService().getBySQL(sql);
			Map map = new HashMap();
			if(listZhicheng!=null && !listZhicheng.isEmpty()){
				map.put("zhicheng", listZhicheng.get(0)[0]);
				map.put("privince", listZhicheng.get(0)[1]);
				map.put("workPlace", listZhicheng.get(0)[2]);
			}
			this.setJsonString(JsonUtil.toJSONString(map));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "json";
	}
	/**
	 * 层次列表，与学习中心级联
	 * 
	 * @return
	 */
	public String getRecEdutypes() {
		List<PeEdutype> edutypeList = new ArrayList();

		DetachedCriteria dcPrRecPlanMajorSite = DetachedCriteria
				.forClass(PrRecPlanMajorSite.class);
		DetachedCriteria dcPeSite = dcPrRecPlanMajorSite
				.createCriteria("peSite","peSite");
		DetachedCriteria dcPrRecPlanMajorEdutype = dcPrRecPlanMajorSite
				.createCriteria("prRecPlanMajorEdutype","prRecPlanMajorEdutype");
		DetachedCriteria dcPeEdutype = dcPrRecPlanMajorEdutype
				.createCriteria("peEdutype","peEdutype");
		DetachedCriteria dcPeRecruitplan = dcPrRecPlanMajorEdutype
				.createCriteria("peRecruitplan","peRecruitplan");
		dcPeSite.add(Restrictions.eq("id", this.getRequestsiteid()));
		 dcPeRecruitplan.add(Restrictions.eq("flagActive", "1"));
		List<PrRecPlanMajorSite> prRecPlanMajorSiteList = new ArrayList();
		try {
			prRecPlanMajorSiteList = this.getGeneralService().getList(
					dcPrRecPlanMajorSite);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if (prRecPlanMajorSiteList.size() == 0) {
			PeEdutype noresult = new PeEdutype();
			noresult.setName("暂无层次可选");
			noresult.setId("0");
			edutypeList.add(noresult);
		} else {
			for (int i = 0; i < prRecPlanMajorSiteList.size(); i++) {
				if (i == 0) {
					edutypeList.add(prRecPlanMajorSiteList.get(i)
							.getPrRecPlanMajorEdutype().getPeEdutype());
				} else {
					// 消除名字相同的层次
					boolean flagequal = false;
					for (int j = 0; j < edutypeList.size(); j++) {
						if (edutypeList.get(j).getName().equals(
								prRecPlanMajorSiteList.get(i)
										.getPrRecPlanMajorEdutype()
										.getPeEdutype().getName())) {
							flagequal = true;
						}
					}
					if (!flagequal) {
						edutypeList.add(prRecPlanMajorSiteList.get(i)
								.getPrRecPlanMajorEdutype().getPeEdutype());
					}
				}
			}
		}
		Map map = new HashMap();
		map.put("edutypes", edutypeList);
		this.setJsonString(JsonUtil.toJSONString(map));
		return "json";
	}

	/**
	 * 生成专业列表，与学习中心和层次级联
	 * 
	 * @return
	 */
	public String getRecMajors() {
		List<PeMajor> majorList = new ArrayList();

		DetachedCriteria dcPrRecPlanMajorSite = DetachedCriteria
				.forClass(PrRecPlanMajorSite.class);

		DetachedCriteria dcPeSite = dcPrRecPlanMajorSite.createCriteria(
				"peSite", "peSite");

		DetachedCriteria dcPrRecPlanMajorEdutype = dcPrRecPlanMajorSite
				.createCriteria("prRecPlanMajorEdutype",
						"prRecPlanMajorEdutype");

		DetachedCriteria dcPeMajor = dcPrRecPlanMajorEdutype.createCriteria(
				"peMajor", "peMajor");

		DetachedCriteria dcPeEdutype = dcPrRecPlanMajorEdutype.createCriteria(
				"peEdutype", "peEdutype");

		DetachedCriteria dcPeRecruitplan = dcPrRecPlanMajorEdutype
				.createCriteria("peRecruitplan", "peRecruitplan");

		dcPeSite.add(Restrictions.eq("id", this.getRequestsiteid()));

		dcPeEdutype.add(Restrictions.eq("id", this.getRequestedutypeid()));

		 dcPeRecruitplan.add(Restrictions.eq("flagActive", "1"));
		List<PrRecPlanMajorSite> prRecPlanMajorSiteList = new ArrayList();

		try {
			prRecPlanMajorSiteList = this.getGeneralService().getList(
					dcPrRecPlanMajorSite);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (prRecPlanMajorSiteList.size() == 0) {
			PeMajor noresult = new PeMajor();
			noresult.setName("暂无专业可选");
			noresult.setId("0");
			majorList.add(noresult);
		} else {
			for (int i = 0; i < prRecPlanMajorSiteList.size(); i++) {
				majorList.add(prRecPlanMajorSiteList.get(i)
						.getPrRecPlanMajorEdutype().getPeMajor());
			}
		}
		Map map = new HashMap();
		map.put("majors", majorList);
		this.setJsonString(JsonUtil.toJSONString(map));
		return "json";
	}

	public String getRequestExpertName() {
		return requestExpertName;
	}

	public void setRequestExpertName(String requestExpertName) {
		this.requestExpertName = requestExpertName;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getPeTraineeId() {
		return peTraineeId ;//!= null ? peTraineeId : (String)ActionContext.getContext().getSession().remove("peTraineeId");
	}

	public void setPeTraineeId(String peTraineeId) {
		this.peTraineeId = peTraineeId;
//		ActionContext.getContext().getSession().put("peTraineeId", peTraineeId);
	}
}