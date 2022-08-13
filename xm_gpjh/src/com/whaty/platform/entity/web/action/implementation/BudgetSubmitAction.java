package com.whaty.platform.entity.web.action.implementation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeFeeBudget;
import com.whaty.platform.entity.bean.PeFeeBudgetDetail;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeProApplyno;
import com.whaty.platform.entity.bean.PeUnit;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.programApply.ProgramImpletationService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

/**
 * 提交预算表
 * 
 * @author yizhi
 *
 */
public class BudgetSubmitAction extends MyBaseAction {
	
	private ProgramImpletationService programImpletationService;
	private String praid;
	private String pbid;
	private PeProApplyno peProApplyno;
	private List subjectList;
	private String trainingYear;
	private String trainingMonth;
	private String trainingEndMonth;
	private PeUnit peUnit;
	private PeFeeBudget peFeeBudget;
	private String classes;
	//private String wenben_item_1;
	//private String wenben_item_2;
	//private String dianzi_item_1;
	//private String dianzi_item_2;
	
	private Double total_item_1;
	private Double total_item_2;
	private Double total_item_3;
	private Double total_item_4;
	private Double total_item_5;
	private Double total_item_6;
	private Double total_item_7;
	private Double total_item_8;
	private Double total_item_9;
	private Double total_item_10;
	private Double total_item_11;
	private Double total_item_12;
	private Double total_item_13;
	private Double total_item_14;
	
/*	private Double other_item_1;
	private Double other_item_2;
	private Double other_item_3;
	private Double other_item_4;
	private Double other_item_5;
	private Double other_item_6;
	private Double other_item_7;
	private Double other_item_8;
	private Double other_item_9;
	private Double other_item_10;
	private Double other_item_11;*/
	
/*	private String note_item_1;
	private String note_item_2;
	private String note_item_3;
	private String note_item_4;
	private String note_item_5;
	private String note_item_6;
	private String note_item_7;
	private String note_item_8;
	private String note_item_9;
	private String note_item_10;
	private String note_item_11;
*/
	private String price_item_1;
	private String price_item_2;
	private String price_item_3;
	private String price_item_4;
	private String price_item_5;
	private String price_item_6;
	private String price_item_7;
	private String price_item_8;
	private String price_item_9;
	private String price_item_10;
	private String price_item_11;
	private String price_item_12;
	private String price_item_13;
	private String price_item_14;
	
	private String qty_item_1;
	private String qty_item_2;
	private String qty_item_3;
	private String qty_item_4;
	private String qty_item_5;
	private String qty_item_6;
	private String qty_item_7;
	private String qty_item_8;
	private String qty_item_9;
	private String qty_item_10;
	private String qty_item_11;
	private String qty_item_12;
	private String qty_item_13;
	private String qty_item_14;
	
	private String time_item_1;
	private String time_item_2;
	private String time_item_3;
	private String time_item_4;
	private String time_item_5;
	private String time_item_6;
	private String time_item_7;
	private String time_item_8;
	private String time_item_9;
	private String time_item_10;
	private String time_item_11;
	private String time_item_12;
	private String time_item_13;
	private String time_item_14;
	
	private String total_person_1;
	//private String xueshi_item_1;
	
