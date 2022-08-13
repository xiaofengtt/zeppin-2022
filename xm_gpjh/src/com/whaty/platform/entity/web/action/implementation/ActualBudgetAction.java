package com.whaty.platform.entity.web.action.implementation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeFeeActualBudget;
import com.whaty.platform.entity.bean.PeFeeActualBudgetDetail;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.programApply.ProgramImpletationService;
import com.whaty.platform.util.Const;

/**
 * 提交决算表
 * 
 * @author yizhi
 *
 */
public class ActualBudgetAction extends BudgetSubmitAction {

	private ProgramImpletationService programImpletationService;
	
	private PeFeeActualBudget peFeeActualBudget;
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("提交决算表"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("培训项目名称"), "applynoName");
		this.getGridConfig().addColumn(this.getText("培训项目编号"), "applynoCode");
		this.getGridConfig().addColumn(this.getText("所属年度"), "applynoYear");
		this.getGridConfig().addColumn(this.getText("项目类型"), "combobox_EnumConstByFkProgramType.programType");
		this.getGridConfig().addRenderScript("决算表操作","{return '<a href=/entity/implementation/actualBudget_toDo.action?praid='+record.data['id']+' target=\\'_blank\\'>提交决算表</a>';}", "");
		this.getGridConfig().addColumn(this.getText("申报时限"), "applynoDeadLine",false,false,true,"Date",false,20);
		this.getGridConfig().addColumn(this.getText("人均经费标准"), "feeStandard",false,true,true,Const.fee_for_extjs);
		this.getGridConfig().addColumn(this.getText("申报学科上限"), "applynoLimit",false,false,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("最后更新时间"), "inputDate",false,false,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("备注"), "applynoNote",false,false,true,"",true,200);
	}
	
	@Override
	public void setEntityClass() {

	}
	
	@Override
	public void setServletPath() {
		this.servletPath = "/entity/implementation/actualBudget";
	}

	public String toDo(){
		initSubjectApplyno();
		return "juesuan";
	}
	
	public String toSave(){
		initSubjectApplyno();
		PeFeeActualBudget peFeeBudget = this.getPeFeeActualBudget();
		PeFeeActualBudgetDetail peFeeBudgetDetail = new PeFeeActualBudgetDetail();
		//更新
		if(this.getPbid()!=null&&!this.getPbid().equals("")){
			peFeeBudget = (PeFeeActualBudget)this.getMyListService().getById(PeFeeActualBudget.class, this.getPbid());
			peFeeBudget = (PeFeeActualBudget)super.setSubIds(peFeeBudget, this.getPeFeeActualBudget());
			peFeeBudgetDetail = peFeeBudget.getPeFeeActualBudgetDetail();
		}else{
			//PeProApplyno applyno = (PeProApplyno)this.getMyListService().getById(PeProApplyno.class, this.getPraid())
			DetachedCriteria dc = DetachedCriteria.forClass(PeFeeActualBudget.class);
			dc.createAlias("peUnit", "peUnit");
			dc.createAlias("peProApplyno", "peProApplyno");
			dc.add(Restrictions.eq("peUnit", this.getPeUnit()));
			dc.add(Restrictions.eq("peProApplyno", this.getPeProApplyno()));
			List feeBudgetList = null;
			try {
				feeBudgetList = this.getGeneralService().getList(dc);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			if(feeBudgetList != null && !feeBudgetList.isEmpty()){
				peFeeBudget = (PeFeeActualBudget)feeBudgetList.get(0);
				peFeeBudget = (PeFeeActualBudget)super.setSubIds(peFeeBudget, this.getPeFeeActualBudget());
				peFeeBudgetDetail = peFeeBudget.getPeFeeActualBudgetDetail();
			}else{
				peFeeBudget.setPeUnit(this.getPeUnit());
				peFeeBudget.setPeProApplyno(this.getPeProApplyno());
			}
			
			HttpServletRequest request = ServletActionContext.getRequest();
			List subList = this.getSubjectList();
			String personNote = "";
			
			for(int i=0;i<subList.size();i++){
				Object obj = subList.get(i);
				Object [] o = (Object[])obj;
				String person = request.getParameter("person_"+i);
				personNote += o[1]+ ":" + person + "人；";
				System.out.println(personNote);
			}
			personNote += "总计：" +this.getTotal_person_1()+ "人、"+this.getClasses()+"个培训班。";
			
			peFeeBudget.setPersonCount(personNote);	
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(this.getTrainingYear()+"-"+this.getTrainingMonth()+"-"+this.getTrainingEndMonth());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		peFeeBudget.setTrainingDate(date);
		peFeeBudget.setInputDate(new Date());
		
		peFeeBudgetDetail.setFeeMeal(this.getTotal_item_1());
		peFeeBudgetDetail.setFeeAccommodation(this.getTotal_item_2());
		peFeeBudgetDetail.setFeeTeach(this.getTotal_item_3());
		peFeeBudgetDetail.setFeeTrafficExpert(this.getTotal_item_4());
		peFeeBudgetDetail.setFeeMealAccExpert(this.getTotal_item_5());
		peFeeBudgetDetail.setFeeMaterials(this.getTotal_item_6());
		peFeeBudgetDetail.setFeeAreaRent(this.getTotal_item_7());
		peFeeBudgetDetail.setFeeEquipmentRent(this.getTotal_item_8());
		peFeeBudgetDetail.setFeeLabourService(this.getTotal_item_9());
		peFeeBudgetDetail.setFeeSurvey(this.getTotal_item_10());
		peFeeBudgetDetail.setFeeResearch(this.getTotal_item_11());
		peFeeBudgetDetail.setFeeArgument(this.getTotal_item_12());
		peFeeBudgetDetail.setFeeTrafficStu(this.getTotal_item_13());
		peFeeBudgetDetail.setFeeElectronCourse(this.getTotal_item_14());
		
		peFeeBudgetDetail.setNoteMeal(this.getPrice_item_1()+"*"+this.getQty_item_1()+"*"+this.getTime_item_1());
		peFeeBudgetDetail.setNoteAccommodation(this.getPrice_item_2()+"*"+this.getQty_item_2()+"*"+this.getTime_item_2());
		peFeeBudgetDetail.setNoteTeach(this.getPrice_item_3()+"*"+this.getQty_item_3());
		peFeeBudgetDetail.setNoteTrafficExpert(this.getPrice_item_4()+"*"+this.getQty_item_4());
		peFeeBudgetDetail.setNoteMealAccExpert(this.getPrice_item_5()+"*"+this.getQty_item_5());
		peFeeBudgetDetail.setNoteMaterials(this.getPrice_item_6()+"*"+this.getQty_item_6());
		peFeeBudgetDetail.setNoteAreaRent(this.getPrice_item_7()+"*"+this.getQty_item_7());
		peFeeBudgetDetail.setNoteEquipmentRent(this.getPrice_item_8()+"*"+this.getQty_item_8());
		peFeeBudgetDetail.setNoteLabourService(this.getPrice_item_9()+"*"+this.getQty_item_9());
		peFeeBudgetDetail.setNoteSurvey(this.getPrice_item_10()+"*"+this.getQty_item_10());
		peFeeBudgetDetail.setNoteResearch(this.getPrice_item_11()+"*"+this.getQty_item_11()+"*"+this.getTime_item_11());
		peFeeBudgetDetail.setNoteArgument(this.getPrice_item_12()+"*"+this.getQty_item_12());;
		peFeeBudgetDetail.setNoteTrafficStu(this.getPrice_item_13()+"*"+this.getQty_item_13()+"*"+this.getTime_item_13());
		peFeeBudgetDetail.setNoteElectronCourse(this.getPrice_item_14()+"*"+this.getQty_item_14());
		
		peFeeBudget.setPeFeeActualBudgetDetail(peFeeBudgetDetail);
		try {
			//this.getGeneralService().save(peFeeBudgetDetail);
			this.getProgramImpletationService().savePeFeeActualBudget(peFeeBudget);
			this.setMsg("保存成功");
			
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg("保存失败");
		}
		this.setTogo("back");
		return "msg";
	}

	public ProgramImpletationService getProgramImpletationService() {
		return programImpletationService;
	}
	public void setProgramImpletationService(ProgramImpletationService programImpletationService) {
		this.programImpletationService = programImpletationService;
	}

	public PeFeeActualBudget getPeFeeActualBudget() {
		return peFeeActualBudget;
	}

	public void setPeFeeActualBudget(PeFeeActualBudget peFeeActualBudget) {
		this.peFeeActualBudget = peFeeActualBudget;
	}

}
