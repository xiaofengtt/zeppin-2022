package com.whaty.platform.entity.web.action.implementation;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeFeeActualBudget;
import com.whaty.platform.entity.bean.PeFeeActualBudgetDetail;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeProApplyno;
import com.whaty.platform.entity.bean.PeUnit;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class BudgetJSManageAction extends MyBaseAction {
	private PeFeeActualBudget peFeeActualBudget;
	private PeProApplyno peProApplyno;
	private String trainingYear;
	private String trainingMonth;
	private String trainingEndMonth;
	private String pbid;
	private Double total;
	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getPbid() {
		return pbid;
	}

	public void setPbid(String pbid) {
		this.pbid = pbid;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("查看决算--<font color='ff0000'>（pdf打印需要pdf阅读器的支持）</font>"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("培训项目名称"), "peProApplyno.name");
		this.getGridConfig().addColumn(this.getText("培训项目编号"), "peProApplyno.code");
		this.getGridConfig().addColumn(this.getText("承办单位"), "peUnit.name");
		this.getGridConfig().addColumn(this.getText("所属年度"), "peProApplyno.year");
		this.getGridConfig().addColumn(this.getText("项目类型"), "peProApplyno.enumConstByFkProgramType.name");
		this.getGridConfig().addColumn(this.getText("预算表操作"), "peProApplyno.replyBook",false,false,false,"File",false,50);
		this.getGridConfig().addRenderScript("详情","{return '<a href=/entity/implementation/budgetJSManage_toDetail.action?pbid='+record.data['id']+' target=\\'_blank\\'>查看详情</a>';}", "");
		
		this.getGridConfig().addColumn(this.getText("申报时限"), "peProApplyno.deadline",false,false,true,"Date",false,20);
		this.getGridConfig().addColumn(this.getText("人均经费标准"), "peProApplyno.feeStandard",false,true,true,Const.fee_for_extjs);
//		this.getGridConfig().addColumn(this.getText("申报书状态"), "peProApplyno.enumConstByFkProgramStatus.name",false,false,true,"",false,50);
//		this.getGridConfig().addColumn(this.getText("是否需要省级审核"), "peProApplyno.enumConstByFkProvinceCheck.name",false,false,true,"",false,50);
		this.getGridConfig().addColumn(this.getText("申报学科上限"), "peProApplyno.limit",false,false,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("最后更新时间"), "inputDate",false,false,true,"",true,200);
		this.getGridConfig().addColumn(this.getText("备注"), "peProApplyno.note",false,false,true,"",true,200);
		this.getGridConfig().addMenuScript(this.getText("PDF打印"), this.getPrintBudgetScript());
		if(this.getPeUnit().getEnumConstByFkUnitType().getCode().equals("1526")){
			this.getGridConfig().addRenderScript("操作","{return '<a href=/entity/implementation/budgetJSManage_toEdit.action?pbid='+record.data['id']+' target=\\'_blank\\'>修改编辑</a>';}", "");
		}
	}

	@Override
	public void setEntityClass() {
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/implementation/budgetJSManage";
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeFeeActualBudget.class);
		DetachedCriteria dcApplyno = dc.createCriteria("peProApplyno","peProApplyno");
		//夏老师提出暂时去掉年度的限制
//		dc.add(Restrictions.eq("peProApplyno.year", Const.getYear()));
//		dcApplyno.createAlias("enumConstByFkProvinceCheck", "enumConstByFkProvinceCheck");
//		dcApplyno.createAlias("enumConstByFkProgramStatus", "enumConstByFkProgramStatus")
//			.add(Restrictions.eq("enumConstByFkProgramStatus.code", "1"));
		dcApplyno.createAlias("enumConstByFkProgramType", "enumConstByFkProgramType");
		DetachedCriteria dcPeUnit = dc.createCriteria("peUnit", "peUnit");
		if( this.getPeUnit().getEnumConstByFkUnitType().getCode().equals("1526")){
			dcPeUnit.add(Restrictions.eq("id", this.getPeUnit().getId()));
		}
		return dc;
	}
	public PeUnit getPeUnit(){
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		try {
			List peManagerList = this.getGeneralService().getByHQL("from PeManager p where p.ssoUser = '"+userSession.getSsoUser().getId()+"'");
			PeManager manager = (PeManager)peManagerList.get(0);
			return manager.getPeUnit();
		} catch (EntityException e) {
			e.printStackTrace();
			return null;
		}
	}
	public String toDetail(){
		PeFeeActualBudget peFB_ = (PeFeeActualBudget)this.getMyListService().getById(PeFeeActualBudget.class, this.getPbid());
		PeFeeActualBudgetDetail  peFB = peFB_.getPeFeeActualBudgetDetail();
		this.setPeFeeActualBudget(peFB_);
/*		this.setTotal(peFB.getFeeAccommodation()+peFB.getFeeAppraise()+peFB.getFeeAreaRent()+peFB.getFeeArgument()+peFB.getFeeCourse()
				+peFB.getFeeElectronCourse()+peFB.getFeeEquipmentRent()+peFB.getFeeLabourService()+peFB.getFeeMaterials()+
				peFB.getFeeMeal()+peFB.getFeeMealAccExpert()+peFB.getFeeOther()+peFB.getFeePetty()+peFB.getFeePublicity()+peFB.getFeeResearch()
				+peFB.getFeeSummaryAppraise()+peFB.getFeeSurvey()+peFB.getFeeTeach()+peFB.getFeeTrafficExpert()+peFB.getFeeTrafficStu()
				);
		return "juesuan_print";*/
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int year = Integer.parseInt(sdf.format(peFB_.getTrainingDate()).split("-")[0]);
		if(year < 2013){
			
			  this.setTotal(peFB.getFeeSurvey()+peFB.getFeeResearch()+peFB.getFeeArgument()+peFB.getFeeTrafficStu()+peFB.getFeeCourse()+peFB.getFeeElectronCourse()+peFB.getFeeAppraise()+peFB.getFeeSummaryAppraise()+peFB.getFeePublicity()+peFB.getFeePetty()+peFB.getFeeMeal()+peFB.getFeeAccommodation()+peFB.getFeeTeach()+peFB.getFeeTrafficExpert()+peFB.getFeeMealAccExpert()+peFB.getFeeMaterials()+peFB.getFeeAreaRent()+peFB.getFeeEquipmentRent()+peFB.getFeeLabourService()
					  );
			return "juesuan_print_2012";
		}else{
			this.setTotal(peFB.getFeeMeal()+peFB.getFeeAccommodation()+peFB.getFeeTeach()+peFB.getFeeTrafficExpert()+peFB.getFeeMealAccExpert()
					+peFB.getFeeMaterials()+peFB.getFeeAreaRent()+peFB.getFeeEquipmentRent()+peFB.getFeeLabourService()+
					peFB.getFeeSurvey()+peFB.getFeeResearch()+peFB.getFeeArgument()+peFB.getFeeTrafficStu()+peFB.getFeeElectronCourse()
					);
			return "juesuan_print";
		}
	}
	public String toEdit(){
		this.setPeFeeActualBudget((PeFeeActualBudget)this.getMyListService().getById(PeFeeActualBudget.class, this.getPbid()));
		this.setPeProApplyno(this.getPeFeeActualBudget().getPeProApplyno());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.setTrainingYear(sdf.format(this.getPeFeeActualBudget().getTrainingDate()).split("-")[0]);
		this.setTrainingMonth(sdf.format(this.getPeFeeActualBudget().getTrainingDate()).split("-")[1]);
		this.setTrainingEndMonth(sdf.format(this.getPeFeeActualBudget().getTrainingDate()).split("-")[2]);
		
		int year = Integer.parseInt(sdf.format(this.getPeFeeActualBudget().getTrainingDate()).split("-")[0]);
		if(year < 2013){
			return "juesuan2012";
		}else{
			return "juesuan";
		}
	}
	public String getPrintBudgetScript(){
		StringBuffer script = new StringBuffer();
		script.append("{var m = grid.getSelections();  ");
		script.append("if(m.length >= 1){	         ");
		script.append("	var jsonData = '';       ");
		script.append("	for(var i = 0, len = m.length; i < len; i++){");
		script.append("		var ss =  m[i].get('id');");
		script.append("		if(i==0)	jsonData = jsonData + ss ;");
		script.append("		else	jsonData = jsonData + ',' + ss;");
		script.append("	}                        ");
		script.append("	document.getElementById('user-defined-content').style.display='none'; ");
		script.append("	document.getElementById('user-defined-content').innerHTML=\"<form target='_blank' action='"+this.servletPath+"_printBudgetPdf.action' method='post' name='formx1' style='display:none'>" );
		script.append("   <input name=ids type=hidden value='\"+jsonData+\"' />");
		script.append("   </form>\";");
		script.append("	document.formx1.submit();");
		script.append("	document.getElementById('user-defined-content').innerHTML=\"\";  ");
		script.append(" } else {                    ");
		script.append("	Ext.MessageBox.alert('" + this.getText("test.error") + "', '请先选择一个项目!');  "  );                
		script.append("}}                         ");
		return script.toString();
	}
	public String printBudgetPdf(){
		DetachedCriteria dc = DetachedCriteria.forClass(PeFeeActualBudget.class);
		dc.add(Restrictions.in("id", this.getIds().split(",")));
		List peFeeBudgetList = null;
		try {
			peFeeBudgetList =  this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.setFilePath(printSign(peFeeBudgetList));
		return "showprint";
	}
	
	private String printSign(List budgetBook) {
		int page = budgetBook.size();
		String returnValue = null ;
		try {
 			/** 主要控制总共的页数*/
			
			String path = ServletActionContext.getRequest().getRealPath("/") ;
			String TemplatePDF; // = path+"/entity/manager/fee/stat/feeActualBudgetBook.pdf";//设置模板路径
			UserSession us =(UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
			String userId = us.getId();
			returnValue = "/export/"+userId+(new Date()).getTime()+".pdf" ;
			FileOutputStream fos = new FileOutputStream(ServletActionContext.getRequest().getRealPath("/")+returnValue);//需要生成PDF
			
			ByteArrayOutputStream baos[] = new ByteArrayOutputStream[page];//用于存储每页生成PDF流
			BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);

			SimpleDateFormat sdf_n = new SimpleDateFormat("yyyy-MM-dd");
			/** 向PDF模板中插入数据 */
			for (int item = 0; item < page; item++) {
				PeFeeActualBudget budget = (PeFeeActualBudget)budgetBook.get(item);				
				
				int year = Integer.parseInt(sdf_n.format(budget.getTrainingDate()).split("-")[0]);
				if(year < 2013){
						TemplatePDF = path+"/entity/manager/fee/stat/feeActualBudgetBook.pdf";
				} else {
					 TemplatePDF = path+"/entity/manager/fee/stat/feeActualBudgetBook_2013.pdf";
				}
				
				baos[item] = new ByteArrayOutputStream();
				PdfReader reader = new PdfReader(TemplatePDF);
				PdfStamper stamp = new PdfStamper(reader, baos[item]);
				AcroFields form = stamp.getAcroFields();
				form.addSubstitutionFont(bfChinese);

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy 年 MM 月 至 dd 月");
				String traingDate = sdf.format(budget.getTrainingDate());
				form.setField("projectName", budget.getPeProApplyno().getName()+"（"+budget.getPersonCount()+"）");
				form.setField("trainingDate", traingDate);
				form.setField("trainingSpace", budget.getTrainingSpace());
				form.setField("unitName", budget.getUnitName()+"（本单位财务专用章）");
				PeFeeActualBudgetDetail budgetDetal = budget.getPeFeeActualBudgetDetail();
				if(year < 2013){
					form.setField("fee1", budgetDetal.getFeeSurvey().toString());
					form.setField("fee2", budgetDetal.getFeeResearch().toString());
					form.setField("fee3", budgetDetal.getFeeArgument().toString());
					form.setField("fee4", budgetDetal.getFeeMeal().toString());
					form.setField("fee5", budgetDetal.getFeeAccommodation().toString());
					form.setField("fee6", budgetDetal.getFeeTrafficStu().toString());
					form.setField("fee7", budgetDetal.getFeeTeach().toString());
					form.setField("fee8", budgetDetal.getFeeTrafficExpert().toString());
					form.setField("fee9", budgetDetal.getFeeMealAccExpert().toString());
					form.setField("fee10", budgetDetal.getFeeMaterials().toString());
					form.setField("fee11", budgetDetal.getFeeCourse().toString());
					form.setField("fee12", budgetDetal.getFeeElectronCourse().toString());
					form.setField("fee13", budgetDetal.getFeeAreaRent().toString());
					form.setField("fee14", budgetDetal.getFeeEquipmentRent().toString());
					form.setField("fee15", budgetDetal.getFeeAppraise().toString());
					form.setField("fee16", budgetDetal.getFeeSummaryAppraise().toString());
					form.setField("fee17", budgetDetal.getFeeLabourService().toString());
					form.setField("fee18", budgetDetal.getFeePublicity().toString());
					form.setField("fee19", budgetDetal.getFeePetty().toString());
	//				form.setField("fee20", budgetDetal.getFeeNoplan().toString());
					
					form.setField("note1", budgetDetal.getNoteSurvey());
					form.setField("note2", budgetDetal.getNoteResearch());
					form.setField("note3", budgetDetal.getNoteArgument());
					String temp[] = budgetDetal.getNoteMeal().split("\\*");
					form.setField("note4", temp[0]+"元/人/天×"+temp[1]+"人×"+temp[2]+"天");
					temp = budgetDetal.getNoteAccommodation().split("\\*");
					form.setField("note5", temp[0]+"元/人/天×"+temp[1]+"人×"+temp[2]+"天");
					form.setField("note6", budgetDetal.getNoteTrafficStu());
					temp = budgetDetal.getNoteTeach().split("\\*");
					form.setField("note7_1", "本地专家:"+ temp[0] +"元/人次×"+ temp[1] +"人次");
					form.setField("note7_2", "外地专家:"+ temp[2] +"元/人次×"+ temp[3] +"人次");
					form.setField("note7_3", "专家每人次平均授课学时数:"+ budgetDetal.getNoteOther() +"（1小时计1学时）");
					
					temp = budgetDetal.getNoteTrafficExpert().split("\\*");
					form.setField("note8", temp[0]+"元/人次×"+temp[1]+"人次");
					temp = budgetDetal.getNoteMealAccExpert().split("\\*");
					form.setField("note9", temp[0]+"元/人次/天×"+temp[1]+"人次×"+temp[2]+"天");
					temp = budgetDetal.getNoteMaterials().split("\\*");
					form.setField("note10", temp[0]+"元/人×"+temp[1]+"人");
					temp = budgetDetal.getNoteTextCourse().split("\\+");
					form.setField("note11", "设计开发费:"+temp[0]+"元;制作使用费:"+temp[1]+"元 ");
					temp = budgetDetal.getNoteElectronCourse().split("\\+");
					form.setField("note12", "设计开发费:"+temp[0]+"元;制作使用费:"+temp[1]+"元 ");
					
					temp = budgetDetal.getNoteAreaRent().split("\\*");
					form.setField("note13", temp[0]+"元/班次/天×"+temp[1]+"班次×"+temp[2]+"天");
					temp = budgetDetal.getNoteEquipmentRent().split("\\*");
					form.setField("note14", temp[0]+"元/班次/天×"+temp[1]+"班次×"+temp[2]+"天");
					form.setField("note15", budgetDetal.getNoteAppraise());
					form.setField("note16", budgetDetal.getNoteSummaryAppraise());
					temp = budgetDetal.getNoteLabourService().split("\\*");
					form.setField("note17", temp[0]+"元/人次/天×"+temp[1]+"人次×"+temp[2]+"天");
					form.setField("note18", budgetDetal.getNotePublicity());
					form.setField("note19", budgetDetal.getNotePetty());
					
					total = budgetDetal.getFeeAccommodation()+budgetDetal.getFeeAppraise()+budgetDetal.getFeeAreaRent()+budgetDetal.getFeeArgument()+budgetDetal.getFeeCourse()
					+budgetDetal.getFeeElectronCourse()+budgetDetal.getFeeEquipmentRent()+budgetDetal.getFeeLabourService()+budgetDetal.getFeeMaterials()+
					budgetDetal.getFeeMeal()+budgetDetal.getFeeMealAccExpert()/*+budgetDetal.getFeeNoplan()*/+budgetDetal.getFeePetty()+budgetDetal.getFeePublicity()+budgetDetal.getFeeResearch()
					+budgetDetal.getFeeSummaryAppraise()+budgetDetal.getFeeSurvey()+budgetDetal.getFeeTeach()+budgetDetal.getFeeTrafficExpert()+budgetDetal.getFeeTrafficStu();
				}else{
					form.setField("projectDirector", budget.getProjectDirector());
					
					form.setField("fee1", budgetDetal.getFeeMeal().toString());
					form.setField("fee2", budgetDetal.getFeeAccommodation().toString());
					form.setField("fee3", budgetDetal.getFeeTeach().toString());
					form.setField("fee4", budgetDetal.getFeeTrafficExpert().toString());
					form.setField("fee5", budgetDetal.getFeeMealAccExpert().toString());
					form.setField("fee6", budgetDetal.getFeeMaterials().toString());
					form.setField("fee7", budgetDetal.getFeeAreaRent().toString());
					form.setField("fee8", budgetDetal.getFeeEquipmentRent().toString());
					form.setField("fee9", budgetDetal.getFeeLabourService().toString());
					form.setField("fee10", budgetDetal.getFeeSurvey().toString());
					form.setField("fee11", budgetDetal.getFeeResearch().toString());
					form.setField("fee12", budgetDetal.getFeeArgument().toString());
					form.setField("fee13", budgetDetal.getFeeTrafficStu().toString());
					form.setField("fee14", budgetDetal.getFeeElectronCourse().toString());
					
					String temp[] = budgetDetal.getNoteMeal().split("\\*");
					form.setField("note1", temp[0]+"元/人/天×"+temp[1]+"人×"+temp[2]+"天");
					temp = budgetDetal.getNoteAccommodation().split("\\*");
					form.setField("note2", temp[0]+"元/人/天×"+temp[1]+"人×"+temp[2]+"天");
					temp = budgetDetal.getNoteTeach().split("\\*");
					form.setField("note3", temp[0]+"元/次×"+temp[1]+"次");
					temp = budgetDetal.getNoteTrafficExpert().split("\\*");
					form.setField("note4", temp[0]+"元/人次×"+temp[1]+"人次");
					temp = budgetDetal.getNoteMealAccExpert().split("\\*");
					form.setField("note5", temp[0]+"元/人次×"+temp[1]+"人次");
					temp = budgetDetal.getNoteMaterials().split("\\*");
					form.setField("note6", temp[0]+"元/人×"+temp[1]+"人");
					temp = budgetDetal.getNoteAreaRent().split("\\*");
					form.setField("note7", temp[0] +"元/人×"+ temp[1] +"人");
					temp = budgetDetal.getNoteEquipmentRent().split("\\*");
					form.setField("note8", temp[0]+"元/人×"+temp[1]+"人");
					temp = budgetDetal.getNoteLabourService().split("\\*");
					form.setField("note9", temp[0]+"元/人×"+temp[1]+"人");
					temp = budgetDetal.getNoteSurvey().split("\\*");
					form.setField("note10", temp[0]+"元/件×"+temp[1]+"件");
					temp = budgetDetal.getNoteResearch().split("\\*");
					form.setField("note11", temp[0]+"元/班次/天×"+temp[1]+"班次×" + temp[2] + "天");
					temp = budgetDetal.getNoteArgument().split("\\*");
					form.setField("note12", temp[0]+"元/人×"+temp[1]+"人");
					temp = budgetDetal.getNoteTrafficStu().split("\\*");
					form.setField("note13", temp[0]+"元/班次/天×"+temp[1]+"班次×"+temp[2]+"天");
					temp = budgetDetal.getNoteElectronCourse().split("\\*");
					form.setField("note14", temp[0]+"元/人×"+temp[1]+"人");
					
					total = budgetDetal.getFeeMeal()+budgetDetal.getFeeAccommodation()+budgetDetal.getFeeTeach()+budgetDetal.getFeeTrafficExpert()+budgetDetal.getFeeMealAccExpert()
							+budgetDetal.getFeeMaterials()+budgetDetal.getFeeAreaRent()+budgetDetal.getFeeEquipmentRent()+budgetDetal.getFeeLabourService()+
							budgetDetal.getFeeSurvey()+budgetDetal.getFeeResearch()+budgetDetal.getFeeArgument()+budgetDetal.getFeeTrafficStu()+budgetDetal.getFeeElectronCourse();
				}
				form.setField("total", total.toString());
				form.setField("payeeName", budget.getPayeeName());
				form.setField("openingBank", budget.getOpeningBank());
				form.setField("accountNumber", budget.getAccountNumber());
				form.setField("contactInfo", budget.getContactInfo());
				
				stamp.setFormFlattening(true); // 千万不漏了这句啊, */
				stamp.close();
			}
			if(page > 0){
				Document doc = new Document();
				PdfCopy pdfCopy = new PdfCopy(doc, fos);
				doc.open();
				/**取出之前保存的每页内容*/
				for (int i = 0; i < page; i++) {
					PdfImportedPage impPage = pdfCopy.getImportedPage(new PdfReader(baos[i]
							.toByteArray()), 1);
					pdfCopy.addPage(impPage);
				}
				doc.close();//当文件拷贝  记得关闭doc
			}else{
				this.setMsg("没有符合条件数据");
				this.setTogo("back");
				return "msg";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}
	public PeFeeActualBudget getPeFeeActualBudget() {
		return peFeeActualBudget;
	}

	public void setPeFeeActualBudget(PeFeeActualBudget peFeeActualBudget) {
		this.peFeeActualBudget = peFeeActualBudget;
	}

	public PeProApplyno getPeProApplyno() {
		return peProApplyno;
	}

	public void setPeProApplyno(PeProApplyno peProApplyno) {
		this.peProApplyno = peProApplyno;
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

	public String getTrainingEndMonth() {
		return trainingEndMonth;
	}

	public void setTrainingEndMonth(String trainingEndMonth) {
		this.trainingEndMonth = trainingEndMonth;
	}
}