	public String getPraid() {
		return praid;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("提交预算表"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
//		this.getGridConfig().addColumn(this.getText("培训项目id"), "peProApplyno.id");
		this.getGridConfig().addColumn(this.getText("培训项目名称"), "applynoName");
		this.getGridConfig().addColumn(this.getText("培训项目编号"), "applynoCode");
		this.getGridConfig().addColumn(this.getText("所属年度"), "applynoYear");
		this.getGridConfig().addColumn(this.getText("项目类型"), "combobox_EnumConstByFkProgramType.programType");
//		this.getGridConfig().addColumn(this.getText("预算表操作"), "peProApplyno.replyBook",false,false,false,"File",false,50);
		this.getGridConfig().addRenderScript("预算表操作","{return '<a href=/entity/implementation/budgetSubmit_toDo.action?praid='+record.data['id']+' target=\\'_blank\\'>提交预算</a>';}", "");
		this.getGridConfig().addColumn(this.getText("申报时限"), "applynoDeadLine",false,false,true,"Date",false,20);
		this.getGridConfig().addColumn(this.getText("人均经费标准"), "feeStandard",false,true,true,Const.fee_for_extjs);
//		this.getGridConfig().addColumn(this.getText("申报书状态"), "peProApplyno.enumConstByFkProgramStatus.name",false,false,true,"",false,50);
//		this.getGridConfig().addColumn(this.getText("是否需要省级审核"), "peProApplyno.enumConstByFkProvinceCheck.name",false,false,true,"",false,50);
		this.getGridConfig().addColumn(this.getText("申报学科上限"), "applynoLimit",false,false,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("最后更新时间"), "inputDate",false,false,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("备注"), "applynoNote",false,false,true,"",true,200);
	}

	@Override
	public void setEntityClass() {

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/implementation/budgetSubmit";
	}
	@Override
	public Page list() {
		StringBuffer sql_a = new StringBuffer();
		this.setPeUnit(loadPeUnit());
		sql_a.append("SELECT DISTINCT APPLYNO.ID           AS ID,                                     ");
		sql_a.append("       APPLYNO.NAME         AS APPLYNONAME,                            ");
		sql_a.append("       APPLYNO.CODE         AS APPLYNOCODE,                            ");
		sql_a.append("       APPLYNO.YEAR         AS APPLYNOYEAR,                            ");
		sql_a.append("       EC1.NAME             AS PROGRAMTYPE,                            ");
		sql_a.append("       APPLYNO.DEADLINE     APPLYNODEADLINE,                           ");
		sql_a.append("       APPLYNO.FEE_STANDARD FEESTANDARD,                               ");
		sql_a.append("       APPLYNO.LIMIT        APPLYNOLIMIT,                              ");
		sql_a.append("		 BUGET.input_date	inputDate,");
		sql_a.append("       TO_CHAR(APPLYNO.NOTE)         AS APPLYNONOTE                             ");
		sql_a.append("  FROM PE_PRO_APPLY APPLY                                              ");
		sql_a.append(" INNER JOIN PE_PRO_APPLYNO APPLYNO ON APPLY.FK_APPLYNO = APPLYNO.ID    ");
		sql_a.append(" LEFT OUTER JOIN PE_FEE_BUDGET BUGET ON BUGET.FK_PRO_APPLYNO = APPLYNO.ID");
		sql_a.append(" INNER JOIN ENUM_CONST EC1 ON APPLYNO.FK_PROGRAM_TYPE = EC1.ID         ");
		sql_a.append(" INNER JOIN ENUM_CONST EC2 ON APPLY.FK_CHECK_FINAL = EC2.ID            ");
		sql_a.append(" WHERE /* APPLYNO.YEAR = '"+Const.getYear()+"'  */ EC2.CODE='1001'      ");
		
		if(this.getPeUnit().getEnumConstByFkUnitType().getCode().equals("1526")){
			sql_a.append("   AND    APPLY.FK_UNIT = '"+this.getPeUnit().getId()+"'              ");
//			sql_a.append("   AND	BUGET.FK_UNIT = '"+this.getPeUnit().getId()+"'			   ");
		}
		
//		sql_a.append("   AND EC2.CODE='1001'    ");
		return this.iniSqllist(sql_a);
	}
	
	
	protected void initSubjectApplyno(){
		this.setPeUnit(loadPeUnit());
		PeProApplyno peProApplyno_ = (PeProApplyno)this.getMyListService().getById(PeProApplyno.class, this.getPraid());
		this.setPeProApplyno(peProApplyno_);
//		String sql = "SELECT S.ID,S.NAME FROM PE_PRO_APPLY T INNER JOIN PE_SUBJECT S ON T.FK_SUBJECT=S.ID " +
//				" INNER JOIN ENUM_CONST EC ON T.FK_CHECK_FINAL = EC.ID WHERE T.FK_UNIT ='"+this.getPeUnit().getId()+
//				"' AND  T.FK_APPLYNO='"+peProApplyno_.getId()+"' AND EC.CODE = '1001'" ;
		StringBuffer sql = new StringBuffer();
		sql.append("select sub.id, sub.name, count(stu.id)						");
		sql.append("  from pe_trainee stu                                       ");
		sql.append("  full outer join pe_pro_apply program                      ");
		sql.append("    on stu.fk_pro_applyno = program.fk_applyno              ");
		sql.append("   and stu.fk_subject = program.fk_subject                  ");
		sql.append("   and stu.fk_training_unit = program.fk_unit               ");
		sql.append(" inner join pe_pro_applyno appno                            ");
		sql.append("    on appno.id = program.fk_applyno                        ");
		sql.append(" inner join pe_subject sub                                  ");
		sql.append("    on program.fk_subject = sub.id                          ");
		sql.append("  left outer join enum_const enu                            ");
		sql.append("    on stu.fk_status_training = enu.id                      ");
		sql.append(" inner join enum_const ec                                   ");
		sql.append("    on program.fk_check_final = ec.id                       ");
		sql.append(" where ec.code = '1001'                                     ");
		sql.append("   and (enu.code is null or enu.code in ('001', '003'))     ");
		sql.append("   and program.fk_unit = '"+this.getPeUnit().getId()+"' ");
		sql.append("   and appno.id = '"+peProApplyno_.getId()+"'        ");
		sql.append(" group by sub.id, sub.name                                  ");
		List subjectList = null;
		try {
			subjectList = this.getProgramImpletationService().getBySQL(sql.toString());
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.setSubjectList(subjectList);
		
	}
	public String toDo(){
		initSubjectApplyno();
		return "yusuan";
	}
	public String toSave(){
		initSubjectApplyno();
		PeFeeBudget peFeeBudget = this.getPeFeeBudget();
		PeFeeBudgetDetail peFeeBudgetDetail = new PeFeeBudgetDetail();
		
		//更新
		if(this.getPbid()!=null&&!this.getPbid().equals("")){
			peFeeBudget = (PeFeeBudget)this.getMyListService().getById(PeFeeBudget.class, this.getPbid());
			peFeeBudget = (PeFeeBudget)super.setSubIds(peFeeBudget, this.getPeFeeBudget());
			peFeeBudgetDetail = peFeeBudget.getPeFeeBudgetDetail();
		}else{
			//PeProApplyno applyno = (PeProApplyno)this.getMyListService().getById(PeProApplyno.class, this.getPraid())
			DetachedCriteria dc = DetachedCriteria.forClass(PeFeeBudget.class);
			dc.createAlias("peUnit", "peUnit");
			dc.createAlias("peProApplyno", "peProApplyno");
			dc.add(Restrictions.eq("peUnit", this.getPeUnit()));
			dc.add(Restrictions.eq("peProApplyno", this.getPeProApplyno()));
			List feeBudgetList = null;
			try {
				feeBudgetList = this.getProgramImpletationService().getList(dc);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			if(feeBudgetList != null && !feeBudgetList.isEmpty()){
				peFeeBudget = (PeFeeBudget)feeBudgetList.get(0);
				peFeeBudget = (PeFeeBudget)super.setSubIds(peFeeBudget, this.getPeFeeBudget());
				peFeeBudgetDetail = peFeeBudget.getPeFeeBudgetDetail();
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
			}
			personNote += "总计：" +this.getTotal_person_1()+ "人、"+this.getClasses()+"个培训班。";
			peFeeBudget.setPersonCount(personNote);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			//用日期的 ”日“表示结束月  很变态的用法~
			date = sdf.parse(this.getTrainingYear()+"-"+this.getTrainingMonth()+"-"+this.getTrainingEndMonth());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		peFeeBudget.setTrainingDate(date);
		peFeeBudget.setInputDate(new Date());
		
/*		peFeeBudgetDetail.setFeeSurvey(this.getOther_item_1());
		peFeeBudgetDetail.setFeeResearch(this.getOther_item_2());
		peFeeBudgetDetail.setFeeArgument(this.getOther_item_3());
		peFeeBudgetDetail.setFeeTrafficStu(this.getOther_item_4());
		peFeeBudgetDetail.setFeeCourse(this.getOther_item_5());
		peFeeBudgetDetail.setFeeElectronCourse(this.getOther_item_6());
		peFeeBudgetDetail.setFeeAppraise(this.getOther_item_7());
		peFeeBudgetDetail.setFeeSummaryAppraise(this.getOther_item_8());
		peFeeBudgetDetail.setFeePublicity(this.getOther_item_9());
		peFeeBudgetDetail.setFeePetty(this.getOther_item_10());
		peFeeBudgetDetail.setFeeNoplan(this.getOther_item_11());
		
		peFeeBudgetDetail.setNoteSurvey(this.getNote_item_1());
		peFeeBudgetDetail.setNoteResearch(this.getNote_item_2());
		peFeeBudgetDetail.setNoteArgument(this.getNote_item_3());
		peFeeBudgetDetail.setNoteTrafficStu(this.getNote_item_4());
//		peFeeBudgetDetail.setNoteTextCourse(this.getNote_item_5());
//		peFeeBudgetDetail.setNoteElectronCourse(this.getNote_item_6());
		peFeeBudgetDetail.setNoteTextCourse(this.getWenben_item_1()+"+"+this.getWenben_item_2());
		peFeeBudgetDetail.setNoteElectronCourse(this.getDianzi_item_1()+"+"+this.getDianzi_item_2());
		peFeeBudgetDetail.setNoteAppraise(this.getNote_item_7());
		peFeeBudgetDetail.setNoteSummaryAppraise(this.getNote_item_8());
		peFeeBudgetDetail.setNotePublicity(this.getNote_item_9());
		peFeeBudgetDetail.setNotePetty(this.getNote_item_10());
//		peFeeBudgetDetail.setNoteNoplan(this.getNote_item_11());
		peFeeBudgetDetail.setNoteNoplan(this.getXueshi_item_1());*/
		
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
		
		peFeeBudget.setPeFeeBudgetDetail(peFeeBudgetDetail);
		try {
			//this.getProgramImpletationService().save(peFeeBudgetDetail);
			this.getProgramImpletationService().save(peFeeBudget);
			this.setMsg("保存成功");
			
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg("保存失败");
		}
		this.setTogo("back");
		return "msg";
	}
	
	public PeUnit loadPeUnit(){
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		List peManagerList = null;
		try {
			peManagerList = this.getProgramImpletationService().getByHQL("from PeManager p where p.ssoUser = '"+userSession.getSsoUser().getId()+"'");
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PeManager manager = (PeManager)peManagerList.get(0);
		return manager.getPeUnit();
	}

	
	public void setPraid(String praid) {
		this.praid = praid;
	}

	public Double getTotal_item_1() {
		return total_item_1;
	}

	public void setTotal_item_1(Double total_item_1) {
		this.total_item_1 = total_item_1;
	}

	public Double getTotal_item_2() {
		return total_item_2;
	}

	public void setTotal_item_2(Double total_item_2) {
		this.total_item_2 = total_item_2;
	}

	public Double getTotal_item_3() {
		return total_item_3;
	}

	public void setTotal_item_3(Double total_item_3) {
		this.total_item_3 = total_item_3;
	}

	public Double getTotal_item_4() {
		return total_item_4;
	}

	public void setTotal_item_4(Double total_item_4) {
		this.total_item_4 = total_item_4;
	}

	public Double getTotal_item_5() {
		return total_item_5;
	}

	public void setTotal_item_5(Double total_item_5) {
		this.total_item_5 = total_item_5;
	}

	public Double getTotal_item_6() {
		return total_item_6;
	}

	public void setTotal_item_6(Double total_item_6) {
		this.total_item_6 = total_item_6;
	}

	public Double getTotal_item_7() {
		return total_item_7;
	}

	public void setTotal_item_7(Double total_item_7) {
		this.total_item_7 = total_item_7;
	}

	public Double getTotal_item_8() {
		return total_item_8;
	}

	public void setTotal_item_8(Double total_item_8) {
		this.total_item_8 = total_item_8;
	}

	public Double getTotal_item_9() {
		return total_item_9;
	}

	public void setTotal_item_9(Double total_item_9) {
		this.total_item_9 = total_item_9;
	}

	public String getPrice_item_1() {
		return price_item_1;
	}

	public void setPrice_item_1(String price_item_1) {
		this.price_item_1 = price_item_1;
	}

	public String getPrice_item_2() {
		return price_item_2;
	}

	public void setPrice_item_2(String price_item_2) {
		this.price_item_2 = price_item_2;
	}

	public String getPrice_item_3() {
		return price_item_3;
	}

	public void setPrice_item_3(String price_item_3) {
		this.price_item_3 = price_item_3;
	}

	public String getPrice_item_4() {
		return price_item_4;
	}

	public void setPrice_item_4(String price_item_4) {
		this.price_item_4 = price_item_4;
	}

	public String getPrice_item_5() {
		return price_item_5;
	}

	public void setPrice_item_5(String price_item_5) {
		this.price_item_5 = price_item_5;
	}

	public String getPrice_item_6() {
		return price_item_6;
	}

	public void setPrice_item_6(String price_item_6) {
		this.price_item_6 = price_item_6;
	}

	public String getPrice_item_7() {
		return price_item_7;
	}

	public void setPrice_item_7(String price_item_7) {
		this.price_item_7 = price_item_7;
	}

	public String getPrice_item_8() {
		return price_item_8;
	}

	public void setPrice_item_8(String price_item_8) {
		this.price_item_8 = price_item_8;
	}

	public String getPrice_item_9() {
		return price_item_9;
	}

	public void setPrice_item_9(String price_item_9) {
		this.price_item_9 = price_item_9;
	}

	public String getPrice_item_10() {
		return price_item_10;
	}

	public void setPrice_item_10(String price_item_10) {
		this.price_item_10 = price_item_10;
	}

	public String getQty_item_1() {
		return qty_item_1;
	}

	public void setQty_item_1(String qty_item_1) {
		this.qty_item_1 = qty_item_1;
	}

	public String getQty_item_2() {
		return qty_item_2;
	}

	public void setQty_item_2(String qty_item_2) {
		this.qty_item_2 = qty_item_2;
	}

	public String getQty_item_3() {
		return qty_item_3;
	}

	public void setQty_item_3(String qty_item_3) {
		this.qty_item_3 = qty_item_3;
	}

	public String getQty_item_4() {
		return qty_item_4;
	}

	public void setQty_item_4(String qty_item_4) {
		this.qty_item_4 = qty_item_4;
	}

	public String getQty_item_5() {
		return qty_item_5;
	}

	public void setQty_item_5(String qty_item_5) {
		this.qty_item_5 = qty_item_5;
	}

	public String getQty_item_6() {
		return qty_item_6;
	}

	public void setQty_item_6(String qty_item_6) {
		this.qty_item_6 = qty_item_6;
	}

	public String getQty_item_7() {
		return qty_item_7;
	}

	public void setQty_item_7(String qty_item_7) {
		this.qty_item_7 = qty_item_7;
	}

	public String getQty_item_8() {
		return qty_item_8;
	}

	public void setQty_item_8(String qty_item_8) {
		this.qty_item_8 = qty_item_8;
	}

	public String getQty_item_9() {
		return qty_item_9;
	}

	public void setQty_item_9(String qty_item_9) {
		this.qty_item_9 = qty_item_9;
	}

	public String getQty_item_10() {
		return qty_item_10;
	}

	public void setQty_item_10(String qty_item_10) {
		this.qty_item_10 = qty_item_10;
	}

	public String getTime_item_1() {
		return time_item_1;
	}

	public void setTime_item_1(String time_item_1) {
		this.time_item_1 = time_item_1;
	}

	public String getTime_item_2() {
		return time_item_2;
	}

	public void setTime_item_2(String time_item_2) {
		this.time_item_2 = time_item_2;
	}

	public String getTime_item_3() {
		return time_item_3;
	}

	public void setTime_item_3(String time_item_3) {
		this.time_item_3 = time_item_3;
	}

	public String getTime_item_4() {
		return time_item_4;
	}

	public void setTime_item_4(String time_item_4) {
		this.time_item_4 = time_item_4;
	}

	public String getTime_item_5() {
		return time_item_5;
	}

	public void setTime_item_5(String time_item_5) {
		this.time_item_5 = time_item_5;
	}

	public String getTime_item_6() {
		return time_item_6;
	}

	public void setTime_item_6(String time_item_6) {
		this.time_item_6 = time_item_6;
	}

	public String getTime_item_7() {
		return time_item_7;
	}

	public void setTime_item_7(String time_item_7) {
		this.time_item_7 = time_item_7;
	}

	public String getTime_item_8() {
		return time_item_8;
	}

	public void setTime_item_8(String time_item_8) {
		this.time_item_8 = time_item_8;
	}

	public String getTime_item_9() {
		return time_item_9;
	}

	public void setTime_item_9(String time_item_9) {
		this.time_item_9 = time_item_9;
	}

	public String getTime_item_10() {
		return time_item_10;
	}

	public void setTime_item_10(String time_item_10) {
		this.time_item_10 = time_item_10;
	}

	public String getPrice_item_11() {
		return price_item_11;
	}

	public void setPrice_item_11(String price_item_11) {
		this.price_item_11 = price_item_11;
	}

	public String getPrice_item_12() {
		return price_item_12;
	}

	public void setPrice_item_12(String price_item_12) {
		this.price_item_12 = price_item_12;
	}

	public String getPrice_item_13() {
		return price_item_13;
	}

	public void setPrice_item_13(String price_item_13) {
		this.price_item_13 = price_item_13;
	}

	public String getPrice_item_14() {
		return price_item_14;
	}

	public void setPrice_item_14(String price_item_14) {
		this.price_item_14 = price_item_14;
	}

	public String getQty_item_11() {
		return qty_item_11;
	}

	public void setQty_item_11(String qty_item_11) {
		this.qty_item_11 = qty_item_11;
	}

	public String getQty_item_12() {
		return qty_item_12;
	}

	public void setQty_item_12(String qty_item_12) {
		this.qty_item_12 = qty_item_12;
	}

	public String getQty_item_13() {
		return qty_item_13;
	}

	public void setQty_item_13(String qty_item_13) {
		this.qty_item_13 = qty_item_13;
	}

	public String getQty_item_14() {
		return qty_item_14;
	}

	public void setQty_item_14(String qty_item_14) {
		this.qty_item_14 = qty_item_14;
	}

	public String getTime_item_11() {
		return time_item_11;
	}

	public void setTime_item_11(String time_item_11) {
		this.time_item_11 = time_item_11;
	}

	public String getTime_item_12() {
		return time_item_12;
	}

	public void setTime_item_12(String time_item_12) {
		this.time_item_12 = time_item_12;
	}

	public String getTime_item_13() {
		return time_item_13;
	}

	public void setTime_item_13(String time_item_13) {
		this.time_item_13 = time_item_13;
	}

	public String getTime_item_14() {
		return time_item_14;
	}

	public void setTime_item_14(String time_item_14) {
		this.time_item_14 = time_item_14;
	}

	public String getPbid() {
		return pbid;
	}

	public void setPbid(String pbid) {
		this.pbid = pbid;
	}

	public PeProApplyno getPeProApplyno() {
		return peProApplyno;
	}

	public void setPeProApplyno(PeProApplyno peProApplyno) {
		this.peProApplyno = peProApplyno;
	}

	public List getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List subjectList) {
		this.subjectList = subjectList;
	}

	public String getTotal_person_1() {
		return total_person_1;
	}

	public void setTotal_person_1(String total_person_1) {
		this.total_person_1 = total_person_1;
	}

	public String getTrainingYear() {
		return trainingYear;
	}

	public void setTrainingYear(String trainingYear) {
		this.trainingYear = trainingYear;
	}

	public String getTrainingMonth() {
		return trainingMonth;
	}

	public void setTrainingMonth(String trainingMonth) {
		this.trainingMonth = trainingMonth;
	}

	
	public ProgramImpletationService getProgramImpletationService() {
		return programImpletationService;
	}

	
	public void setProgramImpletationService(ProgramImpletationService programImpletationService) {
		this.programImpletationService = programImpletationService;
	}

	public PeUnit getPeUnit() {
		return peUnit;
	}

	public void setPeUnit(PeUnit peUnit) {
		this.peUnit = peUnit;
	}

	public PeFeeBudget getPeFeeBudget() {
		return peFeeBudget;
	}

	public void setPeFeeBudget(PeFeeBudget peFeeBudget) {
		this.peFeeBudget = peFeeBudget;
	}

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public String getTrainingEndMonth() {
		return trainingEndMonth;
	}

	public void setTrainingEndMonth(String trainingEndMonth) {
		this.trainingEndMonth = trainingEndMonth;
	}

	public Double getTotal_item_10() {
		return total_item_10;
	}

	public void setTotal_item_10(Double total_item_10) {
		this.total_item_10 = total_item_10;
	}

	public Double getTotal_item_11() {
		return total_item_11;
	}

	public void setTotal_item_11(Double total_item_11) {
		this.total_item_11 = total_item_11;
	}

	public Double getTotal_item_12() {
		return total_item_12;
	}

	public void setTotal_item_12(Double total_item_12) {
		this.total_item_12 = total_item_12;
	}

	public Double getTotal_item_13() {
		return total_item_13;
	}

	public void setTotal_item_13(Double total_item_13) {
		this.total_item_13 = total_item_13;
	}

	public Double getTotal_item_14() {
		return total_item_14;
	}

	public void setTotal_item_14(Double total_item_14) {
		this.total_item_14 = total_item_14;
	}

/*	public String getXueshi_item_1() {
		return xueshi_item_1;
	}

	public void setXueshi_item_1(String xueshi_item_1) {
		this.xueshi_item_1 = xueshi_item_1;
	}*/
}
