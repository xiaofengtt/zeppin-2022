package com.whaty.platform.entity.web.action.implementation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeCoursePlan;
import com.whaty.platform.entity.bean.PeProApply;
import com.whaty.platform.entity.bean.PeProImplemt;
import com.whaty.platform.entity.bean.PeProvince;
import com.whaty.platform.entity.bean.PeTrainExpert;
import com.whaty.platform.entity.bean.PeUnit;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.JsonUtil;

/**
 * 查看培训日程
 * @author houxuelong
 *
 */
public class CoursePlanViewAction extends MyBaseAction {

	private PeCoursePlan peCoursePlan;
	private String applyIds;
	private String trainingBeginTime;
	private String trainingEndTime;
	private String id_;
	
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, true, false);
		this.getGridConfig().setTitle(this.getText("培训日程"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("开始时间"), "trainingBeginTime");
		this.getGridConfig().addColumn(this.getText("结束时间"), "trainingEndTime");
		this.getGridConfig().addColumn(this.getText("专题"), "theme");
		this.getGridConfig().addColumn(this.getText("培训地点"), "trainPlace");
		this.getGridConfig().addColumn(this.getText("培训方式"), "prelectWay");
		this.getGridConfig().addColumn(this.getText("专家姓名"), "expertName");
		this.getGridConfig().addColumn(this.getText("职称"), "zhicheng");
		this.getGridConfig().addColumn(this.getText("工作单位"), "workPlace");
		this.getGridConfig().addColumn(this.getText("省份"), "peProvince.name");
		this.getGridConfig().addColumn(this.getText("评价"), "enumConstByFlagValuation.name");
		this.getGridConfig().addColumn(this.getText("评语"), "note");
		this.getGridConfig().addColumn(this.getText("备注"), "comments");
		this.getGridConfig().addMenuScript(this.getText("关闭"), "{window.close();}");
//		this.getGridConfig().addMenuScript(this.getText("test.back"), "{window.history.back();}");
		this.getGridConfig().addRenderScript(this.getText("修改与评价"), "{ return '<a href=\\'/entity/implementation/coursePlanView_modifyCoursePlan.action?ids='+record.data['id']+'&id_="+this.getId_()+"\\' target=\\'_blank\\'>修改</a>';}", "");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeCoursePlan.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/implementation/coursePlanView";
	}

	public PeProApply loadPeProApply(){
//		this.getGeneralService().getGeneralDao().setEntityClass(PeProImplemt.class);
		PeProApply peProApply = null;
		try {
			peProApply = ((PeProImplemt)this.getGeneralService().getById(PeProImplemt.class,this.getId_())).getPeProApply();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return peProApply;
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeCoursePlan.class);
		dc.createAlias("enumConstByFlagValuation", "enumConstByFlagValuation", DetachedCriteria.LEFT_JOIN);
//		dc.createAlias("peUnit", "peUnit", DetachedCriteria.LEFT_JOIN);
		dc.createAlias("peProvince", "peProvince", DetachedCriteria.LEFT_JOIN);
		dc.createAlias("peProApply", "peProApply", DetachedCriteria.LEFT_JOIN);
		dc.add(Restrictions.eq("peProApply", loadPeProApply()));
		JsonUtil.setDateformat("yyyy-MM-dd HH:mm");System.out.println(loadPeProApply().getId()+"========================");
		return dc;
	}
	public String addCoursePlan(){
		this.setTogo("back");
		this.setMsg("提交成功");
		PeCoursePlan coursePlan = this.getPeCoursePlan();
		if(coursePlan.getId()==null||"".equals(coursePlan.getId().trim())){
			coursePlan.setId(null);
			coursePlan.setFirstvote(0L);
			coursePlan.setSecondvote(0L);
			coursePlan.setThirdvote(0L);
			coursePlan.setFouthvote(0L);
			coursePlan.setFifthvote(0L);
		}
//		else{
//			try {
//				PeCoursePlan cp = (PeCoursePlan)this.getGeneralService().getById(PeCoursePlan.class,coursePlan.getId());
//				coursePlan.setFirstvote(cp.getFirstvote());
//				coursePlan.setSecondvote(cp.getSecondvote());
//				coursePlan.setThirdvote(cp.getThirdvote());
//				coursePlan.setFouthvote(cp.getFouthvote());
//				coursePlan.setFifthvote(cp.getFifthvote());
//			} catch (EntityException e) {
//				e.printStackTrace();
//			}
//		}
//		coursePlan.setPeProApply(loadPeProApply());
		DetachedCriteria dcPeProvince = DetachedCriteria.forClass(PeProvince.class);
		dcPeProvince.add(Restrictions.eq("name", coursePlan.getPeProvince().getName()));
		List<PeProvince> listProvince = null;
		try {
			listProvince = this.getGeneralService().getList(dcPeProvince);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		if(listProvince!=null && !listProvince.isEmpty()){
			coursePlan.setPeProvince(listProvince.get(0));
		}
		DetachedCriteria dcPeTrainExpert = DetachedCriteria.forClass(PeTrainExpert.class);
		dcPeTrainExpert.add(Restrictions.eq("name", coursePlan.getExpertName()));
		dcPeTrainExpert.add(Restrictions.eq("peProvince", coursePlan.getPeProvince()));
		List<PeTrainExpert> listPeTrainExpert = null;
		try {
			listPeTrainExpert = this.getGeneralService().getList(dcPeTrainExpert);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		if(listPeTrainExpert!=null && !listPeTrainExpert.isEmpty()){
			coursePlan.setPeTrainExpert(listPeTrainExpert.get(0));
		}
		
//		this.getGeneralService().getGeneralDao().setEntityClass(PeProApply.class);
		try {
			coursePlan.setPeProApply((PeProApply)this.getGeneralService().getById(PeProApply.class,this.getApplyIds()));
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date;
		try {
			date = sdf.parse(this.getTrainingBeginTime());
			coursePlan.setTrainingBeginTime(date);
			date = sdf.parse(this.getTrainingEndTime());
			coursePlan.setTrainingEndTime(date);
		} catch (ParseException e1) {
			e1.printStackTrace();
			this.setMsg("开始或结束时间填写错误！");
			return "msg";
		}
		if(coursePlan.getId()!=null){
			if(coursePlan.getEnumConstByFlagValuation()!=null){
				coursePlan.setEnumConstByFlagValuation(this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode("FlagValuation", coursePlan.getEnumConstByFlagValuation().getCode()));
			}
			this.setMsg("修改成功！");
		}
		try {
			this.getGeneralService().save(coursePlan);
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg("操作失败！");
		}
		return "msg";
	}
	
	public String modifyCoursePlan(){
		PeCoursePlan coursePlan = null;
		try {
			coursePlan = (PeCoursePlan)this.getGeneralService().getById(PeCoursePlan.class,this.getIds());
		} catch (EntityException e) {
			e.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(coursePlan.getTrainingBeginTime()!=null){
			this.setTrainingBeginTime(sdf.format(coursePlan.getTrainingBeginTime()));
		}
		if(coursePlan.getTrainingEndTime()!=null){
			this.setTrainingEndTime(sdf.format(coursePlan.getTrainingEndTime()));
		}
		this.setPeCoursePlan(coursePlan);
		return "modify_course";
	}
	public PeCoursePlan getPeCoursePlan() {
		return peCoursePlan;
	}

	public void setPeCoursePlan(PeCoursePlan peCoursePlan) {
		this.peCoursePlan = peCoursePlan;
	}

	public String getApplyIds() {
		return applyIds;
	}

	public void setApplyIds(String applyIds) {
		this.applyIds = applyIds;
	}

	public String getId_() {
		return id_;// != null ? id_ : (String)ActionContext.getContext().getSession().get("id_");
	}

	public void setId_(String id_) {
		this.id_ = id_;
//		ActionContext.getContext().getSession().put("id_", id_);
	}

	public String getTrainingBeginTime() {
		return trainingBeginTime;
	}

	public void setTrainingBeginTime(String trainingBeginTime) {
		this.trainingBeginTime = trainingBeginTime;
	}

	public String getTrainingEndTime() {
		return trainingEndTime;
	}

	public void setTrainingEndTime(String trainingEndTime) {
		this.trainingEndTime = trainingEndTime;
	}

}
